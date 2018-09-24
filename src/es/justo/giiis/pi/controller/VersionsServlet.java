package es.justo.giiis.pi.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import es.justo.giiis.pi.dao.JDBCUserDAOImpl;
import es.justo.giiis.pi.dao.JDBCUsersNotesDAOImpl;
import es.justo.giiis.pi.dao.JDBCVersionsNotesDAO;
import es.justo.giiis.pi.dao.NoteDAO;
import es.justo.giiis.pi.dao.UserDAO;
import es.justo.giiis.pi.dao.UsersNotesDAO;
import es.justo.giiis.pi.dao.VersionsNotesDAO;
import es.justo.giiis.pi.model.Note;
import es.justo.giiis.pi.model.User;
import es.justo.giiis.pi.model.UsersNotes;
import es.justo.giiis.pi.model.VersionsNotes;
import es.justo.giiis.pi.util.Triplet;

/**
 * Servlet implementation class VersionsServlet
 */
@WebServlet("/notes/VersionsServlet")
public class VersionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(HttpServlet.class.getName());
	private static final String TAG = "VersionsServlet: ";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VersionsServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info(TAG + "VersionsNote By GET");
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		String idnstr = request.getParameter("idn");
		if (idnstr != null && idnstr.matches("[0-9]+")) {
			Connection connection = (Connection) getServletContext().getAttribute("dbConn");

			UsersNotesDAO usersnotesdao = new JDBCUsersNotesDAOImpl();
			usersnotesdao.setConnection(connection);
			
			Integer idn = Integer.parseInt(idnstr);
			UsersNotes usernote = usersnotesdao.get(user.getIdu(), idn);
			if (usernote != null) {
				VersionsNotesDAO versionsnotesdao = new JDBCVersionsNotesDAO();
				versionsnotesdao.setConnection(connection);
				List<VersionsNotes> versionsnote = versionsnotesdao.getAllByIdn(usernote.getIdn());
				
				NoteDAO notedao = new JDBCNoteDAOImpl();
				notedao.setConnection(connection);
				Note note = (Note) notedao.get(usernote.getIdn());
				request.setAttribute("note", note);
				
				List<Triplet<UsersNotes, Note, List<String>>> notes = new ArrayList<Triplet<UsersNotes, Note, List<String>>>();
				for (VersionsNotes version : versionsnote) {
					UsersNotes usernoteversion = new UsersNotes(version.getIdu(),version.getIdn(),version.getOwner(),version.getArchived(),
							version.getPinned(),version.getIntrash(),version.getColor());
					Note noteversion = new Note(version.getIdn(), version.getTitle(), version.getContent(), version.getUrlimage());
					
					List<String> data = new ArrayList<String>();
					UserDAO userdao = new JDBCUserDAOImpl();
					userdao.setConnection(connection);
					data.add(userdao.get(version.getIdu()).getUsername());
					data.add(version.getTimestamp());
					Triplet<UsersNotes, Note, List<String>> triplet = new Triplet<UsersNotes, Note, List<String>>(usernoteversion, noteversion, data);
					notes.add(triplet);
				}
				Collections.sort(notes, new Comparator<Triplet<UsersNotes, Note, List<String>>>(){
                    public int compare(Triplet<UsersNotes, Note, List<String>> t1, Triplet<UsersNotes, Note, List<String>> t2){
                    	return t2.getThird().get(1).compareTo(t1.getThird().get(1));
                    }});
				request.setAttribute("notes", notes);
			} else 
				logger.info(TAG + "User (" + user.getUsername() + ") try to get foreigns versions");
		} 
		
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/jsp/versions.jsp");
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection connection = (Connection) getServletContext().getAttribute("dbConn");

		logger.info(TAG + "VersionsNote By POST");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		String timestamp_to_recover = request.getParameter("timestamp");
		String idn_to_recover = request.getParameter("idn");
		
		if(timestamp_to_recover != null && !timestamp_to_recover.equals("") && idn_to_recover != null && idn_to_recover.matches("[0-9]+")) {
			VersionsNotesDAO versionsnotesdao = new JDBCVersionsNotesDAO();
			versionsnotesdao.setConnection(connection);
			VersionsNotes version = versionsnotesdao.get(Integer.parseInt(idn_to_recover), timestamp_to_recover);
	
			if(version != null) {
				logger.info(TAG + "Recovering version " + version.toString());
				UsersNotes usernoteversion = new UsersNotes(user.getIdu(),version.getIdn(),version.getOwner(),version.getArchived(),
						version.getPinned(),version.getIntrash(),version.getColor());
				UsersNotesDAO usersnotesdao = new JDBCUsersNotesDAOImpl();
				usersnotesdao.setConnection(connection);
				usersnotesdao.save(usernoteversion);
				
				Note noteversion = new Note(version.getIdn(), version.getTitle(), version.getContent(), version.getUrlimage());
				NoteDAO notedao = new JDBCNoteDAOImpl();
				notedao.setConnection(connection);
				notedao.save(noteversion);	
			}
		}
		response.sendRedirect(request.getContextPath() + "/notes/MainServlet");
	}

}
