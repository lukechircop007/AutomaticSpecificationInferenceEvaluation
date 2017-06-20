package um.edu.mt.fits.dsp0.api;

import um.edu.mt.fits.dsp0.user.UserAccount;
import um.edu.mt.fits.dsp0.user.UserInfo;
import um.edu.mt.fits.dsp0.user.UserSession;

import um.edu.mt.fits.dsp0.exceptions.*;
 
// The methods called by the user interface for (i) the ADMINistrator; and (ii) normal USERs
public class Interface {
	
	/*
	 * 
	 */
	protected TransactionSystem ts = null;
		
	/*
	 * before: 
	 * 	this.ts == null
	 * After:
	 * 	this.ts != null
	 * 	Nothing else changes
	 */
	public Interface(){
			ts = new TransactionSystem();
	}
	
	/*
	 * Fixed size to 3
	 */
	private int maxNumOfOpenSessions = 3;
	
	/*
	 * Before:
	 * 	this.ts != null
	 * After: 
	 * 	this.ts != null
	 * 	return value == this.ts
	 * Nothing changes
	 */
	public TransactionSystem getTransactionSystem(){
		return ts;
	}
	
	// ADMINistrator methods
	
	/*
	 * Before:
	 * 	this.ts.initialised == false
	 * After:
	 * 	this.ts.initialised == true
	 * 	Nothing else changes
	 */
	public void ADMIN_initialise() { ts.initialise(); }
	
	/*
	 * Before:
	 * 	name != null
	 * 	country != null
	 * 	this.ts != null
	 * After:
	 * 	( name != null && country != null ) ==> ( return value >= 0 )
	 *  ( return value >= 0 ) ==> (ts.users.size == original(ts.users.size) + 1
	 *  ( name == null ) ==> InvalidFieldsException thrown
	 *  ( country == null ) ==> InvalidFieldsException thrown
	 */
	public Integer ADMIN_createUser(String name, String country) throws InvalidFieldsException
	{
		//checks that user credentials are valid to create new user.. 
		if(ts.safeToAddUser(name, country)){	
			// creates the user
			Integer uid = ts.addUser(name, country);
			//disables the account
			ts.getUserInfo(uid).makeDisabled();
			return uid;			
		}
		//Invalid credentials returns -null
		throw new InvalidFieldsException("Incorrect name or country ("+name+", "+country+")");
	}

	/*
	 * Before:
	 * 	uid >= 0
	 * After:
	 * 	uid not valid ==> UserInfoNotFoundException thrown
	 */
	public void ADMIN_activateUser(Integer uid) throws UserInfoNotFoundException
	{
		UserInfo userInf;
		if((userInf = ts.getUserInfo(uid)) != null){
			userInf.makeActive();	
		}else{
			throw new UserInfoNotFoundException("UserInfo Not Found: "+uid);
		}
	}
	
	/*
	 * Before:
	 * 	uid >= 0
	 * After:
	 * 	uid not valid ==> UserInfoNotFoundException thrown
	 */
	public void ADMIN_disableUser(Integer uid) throws UserInfoNotFoundException
	{
		UserInfo userInf;
		if((userInf = ts.getUserInfo(uid)) != null){
			userInf.makeDisabled();		
		}else{
			throw new UserInfoNotFoundException("UserInfo Not Found: "+uid);
		}	
	}
	
	/*
	 * Before:
	 * 	uid >= 0
	 * After:
	 * 	uid not valid ==> UserInfoNotFoundException thrown
	 */
	public void ADMIN_blacklistUser(Integer uid) throws UserInfoNotFoundException
	{
		UserInfo userInf;
		if((userInf = ts.getUserInfo(uid)) != null){
			userInf.blacklist();	
		}else{
			throw new UserInfoNotFoundException("UserInfo Not Found: "+uid);
		}	
	}
	
