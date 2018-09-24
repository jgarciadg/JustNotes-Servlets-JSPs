package es.justo.giiis.pi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.justo.giiis.pi.dao.JDBCNotificationDAOImpl;
import es.justo.giiis.pi.dao.NotificationDAO;
import es.justo.giiis.pi.model.Notification;
import es.justo.giiis.pi.model.User;

/**
 * Servlet implementation class SendNotificationServlet
 */
@WebServlet("/notes/SendNotificationServlet")
public class SendNotificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
	private static final String TAG = "SendNotificationServlet: ";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendNotificationServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info(TAG + "SendNotificationServlet By GET");
		response.sendRedirect(request.getContextPath() + "/notes/FriendsServlet");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = (Connection) getServletContext().getAttribute("dbConn");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		logger.info(TAG + "SendNotificationServlet By POST: User " + user.getUsername());
		NotificationDAO notificationdao = new JDBCNotificationDAOImpl();
		notificationdao.setConnection(connection);
	
		String idustr = request.getParameter("idfriend");
		logger.info(TAG + "Sending Notification from " + user.getIdu() + " to " + idustr);
		Notification notification = new Notification(Integer.parseInt(idustr), user.getIdu());
		notificationdao.add(notification);
		
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/jsp/my-friends.jsp");
		view.forward(request, response);
	}

}
