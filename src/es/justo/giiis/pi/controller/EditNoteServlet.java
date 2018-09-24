package es.justo.giiis.pi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.justo.giiis.pi.dao.JDBCLabelDAOImpl;
import es.justo.giiis.pi.dao.JDBCNoteDAOImpl;
import es.justo.giiis.pi.dao.JDBCUsersNotesDAOImpl;
import es.justo.giiis.pi.dao.JDBCVersionsNotesDAO;
import es.justo.giiis.pi.dao.LabelDAO;
import es.justo.giiis.pi.dao.NoteDAO;
import es.justo.giiis.pi.dao.UsersNotesDAO;
import es.justo.giiis.pi.dao.VersionsNotesDAO;
import es.justo.giiis.pi.model.Label;
import es.justo.giiis.pi.model.Note;
import es.justo.giiis.pi.model.User;
import es.justo.giiis.pi.model.UsersNotes;
import es.justo.giiis.pi.model.VersionsNotes;
import es.justo.giiis.pi.util.LabelSeparator;
import es.justo.giiis.pi.util.TimeUtil;

/**
 * Servlet implementation class NoteServlet
 */
@WebServlet(urlPatterns = "/notes/EditNoteServlet", name = "EditNoteServlet")
public class EditNoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
	private static final String TAG = "EditNoteServlet: ";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditNoteServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info(TAG + "EditNote By GET");

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		String idnstr = request.getParameter("idn");
		if (idnstr != null && idnstr.matches("[0-9]+")) {
			Connection connection = (Connection) getServletContext().getAttribute("dbConn");

			NoteDAO notedao = new JDBCNoteDAOImpl();
			notedao.setConnection(connection);

			UsersNotesDAO usersnotesdao = new JDBCUsersNotesDAOImpl();
			usersnotesdao.setConnection(connection);

			LabelDAO labeldao = new JDBCLabelDAOImpl();
			labeldao.setConnection(connection);

			Integer idn = Integer.parseInt(idnstr);
			UsersNotes usernote = usersnotesdao.get(user.getIdu(), idn);
			if (usernote != null) {
				Note note = (Note) notedao.get(usernote.getIdn());
				request.setAttribute("note", note);
				int color = usernote.getColor();
				request.setAttribute("color", color);
				List<Label> labels = labeldao.getAllByIdn(note.getIdn());
				request.setAttribute("labels", labels);
			} else
				logger.info(TAG + "User (" + user.getUsername() + ") try to get a foreign note");
		}
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/jsp/edit-your-note.jsp");
		view.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info(TAG + "EditNote By POST");
		List<String> errors = new ArrayList<String>();

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		Note note = new Note();
		String newnotestr = request.getParameter("newnote");

		Boolean newnote = null;
		if (newnotestr.equals("true") || newnotestr.equals("false"))
			newnote = newnotestr.equals("true");
		else
			errors.add("Parameter newnote must be true or false.");

		String idnstr = request.getParameter("idn");
		if (idnstr != null && idnstr.matches("[0-9]+"))
			note.setIdn(Integer.parseInt(idnstr));
		note.setTitle(request.getParameter("title-note"));
		note.setContent(request.getParameter("description"));

		String urlimage = request.getParameter("url-image");
		if (urlimage != null && !urlimage.equals(""))
			note.setUrlimage(urlimage);
		else
			note.setUrlimage(request.getContextPath() + "/images/Logo.png");

		Connection connection = (Connection) getServletContext().getAttribute("dbConn");
		UsersNotesDAO usersnotesdao = new JDBCUsersNotesDAOImpl();
		usersnotesdao.setConnection(connection);

		UsersNotes usernote = usersnotesdao.get(user.getIdu(), note.getIdn());
		UsersNotes oldusernote = usersnotesdao.get(user.getIdu(), note.getIdn());
		if (usernote != null) {
			String colorstr = request.getParameter("color");
			if (colorstr != null && colorstr.matches("[0-9]"))
				usernote.setColor(Integer.parseInt(colorstr));
			else
				errors.add("Color ID cannot be changed. :-C");
		} else
			logger.info(TAG + "User (" + user.getUsername() + ") try to modify a foreign note");

		if (!newnote && usernote != null)
			usernote.validate(errors);
		if (note.validate(errors)) {
			logger.info(TAG + "Note Validated from Session ID: " + request.getSession().getId());
			NoteDAO notesdao = new JDBCNoteDAOImpl();
			notesdao.setConnection(connection);

			String labels = request.getParameter("labels");
			LabelDAO labeldao = new JDBCLabelDAOImpl();
			labeldao.setConnection(connection);

			Note oldnote = notesdao.get(note.getIdn());
			if (!newnote) {
				if (!note.equals(oldnote))
					notesdao.save(note);
				usersnotesdao.save(usernote);

				processLabelsOldNote(labels, labeldao, note, errors);
			} else {
				note.setIdn((int) notesdao.add(note));
				Integer color = Integer.parseInt(request.getParameter("color"));
				usernote = new UsersNotes(user.getIdu(), note.getIdn(), color);
				usersnotesdao.add(usernote);

				processLabelsNewNote(labels, labeldao, note, errors);
			}
			if (newnote || (!newnote && (!note.equals(oldnote) || oldusernote.getColor() != usernote.getColor()))) {
				VersionsNotesDAO versionsnotesdao = new JDBCVersionsNotesDAO();
				versionsnotesdao.setConnection(connection);

				VersionsNotes version = new VersionsNotes(note.getIdn(), user.getIdu(), usernote.getOwner(),
						usernote.getArchived(), usernote.getPinned(), usernote.getIntrash(), usernote.getColor(),
						TimeUtil.getTime(), note.getTitle(), note.getContent(), note.getUrlimage());
				versionsnotesdao.add(version);
			}

			response.sendRedirect(getServletContext().getContextPath() + "/notes/MainServlet");
		} else {
			if (newnote)
				request.setAttribute("newnote", "true");

			request.setAttribute("note", note);
			request.setAttribute("errors", errors);
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/jsp/edit-your-note.jsp");
			view.forward(request, response);
		}
	}

	private void processLabelsOldNote(String labels, LabelDAO labeldao, Note note, List<String> errors) {
		List<Label> oldlabels = labeldao.getAllByIdn(note.getIdn());
		List<String> separatedLabels = LabelSeparator.separateLabels(labels);

		for (Label label : oldlabels)
			if (!separatedLabels.contains(label.getContent()))
				labeldao.delete(note.getIdn(), label.getContent());

		for (String labelstr : separatedLabels) {
			Label label = new Label(note.getIdn(), labelstr);
			if (label.validate(errors))
				if (!oldlabels.contains(label))
					labeldao.add(label);
		}
	}

	private void processLabelsNewNote(String labels, LabelDAO labeldao, Note note, List<String> errors) {
		List<String> separatedLabels = LabelSeparator.separateLabels(labels);
		for (String labelstr : separatedLabels) {
			Label label = new Label(note.getIdn(), labelstr);
			if (label.validate(errors))
				labeldao.add(label);
		}
	}
}
