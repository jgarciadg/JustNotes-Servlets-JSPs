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

import es.justo.giiis.pi.dao.JDBCNoteDAOImpl;
import es.justo.giiis.pi.dao.JDBCProfileImageDAOImpl;
import es.justo.giiis.pi.dao.JDBCUserDAOImpl;
import es.justo.giiis.pi.dao.JDBCUsersFriendsImpl;
import es.justo.giiis.pi.dao.JDBCUsersNotesDAOImpl;
import es.justo.giiis.pi.dao.NoteDAO;
import es.justo.giiis.pi.dao.ProfileImageDAO;
import es.justo.giiis.pi.dao.UserDAO;
import es.justo.giiis.pi.dao.UsersFriendsDAO;
import es.justo.giiis.pi.dao.UsersNotesDAO;
import es.justo.giiis.pi.model.Note;
import es.justo.giiis.pi.model.ProfileImage;
import es.justo.giiis.pi.model.User;
import es.justo.giiis.pi.model.UsersFriends;
import es.justo.giiis.pi.model.UsersNotes;
import es.justo.giiis.pi.util.QueryParameters;
import es.justo.giiis.pi.util.Triplet;

/**
 * Servlet implementation class ShareNoteServlet
 */
@WebServlet("/notes/ShareNoteServlet")
public class ShareNoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShareNoteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = (Connection) getServletContext().getAttribute("dbConn");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		UsersFriendsDAO usersfriendsdao = new JDBCUsersFriendsImpl();
		usersfriendsdao.setConnection(connection);
		
		UserDAO userdao = new JDBCUserDAOImpl();
		userdao.setConnection(connection);
		
		NoteDAO notedao = new JDBCNoteDAOImpl();
		notedao.setConnection(connection);
		
		UsersNotesDAO usersnotesdao = new JDBCUsersNotesDAOImpl();
		usersnotesdao.setConnection(connection);
		
		ProfileImageDAO profileimagedao = new JDBCProfileImageDAOImpl();
		profileimagedao.setConnection(connection);
		
		List<UsersFriends> usersfriends = usersfriendsdao.getAllByIdu(user.getIdu());
		List<Triplet<User, ProfileImage, List<Note>>> friends = new ArrayList<Triplet<User, ProfileImage, List<Note>>>();
		for (UsersFriends friend : usersfriends) {
			User userfriend = userdao.get(friend.getIdfriend());
			List<UsersNotes> usernotes = usersnotesdao.getAllByUser(friend.getIdu());
			List<UsersNotes> friendnotes = usersnotesdao.getAllByUser(friend.getIdfriend());
			
			List<Note> commonNotes = new ArrayList<Note>();
			for(UsersNotes usernote: usernotes) 
				if(friendnotes.contains(usernote)) 
					commonNotes.add(notedao.get(usernote.getIdn()));
			
			friends.add(new Triplet<User, ProfileImage, List<Note>>(userdao.get(friend.getIdfriend()), profileimagedao.getByIdi(userfriend.getIdi()), commonNotes));
		}
		
		request.setAttribute("idn", request.getParameter("idn"));
		request.setAttribute("friendsAndNotes", friends);
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/jsp/share-note.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = (Connection) getServletContext().getAttribute("dbConn");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		UsersNotesDAO usersnotesdao = new JDBCUsersNotesDAOImpl();
		usersnotesdao.setConnection(connection);
		
		UserDAO userdao = new JDBCUserDAOImpl();
		userdao.setConnection(connection);
		
		String idn = request.getParameter("idn");
		String[] idus = request.getParameterValues("idu");
		if(idus != null && idn.matches("[0-9]+")) {
			for(String idustr: idus) {
				if(usersnotesdao.get(user.getIdu(), Integer.parseInt(idn)) != null) {
					UsersNotes usernote = new UsersNotes(Integer.parseInt(idustr), Integer.parseInt(idn), QueryParameters.NOT_OWNER, QueryParameters.NOT_ARCHIVED, 
							QueryParameters.NOT_PINNED, QueryParameters.NOT_IN_TRASH, QueryParameters.NO_COLOR);
					usersnotesdao.add(usernote);
				}
			}
		}
		response.sendRedirect(request.getContextPath() + "/notes/MainServlet");
	}

}
