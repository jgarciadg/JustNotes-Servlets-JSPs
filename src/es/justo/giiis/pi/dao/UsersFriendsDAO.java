package es.justo.giiis.pi.dao;

import java.sql.Connection;
import java.util.List;

import es.justo.giiis.pi.model.UsersFriends;

public interface UsersFriendsDAO {
	public void setConnection(Connection conn);
	
	public List<UsersFriends> getAllByIdu(int idu);
	
	public UsersFriends get(int idu, int idfriend);
	
	public boolean add(UsersFriends userfriend);
		
	public boolean delete(UsersFriends userfriend);

}
