package es.justo.giiis.pi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
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
import es.justo.giiis.pi.dao.LabelDAO;
import es.justo.giiis.pi.dao.NoteDAO;
import es.justo.giiis.pi.dao.UsersNotesDAO;
import es.justo.giiis.pi.model.Label;
import es.justo.giiis.pi.model.Note;
import es.justo.giiis.pi.model.User;
import es.justo.giiis.pi.model.UsersNotes;
import es.justo.giiis.pi.util.Pair;
import es.justo.giiis.pi.util.QueryParameters;
import es.justo.giiis.pi.util.Triplet;;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/notes/MainServlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
	private static final String TAG = "LoginServlet: ";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MainServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		logger.info(TAG + "Mainpage By Get: User " + user.getUsername());

		Connection connection = (Connection) getServletContext().getAttribute("dbConn");
		UsersNotesDAO usernotesdao = new JDBCUsersNotesDAOImpl();
		usernotesdao.setConnection(connection);

		NoteDAO notedao = new JDBCNoteDAOImpl();
		notedao.setConnection(connection);

		LabelDAO labeldao = new JDBCLabelDAOImpl();
		labeldao.setConnection(connection);

		String filter = request.getParameter("filter");

		if (filter == null)
			filter = "default";

		List<UsersNotes> usernotes = getAllNotes(filter, usernotesdao, user, request.getParameter("idn"));
		Collections.sort(usernotes);

		List<Triplet<UsersNotes, Note, List<Label>>> notes = new ArrayList<Triplet<UsersNotes, Note, List<Label>>>();
		for (UsersNotes usernote : usernotes) {
			Triplet<UsersNotes, Note, List<Label>> pair = new Triplet<UsersNotes, Note, List<Label>>(usernote,
					notedao.get(usernote.getIdn()), labeldao.getAllByIdn(usernote.getIdn()));
			notes.add(pair);
		}

		request.setAttribute("notes", notes);
		request.setAttribute("filter", filter);

		List<Pair<Integer, String>> mostfrequentcolors = getMostFrecuentColor(usernotesdao.getTheMostFrecuent(user.getIdu()));
		session.setAttribute("mostfrequentcolors", mostfrequentcolors);

		List<String> mostfrequentlabels = labeldao.getTheMostFrecuent(user.getIdu());
		session.setAttribute("mostfrequentlabels", mostfrequentlabels);
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/jsp/mainpage.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private List<UsersNotes> getAllNotes(String filter, UsersNotesDAO usernotesdao, User user, String idn) {
		List<UsersNotes> usernotes = new ArrayList<UsersNotes>();
		switch (filter) {
		case "onlyone":
			usernotes.add(usernotesdao.get(user.getIdu(), Integer.parseInt(idn)));
			break;
		case "mynotes":
			usernotes = usernotesdao.getAllNotesByQuery(user.getIdu(), QueryParameters.DONT_MATTER,
					QueryParameters.OWNER, QueryParameters.NOT_ARCHIVED, QueryParameters.DONT_MATTER,
					QueryParameters.NOT_IN_TRASH, QueryParameters.DONT_MATTER);
			break;
		case "archived":
			usernotes = usernotesdao.getAllNotesByQuery(user.getIdu(), QueryParameters.DONT_MATTER,
					QueryParameters.DONT_MATTER, QueryParameters.ARCHIVED, QueryParameters.DONT_MATTER,
					QueryParameters.NOT_IN_TRASH, QueryParameters.DONT_MATTER);
			break;
		case "pinned":
			usernotes = usernotesdao.getAllNotesByQuery(user.getIdu(), QueryParameters.DONT_MATTER,
					QueryParameters.DONT_MATTER, QueryParameters.NOT_ARCHIVED, QueryParameters.PINNED,
					QueryParameters.NOT_IN_TRASH, QueryParameters.DONT_MATTER);
			break;
		case "all":
			usernotes = usernotesdao.getAllNotesByQuery(user.getIdu(), QueryParameters.DONT_MATTER,
					QueryParameters.DONT_MATTER, QueryParameters.DONT_MATTER, QueryParameters.DONT_MATTER,
					QueryParameters.NOT_IN_TRASH, QueryParameters.DONT_MATTER);
			break;
		case "sharedwithme":
			usernotes = usernotesdao.getAllNotesByQuery(user.getIdu(), QueryParameters.DONT_MATTER,
					QueryParameters.NOT_OWNER, QueryParameters.NOT_ARCHIVED, QueryParameters.DONT_MATTER,
					QueryParameters.NOT_IN_TRASH, QueryParameters.DONT_MATTER);
			break;
		case "intrash":
			usernotes = usernotesdao.getAllNotesByQuery(user.getIdu(), QueryParameters.DONT_MATTER,
					QueryParameters.DONT_MATTER, QueryParameters.DONT_MATTER, QueryParameters.DONT_MATTER,
					QueryParameters.IN_TRASH, QueryParameters.DONT_MATTER);
			break;
		default:
			usernotes = usernotesdao.getAllNotesByQuery(user.getIdu(), QueryParameters.DONT_MATTER,
					QueryParameters.DONT_MATTER, QueryParameters.NOT_ARCHIVED, QueryParameters.DONT_MATTER,
					QueryParameters.NOT_IN_TRASH, QueryParameters.DONT_MATTER);
		}

		return usernotes;
	}

	private List<Pair<Integer, String>> getMostFrecuentColor(List<Integer> mostfrequent) {
		List<Pair<Integer, String>> mostfrequentcolors = new ArrayList<Pair<Integer, String>>();

		for (Integer color : mostfrequent) {
			switch (color) {
			case QueryParameters.NO_COLOR:
				mostfrequentcolors.add(new Pair<Integer, String>(QueryParameters.NO_COLOR, "No Color"));
				break;
			case QueryParameters.COLOR_GREEN:
				mostfrequentcolors.add(new Pair<Integer, String>(QueryParameters.COLOR_GREEN, "Green"));
				break;
			case QueryParameters.COLOR_BLUE:
				mostfrequentcolors.add(new Pair<Integer, String>(QueryParameters.COLOR_BLUE, "Blue"));
				break;
			case QueryParameters.COLOR_PINK:
				mostfrequentcolors.add(new Pair<Integer, String>(QueryParameters.COLOR_PINK, "Pink"));
				break;
			case QueryParameters.COLOR_BLACK:
				mostfrequentcolors.add(new Pair<Integer, String>(QueryParameters.COLOR_BLACK, "Black"));
				break;
			default:
				break;
			}
		}
		return mostfrequentcolors;
	}

}
