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

import es.justo.giiis.pi.dao.JDBCNoteDAOImpl;
import es.justo.giiis.pi.dao.JDBCUsersNotesDAOImpl;
import es.justo.giiis.pi.dao.NoteDAO;
import es.justo.giiis.pi.dao.UsersNotesDAO;
import es.justo.giiis.pi.model.Note;
import es.justo.giiis.pi.model.User;
import es.justo.giiis.pi.model.UsersNotes;
import es.justo.giiis.pi.util.QueryParameters;

/**
 * Servlet implementation class TrashServlet
 */
@WebServlet(urlPatterns="/notes/TrashServlet")
public class TrashServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
	private static final String TAG = "TrashServlet: ";   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TrashServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		Connection connection = (Connection) getServletContext().getAttribute("dbConn");
		NoteDAO notedao = new JDBCNoteDAOImpl();
		notedao.setConnection(connection);

		UsersNotesDAO usersnotesdao = new JDBCUsersNotesDAOImpl();
		usersnotesdao.setConnection(connection);
		List<UsersNotes> usernotes = usersnotesdao.getAllNotesByQuery(user.getIdu(), QueryParameters.DONT_MATTER,
				QueryParameters.DONT_MATTER, QueryParameters.DONT_MATTER, QueryParameters.DONT_MATTER,
				QueryParameters.IN_TRASH, QueryParameters.DONT_MATTER);

		List<Note> notes = new ArrayList<Note>();
		logger.info(TAG + "Confirm Empty Trash By Get: User " + user.getUsername());
		for (UsersNotes usernote : usernotes)
			notes.add(notedao.get(usernote.getIdn()));
		
		request.setAttribute("notes", notes);
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/jsp/confirm-delete-note.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		Connection connection = (Connection) getServletContext().getAttribute("dbConn");
		NoteDAO notedao = new JDBCNoteDAOImpl();
		notedao.setConnection(connection);

		UsersNotesDAO usersnotesdao = new JDBCUsersNotesDAOImpl();
		usersnotesdao.setConnection(connection);
		String action = request.getParameter("action");
		List<Note> notes = new ArrayList<Note>();
		if (action != null && !action.equals("emptytrash")) {
			String idn = request.getParameter("idn");
			Note note = (Note) notedao.get(Integer.parseInt(idn));

			if (action.equals("totrash") || action.equals("recover")) {

				UsersNotes usernote = usersnotesdao.get(user.getIdu(), note.getIdn());

				if (action.equals("totrash"))
					usernote.setIntrash(QueryParameters.IN_TRASH);
				else
					usernote.setIntrash(QueryParameters.NOT_IN_TRASH);

				usersnotesdao.save(usernote);
				if (action.equals("totrash"))
					response.sendRedirect(request.getContextPath() + "/notes/MainServlet");
				else
					response.sendRedirect(request.getContextPath() + "/notes/MainServlet?filter=intrash");
			} else if (action.equals("delete")) {
				notes.add(note);
				request.setAttribute("notes", notes);
				RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/jsp/confirm-delete-note.jsp");
				view.forward(request, response);
			}

		}
	}

}
