package es.justo.giiis.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.justo.giiis.pi.model.UsersNotes;

public class JDBCUsersNotesDAOImpl implements UsersNotesDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCUsersNotesDAOImpl.class.getName());

	@Override
	public List<UsersNotes> getAll() {

		if (conn == null)
			return null;

		ArrayList<UsersNotes> usersNotesList = new ArrayList<UsersNotes>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM UsersNotes");

			while (rs.next()) {
				UsersNotes usersNotes = new UsersNotes();
				usersNotes.setIdu(rs.getInt("idu"));
				usersNotes.setIdn(rs.getInt("idn"));
				usersNotes.setOwner(rs.getInt("owner"));
				usersNotes.setArchived(rs.getInt("archived"));
				usersNotes.setPinned(rs.getInt("pinned"));
				usersNotes.setIntrash(rs.getInt("intrash"));
				usersNotes.setColor(rs.getInt("color"));

				usersNotesList.add(usersNotes);
				logger.info("fetching all usersNotes: " + usersNotes.getIdu() + " " + usersNotes.getIdn() + " "
						+ usersNotes.getOwner() + " " + usersNotes.getArchived() + " " + usersNotes.getPinned() + " " + usersNotes.getIntrash() + " " + usersNotes.getColor());

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return usersNotesList;
	}

	@Override
	public List<UsersNotes> getAllByUser(long idu) {

		if (conn == null)
			return null;

		ArrayList<UsersNotes> usersNotesList = new ArrayList<UsersNotes>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM UsersNotes WHERE idu=" + idu);

			while (rs.next()) {
				UsersNotes usersNotes = new UsersNotes();
				usersNotes.setIdu(rs.getInt("idu"));
				usersNotes.setIdn(rs.getInt("idn"));
				usersNotes.setOwner(rs.getInt("owner"));
				usersNotes.setArchived(rs.getInt("archived"));
				usersNotes.setPinned(rs.getInt("pinned"));
				usersNotes.setIntrash(rs.getInt("intrash"));
				usersNotes.setColor(rs.getInt("color"));

				usersNotesList.add(usersNotes);
				logger.info("fetching all usersNotes by idu: " + usersNotes.getIdu() + "->" + usersNotes.getIdn() + " "
						+ usersNotes.getOwner() + " " + usersNotes.getArchived() + " " + usersNotes.getPinned() + " " + usersNotes.getIntrash() + " " + usersNotes.getColor());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return usersNotesList;
	}

	@Override
	public List<UsersNotes> getAllByNote(long idn) {

		if (conn == null)
			return null;

		ArrayList<UsersNotes> usersNotesList = new ArrayList<UsersNotes>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM UsersNotes WHERE idn=" + idn);

			while (rs.next()) {
				UsersNotes usersNotes = new UsersNotes();
				usersNotes.setIdu(rs.getInt("idu"));
				usersNotes.setIdn(rs.getInt("idn"));
				usersNotes.setOwner(rs.getInt("owner"));
				usersNotes.setArchived(rs.getInt("archived"));
				usersNotes.setPinned(rs.getInt("pinned"));
				usersNotes.setIntrash(rs.getInt("intrash"));
				usersNotes.setColor(rs.getInt("color"));

				usersNotesList.add(usersNotes);
				logger.info("fetching all usersNotes by idn: " + usersNotes.getIdn() + "-> " + usersNotes.getIdu() + " "
						+ usersNotes.getOwner() + " " + usersNotes.getArchived() + " " + usersNotes.getPinned() + " " + usersNotes.getIntrash() + " " + usersNotes.getColor());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return usersNotesList;
	}

	@Override
	public UsersNotes get(long idu, long idn) {
		if (conn == null)
			return null;

		UsersNotes usersNotes = null;

		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM UsersNotes WHERE idu=" + idu + " AND idn=" + idn);
			if (!rs.next())
				return null;
			usersNotes = new UsersNotes();
			usersNotes.setIdu(rs.getInt("idu"));
			usersNotes.setIdn(rs.getInt("idn"));
			usersNotes.setOwner(rs.getInt("owner"));
			usersNotes.setArchived(rs.getInt("archived"));
			usersNotes.setPinned(rs.getInt("pinned"));
			usersNotes.setIntrash(rs.getInt("intrash"));
			usersNotes.setColor(rs.getInt("color"));

			logger.info("fetching usersNotes by idu: " + usersNotes.getIdu() + "  and idn: " + usersNotes.getIdn() + " "
					+ usersNotes.getOwner() + " " + usersNotes.getArchived() + " " + usersNotes.getPinned() + " " + usersNotes.getIntrash() + " " + usersNotes.getColor());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return usersNotes;
	}

	@Override
	public boolean add(UsersNotes usersNotes) {
		boolean done = false;
		if (conn != null) {

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO UsersNotes (idu,idn,owner,archived,pinned,intrash,color) VALUES('"
						+ usersNotes.getIdu() + "','" + usersNotes.getIdn() + "','" + usersNotes.getOwner() + "','"
						+ usersNotes.getArchived() + "','" + usersNotes.getPinned() + "','" + usersNotes.getIntrash() + "','" + usersNotes.getColor() + "')");

				logger.info("creating UsersNotes:(" + usersNotes.getIdu() + " " + usersNotes.getIdn() + " "
						+ usersNotes.getOwner() + " " + usersNotes.getArchived() + " " + usersNotes.getPinned() + " " + usersNotes.getIntrash() + " " + usersNotes.getColor());
				done = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean save(UsersNotes usersNotes) {
		boolean done = false;
		if (conn != null) {

			Statement stmt;
			try {
				stmt = conn.createStatement();

				stmt.executeUpdate("UPDATE UsersNotes SET owner='" + usersNotes.getOwner() + "', archived='"
						+ usersNotes.getArchived() + "', pinned='" + usersNotes.getPinned() + "', intrash='" + usersNotes.getIntrash() +"', color='" + usersNotes.getColor()  + "' WHERE idu = "
						+ usersNotes.getIdu() + " AND idn=" + usersNotes.getIdn());

				logger.info("updating UsersNotes:(" + usersNotes.getIdu() + " " + usersNotes.getIdn() + " "
						+ usersNotes.getOwner() + " " + usersNotes.getArchived() + " " + usersNotes.getPinned() + " " + usersNotes.getIntrash() + " " + usersNotes.getColor());

				done = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean delete(long idu, long idn) {
		boolean done = false;
		if (conn != null) {

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM UsersNotes WHERE idu =" + idu + " AND idn=" + idn);
				logger.info("deleting UsersNotes: " + idu + " , idn=" + idn);
				done = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean deleteAll(long idn) {
		boolean done = false;
		if (conn != null) {

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM UsersNotes WHERE idn=" + idn);
				logger.info("deleting All UsersNotes: with idn=" + idn);
				done = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<UsersNotes> getAllNotesByQuery(long idu, long idn, int owner, int archived, int pinned, int intrash, int color) {
		if (conn == null)
			return null;

		ArrayList<UsersNotes> usersNotesList = new ArrayList<UsersNotes>();
		try {
			String query = "SELECT * FROM UsersNotes WHERE";
			if(idu != -1) query += " idu=" + idu;
			if(idn != -1) query += " AND idn=" + idn;
			if(owner != -1) query += " AND owner=" + owner;
			if(archived != -1) query += " AND archived=" + archived;
			if(pinned != -1) query += " AND pinned=" + pinned;
			if(intrash != -1) query += " AND intrash=" + intrash;
			if(color != -1) query += " AND color=" + color;
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				UsersNotes usersNotes = new UsersNotes();
				usersNotes.setIdu(rs.getInt("idu"));
				usersNotes.setIdn(rs.getInt("idn"));
				usersNotes.setOwner(rs.getInt("owner"));
				usersNotes.setArchived(rs.getInt("archived"));
				usersNotes.setPinned(rs.getInt("pinned"));
				usersNotes.setIntrash(rs.getInt("intrash"));
				usersNotes.setColor(rs.getInt("color"));

				usersNotesList.add(usersNotes);
				logger.info("fetching all usersNotes by idu: " + usersNotes.getIdu() + "->" + usersNotes.getIdn() + " "
						+ usersNotes.getOwner() + " " + usersNotes.getArchived() + " " + usersNotes.getPinned() + " " + usersNotes.getIntrash() + " " + usersNotes.getColor());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return usersNotesList;
	}
	
	@Override
	public Integer getHowManyByQuery(long idu, long idn, int owner, int archived, int pinned, int intrash, int color) {
		if (conn == null)
			return null;

		Integer count = 0;
		try {
			String query = "SELECT count(*) FROM UsersNotes WHERE";
			if(idu != -1) query += " idu=" + idu;
			if(idn != -1) query += " AND idn=" + idn;
			if(owner != -1) query += " AND owner=" + owner;
			if(archived != -1) query += " AND archived=" + archived;
			if(pinned != -1) query += " AND pinned=" + pinned;
			if(intrash != -1) query += " AND intrash=" + intrash;
			if(color != -1) query += " AND color=" + color;
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			count = rs.getInt("count(*)");

			logger.info("counting all usersNotes by query: ");

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return count;
	}
	
	@Override
	public List<Integer> getTheMostFrecuent(long idu) {
		if (conn == null)
			return null;
		
		List<Integer> mostfrequent = new ArrayList<Integer>();
		String query = "SELECT count(color), color FROM UsersNotes WHERE idu="+idu + " GROUP BY color ORDER BY count(color) DESC";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				mostfrequent.add(rs.getInt("color"));
			}
			
			logger.info("counting most frequent colors: ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mostfrequent;
	}

}
