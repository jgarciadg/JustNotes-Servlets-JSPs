package es.justo.giiis.pi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.justo.giiis.pi.dao.JDBCNotificationDAOImpl;
import es.justo.giiis.pi.dao.JDBCUsersFriendsImpl;
import es.justo.giiis.pi.dao.NotificationDAO;
import es.justo.giiis.pi.dao.UsersFriendsDAO;
import es.justo.giiis.pi.model.Notification;
import es.justo.giiis.pi.model.User;
import es.justo.giiis.pi.model.UsersFriends;

/**
 * Servlet implementation class AddFriendServlet
 */
@WebServlet(urlPatterns="/notes/AddFriendServlet")
public class AddFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
	private static final String TAG = "AddFriendServlet: ";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddFriendServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info(TAG + "AddFriendServlet By GET");
		response.sendRedirect(request.getContextPath() + "/notes/FriendsServlet");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = (Connection) getServletContext().getAttribute("dbConn");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		logger.info(TAG + "AddFriendServlet By POST: User " + user.getUsername());
		
		UsersFriendsDAO usersfriendsdao = new JDBCUsersFriendsImpl();
		usersfriendsdao.setConnection(connection);
		NotificationDAO notificationdao = new JDBCNotificationDAOImpl();
		notificationdao.setConnection(connection);
		
		String[] idus = request.getParameterValues("idu");
		if(idus != null) {
			@SuppressWarnings("unchecked")
			List<Notification> notifications = (List<Notification>) session.getAttribute("notificationslist");
			for(String idustr: idus) {	
				if(notifications.contains(new Notification(user.getIdu(), Integer.parseInt(idustr)))) {
					UsersFriends userfriend = new UsersFriends(user.getIdu(), Integer.parseInt(idustr));
					if(usersfriendsdao.get(userfriend.getIdu(), userfriend.getIdfriend()) == null) {
						if(userfriend.getIdu() != userfriend.getIdfriend()) {
							logger.info(TAG + "Adding UsersFriends");
							usersfriendsdao.add(userfriend);
							usersfriendsdao.add(new UsersFriends(userfriend.getIdfriend(), userfriend.getIdu()));
							
							Notification notification = new Notification(user.getIdu(), userfriend.getIdfriend());
							notifications.remove(notification);
							notificationdao.delete(notification);
						}else {
							logger.info(TAG + "The Users are the same");
						}
					}else {
						logger.info(TAG + "The Users are friends");
					}
				}else {
					logger.info(TAG + "The Request not exists");
				}
			}
			if (!notifications.isEmpty())
				session.setAttribute("notifications", true);
			else
				session.setAttribute("notifications", false);
			session.setAttribute("notificationslist", notifications);
		}
		response.sendRedirect(request.getContextPath() + "/notes/FriendsServlet");
	}

}
	