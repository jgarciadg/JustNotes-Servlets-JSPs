package es.justo.giiis.pi.dao;

import java.sql.Connection;
import java.util.List;

import es.justo.giiis.pi.model.Label;

public interface LabelDAO {

	/**
	 * Get all labels from a note
	 * 
	 * @param idn
	 *            IDN of the note
	 * @return label from this note
	 */
	public List<Label> getAllByIdn(long idn);
	
	public List<Label> getAllByContent(String label);

	public void setConnection(Connection conn);

	public boolean add(Label label);

	public boolean save(Label label);

	public boolean delete(long idn, String content);

	public boolean deleteAll(long idn);
	
	public List<String> getTheMostFrecuent(long idu);
}
