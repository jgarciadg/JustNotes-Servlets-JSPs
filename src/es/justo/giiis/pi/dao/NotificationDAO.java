package es.justo.giiis.pi.dao;

import java.sql.Connection;
import java.util.List;

import es.justo.giiis.pi.model.Notification;

public interface NotificationDAO {
	public void setConnection(Connection conn);

	public List<Notification> getAllByIdu(int idu); 
	
	public Notification get(int idu, int idrequest);
	
	public boolean add(Notification notification);
	
	public boolean delete(Notification notification);
	
	public boolean deleteAllByIdu(int idu);
}