	/*
	 * Before:
	 * 	uid >= 0
	 * After:
	 * 	uid not valid ==> UserInfoNotFoundException thrown
	 */
	public void ADMIN_greylistUser(Integer uid) throws UserInfoNotFoundException
	{
		UserInfo userInf;
		if((userInf = ts.getUserInfo(uid)) != null){
			userInf.greylist();	
		}else{
			throw new UserInfoNotFoundException("UserInfo Not Found: "+uid);
		}	
	}
	
	/*
	 * Before:
	 * 	uid >= 0
	 * After:
	 * 	uid not valid ==> UserInfoNotFoundException thrown
	 */
	public void ADMIN_whitelistUser(Integer uid) throws UserInfoNotFoundException
	{
		UserInfo userInf;
		if((userInf = ts.getUserInfo(uid)) != null){
			userInf.whitelist();		
		}else{
			throw new UserInfoNotFoundException("UserInfo Not Found: "+uid);
		}	
	}
	
	/*
	 * Before:
	 * 	uid >= 0
	 * After:
	 * 	if user country == Argentina  ==> ( return value == true )
	 * 	if user country != Argentina  ==> ( return value == false )
	 * 	uid not valid ==> UserInfoNotFoundException thrown
	 */
	public boolean ADMIN_makeGoldUser(Integer uid) throws UserInfoNotFoundException
	{
		UserInfo userInf;
		if((userInf = ts.getUserInfo(uid)) != null){
			if(userInf.getCountry().equals("Argentina")){
				userInf.makeGoldUser();	
				return true;
			}
			return false;
		}else{
			throw new UserInfoNotFoundException("UserInfo Not Found: "+uid);
		}			
	}
	
	/*
	 * Before:
	 * 	uid >= 0
	 * After:
	 * 	uid not valid ==> UserInfoNotFoundException thrown
	 */
	public void ADMIN_makeSilverUser(Integer uid) throws UserInfoNotFoundException
	{
		UserInfo userInf;
		if((userInf = ts.getUserInfo(uid)) != null){
			userInf.makeSilverUser();	
		}else{
			throw new UserInfoNotFoundException("UserInfo Not Found: "+uid);
		}	
	}
	
	/*
	 * Before:
	 * 	uid >= 0
	 * After:
	 * 	uid not valid ==> UserInfoNotFoundException thrown
	 */
	public void ADMIN_makeNormalUser(Integer uid) throws UserInfoNotFoundException
	{
		UserInfo userInf;
		if((userInf = ts.getUserInfo(uid)) != null){
			userInf.makeNormalUser();	
		}else{
			throw new UserInfoNotFoundException("UserInfo Not Found: "+uid);
		}	
	}

	/*
	 * Before:
	 * 	uid > = 0 
	 *  account_number != null
	 * After:
	 * 	if uid && account_number are valid ==> ( return value == true )
	 * 	if uid not valid ==> UserInfoNotFoundException thrown
	 * 	if uid is valid && account_number not valid ==> UserAccountNotFoundException thrown
	 */
	public boolean ADMIN_approveOpenAccount(Integer uid, String account_number) throws UserInfoNotFoundException, UserAccountNotFoundException
	{
		UserInfo userInf;
		if((userInf = ts.getUserInfo(uid)) != null){
			UserAccount account;
			if((account = userInf.getAccount(account_number)) != null){
				account.activateAccount();
				return true;
			}else{
				throw new UserAccountNotFoundException("UserInfo Not Found: "+account_number);
			}				
		}else{
			throw new UserInfoNotFoundException("UserInfo Not Found: "+uid);
		}	
	}
	
	// USER methods
	
