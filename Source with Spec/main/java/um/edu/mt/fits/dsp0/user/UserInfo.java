package um.edu.mt.fits.dsp0.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import um.edu.mt.fits.dsp0.exceptions.InvalidFieldsException;
import um.edu.mt.fits.dsp0.exceptions.SessionNotFoundException;
import um.edu.mt.fits.dsp0.exceptions.UserAccountNotFoundException;
import um.edu.mt.fits.dsp0.user.UserSession;

public class UserInfo {
	
	/* 
	 * Nothing 
	 */
	protected enum UserMode {
		ACTIVE, DISABLED, FROZEN;
	}

	/*
	 * Nothing
	 */
	public enum UserStatus {
		WHITELISTED, GREYLISTED, BLACKLISTED;
	}

	/*
	 * Nothing
	 */
	public enum UserType {
		GOLD, SILVER, NORMAL
	}
	
	/*
	 * uid != null
	 * uid >= 0
	 */
	protected Integer uid;
	
	/*
	 * name != null
	 */
	protected String name;
	
	/*
	 * mode != null
	 */
	protected UserMode mode;
	
	/*
	 * status != null
	 */
	protected UserStatus status;
	
	/*
	 * type != null
	 */
	protected UserType type;
	
	/*
	 * sessions != null
	 */
	protected ArrayList<UserSession> sessions;
	
	/*
	 * accounts != null
	 */
	protected ArrayList<UserAccount> accounts;
	
	/*
	 * next_session_id >= 0
	 */
	protected Integer next_session_id;
	
	/*
	 * next_account >= 0
	 */
	protected Integer next_account;
	
	/*
	 * country != null
	 */
	protected String country;
	
	/*
	 * numOfTransactionsWhenGreyListed >= 0 
	 */
	protected Integer numOfTransactionsWhenGreyListed;
	
	/*
	 * accountrequestsPerSession != null
	 */
	protected HashMap<Integer,Integer> accountRequestsPerSession;
	
	/*
	 * maxNumOfAccountRequestsPerSession == fixed amount 
	 */
	private int maxNumOfAccountRequestsPerSession = 10;	
	
	/*
	 * After:
	 * 	accountRequestsPerSession != null
	 * 	this.uid == uid
	 * 	this.name == name
	 * 	this.country == country
	 * 
	 * 	mode == DISABLED
	 * 	status == WHITELISTED
	 * 	type == NORMAL
	 * 
	 * 	sessions != null
	 * 	accounts != null
	 * 
	 * 	next_session_id == 0
	 * 	next_account = 1
	 * 	numOfTransactionsWhenGreyListed == 0
	 * 
	 * 	Nothing else changes
	 */
	public UserInfo(Integer uid, String name, String country) {
		this.accountRequestsPerSession = new HashMap<Integer,Integer>();
		this.uid = uid;
		this.name = name;
		
		makeDisabled();
		whitelist();
		makeNormalUser();
		
		sessions = new ArrayList<UserSession>();
		accounts = new ArrayList<UserAccount>();
		
		next_session_id = 0;
		next_account = 1;
		numOfTransactionsWhenGreyListed = 0;
		
		this.country = country;
	}
	 
	/*
	 * return value == uid 
	 * Nothing changes
	 */
	public Integer getId() { return uid; }
	
	/*
	 * return value == name
	 * Nothing changes
	 */
	public String getName() { return name; }
	
	/*
	 * return value == country
	 * Nothing changes
	 */
	public String getCountry() { return country; }
	
	/*
	 * return value == accounts
	 * Nothing changes
	 */
	public ArrayList<UserAccount> getAccounts() { return accounts; }
	
	/*
	 * return value == sessions
	 * Nothing changes
	 */
	public ArrayList<UserSession> getSessions() { return sessions; }
	
	/*
	 * type == UserType.GOLD ==> true
	 * type != UserType.GOLD ==> false
	 * Nothing changes
	 */
	public boolean isGoldUser() { return (type==UserType.GOLD); }
	
	/*
	 * type == UserType.SILVER ==> true
	 * type != UserType.SILVER ==> false
	 * Nothing changes
	 */
	public boolean isSilverUser() { return (type==UserType.SILVER); }

	/*
	 * type == UserType.NORMAL ==> true
	 * type != UserType.NORMAL ==> false
	 * Nothing changes
	 */
	public boolean isNormalUser() { return (type==UserType.NORMAL); }

	/*
	 * Before:
	 * 	type can be anything
	 * After:
	 * 	type == UterType.GOLD
	 * 	Nothing else changes
	 */
	public void makeGoldUser() { type = UserType.GOLD; }
	
	/*
	 * Before:
	 * 	type can be anything
	 * After: 
	 * 	type == UserType.SILVER
	 * 	Nothing else changes
	 */
	public void makeSilverUser() { type = UserType.SILVER; }
	
