package um.edu.mt.fits.dsp0.user;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import um.edu.mt.fits.dsp0.user.UserAccount;

public class UserAccountUTPass {

	Integer userId = 03;
	String accountNumber = "45678";
	UserAccount userAccount = null; 
	
	double startingBalance = 300.0;
	double amountToDeposit = 100.0;
	double amountToWithdraw = 100.0;
	
	@Before
	public void setUp() throws Exception {
		userAccount = new UserAccount(userId, accountNumber);
	}
 
	@Test
	public void activateAccount_Test_Pass(){
		userAccount.activateAccount();	
		
		assertTrue(userAccount.opened);
	}
	
	@Test
	public void closeAccount_Test_Pass(){
		userAccount.closeAccount();		
		
		assertTrue(!userAccount.opened);
	}

	@Test
	public void getAccountNumber_Test_Pass(){		
		assertTrue(userAccount.getAccountNumber() == accountNumber);
	}

	@Test
	public void getBalance_Zero_Test_Pass(){		
		assertTrue(userAccount.getBalance() == 0.0);
	}
	
	@Test
	public void getBalance_Updates_Test_Pass(){	
		userAccount.balance = startingBalance;
		assertTrue(userAccount.getBalance() == startingBalance);
	}
			
	@Test
	public void getOwner_Test_Pass(){		
		assertTrue(userAccount.getOwner() == userId);
	}
	
	@Test
	public void getOpened_SetToFalse_Test_Pass(){		
		assertTrue(!userAccount.getOpened());
	}
	
	@Test
	public void getOpened_SetToTrue_Test_Pass(){	
		userAccount.opened = true;
		assertTrue(userAccount.getOpened());
	}	
	
	@Test
	public void withdraw_Test_Pass(){
		userAccount.balance = startingBalance;
		
		userAccount.withdraw(amountToWithdraw);
		
		assertTrue(userAccount.balance == (startingBalance - amountToWithdraw));
	}

	@Test
	public void deposit_Test_Pass(){
		userAccount.balance = startingBalance;
		
		userAccount.deposit(amountToDeposit);
		
		assertTrue(userAccount.balance == (startingBalance + amountToDeposit));
	}
	
	@Test
	public void isValidDepositAmount_Test_Pass(){
		userAccount.opened = true;
		assertTrue(userAccount.isValidDepositAmount(amountToDeposit));
	}
	
	@Test
	public void isValidWithdrawalAmount_Test_Pass(){
		userAccount.opened = true;
		userAccount.balance = startingBalance;
		assertTrue(userAccount.isValidWithdrawalAmount(amountToWithdraw));
	}
	
}
