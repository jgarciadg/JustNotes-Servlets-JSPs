package es.justo.giiis.pi.filter;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import es.justo.giiis.pi.dao.JDBCNotificationDAOImpl;
import es.justo.giiis.pi.dao.NotificationDAO;
import es.justo.giiis.pi.model.Notification;
import es.justo.giiis.pi.model.User;

/**
 * Servlet Filter implementation class NotificationFilter
 */
public class NotificationFilter implements Filter {
	FilterConfig fConfig;
    /**
     * Default constructor. 
     */
    public NotificationFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;

		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");

		Connection connection = (Connection) fConfig.getServletContext().getAttribute("dbConn");
		NotificationDAO notificationdao = new JDBCNotificationDAOImpl();
		notificationdao.setConnection(connection);
		
		List<Notification> notifications = new ArrayList<Notification>();
		notifications.addAll(notificationdao.getAllByIdu(user.getIdu()));
		if (!notifications.isEmpty())
			session.setAttribute("notifications", true);
		else
			session.setAttribute("notifications", false);
		session.setAttribute("notificationslist", notifications);
		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.fConfig = fConfig;
	}

}