	/*
	 * Before:
	 * 	type can be anything
	 * After:
	 * 	type == UserType.NORMAL
	 * 	Nothing else changes
	 */
	public void makeNormalUser() { type = UserType.NORMAL; }	
	
	/*
	 * status == UserStatus.WHITELISTED ==> return true
	 * status != UserStatus.WHITELISTED ==> return false
	 * Nothing changes
	 */
	public boolean isWhitelisted() { return (status==UserStatus.WHITELISTED); }

	/*
	 * status == UserStatus.GREYLISTED ==> return true
	 * status != UserStatus.GREYLISTED ==> return false
	 * Nothing changes
	 */
	public boolean isGreylisted() { return (status==UserStatus.GREYLISTED); }

	/*
	 * status == UserStatus.BLACKLISTED ==> return true
	 * status != UserStatus.BLACKLISTED ==> return false
	 * Nothing changes
	 */
	public boolean isBlacklisted() { return (status==UserStatus.BLACKLISTED); }
	
	/*
	 * Before:
	 * 	status can be anything
	 * After:
	 * 	status == UserStatus.BLACKLISTED
	 * 	Nothing else changes
	 */
	public void blacklist() { status=UserStatus.BLACKLISTED; }	
	
	/*
	 * Before:
	 * 	status can be anything
	 * After:
	 * 	status == UserStatus.GREYLISTED
	 * 	Nothing else changes
	 */
	public void greylist() { status=UserStatus.GREYLISTED; }
	
	/*
	 * Before:
	 * 	status can be anything
	 * After:
	 * 	original(status) == UserStatus.GREYLISTED && numOfTransactionsWhenGreyListed < 3 ==> status == UserStatus.GREYLISTED
	 * 	original(status) != UserStatus.GREYLISTED ==> status == UserStatus.WHITELISTED
	 * 	numOfTransactionsWhenGreyListed >= 3 ==> status == UserStatus.WHITELISTED
	 * 	Nothing else changes
	 */
	public void whitelist()
	{ 
		if(status == UserStatus.GREYLISTED && numOfTransactionsWhenGreyListed < 3) return;		
		status=UserStatus.WHITELISTED; 
	}
 
	/*
	 * mode == UserMode.ACTIVE ==> true
	 * mode != UserMode.ACTIVE ==> false
	 * Nothing changes
	 */
	public boolean isActive() { return (mode==UserMode.ACTIVE); }
	 
	/*
	 * mode == UserMode.FROZEN ==> true
	 * mode != UserMode.FROZEN ==> false
	 * Nothing changes
	 */
	public boolean isFrozen() { return (mode==UserMode.FROZEN); }
	 
	/*
	 * mode == UserMode.DISABLED ==> true
	 * mode != UserMode.DISABLED ==> false
	 * Nothing changes
	 */
	public boolean isDisabled() { return (mode==UserMode.DISABLED); }

	/*
	 * Before:
	 * 	mode can be anything
	 * After:
	 * 	mode == UserMode.ACTIVE
	 * 	Nothing else changes
	 */
	public void makeActive() { mode=UserMode.ACTIVE; }	

	/*
	 * Before:
	 * 	mode can be anything
	 * After:
	 * 	mode == UserMode.FROZEN
	 * 	Nothing else changes
	 */
	public void makeFrozen() { mode=UserMode.FROZEN; }	

	/*
	 * Before:
	 * 	mode can be anything
	 * After:
	 * 	mode == UserMode.DISABLED
	 * 	Nothing else changes
	 */
	public void makeDisabled() { mode=UserMode.DISABLED; }	

	/*
	 * Before:
	 * 	mode can be anything
	 * After:
	 * 	mode == UserMode.ACTIVE
	 * 	Nothing else changes
	 */
	public void makeUnfrozen() { mode=UserMode.ACTIVE; }

	/*
	 * if sid is valid 
	 * 	return value != null
	 * 	return value.sid == sid
	 * else	
	 * 	return null
	 * Nothing changes
	 */
	public UserSession getSession(Integer sid) 
	{
		UserSession s;
		
		Iterator<UserSession> iterator = sessions.iterator();
		while (iterator.hasNext()) {
		    s = iterator.next();
		    if (s.getId()==sid) return s;
		}
		return null;
	}
	
	/*
	 * After:
	 * 	next_session_id == orignal(next_session_id) + 1
	 * 	The session with id: original(next_session_id) must be open
	 * 	sessions.size == original(session.size) + 1
	 */
	public Integer openSession() 
	{
		Integer sid = next_session_id;
		
		UserSession session = new UserSession(uid, sid);
		session.openSession();
		sessions.add(session);

		next_session_id++;

		return(sid);
	}
	
