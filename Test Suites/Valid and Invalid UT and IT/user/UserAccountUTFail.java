package um.edu.mt.fits.dsp0.user;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import um.edu.mt.fits.dsp0.user.UserAccount;

public class UserAccountUTFail {

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
	public void isValidDepositAmount_Test_Fail_notOpenedButValidAmount(){
		assertTrue(!userAccount.isValidDepositAmount(amountToDeposit));
	}
	
	@Test
	public void isValidDepositAmount_Test_Fail_invalidAmount(){
		userAccount.opened = true;
		assertTrue(!userAccount.isValidDepositAmount((-1)*amountToDeposit));
	}
		
	@Test
	public void isValidWithdrawalAmount_Test_Fail_notOpenButValidAmount(){
		userAccount.balance = startingBalance;
		assertTrue(!userAccount.isValidWithdrawalAmount(amountToWithdraw));
	}
	
	@Test
	public void isValidWithdrawalAmount_Test_Fail_InvalidAmount(){
		userAccount.opened = true;
		userAccount.balance = startingBalance;
		assertTrue(!userAccount.isValidWithdrawalAmount(amountToWithdraw+startingBalance));
	}
}
