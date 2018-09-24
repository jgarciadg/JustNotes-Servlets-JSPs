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

import es.justo.giiis.pi.dao.JDBCProfileImageDAOImpl;
import es.justo.giiis.pi.dao.JDBCUserDAOImpl;
import es.justo.giiis.pi.dao.ProfileImageDAO;
import es.justo.giiis.pi.dao.UserDAO;
import es.justo.giiis.pi.model.ProfileImage;
import es.justo.giiis.pi.model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(name = "LoginServlet", urlPatterns = "/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
	private static final String TAG = "LoginServlet: ";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info(TAG + "Request by GET");
		HttpSession session = request.getSession();
		
		if (session.getAttribute("user") != null) {
			logger.info(TAG + "Request with Session");
			response.sendRedirect(request.getContextPath() + "/notes/MainServlet");
		} else {
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
			view.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		logger.info(TAG + "Request by GET");
		Connection connection = (Connection) getServletContext().getAttribute("dbConn");
		HttpSession session = request.getSession();
		
		UserDAO userdao = new JDBCUserDAOImpl();
		userdao.setConnection(connection);
		
		User user = null;
		String username = request.getParameter("username");
		if(!username.contains("@"))	user = userdao.get(username);
		else user = userdao.getByEmail(username);

		
		if (user != null && request.getParameter("password")!=null && !request.getParameter("password").equals("") &&user.getPassword().equals(request.getParameter("password"))) {
			logger.info(TAG + "User logged" + user.getUsername());
			ProfileImageDAO profileimagedao = new JDBCProfileImageDAOImpl();
			profileimagedao.setConnection(connection);
			ProfileImage profileimage = profileimagedao.getByIdi(user.getIdi());
			
			session.setAttribute("profileimage", profileimage);
			session.setAttribute("user", user);
			
			response.sendRedirect(request.getContextPath() + "/notes/MainServlet");
		} else {
			String error = "";
			if(user != null) {
				error = "The Username or the email is not registered";
				logger.info(TAG + "Incorrect email from session ID" + session.getId());
			}
			if(user != null && request.getParameter("password")!= null && !request.getParameter("password").equals("")&& user.getPassword().equals(request.getParameter("password"))) {
				error = "The password not are correct.";
				logger.info(TAG + "Incorrect password from session ID" + session.getId());
			}
				
			request.setAttribute("error", error);
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
			view.forward(request, response);
		}

	}

}