	/*
	 * Before:
	 * 	uid >= 0
	 * After:
	 * 	ts.initialised == false ==> FailedLoginException thrown
	 * 	ts.initialised == true && uid is valid && user is active && max number of sessions open < MAXIMUM  ==> return value >= 0 
	 * 	ts.initialised == true && uid is not valid ==> FailedLoginException thrown
	 * 	ts.initialised == true && user is not active ==> FailedLoginException thrown
	 *	ts.initialised == true && uid is valid && user is active && max number of sessions open >= MAXIMUM ==> FailedLoginException thrown
	 */
	public Integer USER_login(Integer uid) throws FailedLoginException 
	{
		if(ts.initialised){
			UserInfo u = ts.getUserInfo(uid);
						
			if (u != null && u.isActive()) {
				
				int tempCounter = 0; 
				for(int j = 0; j< u.getSessions().size(); j++){
					if(!u.getSessions().get(j).isSessionClosed()){
						tempCounter++;
					}			
				}
				if(tempCounter < maxNumOfOpenSessions){
					return (u.openSession());	
				}else{
					throw new FailedLoginException("Failed to login because maximum number of open sessions exceded");
				}				
			} else {
				throw new FailedLoginException("Failed to login because either user does not exist or profile not activated");				
			}
		}
		throw new FailedLoginException("Failed to login since transaction system is not initialised");
	}
	
	/*
	 * Before:
	 * 	uid >= 0 
	 * 	sid >= 0 
	 * After:
	 * 	uid is valid && sid is valid ==> return value == true
	 * 	uid is not valid ==> UserInfoNotFoundException thrown
	 * 	sid is not valid ==> SessionNotFoundException thrown
	 */
	public boolean USER_logout(Integer uid, Integer sid) throws UserInfoNotFoundException, SessionNotFoundException
	{
		UserInfo userInf;
		if((userInf = ts.getUserInfo(uid)) != null){
			userInf.closeSession(sid);	
			return true;
		}else{
			throw new UserInfoNotFoundException("UserInfo Not Found: "+uid);
		}	
	}	
	
	//Currently assuming session id is always correct
	/*
	 * Before:
	 * 	uid >= 0 
	 * 	sid >= 0 
	 * After:
	 * 	uid is valid and sid is valid ==> return value == true
	 * 	uid is not valid ==> UserInfoNotFoundException thrown
	 */
	public boolean USER_freezeUserAccount(Integer uid, Integer sid) throws UserInfoNotFoundException
	{
		UserInfo u = ts.getUserInfo(uid);
		if(u != null){
			u.getSession(sid).log("Freeze account");
			u.makeFrozen();		
			return true;			
		}
		throw new UserInfoNotFoundException("UserInfo Not Found: "+uid);
	}
	
	/*
	 * Before:
	 * 	uid >= 0
	 * 	sid >= 0
	 * After:
	 * 	uid not valid ==> UserInfoNotFoundException thrown
	 * 	if user is frozen ==> return value == true
	 * 	if user not frozen ==> return value == false
	 */
	public boolean USER_unfreezeUserAccount(Integer uid, Integer sid) throws UserInfoNotFoundException
	{
		UserInfo u = ts.getUserInfo(uid);
		if(u == null){
			throw new UserInfoNotFoundException("UserInfo Not Found: "+uid);
		}
		
		UserSession s = u.getSession(sid);
		if (u.isFrozen()) {
			s.log("Unfreeze account");
			u.makeUnfrozen();
			return true;
		} 
		s.log("FAILED (user account not frozen): Unfreeze account");
		return false;
	}
	
	/*
	 * Before:
	 * 	uid >= 0
	 * 	sid >= 0 	
	 * After:
	 *  uid is not valid ==> UserInfoNotFoundException thrown
	 *  uid is valid && sid is valid ==> return value != null
	 */
	public String USER_requestAccount(Integer uid, Integer sid) throws UserInfoNotFoundException
	{
		UserInfo u = ts.getUserInfo(uid);
		if(u != null){
			UserSession s = u.getSession(sid);
			String account_number = u.createAccount(sid);
			s.log("Request new account with number <"+account_number+">");
			return (account_number);			
		}
		throw new UserInfoNotFoundException("User Info not found "+uid);		
	}
	
