package es.justo.giiis.pi.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.justo.giiis.pi.model.User;

public class JDBCUserDAOImpl implements UserDAO {

	private Connection conn;
	private static final Logger logger = Logger.getLogger(JDBCUserDAOImpl.class.getName());
	private static final String TAG = "JDBCUserImpl: ";

	@Override
	public User get(long idu) {
		if (conn == null) return null;
		
		User user = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM User WHERE idu ="+idu);			 
			if (!rs.next()) return null; 
			user  = new User();	 
			user.setIdu(rs.getInt("idu"));
			user.setUsername(rs.getString("username"));
			user.setEmail(rs.getString("email"));
			user.setPassword(rs.getString("password"));
			user.setIdi(rs.getInt("idi"));
			logger.info(TAG + "fetching User by idu: "+idu+" -> "+user.getIdu()+" "+user.getUsername()+" "+user.getEmail()+" "+user.getPassword());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	@Override
	public User get(String username) {
		if (conn == null) return null;
		
		User user = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM User WHERE username ='"+username+"'");			 
			if (!rs.next()) return null; 
			user  = new User();	 
			user.setIdu(rs.getInt("idu"));
			user.setUsername(rs.getString("username"));
			user.setEmail(rs.getString("email"));
			user.setPassword(rs.getString("password"));
			user.setIdi(rs.getInt("idi"));
			logger.info(TAG + "fetching User by name: "+ username + " -> "+ user.getIdu()+" "+user.getUsername()+" "+user.getEmail()+" "+user.getPassword());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	@Override
	public User getByEmail(String email) {
		if (conn == null) return null;
		
		User user = null;		
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM User WHERE email ='"+email+"'");			 
			if (!rs.next()) return null; 
			user  = new User();	 
			user.setIdu(rs.getInt("idu"));
			user.setUsername(rs.getString("username"));
			user.setEmail(rs.getString("email"));
			user.setPassword(rs.getString("password"));
			user.setIdi(rs.getInt("idi"));
			logger.info(TAG + "fetching User by email: " + email + " -> "+ user.getIdu()+" "+user.getUsername()+" "+user.getEmail()+" "+user.getPassword());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	
	public List<User> getAll() {

		if (conn == null) return null;
		
		ArrayList<User> users = new ArrayList<User>();
		try {
			Statement stmt;
			ResultSet rs;
			synchronized(conn){
			  stmt = conn.createStatement();
			  rs = stmt.executeQuery("SELECT * FROM User");
			}
			while ( rs.next() ) {
				User user = new User();
				user.setIdu(rs.getInt("idu"));
				user.setUsername(rs.getString("username"));
				user.setEmail(rs.getString("email"));
				//user.setPassword(rs.getString("password"));
				user.setPassword("********");//We return all users with a hidden password
				user.setIdi(rs.getInt("idi"));
				
				users.add(user);
				logger.info(TAG + "fetching users: "+user.getIdu()+" "+user.getUsername()+" "+user.getEmail()+" "+user.getPassword());
								
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}
	

	@Override
	public long add(User user) {
		long idu=-1;
		long lastidu=-1;
		if (conn != null){

			Statement stmt;
			
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_sequence WHERE name ='User'");			 
				if (!rs.next()) return -1; 
				lastidu=rs.getInt("seq");
								
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO User (username,email,password,idi) VALUES('"
									+user.getUsername()+"','"
									+user.getEmail()+"','"
									+user.getPassword()+"',"
									+user.getIdi()+")");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM sqlite_sequence WHERE name ='User'");			 
				if (!rs.next()) return -1; 
				idu=rs.getInt("seq");
				if (idu<=lastidu) return -1;
											
				logger.info(TAG + "CREATING User("+idu+"): "+user.getUsername()+" "+user.getEmail()+" "+user.getPassword());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return idu;
	}

	@Override
	public boolean save(User user) {
		boolean done = false;
		if (conn != null){
			
			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("UPDATE User SET username='"+user.getUsername()
									+"', email='"+user.getEmail()
									+"', password='"+user.getPassword()
									+"', idi="+user.getIdi()
									+" WHERE idu = "+user.getIdu());
				logger.info(TAG + "updating User: "+user.getIdu()+" "+user.getUsername()+" "+user.getEmail()+" "+user.getPassword());
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return done;

	}

	@Override
	public boolean delete(long idu) {
		boolean done = false;
		if (conn != null){

			Statement stmt;
			try {
				stmt = conn.createStatement();
				stmt.executeUpdate("DELETE FROM User WHERE idu ="+idu);
				logger.info(TAG + "deleting User: "+idu);
				done= true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return done;
	}

	@Override
	public void setConnection(Connection conn) {
		// TODO Auto-generated method stub
		this.conn = conn;
	}

	
}
