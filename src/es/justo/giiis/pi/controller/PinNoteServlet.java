package es.justo.giiis.pi.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.justo.giiis.pi.dao.JDBCUsersNotesDAOImpl;
import es.justo.giiis.pi.dao.UsersNotesDAO;
import es.justo.giiis.pi.model.User;
import es.justo.giiis.pi.model.UsersNotes;

/**
 * Servlet implementation class PinNoteServlet
 */
@WebServlet("/notes/PinNoteServlet")
public class PinNoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PinNoteServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String idn = request.getParameter("idn");
		User user = (User) session.getAttribute("user");

		if (idn != null && request.getParameter("pin") != null) {
			Connection connection = (Connection) getServletContext().getAttribute("dbConn");
			UsersNotesDAO usersnotesdao = new JDBCUsersNotesDAOImpl();
			usersnotesdao.setConnection(connection);

			UsersNotes usersnotes = (UsersNotes) usersnotesdao.get(user.getIdu(), Integer.parseInt(idn));
			if (usersnotes != null) {
				if (request.getParameter("pin").equals("false"))
					usersnotes.setPinned(0);
				else if (request.getParameter("pin").equals("true"))
					usersnotes.setPinned(1);

				usersnotesdao.save(usersnotes);
			}
		}
		response.sendRedirect(request.getContextPath() + "/notes/MainServlet");
	}

}
