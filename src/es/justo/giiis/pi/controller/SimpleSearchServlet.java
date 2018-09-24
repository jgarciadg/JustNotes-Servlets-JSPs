package es.justo.giiis.pi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
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
import es.justo.giiis.pi.util.Triplet;

/**
 * Servlet implementation class SimpleSearchServlet
 */
@WebServlet("/notes/SimpleSearchServlet")
public class SimpleSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SimpleSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = (Connection) getServletContext().getAttribute("dbConn");

		String search_object = request.getParameter("search-object");
		NoteDAO notesdao = new JDBCNoteDAOImpl();
		notesdao.setConnection(connection);

		List<Note> allnotes = notesdao.getAllBySearchAll(search_object);
		UsersNotesDAO usernotesdao = new JDBCUsersNotesDAOImpl();
		usernotesdao.setConnection(connection);
		
		LabelDAO labeldao = new JDBCLabelDAOImpl();
		labeldao.setConnection(connection);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		List<Triplet<UsersNotes, Note, List<Label>>> notes = new ArrayList<Triplet<UsersNotes, Note, List<Label>>>();
		for (Note note : allnotes) {
			UsersNotes usernote = usernotesdao.get(user.getIdu(), note.getIdn());
			if (usernote != null) {
				notes.add(new Triplet<UsersNotes, Note, List<Label>>(usernote, note, labeldao.getAllByIdn(usernote.getIdn())));
			}
		}

		request.setAttribute("notes", notes);

		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/jsp/mainpage.jsp");
		view.forward(request, response);
	}

}
