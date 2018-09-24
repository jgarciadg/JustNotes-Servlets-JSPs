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
import es.justo.giiis.pi.dao.JDBCVersionsNotesDAO;
import es.justo.giiis.pi.dao.LabelDAO;
import es.justo.giiis.pi.dao.NoteDAO;
import es.justo.giiis.pi.dao.UserDAO;
import es.justo.giiis.pi.dao.UsersFriendsDAO;
import es.justo.giiis.pi.dao.UsersNotesDAO;
import es.justo.giiis.pi.dao.VersionsNotesDAO;
import es.justo.giiis.pi.model.Note;
import es.justo.giiis.pi.model.User;
import es.justo.giiis.pi.model.UsersFriends;
import es.justo.giiis.pi.model.UsersNotes;
import es.justo.giiis.pi.util.QueryParameters;

/**
 * Servlet implementation class DeleteAccountServlet
 */
@WebServlet("/notes/DeleteAccountServlet")
public class DeleteAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
	private static final String TAG = "DeleteAccountServlet: ";
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteAccountServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		Connection connection = (Connection) getServletContext().getAttribute("dbConn");
		UsersNotesDAO usersnotesdao = new JDBCUsersNotesDAOImpl();
		usersnotesdao.setConnection(connection);

		logger.info(TAG + "DeleteAccount by GET");
		logger.info(TAG + "Capturing Statistics from User");
		Integer howmanycreated = usersnotesdao.getHowManyByQuery(user.getIdu(), QueryParameters.DONT_MATTER,
				QueryParameters.OWNER, QueryParameters.DONT_MATTER, QueryParameters.DONT_MATTER,
				QueryParameters.DONT_MATTER, QueryParameters.DONT_MATTER);
		Integer howmanyshared = usersnotesdao.getHowManyByQuery(user.getIdu(), QueryParameters.DONT_MATTER,
				QueryParameters.NOT_OWNER, QueryParameters.DONT_MATTER, QueryParameters.DONT_MATTER,
				QueryParameters.DONT_MATTER, QueryParameters.DONT_MATTER);
		Integer howmanyarchived = usersnotesdao.getHowManyByQuery(user.getIdu(), QueryParameters.DONT_MATTER,
				QueryParameters.DONT_MATTER, QueryParameters.ARCHIVED, QueryParameters.DONT_MATTER,
				QueryParameters.DONT_MATTER, QueryParameters.DONT_MATTER);
		
		request.setAttribute("howmanycreated", howmanycreated);
		request.setAttribute("howmanyshared", howmanyshared);
		request.setAttribute("howmanyarchived", howmanyarchived);
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/jsp/check-delete-account.jsp");
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

		UsersNotesDAO usersnotesdao = new JDBCUsersNotesDAOImpl();
		usersnotesdao.setConnection(connection);
		NoteDAO notesdao = new JDBCNoteDAOImpl();
		notesdao.setConnection(connection);
		VersionsNotesDAO versionsnotesdao = new JDBCVersionsNotesDAO();
		versionsnotesdao.setConnection(connection);
		LabelDAO labeldao = new JDBCLabelDAOImpl();
		labeldao.setConnection(connection);
		
		List<UsersNotes> usernotes = usersnotesdao.getAllByUser(user.getIdu());
		List<Note> notes = new ArrayList<Note>();
		List<Note> notesOwner = new ArrayList<Note>();
		logger.info(TAG + "DeleteAccount by POST");
		logger.info(TAG + "Deleting UsersNotes and Notes User");
		for (UsersNotes usernote : usernotes) {
			Note note = notesdao.get(usernote.getIdn());
			if (usernote.getOwner() == 1) 	notesOwner.add(note);
			else 							notes.add(note);
		}
		
		for (Note note : notes) {
			logger.info(TAG + "Deleting UserNote:" + " IDU " + user.getIdu() + " IDN " + note.getIdn());
			usersnotesdao.delete(user.getIdu(), note.getIdn());
		}
		for(Note note : notesOwner) {
			logger.info(TAG + "Deleting Note:" + " IDN " + note.getIdn());
			notesdao.delete(note.getIdn());
			logger.info(TAG + "Deleting All UsersNotes from:" + " IDN " + note.getIdn());
			usersnotesdao.deleteAll(note.getIdn());
			logger.info(TAG + "Deleting Versions:" + " IDN " + note.getIdn());
			versionsnotesdao.deleteAllByIdn(note.getIdn());
			logger.info(TAG + "Deleting All Labels from:" + " IDN " + note.getIdn());
			labeldao.deleteAll(note.getIdn());
		}
		
		UsersFriendsDAO usersfriendsdao = new JDBCUsersFriendsImpl();
		usersfriendsdao.setConnection(connection);
		List<UsersFriends> userfriends = usersfriendsdao.getAllByIdu(user.getIdu());
		for(UsersFriends userfriend :  userfriends) {   
			logger.info(TAG + "Deleting All UsersFriends from:" + " IDU1 " + userfriend.getIdfriend() + " IDU2 " + userfriend.getIdu());
			usersfriendsdao.delete(userfriend);
			usersfriendsdao.delete(new UsersFriends(userfriend.getIdfriend(), userfriend.getIdu()));
		}

		UserDAO userdao = new JDBCUserDAOImpl();
		userdao.setConnection(connection);
		logger.info(TAG + "Deleting User" + " IDU " + user.getIdu());
		userdao.delete(user.getIdu());

		response.sendRedirect(request.getContextPath() + "/notes/LogoutServlet");
	}

}
