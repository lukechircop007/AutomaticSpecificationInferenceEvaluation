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

public class UserInfoUTFail {

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
	public void isNormal_Test_Fail_NotNormalUser(){
		userInfo.makeSilverUser();
		assertTrue(!userInfo.isNormalUser());
	}
	
	@Test
	public void isGreylisted_Test_Fail_NotGreyListed(){
		assertTrue(!userInfo.isGreylisted());
	}
	
	@Test
	public void isBlacklisted_Test_Fail_NotGreyListed(){
		assertTrue(!userInfo.isBlacklisted());
	}
	
	@Test
	public void whitelist_Test_Fail_GreyListedNoTransactions(){
		userInfo.greylist();
		userInfo.numOfTransactionsWhenGreyListed = 2;
		assertTrue(!userInfo.isWhitelisted());
	}

	@Test
	public void isDisabled_Test_Fail_IsNotDisabled(){
		userInfo.makeActive();
		assertTrue(!userInfo.isDisabled());
	}

}
