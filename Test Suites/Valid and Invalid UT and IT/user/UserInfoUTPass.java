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

public class UserInfoUTPass {

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
	public void initialise_Test_Pass(){
		assertTrue(userInfo.uid == uid);
		assertTrue(userInfo.name.equals(name));
		assertTrue(userInfo.country.equals(country));
		assertTrue(userInfo.isDisabled());
		assertTrue(userInfo.isWhitelisted());
		assertTrue(userInfo.isNormalUser());
	}

	@Test
	public void getID_Test_Pass(){
		assertTrue(userInfo.getId() == uid);
	}
	
	@Test
	public void getName_Test_Pass(){
		assertTrue(userInfo.getName().equals(name));
	}
	
	@Test
	public void getCountry_Test_Pass(){
		assertTrue(userInfo.getCountry().equals(country));
	}
	
	@Test
	public void isGoldUser_Test_Pass(){
		assertTrue(!userInfo.isGoldUser());
		userInfo.makeGoldUser();
		assertTrue(userInfo.isGoldUser());
	}
	
	@Test
	public void isSilverUser_Test_Pass(){
		assertTrue(!userInfo.isSilverUser());
		userInfo.makeSilverUser();
		assertTrue(userInfo.isSilverUser());
	}
	
	@Test
	public void isWhitelisted_Test_Pass(){
		assertTrue(userInfo.isWhitelisted());
		userInfo.whitelist();
		assertTrue(userInfo.isWhitelisted());
	}
	
	@Test
	public void isBlacklisted_Test_Pass(){
		assertTrue(userInfo.isWhitelisted());
		userInfo.blacklist();
		assertTrue(userInfo.isBlacklisted());
	}
	
	@Test
	public void whitelist_Test_Pass_GreyListedWithTransactions(){
		userInfo.greylist();
		userInfo.numOfTransactionsWhenGreyListed = 4;
		userInfo.whitelist();
		assertTrue(userInfo.isWhitelisted());
	}
	
	@Test
	public void isActive_Test_Pass(){
		assertTrue(!userInfo.isActive());
		userInfo.makeActive();
		assertTrue(userInfo.isActive());
	}
	
	@Test
	public void isFrozen_Test_Pass(){
		userInfo.makeFrozen();
		assertTrue(userInfo.isFrozen());
	}
	
	@Test
	public void isDisabled_Test_Pass(){
		userInfo.makeDisabled();
		assertTrue(userInfo.isDisabled());
	}
	
	@Test
	public void isUnfrozen_Test_Pass(){
		userInfo.makeFrozen();
		assertTrue(userInfo.isFrozen());
		userInfo.makeUnfrozen();
		assertTrue(!userInfo.isFrozen());
	}

}
