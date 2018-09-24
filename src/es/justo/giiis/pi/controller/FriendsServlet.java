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
import es.justo.giiis.pi.util.Triplet;

/**
 * Servlet implementation class FriendsServlet
 */
@WebServlet(urlPatterns = "/notes/FriendsServlet")
public class FriendsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
	private static final String TAG = "FriendsServlet: ";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FriendsServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = (Connection) getServletContext().getAttribute("dbConn");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		logger.info(TAG + "FriendsServlet By Get: User " + user.getUsername());
		
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
		
		request.setAttribute("friendsAndNotes", friends);
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/jsp/my-friends.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
