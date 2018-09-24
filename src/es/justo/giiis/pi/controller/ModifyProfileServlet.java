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

import es.justo.giiis.pi.dao.JDBCProfileImageDAOImpl;
import es.justo.giiis.pi.dao.JDBCUserDAOImpl;
import es.justo.giiis.pi.dao.ProfileImageDAO;
import es.justo.giiis.pi.dao.UserDAO;
import es.justo.giiis.pi.model.ProfileImage;
import es.justo.giiis.pi.model.User;

/**
 * Servlet implementation class ModifyProfileServlet
 */
@WebServlet("/notes/ModifyProfileServlet")
public class ModifyProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
	private static final String TAG = "ModifyProfileServlet: ";
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModifyProfileServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info(TAG + "Request by GET");

		if(request.getParameter("idi") != null) {
			Connection connection = (Connection) getServletContext().getAttribute("dbConn");
			ProfileImageDAO profileimagedao = new JDBCProfileImageDAOImpl();
			profileimagedao.setConnection(connection);
			
			ProfileImage profileimage = profileimagedao.getByIdi(Integer.parseInt(request.getParameter("idi")));
			request.setAttribute("profileimage", profileimage);
		}
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/jsp/modify-your-profile.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info(TAG + "Request by POST");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		User usermodified = new User();
		usermodified.setIdu(user.getIdu());
		usermodified.setUsername(request.getParameter("username"));
		usermodified.setPassword(request.getParameter("password"));
		usermodified.setEmail(request.getParameter("email"));
		usermodified.setIdi(Integer.parseInt(request.getParameter("idi")));

		List<String> validationMessages = new ArrayList<String>();
		if (usermodified.validate(validationMessages) && !user.equals(usermodified)) {
			Connection connection = (Connection) getServletContext().getAttribute("dbConn");
			UserDAO userdao = new JDBCUserDAOImpl();
			userdao.setConnection(connection);
			
			userdao.save(usermodified);
			logger.info(TAG + "User Modified");

			
			ProfileImageDAO profileimagedao = new JDBCProfileImageDAOImpl();
			profileimagedao.setConnection(connection);
			ProfileImage profileimage = profileimagedao.getByIdi(usermodified.getIdi());
			
			session.setAttribute("profileimage", profileimage);
			session.setAttribute("user", usermodified);
			
			response.sendRedirect(request.getContextPath() + "/notes/MainServlet");
		}else {
			request.setAttribute("errors", validationMessages);
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/jsp/modify-your-profile.jsp");
			view.forward(request, response);
		}
	}

}
