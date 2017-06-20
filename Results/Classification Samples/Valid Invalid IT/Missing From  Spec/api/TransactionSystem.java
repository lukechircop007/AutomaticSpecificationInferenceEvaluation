package um.edu.mt.fits.dsp0.api;

import java.util.ArrayList;
import java.util.Iterator;

import um.edu.mt.fits.dsp0.user.UserInfo;

public class TransactionSystem {
	
	/*
	 * initialised != null -M
	 */
	protected boolean initialised;
	
	/*
	 * users != null
	 *
	 * New Inv Below:
	 * this.users[].getClass().getName() elements == um.edu.mt.fits.dsp0.user.UserInfo.class
	 */
	protected ArrayList<UserInfo> users;
	
	/*
	 * next_user_id != null
	 * next_user_id >= 0 -M
	 */
	protected Integer next_user_id;
 
	/*
	 * After:
	 * 	users != null -M
	 * 	initialised == false
	 * 	next_user_id = 1 -M
	 * 
	 * New Invariants Below:
	 * this.users[] == [] 
	 */
	public TransactionSystem() 
	{
		users = new ArrayList<UserInfo>();
		initialised = false;
		next_user_id = 1;
	}

	/*
	 * Before:
	 * 	initialised  == false
	 * After:
	 * 	initialised == true
	 *
	 *  New Inv Below:
	 *  this.users == orig(this.users)
	 *  this.users[] == orig(this.users[])
	 *  this.next_user_id == orig(this.next_user_id)
	 */
	public void initialise() { initialised = true; }
	
	/* IMP CHECK!!!!!
	 * return value == initialised
	 *
	 * New Invariants Below:
	 * this.initialised == orig(this.initialised) 
	 * this.users == orig(this.users) 
	 * this.users[] == orig(this.users[]) 
	 * this.next_user_id == orig(this.next_user_id) 
	 * Reflects return value == initialised  but if one missing the spec is not satisfied
	 * (this.initialised == false && this.users[].getClass().getName() == [um.edu.mt.fits.dsp0.user.UserInfo])  <==>  (return == false)
	 * (this.initialised == true)  <==>  (return == true)
	 */
	public boolean getInitialised(){ return this.initialised; }	
	 
	/*
	 * Before:
	 * 	name != null
	 * 	country != null
	 * After:
	 * 	uid == original(next_user_id) -M
	 * 	next_user_id == original(next_user_id)+1 -M
	 * 	return value == uid -M
	 *
	 * New Invariants Below: Exit
	 * name.toString == orig(name.toString)
	 * country.toString == orig(country.toString)
	 * this.initialised == orig(this.initialised)
	 * this.users == orig(this.users)
	 * size(this.users[])-1 == orig(size(this.users[]))
	 */
	public Integer addUser(String name, String country) 
	{
		Integer uid = next_user_id;
		next_user_id++;
	
		users.add(new UserInfo(uid, name, country));
		return uid;
	}
		
	/*
	 * return value == users -M
	 * 
	 * New Invariants Below: EXIT
	 * this.initialised == orig(this.initialised) -C
	 * this.users == orig(this.users)-C
	 * this.users[] == orig(this.users[]) -C
	 * this.next_user_id == orig(this.next_user_id) -C
	 * return[].getClass().getName() elements == um.edu.mt.fits.dsp0.user.UserInfo.class -C
	 */
	public ArrayList<UserInfo> getUsers() 
	{
		return users;
	}	
	
