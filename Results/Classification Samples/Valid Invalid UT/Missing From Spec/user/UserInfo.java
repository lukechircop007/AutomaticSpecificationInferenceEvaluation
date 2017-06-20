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
	 * size(um.edu.mt.fits.dsp0.user.UserInfo$UserMode.$VALUES[]) == 3 -C
	 */
	protected enum UserMode {
		ACTIVE, DISABLED, FROZEN;
	}

	/*
	 * Nothing
	 * size(um.edu.mt.fits.dsp0.user.UserInfo$UserStatus.$VALUES[]) == 3 -C
	 */
	public enum UserStatus {
		WHITELISTED, GREYLISTED, BLACKLISTED;
	}

	/*
	 * Nothing
	 * size(um.edu.mt.fits.dsp0.user.UserInfo$UserType.$VALUES[]) == 3 -C
	 */
	public enum UserType {
		GOLD, SILVER, NORMAL
	}
	
	/*
	 * uid != null
	 * uid >= 0 -M
	 */
	protected Integer uid;
	
	/*
	 * name != null
	 */
	protected String name;
	
	/*
	 * mode != null -M
	 */
	protected UserMode mode;
	
	/*
	 * status != null -M
	 */
	protected UserStatus status;
	
	/*
	 * type != null -M
	 */
	protected UserType type;
	
	/*
	 * sessions != null -M
	 * 
	 * New Invariants Below: 
	 * this.sessions[].getClass().getName() elements == um.edu.mt.fits.dsp0.user.UserSession.class -C
	 */
	protected ArrayList<UserSession> sessions;
	
	/*
	 * accounts != null -M
	 * 
	 * New Invariants Below:
	 * this.accounts[].getClass().getName() elements == um.edu.mt.fits.dsp0.user.UserAccount.class -C
	 */
	protected ArrayList<UserAccount> accounts;
	
	/*
	 * next_session_id >= 0 -M
	 */
	protected Integer next_session_id;
	
	/*
	 * next_account >= 0 -M
	 */
	protected Integer next_account;
	
	/*
	 * country != null -M
	 */
	protected String country;
	
	/*
	 * numOfTransactionsWhenGreyListed >= 0 -M
	 */
	protected Integer numOfTransactionsWhenGreyListed;
	
	/*
	 * accountrequestsPerSession != null
	 */
	protected HashMap<Integer,Integer> accountRequestsPerSession;
	
	/*
	 * maxNumOfAccountRequestsPerSession == 10
	 */
	private int maxNumOfAccountRequestsPerSession = 10;	
	
	/*
	 * After:
	 * 	accountRequestsPerSession != null -M
	 * 	this.uid == uid -M
	 * 	this.name == name -M
	 * 	this.country == country -M
	 * 
	 * 	mode == DISABLED -M
	 * 	status == WHITELISTED -M
	 * 	type == NORMAL -M
	 * 
	 * 	sessions != null
	 * 	accounts != null
	 * 
	 * 	next_session_id == 0 -M
	 * 	next_account = 1 -M
	 * 	numOfTransactionsWhenGreyListed == 0 -M
	 * 
	 * New Invariants Below: 
	 * name.toString == orig(name.toString) -C
	 * country.toString == orig(country.toString) -C
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
	 * return value == uid -M
	 *
	 * New Invariants Below:
	 * this.uid == orig(this.uid) -C
	 * this.name == orig(this.name) -C
	 * this.mode == orig(this.mode) -C
	 * this.status == orig(this.status) -C
	 * this.type == orig(this.type) -C
	 * this.sessions == orig(this.sessions) -C
	 * this.sessions[] == orig(this.sessions[]) -C
	 * this.accounts == orig(this.accounts) -C
	 * this.accounts[] == orig(this.accounts[]) -C
	 * this.next_session_id == orig(this.next_session_id) -C
	 * this.next_account == orig(this.next_account) -C
	 * this.country == orig(this.country) -C
	 * this.numOfTransactionsWhenGreyListed == orig(this.numOfTransactionsWhenGreyListed) -C
	 * this.accountRequestsPerSession == orig(this.accountRequestsPerSession) -C
	 * this.maxNumOfAccountRequestsPerSession == orig(this.maxNumOfAccountRequestsPerSession) -C
	 * this.name.toString == orig(this.name.toString) -C
	 * this.country.toString == orig(this.country.toString) -C
	 * 
	 */
	public Integer getId() { return uid; }
	
	/*
	 * return value == name -M
	 *
	 * New Invariants Below:
	 * this.uid == orig(this.uid) -C
	 * this.name == orig(this.name) -C
	 * this.mode == orig(this.mode) -C
	 * this.status == orig(this.status) -C
	 * this.type == orig(this.type) -C
	 * this.sessions == orig(this.sessions) -C
	 * this.sessions[] == orig(this.sessions[]) -C
	 * this.accounts == orig(this.accounts) -C
	 * this.accounts[] == orig(this.accounts[]) -C
	 * this.next_session_id == orig(this.next_session_id) -C
	 * this.next_account == orig(this.next_account) -C
	 * this.country == orig(this.country) -C
	 * this.numOfTransactionsWhenGreyListed == orig(this.numOfTransactionsWhenGreyListed) -C
	 * this.accountRequestsPerSession == orig(this.accountRequestsPerSession) -C
	 * this.maxNumOfAccountRequestsPerSession == orig(this.maxNumOfAccountRequestsPerSession) -C
	 * this.name.toString == orig(this.name.toString) -C
	 * this.country.toString == orig(this.country.toString) -C
	 *
	 */
	public String getName() { return name; }
	
	/*
	 * return value == country -M
	 *
	 * New Invariants Below:
	 * this.uid == orig(this.uid) -C
	 * this.name == orig(this.name) -C
	 * this.mode == orig(this.mode) -C
	 * this.status == orig(this.status) -C
	 * this.type == orig(this.type) -C
	 * this.sessions == orig(this.sessions) -C
	 * this.sessions[] == orig(this.sessions[]) -C
	 * this.accounts == orig(this.accounts) -C
	 * this.accounts[] == orig(this.accounts[]) -C
	 * this.next_session_id == orig(this.next_session_id) -C
	 * this.next_account == orig(this.next_account) -C
	 * this.country == orig(this.country) -C
	 * this.numOfTransactionsWhenGreyListed == orig(this.numOfTransactionsWhenGreyListed) -C
	 * this.accountRequestsPerSession == orig(this.accountRequestsPerSession) -C
	 * this.maxNumOfAccountRequestsPerSession == orig(this.maxNumOfAccountRequestsPerSession) -C
	 * this.name.toString == orig(this.name.toString) -C
	 * this.country.toString == orig(this.country.toString) -C
	 *
	 */
	public String getCountry() { return country; }
	
	/*
	 * return value == accounts -M
	 *
	 * New Invariants Below:
	 * this.uid == orig(this.uid) -C
	 * this.name == orig(this.name) -C
	 * this.mode == orig(this.mode) -C
	 * this.status == orig(this.status) -C
	 * this.type == orig(this.type) -C
	 * this.sessions == orig(this.sessions) -C
	 * this.sessions[] == orig(this.sessions[]) -C
	 * this.accounts == orig(this.accounts) -C
	 * this.accounts[] == orig(this.accounts[]) -C
	 * this.next_session_id == orig(this.next_session_id) -C
	 * this.next_account == orig(this.next_account) -C
	 * this.country == orig(this.country) -C
	 * this.numOfTransactionsWhenGreyListed == orig(this.numOfTransactionsWhenGreyListed) -C
	 * this.accountRequestsPerSession == orig(this.accountRequestsPerSession) -C
	 * this.maxNumOfAccountRequestsPerSession == orig(this.maxNumOfAccountRequestsPerSession) -C
	 * this.name.toString == orig(this.name.toString) -C
	 * this.country.toString == orig(this.country.toString) -C
	 * return[].getClass().getName() elements == um.edu.mt.fits.dsp0.user.UserAccount.class
	 *
	 */
	public ArrayList<UserAccount> getAccounts() { return accounts; }
	
	/*
	 * return value == sessions -M
	 *
	 * New Invariants Below:
	 * this.uid == orig(this.uid) -C
	 * this.name == orig(this.name) -C
	 * this.mode == orig(this.mode) -C
	 * this.status == orig(this.status) -C
	 * this.type == orig(this.type) -C
	 * this.sessions == orig(this.sessions) -C
	 * this.sessions[] == orig(this.sessions[]) -C
	 * this.accounts == orig(this.accounts) -C
	 * this.accounts[] == orig(this.accounts[]) -C
	 * this.next_session_id == orig(this.next_session_id) -C
	 * this.next_account == orig(this.next_account) -C
	 * this.country == orig(this.country) -C
	 * this.numOfTransactionsWhenGreyListed == orig(this.numOfTransactionsWhenGreyListed) -C
	 * this.accountRequestsPerSession == orig(this.accountRequestsPerSession) -C
	 * this.maxNumOfAccountRequestsPerSession == orig(this.maxNumOfAccountRequestsPerSession) -C
	 * this.name.toString == orig(this.name.toString) -C
	 * this.country.toString == orig(this.country.toString) -C
	 * return[].getClass().getName() elements == um.edu.mt.fits.dsp0.user.UserSession.class
	 */
	public ArrayList<UserSession> getSessions() { return sessions; }
	
	/*
	 * type == UserType.GOLD ==> true -M
	 * type != UserType.GOLD ==> false -M
	 *
	 * New Invariants Below:
	 * Condition return == false ==> return == false	
	 * Condition return == true ==> return == true 
	 * this.uid == orig(this.uid) -C
	 * this.name == orig(this.name) -C
	 * this.mode == orig(this.mode) -C
	 * this.status == orig(this.status) -C
	 * this.type == orig(this.type) -C
	 * this.sessions == orig(this.sessions) -C
	 * this.sessions[] == orig(this.sessions[]) -C
	 * this.accounts == orig(this.accounts) -C
	 * this.accounts[] == orig(this.accounts[]) -C
	 * this.next_session_id == orig(this.next_session_id) -C
	 * this.next_account == orig(this.next_account) -C
	 * this.country == orig(this.country) -C
	 * this.numOfTransactionsWhenGreyListed == orig(this.numOfTransactionsWhenGreyListed) -C
	 * this.accountRequestsPerSession == orig(this.accountRequestsPerSession) -C
	 * this.maxNumOfAccountRequestsPerSession == orig(this.maxNumOfAccountRequestsPerSession) -C
	 * this.name.toString == orig(this.name.toString) -C
	 * this.country.toString == orig(this.country.toString) -C
	 */
	public boolean isGoldUser() { return (type==UserType.GOLD); }
	
	/*
	 * type == UserType.SILVER ==> true -M
	 * type != UserType.SILVER ==> false -M
	 *
	 * New Invariants Below:
	 * Condition return == false ==> return == false	
	 * Condition return == true ==> return == true 
	 * this.uid == orig(this.uid) -C
	 * this.name == orig(this.name) -C
	 * this.mode == orig(this.mode) -C
	 * this.status == orig(this.status) -C
	 * this.type == orig(this.type) -C
	 * this.sessions == orig(this.sessions) -C
	 * this.sessions[] == orig(this.sessions[]) -C
	 * this.accounts == orig(this.accounts) -C
	 * this.accounts[] == orig(this.accounts[]) -C
	 * this.next_session_id == orig(this.next_session_id) -C
	 * this.next_account == orig(this.next_account) -C
	 * this.country == orig(this.country) -C
	 * this.numOfTransactionsWhenGreyListed == orig(this.numOfTransactionsWhenGreyListed) -C
	 * this.accountRequestsPerSession == orig(this.accountRequestsPerSession) -C
	 * this.maxNumOfAccountRequestsPerSession == orig(this.maxNumOfAccountRequestsPerSession) -C
	 * this.name.toString == orig(this.name.toString) -C
	 * this.country.toString == orig(this.country.toString) -C
	 */
	public boolean isSilverUser() { return (type==UserType.SILVER); }

	/*
	 * type == UserType.NORMAL ==> true -M
	 * type != UserType.NORMAL ==> false -M
	 *
	 * New Invariants Below:
	 * Condition return == false ==> return == false	
	 * Condition return == true ==> return == true 
	 * this.uid == orig(this.uid) -C
	 * this.name == orig(this.name) -C
	 * this.mode == orig(this.mode) -C
	 * this.status == orig(this.status) -C
	 * this.type == orig(this.type) -C
	 * this.sessions == orig(this.sessions) -C
	 * this.sessions[] == orig(this.sessions[]) -C
	 * this.accounts == orig(this.accounts) -C
	 * this.accounts[] == orig(this.accounts[]) -C
	 * this.next_session_id == orig(this.next_session_id) -C
	 * this.next_account == orig(this.next_account) -C
	 * this.country == orig(this.country) -C
	 * this.numOfTransactionsWhenGreyListed == orig(this.numOfTransactionsWhenGreyListed) -C
	 * this.accountRequestsPerSession == orig(this.accountRequestsPerSession) -C
	 * this.maxNumOfAccountRequestsPerSession == orig(this.maxNumOfAccountRequestsPerSession) -C
	 * this.name.toString == orig(this.name.toString) -C
	 * this.country.toString == orig(this.country.toString) -C
	 */
	public boolean isNormalUser() { return (type==UserType.NORMAL); }

	/*
	 * Before:
	 * 	type != null -M
	 * After:
	 * 	type == UterType.GOLD -M
	 *
	 * New Invariants Below:
	 * this.uid == orig(this.uid) -C
	 * this.name == orig(this.name) -C
	 * this.mode == orig(this.mode) -C
	 * this.status == orig(this.status) -C
	 * this.sessions == orig(this.sessions) -C
	 * this.sessions[] == orig(this.sessions[]) -C
	 * this.accounts == orig(this.accounts) -C
	 * this.accounts[] == orig(this.accounts[]) -C
	 * this.next_session_id == orig(this.next_session_id) -C
	 * this.next_account == orig(this.next_account) -C
	 * this.country == orig(this.country) -C
	 * this.numOfTransactionsWhenGreyListed == orig(this.numOfTransactionsWhenGreyListed) -C
	 * this.accountRequestsPerSession == orig(this.accountRequestsPerSession) -C
	 * this.maxNumOfAccountRequestsPerSession == orig(this.maxNumOfAccountRequestsPerSession) -C
	 * this.name.toString == orig(this.name.toString) -C
	 * this.country.toString == orig(this.country.toString) -C
	 */
	public void makeGoldUser() { type = UserType.GOLD; }
	
	/*
	 * Before:
	 * 	type != null -M
	 * After: 
	 * 	type == UserType.SILVER -M
	 *
	 * New Invariants Below:
	 * this.uid == orig(this.uid) -C
	 * this.name == orig(this.name) -C
	 * this.mode == orig(this.mode) -C
	 * this.status == orig(this.status) -C
	 * this.sessions == orig(this.sessions) -C
	 * this.sessions[] == orig(this.sessions[]) -C
	 * this.accounts == orig(this.accounts) -C
	 * this.accounts[] == orig(this.accounts[]) -C
	 * this.next_session_id == orig(this.next_session_id) -C
	 * this.next_account == orig(this.next_account) -C
	 * this.country == orig(this.country) -C
	 * this.numOfTransactionsWhenGreyListed == orig(this.numOfTransactionsWhenGreyListed) -C
	 * this.accountRequestsPerSession == orig(this.accountRequestsPerSession) -C
	 * this.maxNumOfAccountRequestsPerSession == orig(this.maxNumOfAccountRequestsPerSession) -C
	 * this.name.toString == orig(this.name.toString) -C
	 * this.country.toString == orig(this.country.toString) -C
	 */
	public void makeSilverUser() { type = UserType.SILVER; }
	
	/*
	 * Before:
	 * 	type != null -M
	 * After:
	 * 	type == UserType.NORMAL -M
	 *
	 * New Invariants Below:
	 * this.uid == orig(this.uid) -C
	 * this.name == orig(this.name) -C
	 * this.mode == orig(this.mode) -C
	 * this.status == orig(this.status) -C
	 * this.sessions == orig(this.sessions) -C
	 * this.sessions[] == orig(this.sessions[]) -C
	 * this.accounts == orig(this.accounts) -C
	 * this.accounts[] == orig(this.accounts[]) -C
	 * this.next_session_id == orig(this.next_session_id) -C
	 * this.next_account == orig(this.next_account) -C
	 * this.country == orig(this.country) -C
	 * this.numOfTransactionsWhenGreyListed == orig(this.numOfTransactionsWhenGreyListed) -C
	 * this.accountRequestsPerSession == orig(this.accountRequestsPerSession) -C
	 * this.maxNumOfAccountRequestsPerSession == orig(this.maxNumOfAccountRequestsPerSession) -C
	 * this.name.toString == orig(this.name.toString) -C
	 * this.country.toString == orig(this.country.toString) -C
	 */
	public void makeNormalUser() { type = UserType.NORMAL; }	
	
	/*
	 * status == UserStatus.WHITELISTED ==> return true -M
	 * status != UserStatus.WHITELISTED ==> return false -M
	 *
	 * New Invariants Below:
	 * Condition return == false ==> return == false	
	 * Condition return == true ==> return == true 
	 * this.uid == orig(this.uid) -C
	 * this.name == orig(this.name) -C
	 * this.mode == orig(this.mode) -C
	 * this.status == orig(this.status) -C
	 * this.type == orig(this.type) -C
	 * this.sessions == orig(this.sessions) -C
	 * this.sessions[] == orig(this.sessions[]) -C
	 * this.accounts == orig(this.accounts) -C
	 * this.accounts[] == orig(this.accounts[]) -C
	 * this.next_session_id == orig(this.next_session_id) -C
	 * this.next_account == orig(this.next_account) -C
	 * this.country == orig(this.country) -C
	 * this.numOfTransactionsWhenGreyListed == orig(this.numOfTransactionsWhenGreyListed) -C
	 * this.accountRequestsPerSession == orig(this.accountRequestsPerSession) -C
	 * this.maxNumOfAccountRequestsPerSession == orig(this.maxNumOfAccountRequestsPerSession) -C
	 * this.name.toString == orig(this.name.toString) -C
	 * this.country.toString == orig(this.country.toString) -C
	 */
	public boolean isWhitelisted() { return (status==UserStatus.WHITELISTED); }

	/*
	 * status == UserStatus.GREYLISTED ==> return true -M
	 * status != UserStatus.GREYLISTED ==> return false -M
	 *
	 * New Invariants Below:
	 * Condition return == false ==> return == false	
	 * Condition return == true ==> return == true 
	 * this.uid == orig(this.uid) -C
	 * this.name == orig(this.name) -C
	 * this.mode == orig(this.mode) -C
	 * this.status == orig(this.status) -C
	 * this.type == orig(this.type) -C
	 * this.sessions == orig(this.sessions) -C
	 * this.sessions[] == orig(this.sessions[]) -C
	 * this.accounts == orig(this.accounts) -C
	 * this.accounts[] == orig(this.accounts[]) -C
	 * this.next_session_id == orig(this.next_session_id) -C
	 * this.next_account == orig(this.next_account) -C
	 * this.country == orig(this.country) -C
	 * this.numOfTransactionsWhenGreyListed == orig(this.numOfTransactionsWhenGreyListed) -C
	 * this.accountRequestsPerSession == orig(this.accountRequestsPerSession) -C
	 * this.maxNumOfAccountRequestsPerSession == orig(this.maxNumOfAccountRequestsPerSession) -C
	 * this.name.toString == orig(this.name.toString) -C
	 * this.country.toString == orig(this.country.toString) -C
	 */
	public boolean isGreylisted() { return (status==UserStatus.GREYLISTED); }

	/*
	 * status == UserStatus.BLACKLISTED ==> return true -M
	 * status != UserStatus.BLACKLISTED ==> return false -M
	 *
	 * New Invariants Below:
	 * Condition return == false ==> return == false	
	 * Condition return == true ==> return == true 
	 * this.uid == orig(this.uid) -C
	 * this.name == orig(this.name) -C
	 * this.mode == orig(this.mode) -C
	 * this.status == orig(this.status) -C
	 * this.type == orig(this.type) -C
	 * this.sessions == orig(this.sessions) -C
	 * this.sessions[] == orig(this.sessions[]) -C
	 * this.accounts == orig(this.accounts) -C
	 * this.accounts[] == orig(this.accounts[]) -C
	 * this.next_session_id == orig(this.next_session_id) -C
	 * this.next_account == orig(this.next_account) -C
	 * this.country == orig(this.country) -C
	 * this.numOfTransactionsWhenGreyListed == orig(this.numOfTransactionsWhenGreyListed) -C
	 * this.accountRequestsPerSession == orig(this.accountRequestsPerSession) -C
	 * this.maxNumOfAccountRequestsPerSession == orig(this.maxNumOfAccountRequestsPerSession) -C
	 * this.name.toString == orig(this.name.toString) -C
	 * this.country.toString == orig(this.country.toString) -C
	 */
	public boolean isBlacklisted() { return (status==UserStatus.BLACKLISTED); }
	
	/*
	 * Before:
	 * 	status != null -M
	 * After:
	 * 	status == UserStatus.BLACKLISTED -M
	 *
	 * New Invariants Below:
	 * 
	 * this.uid == orig(this.uid) -C
	 * this.name == orig(this.name) -C
	 * this.mode == orig(this.mode) -C
	 * this.type == orig(this.type) -C
	 * this.sessions == orig(this.sessions) -C
	 * this.sessions[] == orig(this.sessions[]) -C
	 * this.accounts == orig(this.accounts) -C
	 * this.accounts[] == orig(this.accounts[]) -C
	 * this.next_session_id == orig(this.next_session_id) -C
	 * this.next_account == orig(this.next_account) -C
	 * this.country == orig(this.country) -C
	 * this.numOfTransactionsWhenGreyListed == orig(this.numOfTransactionsWhenGreyListed) -C
	 * this.accountRequestsPerSession == orig(this.accountRequestsPerSession) -C
	 * this.maxNumOfAccountRequestsPerSession == orig(this.maxNumOfAccountRequestsPerSession) -C
	 * this.name.toString == orig(this.name.toString) -C
	 * this.country.toString == orig(this.country.toString) -C
	 */
	public void blacklist() { status=UserStatus.BLACKLISTED; }	
	
	/*
	 * Before:
	 * 	status != null -M
	 * After:
	 * 	status == UserStatus.GREYLISTED -M
	 *
	 * New Invariants Below:
	 * 
	 * this.uid == orig(this.uid) -C
	 * this.name == orig(this.name) -C
	 * this.mode == orig(this.mode) -C
	 * this.type == orig(this.type) -C
	 * this.sessions == orig(this.sessions) -C
	 * this.sessions[] == orig(this.sessions[]) -C
	 * this.accounts == orig(this.accounts) -C
	 * this.accounts[] == orig(this.accounts[]) -C
	 * this.next_session_id == orig(this.next_session_id) -C
	 * this.next_account == orig(this.next_account) -C
	 * this.country == orig(this.country) -C
	 * this.numOfTransactionsWhenGreyListed == orig(this.numOfTransactionsWhenGreyListed) -C
	 * this.accountRequestsPerSession == orig(this.accountRequestsPerSession) -C
	 * this.maxNumOfAccountRequestsPerSession == orig(this.maxNumOfAccountRequestsPerSession) -C
	 * this.name.toString == orig(this.name.toString) -C
	 * this.country.toString == orig(this.country.toString) -C
	 */
	public void greylist() { status=UserStatus.GREYLISTED; }
	
	/*
	 * Before:
	 * 	status != null -M
	 * After:
	 * 	original(status) == UserStatus.GREYLISTED && numOfTransactionsWhenGreyListed < 3 ==> status == UserStatus.GREYLISTED -M
	 * 	original(status) != UserStatus.GREYLISTED ==> status == UserStatus.WHITELISTED -M
	 * 	numOfTransactionsWhenGreyListed >= 3 ==> status == UserStatus.WHITELISTED -M
	 *
	 * New Invariants Below:
	 * 
	 * this.uid == orig(this.uid) -C
	 * this.name == orig(this.name) -C
	 * this.mode == orig(this.mode) -C
	 * this.type == orig(this.type) -C
	 * this.sessions == orig(this.sessions) -C
	 * this.sessions[] == orig(this.sessions[]) -C
	 * this.accounts == orig(this.accounts) -C
	 * this.accounts[] == orig(this.accounts[]) -C
	 * this.next_session_id == orig(this.next_session_id) -C
	 * this.next_account == orig(this.next_account) -C
	 * this.country == orig(this.country) -C
	 * this.numOfTransactionsWhenGreyListed == orig(this.numOfTransactionsWhenGreyListed) -C
	 * this.accountRequestsPerSession == orig(this.accountRequestsPerSession) -C
	 * this.maxNumOfAccountRequestsPerSession == orig(this.maxNumOfAccountRequestsPerSession) -C
	 * this.name.toString == orig(this.name.toString) -C
	 * this.country.toString == orig(this.country.toString) -C
	 *
	 * EXIT 268 this.status == orig(this.status) -- when conditions not met
	 */
	public void whitelist()
	{ 
		if(status == UserStatus.GREYLISTED && numOfTransactionsWhenGreyListed < 3) return;		
		status=UserStatus.WHITELISTED; 
	}
 
	/*
	 * mode == UserMode.ACTIVE ==> true -M
	 * mode != UserMode.ACTIVE ==> false -M
	 *
	 * New Invariants Below:
	 * Condition return == false ==> return == false	
	 * Condition return == true ==> return == true 
	 * this.uid == orig(this.uid) -C
	 * this.name == orig(this.name) -C
	 * this.mode == orig(this.mode) -C
	 * this.status == orig(this.status) -C
	 * this.type == orig(this.type) -C
	 * this.sessions == orig(this.sessions) -C
	 * this.sessions[] == orig(this.sessions[]) -C
	 * this.accounts == orig(this.accounts) -C
	 * this.accounts[] == orig(this.accounts[]) -C
	 * this.next_session_id == orig(this.next_session_id) -C
	 * this.next_account == orig(this.next_account) -C
	 * this.country == orig(this.country) -C
	 * this.numOfTransactionsWhenGreyListed == orig(this.numOfTransactionsWhenGreyListed) -C
	 * this.accountRequestsPerSession == orig(this.accountRequestsPerSession) -C
	 * this.maxNumOfAccountRequestsPerSession == orig(this.maxNumOfAccountRequestsPerSession) -C
	 * this.name.toString == orig(this.name.toString) -C
	 * this.country.toString == orig(this.country.toString) -C
	 */
	public boolean isActive() { return (mode==UserMode.ACTIVE); }
	 
	/*
	 * mode == UserMode.FROZEN ==> true -M
	 * mode != UserMode.FROZEN ==> false -M
	 *
	 * New Invariants Below:
	 * Condition return == false ==> return == false	
	 * Condition return == true ==> return == true 
	 * this.uid == orig(this.uid) -C
	 * this.name == orig(this.name) -C
	 * this.mode == orig(this.mode) -C
	 * this.status == orig(this.status) -C
	 * this.type == orig(this.type) -C
	 * this.sessions == orig(this.sessions) -C
	 * this.sessions[] == orig(this.sessions[]) -C
	 * this.accounts == orig(this.accounts) -C
	 * this.accounts[] == orig(this.accounts[]) -C
	 * this.next_session_id == orig(this.next_session_id) -C
	 * this.next_account == orig(this.next_account) -C
	 * this.country == orig(this.country) -C
	 * this.numOfTransactionsWhenGreyListed == orig(this.numOfTransactionsWhenGreyListed) -C
	 * this.accountRequestsPerSession == orig(this.accountRequestsPerSession) -C
	 * this.maxNumOfAccountRequestsPerSession == orig(this.maxNumOfAccountRequestsPerSession) -C
	 * this.name.toString == orig(this.name.toString) -C
	 * this.country.toString == orig(this.country.toString) -C
	 * Condition return == true ==> return == true
	 * Condition return == false ==> return == false
	 * this.sessions[].getClass().getName() == [um.edu.mt.fits.dsp0.user.UserSession]
	 * this.sessions[].getClass().getName() == [um.edu.mt.fits.dsp0.user.UserSession] :::ENTRY
	 */
	public boolean isFrozen() { return (mode==UserMode.FROZEN); }
	 
	/*
	 * mode == UserMode.DISABLED ==> true -M
	 * mode != UserMode.DISABLED ==> false -M
	 *
	 * New Invariants Below:
	 * Condition return == false ==> return == false	
	 * Condition return == true ==> return == true 
	 * this.uid == orig(this.uid) -C
	 * this.name == orig(this.name) -C
	 * this.mode == orig(this.mode) -C
	 * this.status == orig(this.status) -C
	 * this.type == orig(this.type) -C
	 * this.sessions == orig(this.sessions) -C
	 * this.sessions[] == orig(this.sessions[]) -C
	 * this.accounts == orig(this.accounts) -C
	 * this.accounts[] == orig(this.accounts[]) -C
	 * this.next_session_id == orig(this.next_session_id) -C
	 * this.next_account == orig(this.next_account) -C
	 * this.country == orig(this.country) -C
	 * this.numOfTransactionsWhenGreyListed == orig(this.numOfTransactionsWhenGreyListed) -C
	 * this.accountRequestsPerSession == orig(this.accountRequestsPerSession) -C
	 * this.maxNumOfAccountRequestsPerSession == orig(this.maxNumOfAccountRequestsPerSession) -C
	 * this.name.toString == orig(this.name.toString) -C
	 * this.country.toString == orig(this.country.toString) -C
	 */
	public boolean isDisabled() { return (mode==UserMode.DISABLED); }

	/*
	 * Before:
	 * 	mode != null -M
	 * After:
	 * 	mode == UserMode.ACTIVE -M
	 *
	 * New Invariants Below:
	 * this.uid == orig(this.uid) -C
	 * this.mode == orig(this.mode) -C
	 * this.status == orig(this.status) -C
	 * this.type == orig(this.type) -C
	 * this.sessions == orig(this.sessions) -C
	 * this.sessions[] == orig(this.sessions[]) -C
	 * this.accounts == orig(this.accounts) -C
	 * this.accounts[] == orig(this.accounts[]) -C
	 * this.next_session_id == orig(this.next_session_id) -C
	 * this.next_account == orig(this.next_account) -C
	 * this.country == orig(this.country) -C
	 * this.numOfTransactionsWhenGreyListed == orig(this.numOfTransactionsWhenGreyListed) -C
	 * this.accountRequestsPerSession == orig(this.accountRequestsPerSession) -C
	 * this.maxNumOfAccountRequestsPerSession == orig(this.maxNumOfAccountRequestsPerSession) -C
	 * this.name.toString == orig(this.name.toString) -C
	 * this.country.toString == orig(this.country.toString) -C
	 */
	public void makeActive() { mode=UserMode.ACTIVE; }	

	/*
	 * Before:
	 * 	mode != null -M
	 * After:
	 * 	mode == UserMode.FROZEN -M
	 *
	 * New Invariants Below:
	 * this.uid == orig(this.uid) -C
	 * this.mode == orig(this.mode) -C
	 * this.status == orig(this.status) -C
	 * this.type == orig(this.type) -C
	 * this.sessions == orig(this.sessions) -C
	 * this.sessions[] == orig(this.sessions[]) -C
	 * this.accounts == orig(this.accounts) -C
	 * this.accounts[] == orig(this.accounts[]) -C
	 * this.next_session_id == orig(this.next_session_id) -C
	 * this.next_account == orig(this.next_account) -C
	 * this.country == orig(this.country) -C
	 * this.numOfTransactionsWhenGreyListed == orig(this.numOfTransactionsWhenGreyListed) -C
	 * this.accountRequestsPerSession == orig(this.accountRequestsPerSession) -C
	 * this.maxNumOfAccountRequestsPerSession == orig(this.maxNumOfAccountRequestsPerSession) -C
	 * this.name.toString == orig(this.name.toString) -C
	 * this.country.toString == orig(this.country.toString) -C
	 * this.sessions[].getClass().getName() == [um.edu.mt.fits.dsp0.user.UserSession] -C
	 * this.sessions[].getClass().getName() == [um.edu.mt.fits.dsp0.user.UserSession] -C :::Entry
	 */
	public void makeFrozen() { mode=UserMode.FROZEN; }	

	/*
	 * Before:
	 * 	mode != null -M
	 * After:
	 * 	mode == UserMode.DISABLED -M
	 *
	 * New Invariants Below:
	 * this.uid == orig(this.uid) -C
	 * this.mode == orig(this.mode) -C
	 * this.status == orig(this.status) -C
	 * this.type == orig(this.type) -C
	 * this.sessions == orig(this.sessions) -C
	 * this.sessions[] == orig(this.sessions[]) -C
	 * this.accounts == orig(this.accounts) -C
	 * this.accounts[] == orig(this.accounts[]) -C
	 * this.next_session_id == orig(this.next_session_id) -C
	 * this.next_account == orig(this.next_account) -C
	 * this.country == orig(this.country) -C
	 * this.numOfTransactionsWhenGreyListed == orig(this.numOfTransactionsWhenGreyListed) -C
	 * this.accountRequestsPerSession == orig(this.accountRequestsPerSession) -C
	 * this.maxNumOfAccountRequestsPerSession == orig(this.maxNumOfAccountRequestsPerSession) -C
	 * this.name.toString == orig(this.name.toString) -C
	 * this.country.toString == orig(this.country.toString) -C
	 */
	public void makeDisabled() { mode=UserMode.DISABLED; }	

	/*
	 * Before:
	 * 	mode != null -M
	 * After:
	 * 	mode == UserMode.ACTIVE -M
	 *
	 * New Invariants Below:
	 * this.uid == orig(this.uid) -C
	 * this.mode == orig(this.mode) -C
	 * this.status == orig(this.status) -C
	 * this.type == orig(this.type) -C
	 * this.sessions == orig(this.sessions) -C
	 * this.sessions[] == orig(this.sessions[]) -C
	 * this.accounts == orig(this.accounts) -C
	 * this.accounts[] == orig(this.accounts[]) -C
	 * this.next_session_id == orig(this.next_session_id) -C
	 * this.next_account == orig(this.next_account) -C
	 * this.country == orig(this.country) -C
	 * this.numOfTransactionsWhenGreyListed == orig(this.numOfTransactionsWhenGreyListed) -C
	 * this.accountRequestsPerSession == orig(this.accountRequestsPerSession) -C
	 * this.maxNumOfAccountRequestsPerSession == orig(this.maxNumOfAccountRequestsPerSession) -C
	 * this.name.toString == orig(this.name.toString) -C
	 * this.country.toString == orig(this.country.toString) -C
	 * this.sessions[].getClass().getName() == [um.edu.mt.fits.dsp0.user.UserSession] -C
	 * this.sessions[].getClass().getName() == [um.edu.mt.fits.dsp0.user.UserSession] -C :::ENTRY
	 */
	public void makeUnfrozen() { mode=UserMode.ACTIVE; }

	/*
	 * if sid is valid 
	 * 	return value != null -M
	 * 	return value.sid == sid -M
	 * else	
	 * 	return null -M
	 *
	 * New Invariants Below:
	 * this.uid == orig(this.uid) -C
	 * this.name == orig(this.name) -C
	 * this.mode == orig(this.mode) -C
	 * this.status == orig(this.status) -C
	 * this.type == orig(this.type) -C
	 * this.sessions == orig(this.sessions) -C
	 * this.sessions[] == orig(this.sessions[]) -C
	 * this.accounts == orig(this.accounts) -C
	 * this.accounts[] == orig(this.accounts[]) -C
	 * this.next_session_id == orig(this.next_session_id) -C
	 * this.next_account == orig(this.next_account) -C
	 * this.country == orig(this.country) -C
	 * this.numOfTransactionsWhenGreyListed == orig(this.numOfTransactionsWhenGreyListed) -C
	 * this.accountRequestsPerSession == orig(this.accountRequestsPerSession) -C
	 * this.maxNumOfAccountRequestsPerSession == orig(this.maxNumOfAccountRequestsPerSession) -C
	 * this.name.toString == orig(this.name.toString) -C
	 * this.country.toString == orig(this.country.toString) -C
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
	 * 	next_session_id == orignal(next_session_id) + 1 -M
	 * 	The session with id: original(next_session_id) must be open -M
	 * 	sessions.size == original(session.size) + 1 -M
	 *
	 * New Invariants Below:
	 * this.uid == orig(this.uid) -C
	 * this.name == orig(this.name) -C
	 * this.mode == orig(this.mode) -C
	 * this.status == orig(this.status) -C
	 * this.type == orig(this.type) -C
	 * this.sessions == orig(this.sessions) -C
	 * this.accounts == orig(this.accounts) -C
	 * this.accounts[] == orig(this.accounts[]) -C
	 * this.next_account == orig(this.next_account) -C
	 * this.country == orig(this.country) -C
	 * this.numOfTransactionsWhenGreyListed == orig(this.numOfTransactionsWhenGreyListed) -C
	 * this.accountRequestsPerSession == orig(this.accountRequestsPerSession) -C
	 * this.maxNumOfAccountRequestsPerSession == orig(this.maxNumOfAccountRequestsPerSession) -C
	 * this.name.toString == orig(this.name.toString) -C
	 * this.country.toString == orig(this.country.toString) -C
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
	 * After: 
	 * 	if the sid is invalid ==> return exception -M
	 * 	if sid valid ==> sessionClosed of sid == true -M
	 *
	 * New Invariants Below:
	 * this.uid == orig(this.uid) -C
	 * this.name == orig(this.name) -C
	 * this.mode == orig(this.mode) -C
	 * this.status == orig(this.status) -C
	 * this.type == orig(this.type) -C
	 * this.sessions == orig(this.sessions) -C
	 * this.sessions[] == orig(this.sessions[]) -C
	 * this.accounts == orig(this.accounts) -C
	 * this.accounts[] == orig(this.accounts[]) -C
	 * this.next_session_id == orig(this.next_session_id) -C
	 * this.next_account == orig(this.next_account) -C
	 * this.country == orig(this.country) -C
	 * this.numOfTransactionsWhenGreyListed == orig(this.numOfTransactionsWhenGreyListed) -C
	 * this.accountRequestsPerSession == orig(this.accountRequestsPerSession) -C
	 * this.maxNumOfAccountRequestsPerSession == orig(this.maxNumOfAccountRequestsPerSession) -C
	 * this.name.toString == orig(this.name.toString) -C
	 * this.country.toString == orig(this.country.toString) -C
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
	 * 	account_number != null -M
	 * 
	 * After:
	 * 	if acount_number exists
	 * 		return value.account_number == account_number -M
	 * 	else
	 * 		return value == null -M
	 *
	 * New Invariants Below:
	 * this.uid == orig(this.uid) -C
	 * this.name == orig(this.name) -C
	 * this.mode == orig(this.mode) -C
	 * this.status == orig(this.status) -C
	 * this.type == orig(this.type) -C
	 * this.sessions == orig(this.sessions) -C
	 * this.sessions[] == orig(this.sessions[]) -C
	 * this.accounts == orig(this.accounts) -C
	 * this.accounts[] == orig(this.accounts[]) -C
	 * this.next_session_id == orig(this.next_session_id) -C
	 * this.next_account == orig(this.next_account) -C
	 * this.country == orig(this.country) -C
	 * this.numOfTransactionsWhenGreyListed == orig(this.numOfTransactionsWhenGreyListed) -C
	 * this.accountRequestsPerSession == orig(this.accountRequestsPerSession) -C
	 * this.maxNumOfAccountRequestsPerSession == orig(this.maxNumOfAccountRequestsPerSession) -C
	 * this.name.toString == orig(this.name.toString) -C
	 * this.country.toString == orig(this.country.toString) -C
	 * account_number.toString == orig(account_number.toString)
	 * return.balance >= 0.0
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
	 * 		accounts.size == original(accounts.size) + 1 -M
	 * 		next_account == original(next_account) + 1 -M
	 * 		accountRequestsPerSession.get(sid) == original(accountRequestsPerSession.get(sid)) + 1 -M
	 * 		return value == uid + original(next_account) -M		
	 * 
	 * 
	 *
	 * New Invariants Below: 
	 *
	 * this.uid == orig(this.uid) -C
	 * this.name == orig(this.name) -C
	 * this.mode == orig(this.mode) -C
	 * this.status == orig(this.status) -C
	 * this.type == orig(this.type) -C
	 * this.sessions == orig(this.sessions) -C
	 * this.sessions[] == orig(this.sessions[]) -C
	 * this.sessions[].getClass().getName() == [um.edu.mt.fits.dsp0.user.UserSession] 
	 * this.accounts == orig(this.accounts) -C
	 * this.next_session_id == orig(this.next_session_id) -C
	 * this.country == orig(this.country) -C
	 * this.numOfTransactionsWhenGreyListed == orig(this.numOfTransactionsWhenGreyListed) -C
	 * this.accountRequestsPerSession == orig(this.accountRequestsPerSession) -C
	 * this.maxNumOfAccountRequestsPerSession == orig(this.maxNumOfAccountRequestsPerSession) -C
	 * this.name.toString == orig(this.name.toString) -C
	 * this.country.toString == orig(this.country.toString) -C
	 * (return == null)  ==>  (this.accounts[] == orig(this.accounts[])) -C
	 * (return == null)  ==>  (this.next_account == orig(this.next_account)) -C
	 * size(this.accounts[]) >= orig(size(this.accounts[])) -C
	 * size(this.accounts[])-1 <= orig(size(this.accounts[])) -C
	 * size(this.accounts[])-1 >= orig(size(this.accounts[]))-1 -C	 
	 * Condition return != null ==> size(this.accounts[])-1 == orig(size(this.accounts[])) -C
	 *size(this.accounts[]) >= 1 -C
	 *
	 *this.sessions[].getClass().getName() == [um.edu.mt.fits.dsp0.user.UserSession] :::Entry
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
	 * 		next_account == original(next_account) -M
	 * 		accounts.size == original(accounts.size) -M
	 * 		accountRequestsPerSession.get(sid) == original(accountRequestsPerSession.get(sid)) -M
	 * 		return value == true -M
	 * 		the account opened == false ==> return true	 -M
	 * 	else 
	 * 		throw exception -M
	 *
	 * New Invariants Below:
	 * this.uid == orig(this.uid) -C
	 * this.name == orig(this.name) -C
	 * this.mode == orig(this.mode) -C
	 * this.status == orig(this.status) -C
	 * this.type == orig(this.type) -C
	 * this.sessions == orig(this.sessions) -C
	 * this.sessions[] == orig(this.sessions[]) -C
	 * this.accounts == orig(this.accounts) -C
	 * this.accounts[] == orig(this.accounts[]) -C
	 * this.next_session_id == orig(this.next_session_id) -C
	 * this.next_account == orig(this.next_account) -C
	 * this.country == orig(this.country) -C
	 * this.numOfTransactionsWhenGreyListed == orig(this.numOfTransactionsWhenGreyListed) -C
	 * this.accountRequestsPerSession == orig(this.accountRequestsPerSession) -C
	 * this.maxNumOfAccountRequestsPerSession == orig(this.maxNumOfAccountRequestsPerSession) -C
	 * this.name.toString == orig(this.name.toString) -C
	 * this.country.toString == orig(this.country.toString) -C
	 * account_number.toString == orig(account_number.toString) -C
	 * this.sessions[].getClass().getName() == [um.edu.mt.fits.dsp0.user.UserSession] -C
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
	 * 	account_number != null -M
	 * 	amount >= 0 -M
	 * After:
	 * 	account_number valid && amount >= 0 ==> returns successfully -M
	 * 	account_number invalid ==> UserAccountNotFoundException thrown -M
	 * 	!( amount >= 0.0 ) ==> InvalidFieldsException thrown -M
	 * 
	 *
	 * New Invariants Below:
	 * this.uid == orig(this.uid) -C
	 * this.name == orig(this.name) -C
	 * this.mode == orig(this.mode) -C
	 * this.status == orig(this.status) -C
	 * this.type == orig(this.type) -C
	 * this.sessions == orig(this.sessions) -C
	 * this.sessions[] == orig(this.sessions[]) -C
	 * this.accounts == orig(this.accounts) -C
	 * this.accounts[] == orig(this.accounts[]) -C
	 * this.next_session_id == orig(this.next_session_id) -C
	 * this.next_account == orig(this.next_account) -C
	 * this.country == orig(this.country) -C
	 * this.numOfTransactionsWhenGreyListed == orig(this.numOfTransactionsWhenGreyListed) -C
	 * this.accountRequestsPerSession == orig(this.accountRequestsPerSession) -C
	 * this.maxNumOfAccountRequestsPerSession == orig(this.maxNumOfAccountRequestsPerSession) -C
	 * this.name.toString == orig(this.name.toString) -C
	 * this.country.toString == orig(this.country.toString) -C
	 * account_number.toString == orig(account_number.toString)
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
	 * 	account_number != null -M
	 * 	amount >= 0 -M
	 * After:
	 * 	account_number valid && amount >= 0 ==> returns successfully -M
	 * 	account_number invalid ==> UserAccountNotFoundException thrown -M
	 * 	!( amount >= 0.0 ) ==> InvalidFieldsException thrown -M
	 *  status == UserStatus.GREYLISTED ==> numOfTransactionsWhenGreyListed == original(numOfTransactionsWhenGreylisted) + 1 -M 
	 *
	 *
	 * New Invariants Below:
	 * this.uid == orig(this.uid) -C
	 * this.name == orig(this.name) -C
	 * this.mode == orig(this.mode) -C
	 * this.status == orig(this.status) -C
	 * this.type == orig(this.type) -C
	 * this.sessions == orig(this.sessions) -C
	 * this.sessions[] == orig(this.sessions[]) -C
	 * this.accounts == orig(this.accounts) -C
	 * this.accounts[] == orig(this.accounts[]) -C
	 * this.next_session_id == orig(this.next_session_id) -C
	 * this.next_account == orig(this.next_account) -C
	 * this.country == orig(this.country) -C
	 * this.accountRequestsPerSession == orig(this.accountRequestsPerSession) -C
	 * this.maxNumOfAccountRequestsPerSession == orig(this.maxNumOfAccountRequestsPerSession) -C
	 * this.name.toString == orig(this.name.toString) -C
	 * this.country.toString == orig(this.country.toString) -C
	 * account_number.toString == orig(account_number.toString)
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
