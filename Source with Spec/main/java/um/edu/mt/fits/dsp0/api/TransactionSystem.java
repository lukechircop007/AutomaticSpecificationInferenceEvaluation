package um.edu.mt.fits.dsp0.api;

import java.util.ArrayList;
import java.util.Iterator;

import um.edu.mt.fits.dsp0.user.UserInfo;

public class TransactionSystem {
	
	/*
	 * initialised != null
	 * initialised can be true and false
	 */
	protected boolean initialised;
	
	/*
	 * users == null when we have no users
	 * users != null when we have registered users
	 */
	protected ArrayList<UserInfo> users;
	
	/*
	 * next_user_id != null
	 * next_user_id >= 0
	 */
	protected Integer next_user_id;
 
	/*
	 * After:
	 * 	users != null
	 * 	initialised == false
	 * 	next_user_id = 1
	 * 
	 */
	public TransactionSystem() 
	{
		users = new ArrayList<UserInfo>();
		initialised = false;
		next_user_id = 1;
	}

	/*
	 * Before:
	 * 	initialised can be false and true
	 * After:
	 * 	initialised == true
	 *	Nothing else changed
	 */
	public void initialise() { initialised = true; }
	
	/*
	 * return value == initialised
	 * value can be both true and false
	 * Nothing else changes
	 */
	public boolean getInitialised(){ return this.initialised; }	
	 
	/*
	 * Before:
	 * 	name != null
	 * 	country != null
	 * After:
	 * 	uid == original(next_user_id)
	 * 	next_user_id == original(next_user_id)+1
	 * 	return value == uid
	 * 	In users there should be a new user with uid , name and country set with balance 0.0
	 */
	public Integer addUser(String name, String country) 
	{
		Integer uid = next_user_id;
		next_user_id++;
	
		users.add(new UserInfo(uid, name, country));
		return uid;
	}
		
	/*
	 * return value == users
	 * Nothing Changes
	 */
	public ArrayList<UserInfo> getUsers() 
	{
		return users;
	}	
	
	/* Lookup a user by user-id
	 * uid != null
	 * if uid exists in list
	 * 	return value.uid == uid	
	 * Nothing changes
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
	 * name == empty ==> return false
	 * country == empty ==> return false
	 * name != empty && country != empty ==> return true
	 * Nothing changes
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
	 * uid == null ==> ( return == -1 )
	 * amount >= 0.0
	 * 
	 * if ( user is gold user )
	 * 	amount <= 100 ==> ( return value == 0 )
	 * 	amount <= 1000 ==> ( return value == amount * 0.02 )
	 * 	amount > 1000 ==> ( return value == amount * 0.01 )
	 * 
	 * if( user is silver user )
	 * 	amount <= 1000 ==> ( return value == amount * 0.03 )
	 * 	amount > 1000 ==> ( return value == amount * 0.02 )
	 * 
	 * if( user is normal user )
	 * 	( amount * 0.05 > 2.0 ) ==> ( return value == amount * 0.05 )
	 * 	( amount * 0.05 <= 2.0 ) ==> return value == 2.0
	 * 
	 * Nothing changes
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