	/*
	 * Before:
	 * 	session with Id: sid can have its opened state either true or false
	 * After: 
	 * 	if the sid is invalid ==> return exception
	 * 	if sid valid = sessionClosed == true
	 * 	Nothing else changes
	 */
	public void closeSession(Integer sid) throws SessionNotFoundException 
	{
		UserSession s = getSession(sid);
		if(s != null){
			s.closeSession();			
		}else {
			throw new SessionNotFoundException("Session was not found "+sid);
		}
	}
 
	/*
	 * Before:
	 * 	account_number != null
	 * 	account_number must be valid
	 * 
	 * After:
	 * 	if acount_number exists
	 * 		return value.account_number == account_number
	 * 	else
	 * 		return value == null
	 * 	Nothing Changes
	 */
	public UserAccount getAccount(String account_number) 
	{
		UserAccount a;
		
		Iterator<UserAccount> iterator = accounts.iterator();
		while (iterator.hasNext()) {
		    a = iterator.next();
		    if (a.getAccountNumber() == account_number) return a;
		}
		return null;
	} 
	
	/*
	 * After:
	 * 	if sid is valid,  exists and maximum number of Account requests per session not exceeded 
	 * 		accounts.size == original(accounts.size) + 1
	 * 		next_account == original(next_account) + 1
	 * 		accountRequestsPerSession.get(sid) == original(accountRequestsPerSession.get(sid)) + 1
	 * 		return value == uid + original(next_account) 		
	 * 	Nothing else changes
	 */
	public String createAccount(Integer sid) 
	{ 
		if(!accountRequestsPerSession.containsKey(sid)){
			accountRequestsPerSession.put(sid, 0);			
		}
		
		if(accountRequestsPerSession.containsKey(sid) && accountRequestsPerSession.get(sid) < maxNumOfAccountRequestsPerSession){
			String account_number = uid.toString() + next_account.toString();
			next_account++;
			UserAccount a = new UserAccount(uid, account_number);
			accounts.add(a);
			accountRequestsPerSession.put(sid, accountRequestsPerSession.get(sid)+1);				
			return account_number;	
		}
		return null;
	}
	
	/*
	 * After:
	 * 	if account_number is valid and exists
	 * 		next_account == original(next_account)
	 * 		accounts.size == original(accounts.size)
	 * 		accountRequestsPerSession.get(sid) == original(accountRequestsPerSession.get(sid))
	 * 		return value == true
	 * 		the account opened == false ==> return true	
	 * 		Nothing else changes
	 * 	else 
	 * 		throw exception
	 * 		Nothing changes
	 */
	public boolean deleteAccount(String account_number) throws UserAccountNotFoundException 
	{
		UserAccount a = getAccount(account_number);
		if(a != null){
			a.closeAccount();
			return true;
		}
		throw new UserAccountNotFoundException("User Account not found "+account_number);
	}

	/*
	 * Before:
	 * 	account_number != null
	 * 	amount >= 0
	 * After:
	 * 	account_number valid && amount >= 0 ==> returns successfully 
	 * 	account_number invalid ==> UserAccountNotFoundException thrown
	 * 	!( amount >= 0.0 ) ==> InvalidFieldsException thrown
	 * 
	 * Nothing else changes..
	 */
	public void withdrawFrom(String account_number, double amount) throws InvalidFieldsException, UserAccountNotFoundException
	{
		UserAccount userAccount = getAccount(account_number);
		if(userAccount != null){
			if(userAccount.isValidWithdrawalAmount(amount)){
				userAccount.withdraw(amount);				
			}else {
				throw new InvalidFieldsException("Amount incorrect or Account not open "+amount);					
			}
		}else{
			throw new UserAccountNotFoundException("User Account not found "+account_number);				
		}	
	}
	
	/*
	 * Before:
	 * 	account_number != null
	 * 	amount >= 0
	 * After:
	 * 	account_number valid && amount >= 0 ==> returns successfully 
	 * 	account_number invalid ==> UserAccountNotFoundException thrown
	 * 	!( amount >= 0.0 ) ==> InvalidFieldsException thrown
	 * status == UserStatus.GREYLISTED ==> numOfTransactionsWhenGreyListed == original(numOfTransactionsWhenGreylisted) + 1	 * 
	 */
	public void depositTo(String account_number, double amount) throws UserAccountNotFoundException, InvalidFieldsException
	{
		UserAccount userAccount = getAccount(account_number);
				
		if(userAccount != null){
			if(userAccount.isValidDepositAmount(amount)){
				userAccount.deposit(amount);
				if(status == UserStatus.GREYLISTED){
					numOfTransactionsWhenGreyListed++;
				}
			}else {
				throw new InvalidFieldsException("Amount incorrect or Account not open "+amount);					
			}
		}else{
			throw new UserAccountNotFoundException("User Account not found "+account_number);				
		}	
	}
}
