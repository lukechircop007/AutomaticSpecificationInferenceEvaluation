package um.edu.mt.fits.dsp0.user;

public class UserSession {
	
	/*
	 *  sid != null
	 *  sid >= 0  
	 */
	protected Integer sid;
	
	/*
	 *  log != null
	 *  log starts empty
	 *  log contains text
	 */
	protected String log;
	
	/*
	 * owner != null
	 * owner >= 0
	 */
	protected Integer owner;
	
	/*
	 * sessionClosed != null
	 * sessionClosed can be false or positive
	 */
	protected boolean sessionClosed;
	
	/*
	 * uid >= 0 
	 * sid >= 0 
	 */	
	public UserSession(Integer uid, Integer sid) {
		this.sid = sid;
		owner = uid;
		log = "";
		sessionClosed = true;
	}

	/*
	 * Nothing changes
	 * return value == sid
	 */
	public Integer getId() { return sid; }
	
	/*
	 * Nothing changes
	 * return value == owner
	 */
	public Integer getOwner() { return owner; }
	
	/*
	 * Nothing Changes
	 * return value == log
	 */
	public String getLog() { return log; }
	
	/*
	 * Nothing Changes
	 * return value == sessionClosed
	 */
	public boolean isSessionClosed(){return sessionClosed;}
	
	/*
	 * Before: 
	 * 	sessionClosed can be true or false
	 * After: 
	 * 	sessionClosed == false
	 * 	Nothing else changes 
	 */
	public void openSession() { sessionClosed = false; }
	
	/*
	 * Before: 
	 * 	sessionClosed can be true or false
	 * After: 
	 * 	sessionClosed == true
	 * 	Nothing else changes
	 */
	public void closeSession() { sessionClosed = true; }
	
	public void log(String l) { log+=l+"\n"; }
	
			
}
