package es.justo.giiis.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.justo.giiis.pi.model.Note;

public class JDBCNoteDAOImpl implements NoteDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCNoteDAOImpl.class.getName());
	
	@Override
	public Note get(long idn) {
		if (conn == null) return null;
		
		Note note = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Note WHERE idn ="+idn);			 
			if (!rs.next()) return null; 
			note  = new Note();	 
			note.setIdn(rs.getInt("idn"));
			note.setTitle(rs.getString("title"));
			note.setContent(rs.getString("content"));
			note.setUrlimage(rs.getString("urlimage"));
			
			logger.info("fetching Note by idn: "+idn+" -> "+note.getIdn()+" "+note.getTitle()+" "+note.getContent());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return note;
	}
	
	public List<Note> getAll() {

		if (conn == null) return null;
		
		ArrayList<Note> notes = new ArrayList<Note>();
		try {
			Statement stmt;
			ResultSet rs;
			synchronized(conn){
			  stmt = conn.createStatement();
			  rs = stmt.executeQuery("SELECT * FROM Note");
			}
			while ( rs.next() ) {
				Note note = new Note();
				note.setIdn(rs.getInt("idn"));
				note.setTitle(rs.getString("title"));
				note.setContent(rs.getString("content"));
				note.setUrlimage(rs.getString("urlimage"));

				notes.add(note);
				logger.info("fetching notes: "+note.getIdn()+" "+note.getTitle()+" "+note.getContent());
								
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return notes;
	}
	
	public List<Note> getAllBySearchTitle(String search) {
		search = search.toUpperCase();
		if (conn == null)
			return null;

		ArrayList<Note> notes = new ArrayList<Note>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Note WHERE UPPER(title) LIKE '%" + search + "%'");

			while (rs.next()) {
				Note note = new Note();
				
				note.setIdn(rs.getInt("idn"));
				note.setTitle(rs.getString("title"));
				note.setContent(rs.getString("content"));
				note.setUrlimage(rs.getString("urlimage"));

				notes.add(note);
				
				
				logger.info("fetching notes by text in the title: "+search+": "+note.getIdn()+" "+note.getTitle()+" "+note.getContent());
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return notes;
	}
	
	public List<Note> getAllBySearchContent(String search) {
		search = search.toUpperCase();
		if (conn == null)
			return null;

		ArrayList<Note> notes = new ArrayList<Note>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Note WHERE UPPER(content) LIKE '%" + search + "%'");

			while (rs.next()) {
				Note note = new Note();
				
				note.setIdn(rs.getInt("idn"));
				note.setTitle(rs.getString("title"));
				note.setContent(rs.getString("content"));
				note.setUrlimage(rs.getString("urlimage"));

				notes.add(note);
				
				logger.info("fetching notes by text in the content: "+search+": "+note.getIdn()+" "+note.getTitle()+" "+note.getContent());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return notes;
	}
	
	public List<Note> getAllBySearchAll(String search) {
		search = search.toUpperCase();
		if (conn == null)
			return null;

		ArrayList<Note> notes = new ArrayList<Note>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Note WHERE UPPER(title) LIKE '%" + search + "%' OR UPPER(content) LIKE '%" + search + "%'");

			while (rs.next()) {
				Note note = new Note();
				
				note.setIdn(rs.getInt("idn"));
				note.setTitle(rs.getString("title"));
				note.setContent(rs.getString("content"));
				note.setUrlimage(rs.getString("urlimage"));

				notes.add(note);
				
				
				logger.info("fetching notes by text either in the title or in the content: "+search+": "+note.getIdn()+" "+note.getTitle()+" "+note.getContent());
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return notes;
	}

	@Override
	public long add(Note note) {
		long idn=-1;
		long lastidn=-1;
		if (conn != null){

			Statement stmt;
			
			
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_sequence WHERE name ='Note'");			 
				if (!rs.next()) return -1; 
				lastidn=rs.getInt("seq");
								
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO Note (title,content,urlimage) VALUES('"
									+note.getTitle()+"','"+note.getContent()+"','"+note.getUrlimage()+"')");
				
								
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_sequence WHERE name ='Note'");			 
				if (!rs.next()) return -1; 
				idn=rs.getInt("seq");
				if (idn<=lastidn) return -1;
											
				logger.info("CREATING Note("+idn+"): "+note.getTitle()+" "+note.getContent());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return idn;
	}

	@Override
	public boolean save(Note note) {
		boolean done = false;
		if (conn != null){
			
			Statement stmt;
			try {
				stmt = conn.createStatement();
				
				stmt.executeUpdate("UPDATE Note SET title='"+note.getTitle()
				+"', content='"+note.getContent() 
				+"', urlimage='"+note.getUrlimage() 
				+"' WHERE idn = "+note.getIdn());
				logger.info("updating Note: "+note.getIdn()+" "+note.getTitle()+" "+note.getContent());
				
						
				done= true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return done;

	}

	@Override
	public boolean delete(long idn) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM Note WHERE idn ="+idn);
				logger.info("deleting Note: "+idn);
				done= true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public void setConnection(Connection conn) {
				this.conn = conn;
	}

	
}
