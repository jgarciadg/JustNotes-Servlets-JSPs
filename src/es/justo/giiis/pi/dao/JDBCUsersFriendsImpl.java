package es.justo.giiis.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.justo.giiis.pi.model.UsersFriends;

public class JDBCUsersFriendsImpl implements UsersFriendsDAO {
	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCUserDAOImpl.class.getName());
	private static final String TAG = "JDBCUsersFriendsImpl: ";
	
	@Override
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<UsersFriends> getAllByIdu(int idu) {
		if (conn == null) return null;
		
		ArrayList<UsersFriends> friends = new ArrayList<UsersFriends>();
		try {
			Statement stmt;
			ResultSet rs;
			synchronized(conn){
			  stmt = conn.createStatement();
			  rs = stmt.executeQuery("SELECT * FROM UsersFriends WHERE idu=" + idu);
			}
			while ( rs.next() ) {
				UsersFriends userfriend = new UsersFriends();
				userfriend.setIdu(rs.getInt("idu"));
				userfriend.setIdfriend(rs.getInt("idfriend"));
				
				friends.add(userfriend);
				logger.info(TAG + "fetching userfriends: "+userfriend.getIdu()+" "+userfriend.getIdfriend());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return friends;
	}

	@Override
	public boolean add(UsersFriends userfriend) {
		boolean done = false;
		if (conn != null) {

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO UsersFriends (idu,idfriend) VALUES(" + userfriend.getIdu() + ", "+ userfriend.getIdfriend() + ")"); 

				logger.info("creating UsersFriends:" + userfriend.getIdu() + ", "+ userfriend.getIdfriend());
				done = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public boolean delete(UsersFriends userfriend) {
		boolean done = false;
		if (conn != null) {

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM UsersFriends WHERE idu =" + userfriend.getIdu() + " AND idfriend=" + userfriend.getIdfriend());
				
				logger.info("deleting UsersFriends: " + userfriend.getIdu() + ", " +  userfriend.getIdfriend());
				done = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public UsersFriends get(int idu, int idfriend) {
		if (conn == null) return null;
		
		UsersFriends userfriend = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT idu,idfriend FROM UsersFriends WHERE idu="+idu +" AND idfriend="+idfriend);		
			
			if (!rs.next()) return null; 
			userfriend  = new UsersFriends();	 
			userfriend.setIdu(rs.getInt("idu"));
			userfriend.setIdfriend(rs.getInt("idfriend"));
			
			logger.info("fetching UserFriend by idu and idfriend:  -> "+ userfriend.getIdu() + " " + userfriend.getIdfriend());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userfriend;
	}

}