	/*
	 * Before:
	 * 	uid >= 0 
	 * 	sid >= 0
	 * 	account_number != null
	 * After:
	 * 	uid is valid && sid is valid && account_number exists ==> return value == true
	 * 	uid is not valid ==> UserInfoNotFoundException thrown
	 * 	uid is valid && sid is valid && account_number does not exist ==> UserAccountNotFoundException thrown
	 */
	public boolean USER_closeAccount(Integer uid, Integer sid, String account_number) throws UserInfoNotFoundException, UserAccountNotFoundException
	{
		UserInfo u = ts.getUserInfo(uid);
		if( u != null){
			UserSession s = u.getSession(sid);
			s.log("Close account number <"+account_number+">");
			u.deleteAccount(account_number);	
			return true;
		}else{
			throw new UserInfoNotFoundException("User Info not found "+uid);	
		}
	}
	
	/*
	 * Before:
	 * 	uid >= 0
	 * 	sid >= 0
	 * 	to_account_number != null
	 * 	amount >= 0.0
	 * After:
	 * 	uid is not valid ==> UserInfoNotFoundException thrown
	 * 	sid is valid and not open ==> UserNotLoggedInException thrown
	 * 	uid is valid and sid is valid and open && to_account_number not valid ==> UserAccountNotFoundException
	 *  !( amount >= 0.0 ) ==> InvalidFieldsException
	 *  uid is valid and sid is valid and open && to_account_number not valid && amount >= 0.0 ==> return value == true
	 */
	public boolean USER_depositFromExternal(Integer uid, Integer sid, String to_account_number, double amount) throws UserInfoNotFoundException, UserAccountNotFoundException, InvalidFieldsException, UserNotLoggedInException
	{		
		UserInfo u = ts.getUserInfo(uid);
		if(u != null){
			UserSession s = u.getSession(sid);
			if(!s.isSessionClosed()){
				s.log("Deposit $"+amount+"to account <"+to_account_number+">");
				u.depositTo(to_account_number,amount);	
				return true;
			}else{
				throw new UserNotLoggedInException("Deposit ");
			}						
		}else{
			throw new UserInfoNotFoundException("User Info not found "+uid);			
		}			
	}
	
	// * Pay a bill (i.e. an external money account) - charges apply
	/*
	 * Before:
	 * 	uid >= 0 
	 * 	sid >= 0
	 * 	from_account_number != null
	 * 	amount >= 0
	 * After:
	 * 	uid is not valid ==> UserInfoNotFoundException thrown
	 * 	from_account_number invalid ==> UserAccountNotFoundException thrown
	 * 	uid is valid && sid is valid and open && from_account_number valid && amount >= 0 && sufficient funds ==> return value == true
	 * 	uid is valid && sid is valid and open && from_account_number valid && amount >= 0 && funds not sufficient ==> return value == false
	 * 	amount invalid ==> InvalidFieldsException thrown
	 */
	public boolean USER_payToExternal(Integer uid, Integer sid, String from_account_number, double amount) throws UserInfoNotFoundException
	{
		UserInfo u = ts.getUserInfo(uid);
		
		if(u != null){
			UserSession s = u.getSession(sid);
			if (!u.isActive()) return false;
			if (s == null || s.isSessionClosed()) return false;

			UserAccount userAccount = u.getAccount(from_account_number);
			
			if(userAccount != null && userAccount.getOpened()){
				double total_amount = amount + ts.charges(uid, amount);
				
				//if (u.getAccount(from_account_number).getBalance() >= amount) { //Test will fail
				
				if (u.getAccount(from_account_number).getBalance() >= total_amount) {
					s.log("Payment of $"+amount+" from account <"+from_account_number+">");
					try {
						u.withdrawFrom(from_account_number, total_amount);
					} catch (InvalidFieldsException e) {
						return false;
					} catch (UserAccountNotFoundException e) {
						return false;
					} catch (Exception e){
						return false;
					}
					return true;
				}
			}			
			
			s.log("FAILED (not enough funds): Payment of $"+amount+" from account <"+from_account_number+"> or account not open");
			return false;
		}
		throw new UserInfoNotFoundException("User Info not found "+uid);			
	}
	
