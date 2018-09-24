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
 * Servlet implementation class RegisterServlet
 */
@WebServlet(name="RegisterServlet", urlPatterns= {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
    private static final String TAG = "RegisterServlet: ";
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if(request.getAttribute("errors") != null)
			request.removeAttribute("errors");
		
		if(user == null) {
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/jsp/register.jsp");
			view.forward(request, response);
		}else {
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/jsp/mainpage.jsp");
			view.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = new User();
		user.setUsername(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));
		user.setEmail(request.getParameter("email"));
		user.setIdi(1);
		String verifiedpassword = request.getParameter("verifiedpassword");
		
		List<String> errors = new ArrayList<String>();
		if(!user.getPassword().equals(verifiedpassword))
			errors.add("The password are not equals.");
		
		logger.info(TAG + " Usuario Registraandose por POST");
		
		Connection connection = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(connection);	
		
		HttpSession session = request.getSession();
		if(userDao.get(user.getUsername())!= null || userDao.getByEmail(user.getEmail())!=null)
			errors.add("The username or the email cannot be used.");
			
		if(user.validate(errors)) {
			logger.info(TAG + " Insertando nuevo usuario en la BD");
			long idu = userDao.add(user);
			user.setIdu((int) idu);
			
			ProfileImageDAO profileimagedao = new JDBCProfileImageDAOImpl();
			profileimagedao.setConnection(connection);
			ProfileImage profileimage = profileimagedao.getByIdi(user.getIdi());
			session.setAttribute("profileimage", profileimage);

			session.setAttribute("user", user);
			response.sendRedirect(request.getContextPath() + "/notes/MainServlet");
		}else {
			request.setAttribute("errors", errors);
			request.setAttribute("user", user);
			request.setAttribute("verifiedpassword", verifiedpassword);
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/jsp/register.jsp");
			view.forward(request, response);
		}
		
	}

}
