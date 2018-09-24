package es.justo.giiis.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.justo.giiis.pi.model.Notification;

public class JDBCNotificationDAOImpl implements NotificationDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCNoteDAOImpl.class.getName());
	
	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<Notification> getAllByIdu(int idu) {
		if (conn == null) return null;
		
		ArrayList<Notification> notifications = new ArrayList<Notification>();
		try {
			Statement stmt;
			ResultSet rs;
			
			synchronized(conn){
			  stmt = conn.createStatement();
			  rs = stmt.executeQuery("SELECT * FROM Notification WHERE idu=" + idu);
			}
			
			while ( rs.next() ) {
				Notification notification = new Notification();
				notification.setIdu(rs.getInt("idu"));
				notification.setIdrequest(rs.getInt("idrequest"));
				
				notifications.add(notification);
				logger.info("fetching notifications: "+notification.getIdu()+" "+notification.getIdrequest());	
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return notifications;
	}

	@Override
	public Notification get(int idu, int idrequest) {
		if (conn == null) return null;
		
		Notification notification = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Notification WHERE idu ="+idu+" AND idrequest="+idrequest);			 
			if (!rs.next()) return null; 
			notification  = new Notification();	 
			notification.setIdu(rs.getInt("idu"));
			notification.setIdrequest(rs.getInt("idrequest"));
			
			logger.info("fetching Notification by idu and idrequest: "+ notification.getIdu() + notification.getIdrequest());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return notification;
	}

	@Override
	public boolean delete(Notification notification) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM Notification WHERE idu="+notification.getIdu() + " AND idrequest=" + notification.getIdrequest());
				logger.info("deleting Notification: "+notification.getIdu()+ " " + notification.getIdrequest());
				done= true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean deleteAllByIdu(int idu) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM Notification WHERE idu="+idu);
				logger.info("deleting Notification: "+idu);
				done= true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean add(Notification notification) {
		boolean done = false;
		if (conn != null) {

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO Notification (idu, idrequest) VALUES(" + notification.getIdu()  + ", "+ notification.getIdrequest() + ")"); 

				logger.info("creating Notification:" + notification.getIdu()  + ", "+ notification.getIdrequest());
				done = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return done;
	}

}