	// * Transfer money to another user's account - charges apply
	/*
	 * Before:
	 * 	from_uid >= 0 
	 * 	sid >= 0
	 * 	from_account_number != null
	 * 	to_uid >= 0
	 * 	to_account_number != null
	 * 	amount >= 0
	 * After:
	 * 	from_uid is not valid ==> UserInfoNotFoundException thrown
	 * 	to_uid is not valid ==> UserInfoNotFoundException thrown
	 * 	sid == null ==> return value == false
	 *  sid != null && not closed ==> return value == false
	 * 	from_account_number or to_account_number invalid ==> UserAccountNotFoundException thrown
	 * 	from_uid is valid && to_uid is valid && sid is valid and open && from_account_number valid && to_account_number valid && amount >= 0 && sufficient funds ==> return value == true
	 * 	from_uid is valid && to_uid is valid && sid is valid and open && from_account_number valid && to_account_number valid && amount >= 0 && funds not sufficient ==> return value == false
	 */
	public boolean USER_transferToOtherAccount(Integer from_uid, Integer sid, String from_account_number, Integer to_uid, String to_account_number, double amount) throws UserInfoNotFoundException, UserAccountNotFoundException, InvalidFieldsException
	{
		UserInfo from_u = ts.getUserInfo(from_uid);
		
		if(from_u != null){
			UserSession s = from_u.getSession(sid);
			
			if (s == null || s.isSessionClosed()) return false;
			
			UserInfo to_u = ts.getUserInfo(to_uid);
			
			if(to_u != null){
				double total_amount = amount + ts.charges(from_uid, amount);
				 
				if (from_u.getAccount(from_account_number).getBalance() >= total_amount) {
					from_u.withdrawFrom(from_account_number, total_amount);
					to_u.depositTo(to_account_number, amount);
					s.log("Payment of $"+amount+" from account <"+from_account_number+"> to account "+
							"<"+to_account_number+" of user "+to_uid);
					return true;
				}
				s.log("FAILED (not enough funds): "+
						"Payment of $"+amount+" from account <"+from_account_number+"> to account "+
						"<"+to_account_number+" of user "+to_uid);
				return false;
			}
			throw new UserInfoNotFoundException("To User Info not found "+to_uid);
		}
		throw new UserInfoNotFoundException("From User Info not found "+from_uid);
	}
	
	// * Transfer money across own accounts - charges do not apply
	/*
	 * Before:
	 * 	uid >= 0 
	 * 	sid >= 0
	 * 	from_account_number != null
	 * 	to_account_number != null
	 * 	amount >= 0
	 * After:
	 * 	uid is not valid ==> UserInfoNotFoundException thrown
	 * 	from_account_number or to_account_number invalid ==> UserAccountNotFoundException thrown
	 * 	uid is valid && sid is valid and open && from_account_number valid && to_account_number valid && amount >= 0 && sufficient funds ==> return value == true
	 * 	uid is valid && sid is valid and open && from_account_number valid && to_account_number valid && amount >= 0 && funds not sufficient ==> return value == false
	 */
	public boolean USER_transferOwnAccounts(Integer uid, Integer sid, String from_account_number, String to_account_number, double amount) throws UserInfoNotFoundException, UserAccountNotFoundException
	{
		UserInfo u = ts.getUserInfo(uid);
		
		if(u != null){
			UserSession s = u.getSession(sid);
			UserAccount 
				from_a = ts.getUserInfo(uid).getAccount(from_account_number),
				to_a   = ts.getUserInfo(uid).getAccount(to_account_number);
			
			if(from_a != null && to_a != null){
				if (from_a.getBalance() >= amount) {
					from_a.withdraw(amount);
					to_a.deposit(amount);
					s.log("Transfer of $"+amount+" from account <"+from_account_number+"> to own account <"+to_account_number);
					return true;
				}
				s.log("FAILED (not enough funds)"+
						"Transfer of $"+amount+" from account <"+from_account_number+"> to own account <"+to_account_number);
				return false;				
			}
			throw new UserAccountNotFoundException("User Account not found");
		}
		throw new UserInfoNotFoundException("User Info not found "+uid);		
	}

}


