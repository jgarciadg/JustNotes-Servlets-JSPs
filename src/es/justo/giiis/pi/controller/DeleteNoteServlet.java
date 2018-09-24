package es.justo.giiis.pi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.justo.giiis.pi.dao.JDBCNoteDAOImpl;
import es.justo.giiis.pi.dao.JDBCUsersNotesDAOImpl;
import es.justo.giiis.pi.dao.JDBCVersionsNotesDAO;
import es.justo.giiis.pi.dao.NoteDAO;
import es.justo.giiis.pi.dao.UsersNotesDAO;
import es.justo.giiis.pi.dao.VersionsNotesDAO;
import es.justo.giiis.pi.model.User;
import es.justo.giiis.pi.model.UsersNotes;

/**
 * Servlet implementation class DeleteNoteServlet
 */
@WebServlet("/notes/DeleteNoteServlet")
public class DeleteNoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
	private static final String TAG = "DeleteNoteServlet: ";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteNoteServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TO TRASH
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection connection = (Connection) getServletContext().getAttribute("dbConn");
		NoteDAO notedao = new JDBCNoteDAOImpl();
		notedao.setConnection(connection);

		UsersNotesDAO usersnotesdao = new JDBCUsersNotesDAOImpl();
		usersnotesdao.setConnection(connection);
		VersionsNotesDAO versionsnotesdao = new JDBCVersionsNotesDAO();
		versionsnotesdao.setConnection(connection);
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		String[] idns = request.getParameterValues("idn");
		for (String idn : idns) {
			int idnint = Integer.parseInt(idn);
			UsersNotes usernote = (UsersNotes) usersnotesdao.get(user.getIdu(), idnint);
			if (usernote.getOwner() == 1) {
				logger.info(TAG + "Deleting owner note "+ idn);
				notedao.delete(idnint);
				usersnotesdao.deleteAll(idnint);
				versionsnotesdao.deleteAllByIdn(idnint);
			} else {
				logger.info(TAG + "Deleting not owner note " + idn);
				usersnotesdao.delete(user.getIdu(), idnint);
			}
		}

		response.sendRedirect(request.getContextPath() + "/notes/MainServlet");
	}

}
