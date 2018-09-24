package es.justo.giiis.pi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import es.justo.giiis.pi.dao.LabelDAO;
import es.justo.giiis.pi.dao.NoteDAO;
import es.justo.giiis.pi.dao.UsersNotesDAO;
import es.justo.giiis.pi.model.Label;
import es.justo.giiis.pi.model.Note;
import es.justo.giiis.pi.model.User;
import es.justo.giiis.pi.model.UsersNotes;
import es.justo.giiis.pi.util.QueryParameters;
import es.justo.giiis.pi.util.Triplet;

/**
 * Servlet implementation class FilterDataServlet
 */
@WebServlet("/notes/FilterDataServlet")
public class FilterDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FilterDataServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String[] colors = request.getParameterValues("color");
		String[] labelsstrs = request.getParameterValues("labels");

		if (colors != null || labelsstrs!= null) {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");

			Connection connection = (Connection) getServletContext().getAttribute("dbConn");
			UsersNotesDAO usernotesdao = new JDBCUsersNotesDAOImpl();
			usernotesdao.setConnection(connection);

			NoteDAO notesdao = new JDBCNoteDAOImpl();
			notesdao.setConnection(connection);

			LabelDAO labeldao = new JDBCLabelDAOImpl();
			labeldao.setConnection(connection);

			List<UsersNotes> usernotes = new ArrayList<UsersNotes>();
			if (colors != null)
				for (String colorstring : colors) {
					Integer color = Integer.parseInt(colorstring);
					usernotes.addAll(usernotesdao.getAllNotesByQuery(user.getIdu(), QueryParameters.DONT_MATTER,
							QueryParameters.DONT_MATTER, QueryParameters.NOT_ARCHIVED, QueryParameters.DONT_MATTER,
							QueryParameters.DONT_MATTER, color));
				}

			if (labelsstrs != null) {
				List<Label> labels = new ArrayList<Label>();
				for (String label : labelsstrs) {
					labels.addAll(labeldao.getAllByContent(label));
				}
				for (Label label : labels) {
					UsersNotes usernote = null;
					if((usernote = usernotesdao.get(user.getIdu(), label.getIdn())) != null)
						usernotes.add(usernote);
				}
			}
			if(usernotes != null)
				Collections.sort(usernotes);

			List<Triplet<UsersNotes, Note, List<Label>>> notes = new ArrayList<Triplet<UsersNotes, Note, List<Label>>>();

			for (UsersNotes usernotesiter : usernotes) {
				notes.add(new Triplet<UsersNotes, Note, List<Label>>(usernotesiter,
						notesdao.get(usernotesiter.getIdn()), labeldao.getAllByIdn(usernotesiter.getIdn())));
			}

			request.setAttribute("colorsselected", colors);
			request.setAttribute("labelsselected", labelsstrs);
			request.setAttribute("notes", notes);

			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/jsp/mainpage.jsp");
			view.forward(request, response);
		} else
			response.sendRedirect(request.getContextPath() + "/notes/MainServlet");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
