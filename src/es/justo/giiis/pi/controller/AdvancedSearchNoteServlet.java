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
import es.justo.giiis.pi.dao.JDBCUserDAOImpl;
import es.justo.giiis.pi.dao.JDBCUsersFriendsImpl;
import es.justo.giiis.pi.dao.JDBCUsersNotesDAOImpl;
import es.justo.giiis.pi.dao.LabelDAO;
import es.justo.giiis.pi.dao.NoteDAO;
import es.justo.giiis.pi.dao.UserDAO;
import es.justo.giiis.pi.dao.UsersFriendsDAO;
import es.justo.giiis.pi.dao.UsersNotesDAO;
import es.justo.giiis.pi.model.Label;
import es.justo.giiis.pi.model.Note;
import es.justo.giiis.pi.model.User;
import es.justo.giiis.pi.model.UsersFriends;
import es.justo.giiis.pi.model.UsersNotes;
import es.justo.giiis.pi.util.LabelSeparator;
import es.justo.giiis.pi.util.QueryParameters;
import es.justo.giiis.pi.util.Triplet;

/**
 * Servlet implementation class AdvancedSearchNoteServlet
 */
@WebServlet("/notes/AdvancedSearchNoteServlet")
public class AdvancedSearchNoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
	private static final String TAG = "AdvancedSearchNoteServlet : ";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdvancedSearchNoteServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info(TAG + "AdvancedSearchNoteServlet by GET");

		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/jsp/advanced-search-notes.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection connection = (Connection) getServletContext().getAttribute("dbConn");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		NoteDAO notedao = new JDBCNoteDAOImpl();
		notedao.setConnection(connection);
		UsersNotesDAO usersnotesdao = new JDBCUsersNotesDAOImpl();
		usersnotesdao.setConnection(connection);
		UsersFriendsDAO usersfriendsdao = new JDBCUsersFriendsImpl();
		usersfriendsdao.setConnection(connection);
		UserDAO userdao = new JDBCUserDAOImpl();
		userdao.setConnection(connection);
		LabelDAO labeldao = new JDBCLabelDAOImpl();
		labeldao.setConnection(connection);

		logger.info(TAG + "AdvancedSearchNoteServlet by POST");
		List<UsersNotes> allusersnotes = usersnotesdao.getAllByUser(user.getIdu());

		String title_search = request.getParameter("title-search");
		String description_search = request.getParameter("description-search");

		List<UsersNotes> to_remove = null;
		if (title_search != null && !title_search.equals("")) {
			logger.info(TAG + "Excluding By Title: " + title_search);
			to_remove = new ArrayList<UsersNotes>();
			for (UsersNotes usernote : allusersnotes) {
				Note note = notedao.get(usernote.getIdn());
				if (!note.getTitle().contains(title_search))
					to_remove.add(usernote);
			}
			allusersnotes.removeAll(to_remove);
		}

		if (description_search != null && !description_search.equals("")) {
			logger.info(TAG + "Excluding By Description: " + description_search);
			to_remove = new ArrayList<UsersNotes>();
			for (UsersNotes usernote : allusersnotes) {
				Note note = notedao.get(usernote.getIdn());
				if (!note.getContent().contains(description_search))
					to_remove.add(usernote);
			}
			allusersnotes.removeAll(to_remove);
		}

		String labels = request.getParameter("labels");
		if(labels != null && !labels.equals("")) {
			to_remove = new ArrayList<UsersNotes>();
			List<String> labelslist = LabelSeparator.separateLabels(labels);
			for(UsersNotes usernote: allusersnotes) {
				List<Label> labelsbyidn = labeldao.getAllByIdn(usernote.getIdn());
				for(String label: labelslist) {
					if(!labelsbyidn.contains(new Label(usernote.getIdn(), label)))
						to_remove.add(usernote);
				}
			}
			allusersnotes.removeAll(to_remove);
		}
		
		to_remove = new ArrayList<UsersNotes>();
		if (request.getParameter("typeuser").equals("friends")) {
			logger.info(TAG + "Excluding By TypeUser: friends");
			List<UsersFriends> usersfriends = usersfriendsdao.getAllByIdu(user.getIdu());
			List<UsersNotes> friendnotes = new ArrayList<UsersNotes>();
			for (UsersFriends friend : usersfriends) {
				friendnotes.addAll(usersnotesdao.getAllByUser(friend.getIdfriend()));
			}
			for (UsersNotes usernote : allusersnotes)
				if (!friendnotes.contains(usernote))
					to_remove.add(usernote);

		} else if (request.getParameter("typeuser").equals("owner")) {
			logger.info(TAG + "Excluding By TypeUser: owner");
			List<UsersNotes> myusernotes = usersnotesdao.getAllNotesByQuery(user.getIdu(), QueryParameters.DONT_MATTER,
					QueryParameters.OWNER, QueryParameters.DONT_MATTER, QueryParameters.DONT_MATTER,
					QueryParameters.DONT_MATTER, QueryParameters.DONT_MATTER);
			for (UsersNotes usernote : allusersnotes)
				if (!myusernotes.contains(usernote))
					to_remove.add(usernote);
		}
		allusersnotes.removeAll(to_remove);

		String[] colors = request.getParameterValues("color-searched");
		if (colors != null && colors.length != 0) {
			to_remove = new ArrayList<UsersNotes>();
			logger.info(TAG + "Excluding By Colors");
			List<UsersNotes> usersnotesbycolor = new ArrayList<UsersNotes>();
			for (String color : colors) {
				int colorint = Integer.parseInt(color);
				usersnotesbycolor.addAll(usersnotesdao.getAllNotesByQuery(user.getIdu(), QueryParameters.DONT_MATTER,
						QueryParameters.DONT_MATTER, QueryParameters.DONT_MATTER, QueryParameters.DONT_MATTER,
						QueryParameters.DONT_MATTER, colorint));
			}
			for (UsersNotes usernote : allusersnotes)
				if (!usersnotesbycolor.contains(usernote))
					to_remove.add(usernote);
			allusersnotes.removeAll(to_remove);
		}

		String[] notetypes = request.getParameterValues("type-note");
		if(notetypes != null && notetypes.length != 0) {
			to_remove = new ArrayList<UsersNotes>();
			logger.info(TAG + "Excluding By Types");
			List<UsersNotes> usersnotesbytypes = new ArrayList<UsersNotes>();
			for(String notetype : notetypes) {
				switch (notetype) {
				case "pinned":
					usersnotesbytypes.addAll(usersnotesdao.getAllNotesByQuery(user.getIdu(), QueryParameters.DONT_MATTER, QueryParameters.DONT_MATTER, QueryParameters.DONT_MATTER, QueryParameters.PINNED, QueryParameters.DONT_MATTER, QueryParameters.DONT_MATTER));
					break;
				case "archived":
					usersnotesbytypes.addAll(usersnotesdao.getAllNotesByQuery(user.getIdu(), QueryParameters.DONT_MATTER, QueryParameters.DONT_MATTER, QueryParameters.ARCHIVED, QueryParameters.DONT_MATTER, QueryParameters.DONT_MATTER, QueryParameters.DONT_MATTER));
					break;
				case "shared":
					usersnotesbytypes.addAll(usersnotesdao.getAllNotesByQuery(user.getIdu(), QueryParameters.DONT_MATTER, QueryParameters.NOT_OWNER, QueryParameters.DONT_MATTER, QueryParameters.DONT_MATTER, QueryParameters.DONT_MATTER, QueryParameters.DONT_MATTER));
					break;
				case "intrash":
					usersnotesbytypes.addAll(usersnotesdao.getAllNotesByQuery(user.getIdu(), QueryParameters.DONT_MATTER, QueryParameters.DONT_MATTER, QueryParameters.DONT_MATTER, QueryParameters.DONT_MATTER, QueryParameters.IN_TRASH, QueryParameters.DONT_MATTER));
					break;
				default:
					logger.info(TAG + "Type No Valid");
					break;
				}
			}
			for (UsersNotes usernote : allusersnotes)
				if (!usersnotesbytypes.contains(usernote))
					to_remove.add(usernote);
			allusersnotes.removeAll(to_remove);
		}
		
		List<Triplet<UsersNotes, Note, List<Label>>> notes_searched = new ArrayList<Triplet<UsersNotes, Note, List<Label>>>();
		for (UsersNotes usernote : allusersnotes)
			notes_searched.add(new Triplet<UsersNotes, Note, List<Label>>(usernote, notedao.get(usernote.getIdn()),
					labeldao.getAllByIdn(usernote.getIdn())));

		request.setAttribute("notes", notes_searched);
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/jsp/mainpage.jsp");
		view.forward(request, response);
	}

}