	/* Lookup a user by user-id
	 * uid != null
	 * if uid exists in list ==> return value.uid == uid -M
	 * 
	 * New Invariants Below: EXIT
	 * return.maxNumOfAccountRequestsPerSession == 10 -C
	 * size(um.edu.mt.fits.dsp0.user.UserInfo$UserMode.$VALUES[]) == 3 -C
	 * size(um.edu.mt.fits.dsp0.user.UserInfo$UserStatus.$VALUES[]) == 3 -C
	 * size(um.edu.mt.fits.dsp0.user.UserInfo$UserType.$VALUES[]) == 3 -C
	 * this.initialised == orig(this.initialised) -C
	 * this.users == orig(this.users)-C
	 * this.users[] == orig(this.users[]) -C
	 * this.next_user_id == orig(this.next_user_id) -C
	 * return.sessions[].getClass().getName() elements == um.edu.mt.fits.dsp0.user.UserSession.class -C
	 * return.accounts[].getClass().getName() elements == um.edu.mt.fits.dsp0.user.UserAccount.class -C
	 */
	public UserInfo getUserInfo(Integer uid) 
	{
		UserInfo u;
		
		Iterator<UserInfo> iterator = users.iterator();
		while (iterator.hasNext()) {
		    u = iterator.next();
		    if (u.getId()==uid) return u;
		}
		return null;
	}

	/*
	 * name == empty ==> return false -M
	 * country == empty ==> return false -M
	 * name != empty && country != empty ==> return true -M
	 *
	 * New invariants below: Exit
	 * Condition return == true  ==> return == true 
	 * Condition return == false  ==> return == false
	 * name.toString == orig(name.toString) -C
	 * country.toString == orig(country.toString) -C
	 * this.initialised == orig(this.initialised) -C
	 * this.users == orig(this.users) -C
	 * this.users[] == orig(this.users[]) -C
	 * this.next_user_id == orig(this.next_user_id) -C
	 * (return == false)  ==>  (this.initialised == false) -C 
	 * (return == true)  ==>  (this.users[].getClass().getName() elements == um.edu.mt.fits.dsp0.user.UserInfo.class) 
	 */
	public boolean safeToAddUser(String name, String country){
		
		//check if missing values... 
		if(name.equals("") || country.equals("")){
			return false;
		}
		
		return true;
	}
	
	/* Calculate the charges when a particular user makes a transfer
	 * 
	 * uid == null ==> ( return == -1 ) -M
	 * amount >= 0.0 -M
	 * 
	 * if ( user is gold user )
	 * 	amount <= 100 ==> ( return value == 0 ) -M
	 * 	amount <= 1000 ==> ( return value == amount * 0.02 ) -M
	 * 	amount > 1000 ==> ( return value == amount * 0.01 ) -M
	 * 
	 * if( user is silver user )
	 * 	amount <= 1000 ==> ( return value == amount * 0.03 ) -M
	 * 	amount > 1000 ==> ( return value == amount * 0.02 ) -M
	 * 
	 * if( user is normal user )
	 * 	( amount * 0.05 > 2.0 ) ==> ( return value == amount * 0.05 ) -M
	 * 	( amount * 0.05 <= 2.0 ) ==> return value == 2.0 -M
	 * 
	 *
	 * New Invariants Below: EXIT
	 * this.initialised == orig(this.initialised) -C
	 * this.users == orig(this.users) -C
	 * this.users[] == orig(this.users[]) -C
	 * this.next_user_id == orig(this.next_user_id) -C
	 * this.users[].getClass().getName() == [um.edu.mt.fits.dsp0.user.UserInfo] -C
	 * this.initialised == true Exit 161
	 */
	public double charges(Integer uid, double amount) 
	{
		UserInfo u = getUserInfo(uid);
		if( u != null){		
			if (u.isGoldUser()) {
				if (amount <= 100) 
					return 0;				// no charges
				if (amount <= 1000) 
					return (amount * 0.02); // 2% charges
				
				return (amount * 0.01);						// 1% charges
			}
			
			if (u.isSilverUser()) {
				if (amount <= 1000) 
					return (amount * 0.03); // 3% charges
				return (amount * 0.02);						// 2% charges
			}
			
			if (u.isNormalUser()) {
				if (amount*0.05 > 2.0) {
					return (amount*0.05); 
				} else {
					return 2.0;
				}											// 5% charges, minimum of $2
			}
		}
		return -1;
	}

}
