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

import es.justo.giiis.pi.dao.JDBCProfileImageDAOImpl;
import es.justo.giiis.pi.dao.JDBCUserDAOImpl;
import es.justo.giiis.pi.dao.ProfileImageDAO;
import es.justo.giiis.pi.dao.UserDAO;
import es.justo.giiis.pi.model.Note;
import es.justo.giiis.pi.model.Notification;
import es.justo.giiis.pi.model.ProfileImage;
import es.justo.giiis.pi.model.User;
import es.justo.giiis.pi.util.Triplet;

/**
 * Servlet implementation class NotificationsServlet
 */
@WebServlet("/notes/NotificationsServlet")
public class NotificationsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NotificationsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = (Connection) getServletContext().getAttribute("dbConn");
		
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		List<Notification> notifications = (List<Notification>) session.getAttribute("notificationslist");
		
		UserDAO userdao = new JDBCUserDAOImpl();
		userdao.setConnection(connection);
		ProfileImageDAO profileimagedao = new JDBCProfileImageDAOImpl();
		profileimagedao.setConnection(connection);
		if(!notifications.isEmpty()) {
			List<Triplet<User, ProfileImage, List<Note>>> usersrequested = new ArrayList<Triplet<User, ProfileImage, List<Note>>>();
			for(Notification notification: notifications) {
				User userrequest = userdao.get(notification.getIdrequest());
				usersrequested.add(new Triplet<User, ProfileImage, List<Note>>(userrequest, profileimagedao.getByIdi(userrequest.getIdi()), null));
			}
			request.setAttribute("friendsAndNotes", usersrequested);
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/jsp/share-note.jsp");
			view.forward(request, response);
		}else {
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/jsp/logged-header.jsp");
			view.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view = request.getRequestDispatcher("/notes/AddFriendServlet");
		view.forward(request, response);
	}

}
