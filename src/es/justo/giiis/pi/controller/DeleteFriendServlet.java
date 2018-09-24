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

import es.justo.giiis.pi.dao.JDBCUsersFriendsImpl;
import es.justo.giiis.pi.dao.UsersFriendsDAO;
import es.justo.giiis.pi.model.User;
import es.justo.giiis.pi.model.UsersFriends;

/**
 * Servlet implementation class DeleteFriendServlet
 */
@WebServlet("/notes/DeleteFriendServlet")
public class DeleteFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
	private static final String TAG = "DeleteFriendServlet: ";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteFriendServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info(TAG + "DeleteFriendServlet By GET");
		response.sendRedirect(request.getContextPath() + "/notes/FriendsServlet");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection connection = (Connection) getServletContext().getAttribute("dbConn");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		logger.info(TAG + "DeleteFriendServlet By POST: User " + user.getUsername());
		String idfriendstr = request.getParameter("idfriend");
		
		UsersFriendsDAO usersfriendsdao = new JDBCUsersFriendsImpl();
		usersfriendsdao.setConnection(connection);
		logger.info(TAG + "Deleting UsersFriends");
		usersfriendsdao.delete(new UsersFriends(user.getIdu(), Integer.parseInt(idfriendstr)));
		usersfriendsdao.delete(new UsersFriends(Integer.parseInt(idfriendstr), user.getIdu()));
		
		response.sendRedirect(request.getContextPath() + "/notes/FriendsServlet");
	}

}
