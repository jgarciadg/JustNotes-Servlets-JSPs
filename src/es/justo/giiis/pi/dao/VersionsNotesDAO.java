package es.justo.giiis.pi.dao;

import java.sql.Connection;
import java.util.List;

import es.justo.giiis.pi.model.VersionsNotes;

public interface VersionsNotesDAO {
	public void setConnection(Connection conn);
	
	public List<VersionsNotes> getAll();
	
	public List<VersionsNotes> getAllByIdu(int idu);
		
	public List<VersionsNotes> getAllByIdn(int idn);
	
	public List<VersionsNotes> getAllByIdnAndIdu(int idu, int idn);
	
	public VersionsNotes get(int idn, String timestamp);

	public boolean deleteAllByIdn(int idn);
	
	public boolean deleteAllByIdu(int idu);
	
	public boolean add(VersionsNotes version);
}
