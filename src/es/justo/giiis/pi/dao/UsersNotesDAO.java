package es.justo.giiis.pi.dao;

import java.sql.Connection;
import java.util.List;

import es.justo.giiis.pi.model.UsersNotes;




public interface UsersNotesDAO {

	/**
	 * set the database connection in this DAO.
	 * 
	 * @param conn
	 *            database connection.
	 */
	public void setConnection(Connection conn);

	/**
	 * Gets all the notes and the users related to them from the database.
	 * 
	 * @return List of all the notes and the users related to them from the database.
	 */
	
	public List<UsersNotes> getAll();

	/**
	 * Gets all userNotes objects correct to the query.
	 * 
	 * @param idu
	 *            User identifier
	 * @param idn
	 *            Note identifier
	 * @param owner
	 *            1 true, 0 false  
	 * @param archived
	 *            1 true, 0 false   
	 * @param pinned
	 *            1 true, 0 false                       
	 *           
	 * @return List of all the notes and the users related to them from the database.
	 */
	public List<UsersNotes> getAllNotesByQuery(long idu, long idn, int owner, int archived, int pinned, int intrash, int color);
	
	/**
	 *Gets all the UsersNotes that are related to an user  (either the user owns them or they were shared with the user).
	 * 
	 * @param idu
	 *            User identifier
	 * 
	 * @return List of all the UsersNotes that an user can access (either owns them or they were shared with the user).
	 */
	public List<UsersNotes> getAllByUser(long idu);
	
	/**
	 * Gets all the UsersNotes that can access a specific note (either the users own it or it was shared with them).
	 * 
	 * @param idn
	 *            Note Identifier
	 * 
	 * @return List of all the UsersNotes that can access a specific note (either the users own it or it was shared with them).
	 */
	public List<UsersNotes> getAllByNote(long idn);

	/**
	 * Gets an UsersNotes from the DB using idu and idn.
	 * 
	 * @param idu username
	 *            Username of the user.
	 *            
	 * @param idn
	 *            Note Identifier
	 * 
	 * @return UsersNotes with that idu and idn.
	 */
	
	public UsersNotes get(long idu,long idn);

	/**
	 * Adds an userNote to the database.
	 * 
	 * @param userNote
	 *            UserNote object with the details of the relation between the user and the order.
	 * 
	 * @return User identifier or -1 in case the operation failed.
	 */
	
	public boolean add(UsersNotes userNote);

	/**
	 * Updates an existing userNote in the database.
	 * 
	 * @param userNote
	 *            UserNote object with the new details of the existing relation between the user and the order. 
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	
	public boolean save(UsersNotes userNote);

	/**
	 * Deletes an userNote from the database.
	 * 
	 * @param idu
	 *            User identifier.
	 *            
	 * @param idn
	 *            Note Identifier
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	
	public boolean delete(long idu, long idn);

	/**
	 * Deletes all usernotes from an usernote from the database.
	 * 
	 * @param idn
	 *            Note Identifier
	 * 
	 * @return True if the operation was made and False if the operation failed.
	 */
	public boolean deleteAll(long idn);

	public Integer getHowManyByQuery(long idu, long idn, int owner, int archived, int pinned, int intrash, int color);
	
	public List<Integer> getTheMostFrecuent(long idu);
}