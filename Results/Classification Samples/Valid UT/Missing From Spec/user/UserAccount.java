package um.edu.mt.fits.dsp0.user;


public class UserAccount {
	
	/*
	 * opened != null -M
	 */
	protected boolean opened;
	
	/*
	 * account_number != null
	 */
	protected String account_number;
	
	/*
	 * balance >= 0.0 
	 */
	protected double balance;
	
	/*
	 * owner != null
	 * owner >= 0 -M
	 */
	protected Integer owner;
	 
	/*
	 *  uid >= 0 -M
	 *  anumber != null
	 *  After:
	 *  	this.account_number == anumber -M
	 *  	this.balance == 0.0
	 *  	this.opened == false
	 *  	this.owner == uid -M
	 *
	 * New Invariants Below: EXIT
	 * anumber.toString == orig(anumber.toString) -C
	 */
	public UserAccount(Integer uid, String anumber) {
		account_number = anumber;
		balance = 0;
		opened = false;
		owner = uid;
	}
 
	/*
	 * before:
	 * 	opened  == false
	 * After: 
	 * 	opened == true
	 * 
	 * New Invariants Below: Exit
	 * this.account_number == orig(this.account_number) -C
	 * this.balance == orig(this.balance) -C
	 * this.owner == orig(this.owner) -C
	 * this.account_number.toString == orig(this.account_number.toString) -C
	 */
	public void activateAccount() { opened = true; }
	
	/*
	 * Before:
	 * 	opened == true -M
	 * After:
	 * 	opened == false
	 *
	 * New Invariants Below: EXIT
	 * this.account_number == orig(this.account_number) -C
	 * this.balance == orig(this.balance) -C
	 * this.owner == orig(this.owner) -C
	 * this.account_number.toString == orig(this.account_number.toString) -C	 
	 */
	public void closeAccount() { opened = false; }

	/*
	 * Before:
	 * 	balance >= 0.0 -M
	 * 	amount >= 0.0 -M
	 * After:
	 * 	amount == original(amount) -M
	 *  balance >= 0.0 -M
	 * 	balance = original(balance) - amount -M
	 *
	 * New Invariants Below: 
	 * this.opened == orig(this.opened) -C
	 * this.account_number == orig(this.account_number) -C
	 * this.owner == orig(this.owner) -C
	 * this.account_number.toString == orig(this.account_number.toString) -C
	 * this.balance < orig(this.balance) -C	 
	 * this.opened == true -C
	 * this.opened == true -C :::Entry
	 */
	public void withdraw(double amount) { balance -= amount; }
	
	/*
	 * Before:
	 * 	amount >= 0.0 -M
	 * 	balance >= 0.0 -M
	 * After:
	 * 	amount == original(amount) -M
	 * 	balance >= 0.0 -M
	 * 	balance = original(balance) + amount -M
	 *
	 * New Invariants Below: EXIT
	 * this.opened == true -C
	 * this.opened == orig(this.opened) -C
	 * this.account_number == orig(this.account_number) -C
	 * this.owner == orig(this.owner) -C
	 * this.account_number.toString == orig(this.account_number.toString) -C
	 * this.balance > orig(this.balance) -C
	 *	 
	 * this.opened == true -C :::ENTRY
	 */
	public void deposit(double amount) { balance += amount; }

	/*
	 * Before: 
	 * 	amount != null -M
	 * After:
	 * 	( opened == true && amount >= 0.0 ) ==> return true -M
	 * 	opened == false ==> return false -M
	 *  amount < 0.0 ==> return false -M
	 * 
	 * New Invariants Below:
	 * Condition return == true ==> return true && opened == true
	 * Condition return == false ==> return false
	 * this.opened == orig(this.opened) -C
	 * this.account_number == orig(this.account_number) -C
	 * this.balance == orig(this.balance) -C
	 * this.owner == orig(this.owner) -C
	 * (this.account_number.toString == orig(this.account_number.toString) -C
	 * (return == true)  ==>  (this.opened == true) -C
	 */
	public boolean isValidDepositAmount(double amount){ if(amount >= 0.0 && opened) return true; else return false;}
	
	/*
	 * Before: 
	 * 	amount != null -M
	 * After:
	 * 	( opened == true && ( balance - amount ) >= 0.0 ) ==> return true -M
	 *  balance - amount < 0.0 ==> return false -M
	 * 	opened == false ==> return false -M
	 *  amount < 0.0 ==> return false  -M
	 * 
	 * New Invariants Below: 
	 * Condition return == false ==> return == false
	 * Condition return == true ==> opened == true && return == true
	 * this.opened == orig(this.opened) -C
	 * this.account_number == orig(this.account_number) -C
	 * this.balance == orig(this.balance) -C
	 * this.owner == orig(this.owner) -C
	 * this.account_number.toString == orig(this.account_number.toString) -C
	 * (return == true)  ==>  (this.opened == true) -C
	 */
	public boolean isValidWithdrawalAmount(double amount){ if(((balance-amount) >= 0.0) && opened) return true; else return false;}
	 
	/*
	 * return value == account_number -M
	 * 
	 * New Invariants Below:
	 * this.opened == orig(this.opened) -C
	 * this.account_number == orig(this.account_number) -C
	 * this.balance == orig(this.balance) -C
	 * this.owner == orig(this.owner) -C
	 * this.account_number.toString == orig(this.account_number.toString) -C
	 */
	public String getAccountNumber() { return account_number; }
	
	/*
	 * return value == balance -M
	 * 
	 * New Invariants Below: 
	 * this.opened == orig(this.opened) -C
	 * this.account_number == orig(this.account_number) -C
	 * this.balance == orig(this.balance) -C
	 * this.owner == orig(this.owner) -C
	 * this.account_number.toString == orig(this.account_number.toString) -C
	 * return >= 0.0 -C
	 */
	public double getBalance() { return balance; }
	
	/*
	 * return value == owner -M
	 * 
	 * New Invariants Below:
	 * this.opened == orig(this.opened) -C
	 * this.account_number == orig(this.account_number) -C
	 * this.balance == orig(this.balance) -C
	 * this.owner == orig(this.owner) -C
	 * this.account_number.toString == orig(this.account_number.toString) -C
	 */
	public Integer getOwner() { return owner; }
	
	/* IMP CHECK !!!!!
	 * return value == opened -M
	 * 
	 * New Invariants Below: EXIT
	 * this.opened == orig(this.opened) -C
	 * this.account_number == orig(this.account_number) -C
	 * this.balance == orig(this.balance) -C
	 * this.owner == orig(this.owner) -C
	 * (this.opened == false)  <==>  (return == false) -C
	 * (this.opened == true)  <==>  (return == true) -C
	 * this.account_number.toString == orig(this.account_number.toString) -C
	 * Condition Return == true ==> opened == true && return == true
	 * Condition return == false ==> opened == false && return == false
	 */
	public boolean getOpened() { return opened; }
	
}
