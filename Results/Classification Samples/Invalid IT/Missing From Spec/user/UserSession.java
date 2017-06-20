package um.edu.mt.fits.dsp0.user;

public class UserSession {
	
	/*
	 *  sid != null
	 *  sid >= 0 -M  
	 */
	protected Integer sid;
	
	/*
	 *  log != null
	 */
	protected String log;
	
	/*
	 * owner != null
	 * owner >= 0 -M
	 */
	protected Integer owner;
	
	/*
	 * sessionClosed != null -M
	 */
	protected boolean sessionClosed;
	
	/*
	 * uid >= 0  -M
	 * sid >= 0  -M
	 * After:
	 * 	this.sid == sid -M
	 * 	this.owner == uid -M
	 * 	this.log == ""
	 * 	this.sessionClosed == true
	 */	
	public UserSession(Integer uid, Integer sid) {
		this.sid = sid;
		owner = uid;
		log = "";
		sessionClosed = true;
	}

	/*
	 * return value == sid -M
	 *
	 * New Invariants Below: EXIT
	 * this.sid == orig(this.sid) -C
	 * this.log == orig(this.log) -C
	 * this.owner == orig(this.owner) -C
	 * this.sessionClosed == orig(this.sessionClosed) -C
	 * this.log.toString == orig(this.log.toString) -C
	 */
	public Integer getId() { return sid; }
	
	/*
	 * return value == owner -M
	 * 
	 * New Invariants Below:
	 * this.sid == orig(this.sid) -C
	 * this.log == orig(this.log) -C
	 * this.owner == orig(this.owner) -C	 
	 * this.sessionClosed == orig(this.sessionClosed) -C
	 * this.log.toString == orig(this.log.toString) -C
	 */
	public Integer getOwner() { return owner; }
	
	/*
	 * return value == log -M
	 * 
	 * New Invariants Below: 
	 * this.sid == orig(this.sid) -C
	 * this.log == orig(this.log) -C
	 * this.owner == orig(this.owner) -C
	 * this.sessionClosed == orig(this.sessionClosed) -C
	 * this.log.toString == orig(this.log.toString) -C
	 */
	public String getLog() { return log; }
	
	/*
	 * return value == sessionClosed -M
	 *
	 * New Invariants Below:
	 * this.sid == orig(this.sid) -C
	 * this.log == orig(this.log) -C
	 * this.owner == orig(this.owner) -C
	 * this.sessionClosed == orig(this.sessionClosed) -C
	 * (this.sessionClosed == false)  <==>  (return == false) -C
	 * (this.sessionClosed == true)  <==>  (return == true) -C
	 * this.log.toString == orig(this.log.toString) -C
	 * Condition return == true ==> sessionClosed == true && return == true
	 * Condition return == false ==> sessionClosed == false && retrun == false
	 */
	public boolean isSessionClosed(){return sessionClosed;}
	
	/*
	 * Before: 
	 * 	sessionClosed == true
	 * After: 
	 * 	sessionClosed == false
	 *
	 * New Invariants Below: 
	 * this.sid == orig(this.sid) -C
	 * this.log == orig(this.log) -C
	 * this.owner == orig(this.owner) -C
	 * this.log.toString == orig(this.log.toString) -C
	 *
	 */
	public void openSession() { sessionClosed = false; }
	
	/*
	 * Before: 
	 * 	sessionClosed  == false
	 * After: 
	 * 	sessionClosed == true
	 *
	 * New Invariants Below:
	 * this.sid == orig(this.sid) -C
	 * this.log == orig(this.log) -C
	 * this.owner == orig(this.owner) -C
	 * this.log.toString == orig(this.log.toString) -C
	 */
	public void closeSession() { sessionClosed = true; }
	
	/*
	*New Invariants Below:
	* this.sid == orig(this.sid) -C
	* this.owner == orig(this.owner) -C
	* this.sessionClosed == orig(this.sessionClosed) -C
	* l.toString == orig(l.toString) -C
	* this.log.toString > orig(this.log.toString) -C
	*/
	public void log(String l) { log+=l+"\n"; }
	
	
}
