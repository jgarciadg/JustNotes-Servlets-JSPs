package es.justo.giiis.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.justo.giiis.pi.model.Label;

public class JDBCLabelDAOImpl implements LabelDAO {
	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCLabelDAOImpl.class.getName());
	
	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public List<Label> getAllByIdn(long idn) {
		if (conn == null)
			return null;

		ArrayList<Label> labels = new ArrayList<Label>();
		try {
			Statement stmt;
			ResultSet rs;
			synchronized (conn) {
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT * FROM Label WHERE idn=" + idn);
			}
			while (rs.next()) {
				Label label = new Label();
				label.setIdn(rs.getInt("idn"));
				label.setContent(rs.getString("content"));

				labels.add(label);
				logger.info("fetching labels: " + label.getIdn() + " " + label.getContent());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return labels;
	}
	

	@Override
	public boolean add(Label label) {
		boolean done = false;
		if (conn != null) {

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO Label (idn, content) VALUES('" + label.getIdn() + "','" + label.getContent() + "')");

				done = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean save(Label label) {
		boolean done = false;
		if (conn != null) {

			Statement stmt;
			try {
				stmt = conn.createStatement();

				stmt.executeUpdate("UPDATE Label SET label='" + label.getContent() + "' WHERE idn=" + label.getIdn());

				logger.info("updating label: " + label.getIdn() + " " + label.getContent());
				done = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean delete(long idn, String content) {
		boolean done = false;
		if (conn != null) {

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM Label WHERE idn=" + idn + " AND content='" + content +"'");
				logger.info("deleting UsersNotes: idn =" + idn + " AND content=" + content);
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
				stmt.executeUpdate("DELETE FROM Label WHERE idn=" + idn);
				logger.info("deleting All Label: with idn=" + idn);
				done = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public List<String> getTheMostFrecuent(long idu) {
		if (conn == null)
			return null;
		
		List<String> mostfrequent = new ArrayList<String>();
		String query = "SELECT count(content), content \n" + 
							"FROM Label l JOIN UsersNotes un USING(idn)\n" + 
							"WHERE idu="+ idu +"\n" + 
							"GROUP BY content \n" + 
							"ORDER BY count(content) DESC \n" + 
							"LIMIT 3";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while (rs.next()) {
				mostfrequent.add(rs.getString("content"));
			}
			
			logger.info("counting most frequent colors: ");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mostfrequent;
	}

	@Override
	public List<Label> getAllByContent(String labelstr) {
		if (conn == null)
			return null;

		ArrayList<Label> labels = new ArrayList<Label>();
		try {
			Statement stmt;
			ResultSet rs;
			synchronized (conn) {
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT * FROM Label WHERE content='" + labelstr + "'");
			}
			while (rs.next()) {
				Label label = new Label();
				label.setIdn(rs.getInt("idn"));
				label.setContent(rs.getString("content"));

				labels.add(label);
				logger.info("fetching labels: " + label.getIdn() + " " + label.getContent());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return labels;
	}
}
