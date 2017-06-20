package um.edu.mt.fits.dsp0.user;


public class UserAccount {
	
	/*
	 * opened != null
	 * opened can be both false and true
	 */
	protected boolean opened;
	
	/*
	 * account_number != null
	 */
	protected String account_number;
	
	/*
	 * balance starts with 0.0 value
	 * balance >= 0.0 
	 */
	protected double balance;
	
	/*
	 * owner != null
	 * owner >= 0
	 */
	protected Integer owner;
	 
	/*
	 *  uid >= 0
	 *  anumber != null
	 */
	public UserAccount(Integer uid, String anumber) {
		account_number = anumber;
		balance = 0;
		opened = false;
		owner = uid;
	}
 
	/*
	 * before:
	 * 	opened can be set to true or false
	 * After: 
	 * 	opened == true
	 * 	Nothing else changes 
	 */
	public void activateAccount() { opened = true; }
	
	/*
	 * Before:
	 * 	opened can be both true or false
	 * After:
	 * 	opened == false
	 * 	Nothing else changes
	 */
	public void closeAccount() { opened = false; }

	/*
	 * Before:
	 * 	balance >= 0.0
	 * 	amount >= 0.0
	 * After:
	 * 	amount == original(amount)
	 *  balance >= 0.0
	 * 	balance = original(balance) + amount
	 * 	Nothing Else Changes
	 */
	public void withdraw(double amount) { balance -= amount; }
	
	/*
	 * Before:
	 * 	amount >= 0.0
	 * 	balance >= 0.0
	 * After:
	 * 	amount == original(amount)
	 * 	balance >= 0.0
	 * 	balance = original(balance) - amount
	 * 	Nothing Else Changes
	 */
	public void deposit(double amount) { balance += amount; }

	/*
	 * Before: 
	 * 	amount can be any value
	 * After:
	 * 	( opened == true && amount >= 0.0 ) ==> return true
	 * 	opened == false ==> return false
	 *  amount < 0.0 --> return false
	 *  Nothing Changes	 
	 */
	public boolean isValidDepositAmount(double amount){ if(amount >= 0.0 && opened) return true; else return false;}
	
	/*
	 * Before: 
	 * 	amount can be any value
	 * After:
	 * 	( opened == true && ( balance - amount ) >= 0.0 ) ==> return true
	 *  balance - amount < 0.0 ==> return false
	 * 	opened == false ==> return false
	 *  amount < 0.0 --> return false 
	 *  Nothing changes
	 */
	public boolean isValidWithdrawalAmount(double amount){ if(((balance-amount) >= 0.0) && opened) return true; else return false;}
	 
	/*
	 * return value == account_number
	 * Nothing Changes
	 */
	public String getAccountNumber() { return account_number; }
	
	/*
	 * return value == balance
	 * Nothing changes
	 */
	public double getBalance() { return balance; }
	
	/*
	 * return value == owner
	 * Nothing changes
	 */
	public Integer getOwner() { return owner; }
	
	/*
	 * return value == opened
	 * Nothing Changes
	 */
	public boolean getOpened() { return opened; }
	
}
