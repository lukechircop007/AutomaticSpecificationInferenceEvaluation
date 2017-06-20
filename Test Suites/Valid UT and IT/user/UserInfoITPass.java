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

public class UserInfoITPass {

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
	
	@Test
	public void openSession_Test_Pass(){
		Integer sid = userInfo.openSession();
		assertTrue(!userInfo.getSession(sid).sessionClosed);
		assertTrue(userInfo.next_session_id == 1);		
	}
	 
	@Test
	public void closeSession_Test_Pass() throws SessionNotFoundException
	{
		Integer sid = userInfo.openSession();
		assertTrue(!userInfo.getSession(sid).sessionClosed);
		assertTrue(userInfo.next_session_id == 1);	
		
		userInfo.closeSession(sid);
		assertTrue(userInfo.getSession(sid).sessionClosed);
	}
	
	@Test
	public void createAccount_Test_Pass(){
		
		Integer sid = userInfo.openSession();
		String account = userInfo.createAccount(sid);
		
		assertTrue(userInfo.accounts.size() == 1);
		assertTrue(userInfo.accountRequestsPerSession.get(sid)== 1);
		assertTrue(!userInfo.getAccount(account).opened);
	}
	
	@Test
	public void deleteAccount_Test_Pass() throws UserAccountNotFoundException{
		
		Integer sid = userInfo.openSession();
		String account = userInfo.createAccount(sid);
		userInfo.getAccount(account).activateAccount();
				
		assertTrue(userInfo.accounts.size() == 1);
		assertTrue(userInfo.accountRequestsPerSession.get(sid)== 1);
		assertTrue(userInfo.getAccount(account).opened);
		
		userInfo.deleteAccount(account);		

		assertTrue(userInfo.accounts.size() == 1);
		assertTrue(userInfo.accountRequestsPerSession.get(sid)== 1);
		assertTrue(!userInfo.getAccount(account).opened);
	}
	
	@Test
	public void withdrawFrom_Test_Pass() throws InvalidFieldsException, UserAccountNotFoundException{
		
		Integer sid = userInfo.openSession();
		String account = userInfo.createAccount(sid);
		userInfo.getAccount(account).opened = true;

		assertTrue(userInfo.getAccount(account).balance == 0.0);
		userInfo.getAccount(account).balance = setInitialGoodBalance;

		assertTrue(userInfo.getAccount(account).balance == setInitialGoodBalance);
		
		userInfo.withdrawFrom(account, amountToWithdrawGood);
		
		assertTrue(userInfo.getAccount(account).balance == (setInitialGoodBalance-amountToWithdrawGood));		
	}
	
	@Test
	public void depositTo_Test_Pass() throws InvalidFieldsException, UserAccountNotFoundException{
		
		Integer sid = userInfo.openSession();
		String account = userInfo.createAccount(sid);
		userInfo.getAccount(account).opened = true;

		assertTrue(userInfo.getAccount(account).balance == 0.0);
		userInfo.getAccount(account).balance = setInitialGoodBalance;

		assertTrue(userInfo.getAccount(account).balance == setInitialGoodBalance);
		
		userInfo.depositTo(account, amountToDepositGood);
		
		assertTrue(userInfo.getAccount(account).balance == (setInitialGoodBalance+amountToDepositGood));		
	}
	
}
