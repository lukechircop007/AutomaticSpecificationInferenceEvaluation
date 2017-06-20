package um.edu.mt.fits.dsp0.user;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import um.edu.mt.fits.dsp0.exceptions.InvalidFieldsException;
import um.edu.mt.fits.dsp0.exceptions.SessionNotFoundException;
import um.edu.mt.fits.dsp0.exceptions.UserAccountNotFoundException;
import um.edu.mt.fits.dsp0.exceptions.UserInfoNotFoundException;
import um.edu.mt.fits.dsp0.user.UserInfo;

public class UserInfoITFail {

	Integer uid = 03;
	String name = "john";
	String country = "Malta";
	UserInfo userInfo = null;

	double setInitialGoodBalance = 2000.0;
	double setInitialBadBalance = 100.0;
	
	double amountToWithdrawGood = 200.0;
	double amountToWithdrawBad = 300.0;
	
	double amountToDepositGood = 350.50;

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setUp() throws Exception {
		userInfo = new UserInfo(uid,name,country);
	}
	 
	@Test(expected = SessionNotFoundException.class)
	public void closeSession_Test_Fail_SessionNotFound() throws SessionNotFoundException
	{
		userInfo.closeSession(34);
	}
	
	@Test
	public void createAccount_Test_Fail_ExceededMaxRequests(){
		
		Integer sid = userInfo.openSession();
		userInfo.createAccount(sid);

		assertTrue(userInfo.createAccount(sid) != null);
		assertTrue(userInfo.createAccount(sid) != null);
		assertTrue(userInfo.createAccount(sid) != null);
		assertTrue(userInfo.createAccount(sid) != null);
		assertTrue(userInfo.createAccount(sid) != null);
		assertTrue(userInfo.createAccount(sid) != null);
		assertTrue(userInfo.createAccount(sid) != null);
		assertTrue(userInfo.createAccount(sid) != null);
		assertTrue(userInfo.createAccount(sid) != null);

		assertTrue(userInfo.accounts.size() == 10);
		assertTrue(userInfo.accountRequestsPerSession.get(sid)== 10);
		
		assertTrue(userInfo.createAccount(sid) == null);
		assertTrue(userInfo.accounts.size() == 10);
		assertTrue(userInfo.accountRequestsPerSession.get(sid)== 10);
	}
	
	@Test
	public void getAccount_Test_Fail_NoAccountExists(){
		assertTrue(userInfo.getAccount("32546") == null);
	}
	
	@Test(expected = UserAccountNotFoundException.class)
	public void deleteAccount_Test_Fail_AccountDoesNotExist() throws UserAccountNotFoundException{
		userInfo.deleteAccount("345789635247896");
	}
	
	@Test
	public void withdrawFrom_Test_Fail_InsufficientFunds()throws InvalidFieldsException, UserAccountNotFoundException{

		exception.expect(InvalidFieldsException.class);
		
		Integer sid = userInfo.openSession();
		String account = userInfo.createAccount(sid);
		userInfo.getAccount(account).opened = true;

		assertTrue(userInfo.getAccount(account).balance == 0.0);
		
		userInfo.getAccount(account).balance = setInitialBadBalance;

		assertTrue(userInfo.getAccount(account).balance == setInitialBadBalance);

		
		userInfo.withdrawFrom(account, amountToWithdrawBad);
		
		assertTrue(userInfo.getAccount(account).balance == (setInitialBadBalance));	
	}

	@Test
	public void withdrawFrom_Test_Fail_AccountNotOpen()throws InvalidFieldsException, UserAccountNotFoundException{

		exception.expect(InvalidFieldsException.class);
		
		Integer sid = userInfo.openSession();
		String account = userInfo.createAccount(sid);		

		assertTrue(userInfo.getAccount(account).balance == 0.0);
		
		userInfo.getAccount(account).balance = setInitialBadBalance;

		assertTrue(userInfo.getAccount(account).balance == setInitialBadBalance);

		userInfo.withdrawFrom(account, amountToWithdrawBad);
		
		assertTrue(userInfo.getAccount(account).balance == (setInitialBadBalance));		
	}
	
	@Test(expected = UserAccountNotFoundException.class)
	public void withdrawFrom_Test_Fail_AccountDoesNotExist() throws InvalidFieldsException, UserAccountNotFoundException{
		userInfo.withdrawFrom("35469o78", amountToWithdrawGood);
	}
	
	@Test
	public void DepositTo_Test_Fail_InvalidValue()throws InvalidFieldsException, UserAccountNotFoundException{

		exception.expect(InvalidFieldsException.class);
		
		Integer sid = userInfo.openSession();
		String account = userInfo.createAccount(sid);
		userInfo.getAccount(account).opened = true;

		assertTrue(userInfo.getAccount(account).balance == 0.0);
		
		userInfo.getAccount(account).balance = setInitialGoodBalance;

		assertTrue(userInfo.getAccount(account).balance == setInitialGoodBalance);

		
		userInfo.depositTo(account, (-1*amountToDepositGood));
		System.out.println(""+(-1*amountToDepositGood));
		
		assertTrue(userInfo.getAccount(account).balance == (setInitialGoodBalance));	
	}

	@Test
	public void DepositTo_Test_Fail_AccountNotOpen()throws InvalidFieldsException, UserAccountNotFoundException{

		exception.expect(InvalidFieldsException.class);
		
		Integer sid = userInfo.openSession();
		String account = userInfo.createAccount(sid);		

		assertTrue(userInfo.getAccount(account).balance == 0.0);
		
		userInfo.getAccount(account).balance = setInitialGoodBalance;

		assertTrue(userInfo.getAccount(account).balance == setInitialGoodBalance);

		userInfo.depositTo(account, amountToDepositGood);
		
		assertTrue(userInfo.getAccount(account).balance == (setInitialGoodBalance));		
	}

	@Test(expected = UserAccountNotFoundException.class)
	public void DepositTo_Test_Fail_AccountDoesNotExist() throws UserAccountNotFoundException, InvalidFieldsException{
		userInfo.depositTo("35407896", amountToDepositGood);
	}


}
