package es.justo.giiis.pi.dao;

import java.sql.Connection;
import java.util.List;

import es.justo.giiis.pi.model.ProfileImage;

public interface ProfileImageDAO {
	/**
	 * Sets the database connection in this DAO.
	 * 
	 * @param conn
	 *            database connection.
	 */
	public void setConnection(Connection conn);
	
	public List<ProfileImage> getAll();
	
	public ProfileImage getByIdi(int idi);
}
