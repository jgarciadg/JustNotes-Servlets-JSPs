package es.justo.giiis.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.justo.giiis.pi.model.VersionsNotes;

public class JDBCVersionsNotesDAO implements VersionsNotesDAO {
	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCUsersNotesDAOImpl.class.getName());

	@Override
	public List<VersionsNotes> getAll() {
		if (conn == null)
			return null;

		ArrayList<VersionsNotes> versionsNotesList = new ArrayList<VersionsNotes>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM VersionsNotes");

			while (rs.next()) {
				VersionsNotes versionnote = new VersionsNotes();
				versionnote.setIdu(rs.getInt("idu"));
				versionnote.setIdn(rs.getInt("idn"));
				versionnote.setOwner(rs.getInt("owner"));
				versionnote.setArchived(rs.getInt("archived"));
				versionnote.setPinned(rs.getInt("pinned"));
				versionnote.setIntrash(rs.getInt("intrash"));
				versionnote.setColor(rs.getInt("color"));
				versionnote.setTimestamp(rs.getString("timestamp"));
				versionnote.setTitle(rs.getString("title"));
				versionnote.setContent(rs.getString("content"));
				versionnote.setUrlimage(rs.getString("urlimage"));

				versionsNotesList.add(versionnote);
				logger.info("fetching all versionsnote: " + versionnote.toString());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return versionsNotesList;
	}

	@Override
	public List<VersionsNotes> getAllByIdu(int idu) {
		if (conn == null)
			return null;

		ArrayList<VersionsNotes> versionsNotesList = new ArrayList<VersionsNotes>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM VersionsNotes WHERE idu=" + idu);

			while (rs.next()) {
				VersionsNotes versionnote = new VersionsNotes();
				versionnote.setIdu(rs.getInt("idu"));
				versionnote.setIdn(rs.getInt("idn"));
				versionnote.setOwner(rs.getInt("owner"));
				versionnote.setArchived(rs.getInt("archived"));
				versionnote.setPinned(rs.getInt("pinned"));
				versionnote.setIntrash(rs.getInt("intrash"));
				versionnote.setColor(rs.getInt("color"));
				versionnote.setTimestamp(rs.getString("timestamp"));
				versionnote.setTitle(rs.getString("title"));
				versionnote.setContent(rs.getString("content"));
				versionnote.setUrlimage(rs.getString("urlimage"));

				versionsNotesList.add(versionnote);
				logger.info("fetching all versionsnote: " + versionnote.toString());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return versionsNotesList;
	}

	@Override
	public List<VersionsNotes> getAllByIdn(int idn) {
		if (conn == null)
			return null;

		ArrayList<VersionsNotes> versionsNotesList = new ArrayList<VersionsNotes>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM VersionsNotes WHERE idn=" + idn);

			while (rs.next()) {
				VersionsNotes versionnote = new VersionsNotes();
				versionnote.setIdu(rs.getInt("idu"));
				versionnote.setIdn(rs.getInt("idn"));
				versionnote.setOwner(rs.getInt("owner"));
				versionnote.setArchived(rs.getInt("archived"));
				versionnote.setPinned(rs.getInt("pinned"));
				versionnote.setIntrash(rs.getInt("intrash"));
				versionnote.setColor(rs.getInt("color"));
				versionnote.setTimestamp(rs.getString("timestamp"));
				versionnote.setTitle(rs.getString("title"));
				versionnote.setContent(rs.getString("content"));
				versionnote.setUrlimage(rs.getString("urlimage"));

				versionsNotesList.add(versionnote);
				logger.info("fetching all versionsnote: " + versionnote.toString());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return versionsNotesList;
	}

	@Override
	public List<VersionsNotes> getAllByIdnAndIdu(int idu, int idn) {
		if (conn == null)
			return null;

		ArrayList<VersionsNotes> versionsNotesList = new ArrayList<VersionsNotes>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM VersionsNotes WHERE idu=" + idu + " AND idn=" + idn);

			while (rs.next()) {
				VersionsNotes versionnote = new VersionsNotes();
				versionnote.setIdu(rs.getInt("idu"));
				versionnote.setIdn(rs.getInt("idn"));
				versionnote.setOwner(rs.getInt("owner"));
				versionnote.setArchived(rs.getInt("archived"));
				versionnote.setPinned(rs.getInt("pinned"));
				versionnote.setIntrash(rs.getInt("intrash"));
				versionnote.setColor(rs.getInt("color"));
				versionnote.setTimestamp(rs.getString("timestamp"));
				versionnote.setTitle(rs.getString("title"));
				versionnote.setContent(rs.getString("content"));
				versionnote.setUrlimage(rs.getString("urlimage"));

				versionsNotesList.add(versionnote);
				logger.info("fetching all versionsnote: " + versionnote.toString());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return versionsNotesList;
	}

	@Override
	public boolean deleteAllByIdn(int idn) {
		boolean done = false;
		if (conn != null) {

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM VersionsNotes WHERE idn=" + idn);
				logger.info("deleting All VersionsNotes: with idn=" + idn);
				done = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean deleteAllByIdu(int idu) {
		boolean done = false;
		if (conn != null) {

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM VersionsNotes WHERE idu=" + idu);
				logger.info("deleting All VersionsNotes: with idu=" + idu);
				done = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean add(VersionsNotes version) {
		boolean done = false;
		if (conn != null) {

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate(
						"INSERT INTO VersionsNotes (idu,idn,owner,archived,pinned,intrash,color,timestamp,title,content,urlimage) VALUES("
								+ version.getIdu() + "," + version.getIdn() + "," + version.getOwner() + ","
								+ version.getArchived() + "," + version.getPinned() + "," + version.getIntrash() + ","
								+ version.getColor() + ",'" + version.getTimestamp() + "','" + version.getTitle()
								+ "','" + version.getContent() + "','" + version.getUrlimage() + "')");

				logger.info("creating VersionNote" + version.toString());
				done = true;
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

	@Override
	public VersionsNotes get(int idn, String timestamp) {
		if (conn == null)
			return null;

		VersionsNotes versionnote = new VersionsNotes();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM VersionsNotes WHERE idn=" + idn + " AND timestamp LIKE '" + timestamp + "'");

			versionnote.setIdu(rs.getInt("idu"));
			versionnote.setIdn(rs.getInt("idn"));
			versionnote.setOwner(rs.getInt("owner"));
			versionnote.setArchived(rs.getInt("archived"));
			versionnote.setPinned(rs.getInt("pinned"));
			versionnote.setIntrash(rs.getInt("intrash"));
			versionnote.setColor(rs.getInt("color"));
			versionnote.setTimestamp(rs.getString("timestamp"));
			versionnote.setTitle(rs.getString("title"));
			versionnote.setContent(rs.getString("content"));
			versionnote.setUrlimage(rs.getString("urlimage"));

			logger.info("fetching versionsnote: " + versionnote.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return versionnote;
	}

}
