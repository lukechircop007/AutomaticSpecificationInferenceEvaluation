package um.edu.mt.fits.dsp0.api;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import um.edu.mt.fits.dsp0.api.Interface;
import um.edu.mt.fits.dsp0.exceptions.*;
import um.edu.mt.fits.dsp0.user.UserInfo;

public class InterfaceITPass {
	Interface it = null; 
	
	String name = "Luke";
	String country = "Malta";
	
	double amountToDeposit = 100.0;

	double amountToWithdraw = 100.0;
	double setInitialBalanceForGoodWithdraw = 1000.0;
	double setInitialBalanceForBadWithdraw = 50.0;
	
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	 
	//Functionality Tests
	
	@Before
	public void setUp() throws Exception {
		it = new Interface();
	}

	@Test
	public void interface_TransactionSystem_Test_Pass(){		
		assertTrue(!it.ts.initialised);
	}

	@Test
	public void ADMIN_initialise_Test_Pass(){
		it.ADMIN_initialise();
		assertTrue(it.ts.initialised);
	}
	
	@Test
	public void ADMIN_createUser_Test_Pass(){
		try {
			Integer uid = it.ADMIN_createUser(name, country);
			
			UserInfo userInfo = it.ts.getUserInfo(uid);
			
			assertTrue(uid != null);
			assertTrue(userInfo.getId() == uid); // redundant?
			assertTrue(userInfo.getName().equals(name));
			assertTrue(userInfo.getCountry().equals(country));
			
			assertTrue(userInfo.isDisabled());
			assertTrue(userInfo.isWhitelisted());
			assertTrue(userInfo.isNormalUser());
			
			assertTrue(userInfo.getSessions().size() == 0);
			assertTrue(userInfo.getAccounts().size() == 0);
			
		} catch (Exception e) {
			assertTrue(false);
		}		
	}
	
	@Test
	public void ADMIN_activateUser_Test_Pass(){
		try {
			Integer uid = it.ADMIN_createUser(name, country);			
			it.ADMIN_activateUser(uid);
			
			assertTrue(it.ts.getUserInfo(uid).isActive());
			
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void ADMIN_disableUser_Test_Pass(){
		try {
			Integer uid = it.ADMIN_createUser(name, country);			
			it.ADMIN_activateUser(uid);
			it.ADMIN_disableUser(uid);
			
			assertTrue(it.ts.getUserInfo(uid).isDisabled());
			
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void ADMIN_blacklistUser_Test_Pass(){
		try {
			Integer uid = it.ADMIN_createUser(name, country);	
			it.ADMIN_blacklistUser(uid);
			
			assertTrue(it.ts.getUserInfo(uid).isBlacklisted());
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void ADMIN_greylistUser_Test_Pass(){
		try {
			Integer uid = it.ADMIN_createUser(name, country);	
			it.ADMIN_greylistUser(uid);
			
			assertTrue(it.ts.getUserInfo(uid).isGreylisted());
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void ADMIN_whitelistUser_Test_Pass(){
		try {
			Integer uid = it.ADMIN_createUser(name, country);	
			it.ADMIN_whitelistUser(uid);
			
			assertTrue(it.ts.getUserInfo(uid).isWhitelisted());
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void ADMIN_makeGoldUser_Test_Pass(){
		try {
			Integer uid = it.ADMIN_createUser(name, "Argentina");	
			it.ADMIN_makeGoldUser(uid);
			
			assertTrue(it.ts.getUserInfo(uid).isGoldUser());
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void ADMIN_makeSilverUser_Test_Pass(){
		try {
			Integer uid = it.ADMIN_createUser(name, country);	
			it.ADMIN_makeSilverUser(uid);
			
			assertTrue(it.ts.getUserInfo(uid).isSilverUser());
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void ADMIN_makeNormalUser_Test_Pass(){
		try {
			Integer uid = it.ADMIN_createUser(name, country);	
			it.ADMIN_makeNormalUser(uid);
			
			assertTrue(it.ts.getUserInfo(uid).isNormalUser());
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void User_Login_Test_Pass() throws UserInfoNotFoundException, InvalidFieldsException, FailedLoginException{
		it.ADMIN_initialise();
		Integer uid = it.ADMIN_createUser(name,country);
		it.ADMIN_activateUser(uid);
		
		assertTrue(it.USER_login(uid) != -1);
		assertTrue(it.ts.getInitialised());
	}
	
	@Test
	public void User_Logout_Test_Pass(){
		try {
			it.ADMIN_initialise();
			Integer uid = it.ADMIN_createUser(name,country);
			it.ADMIN_activateUser(uid);
			
			Integer sid = it.USER_login(uid);
			
			it.USER_logout(uid, sid);
			
			assertTrue(it.ts.getUserInfo(uid).getSession(sid).isSessionClosed());			
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test 
	public void User_freazeUserAccount_Test_Pass(){
		try {
			it.ADMIN_initialise();
			Integer uid = it.ADMIN_createUser(name,country);
			it.ADMIN_activateUser(uid);
			
			Integer sid = it.USER_login(uid);
			
			it.USER_freezeUserAccount(uid, sid);
			
			assertTrue(it.ts.getUserInfo(uid).isFrozen());			
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void User_unFreazeUserAccount_Test_Pass(){
		try {
			it.ADMIN_initialise();
			Integer uid = it.ADMIN_createUser(name,country);
			it.ADMIN_activateUser(uid);
			
			Integer sid = it.USER_login(uid);
			
			it.USER_freezeUserAccount(uid, sid);
			
			assertTrue(it.ts.getUserInfo(uid).isFrozen());	
			
			it.USER_unfreezeUserAccount(uid, sid);
			
			assertTrue(!it.ts.getUserInfo(uid).isFrozen());
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void User_requestAccount_Test_Pass(){
		try {
			
			it.ADMIN_initialise();
			Integer uid = it.ADMIN_createUser(name,country);
			it.ADMIN_activateUser(uid);
			
			Integer sid = it.USER_login(uid);
			
			String userAccount = it.USER_requestAccount(uid, sid);
			
			assertTrue(it.ts.getUserInfo(uid).getAccounts().size() == 1);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void User_closeAccount_Test_Pass(){
		try {
			
			it.ADMIN_initialise();
			Integer uid = it.ADMIN_createUser(name,country);
			it.ADMIN_activateUser(uid);
			
			Integer sid = it.USER_login(uid);
			
			String userAccount = it.USER_requestAccount(uid, sid);
			
			it.USER_closeAccount(uid, sid, userAccount);
			
			assertTrue(it.ts.getUserInfo(uid).getAccounts().size() == 1);		
			assertTrue(!it.ts.getUserInfo(uid).getAccount(userAccount).getOpened());
		} catch (Exception e) {
			System.out.println("Exception "+e.getMessage());
			assertTrue(false);
		}
	}
	
	@Test
	public void ADMIN_approveOpenAccount_Test_Pass(){
		try {
			
			it.ADMIN_initialise();
			Integer uid = it.ADMIN_createUser(name,country);
			it.ADMIN_activateUser(uid);
			
			Integer sid = it.USER_login(uid);
			
			String userAccount = it.USER_requestAccount(uid, sid);
			
			it.ADMIN_approveOpenAccount(uid, userAccount);
			
			assertTrue(it.ts.getUserInfo(uid).getAccount(userAccount).getOpened());
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
	}
	
	@Test
	public void USER_depostFromExternal_Test_Pass(){
		try {
			it.ADMIN_initialise();
			
			Integer uid = it.ADMIN_createUser(name,country);			
			it.ADMIN_activateUser(uid);
			
			Integer sid = it.USER_login(uid);
			String account_number = it.USER_requestAccount(uid,sid);			
			it.ADMIN_approveOpenAccount(uid, account_number);
			
			it.USER_depositFromExternal(uid, sid, account_number, 100.0);
			
			assertTrue(it.ts.initialised);
			assertTrue(!it.ts.getUserInfo(uid).getSession(sid).isSessionClosed());
			assertTrue(it.ts.getUserInfo(uid).getAccount(account_number).getOpened());
			assertTrue(it.ts.getUserInfo(uid).getAccount(account_number).getBalance() == 100.0);
		} catch (Exception e) {
			System.out.println("Message: "+e.getMessage());
			assertTrue(false);
		}
	}
	
	@Test
	public void User_payToExternal_Test_Pass_NormalUser(){
		try {
			it.ADMIN_initialise();
			
			Integer uid = it.ADMIN_createUser(name,country);			
			it.ADMIN_activateUser(uid);
			
			Integer sid = it.USER_login(uid);
			String account_number = it.USER_requestAccount(uid,sid);			
			it.ADMIN_approveOpenAccount(uid, account_number);
			
			it.ts.getUserInfo(uid).getAccount(account_number).deposit(setInitialBalanceForGoodWithdraw);
						
			it.USER_payToExternal(uid, sid, account_number, amountToWithdraw);
			
			assertTrue(it.ts.initialised);
			assertTrue(!it.ts.getUserInfo(uid).getSession(sid).isSessionClosed());
			assertTrue(it.ts.getUserInfo(uid).isNormalUser());
			assertTrue(it.ts.getUserInfo(uid).getAccount(account_number).getOpened());
			assertTrue(it.ts.getUserInfo(uid).getAccount(account_number).getBalance() == (setInitialBalanceForGoodWithdraw - (amountToWithdraw+ it.ts.charges(uid, amountToWithdraw))));
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void User_payToExternal_Test_Pass_SilverUser(){
		try {
			it.ADMIN_initialise();
			
			Integer uid = it.ADMIN_createUser(name,country);			
			it.ADMIN_activateUser(uid);
			
			it.ADMIN_makeSilverUser(uid);
			
			Integer sid = it.USER_login(uid);
			String account_number = it.USER_requestAccount(uid,sid);			
			it.ADMIN_approveOpenAccount(uid, account_number);
			
			it.ts.getUserInfo(uid).getAccount(account_number).deposit(setInitialBalanceForGoodWithdraw);
						
			it.USER_payToExternal(uid, sid, account_number, amountToWithdraw);
			
			assertTrue(it.ts.initialised);
			assertTrue(!it.ts.getUserInfo(uid).getSession(sid).isSessionClosed());
			assertTrue(it.ts.getUserInfo(uid).isSilverUser());
			assertTrue(it.ts.getUserInfo(uid).getAccount(account_number).getOpened());
			assertTrue(it.ts.getUserInfo(uid).getAccount(account_number).getBalance() == (setInitialBalanceForGoodWithdraw - (amountToWithdraw+ it.ts.charges(uid, amountToWithdraw))));
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void User_payToExternal_Test_Pass_GoldUser(){
		try {
			it.ADMIN_initialise();
			
			Integer uid = it.ADMIN_createUser(name,"Argentina");			
			it.ADMIN_activateUser(uid);

			it.ADMIN_makeGoldUser(uid);
			
			Integer sid = it.USER_login(uid);
			String account_number = it.USER_requestAccount(uid,sid);			
			it.ADMIN_approveOpenAccount(uid, account_number);
			
			it.ts.getUserInfo(uid).getAccount(account_number).deposit(setInitialBalanceForGoodWithdraw);
						
			it.USER_payToExternal(uid, sid, account_number, amountToWithdraw);
			
			assertTrue(it.ts.initialised);
			assertTrue(!it.ts.getUserInfo(uid).getSession(sid).isSessionClosed());
			assertTrue(it.ts.getUserInfo(uid).isGoldUser());
			assertTrue(it.ts.getUserInfo(uid).getAccount(account_number).getOpened());
			assertTrue(it.ts.getUserInfo(uid).getAccount(account_number).getBalance() == (setInitialBalanceForGoodWithdraw - (amountToWithdraw+ it.ts.charges(uid, amountToWithdraw))));
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void User_transferToOtherAccount_Test_Pass(){
		try {
			//Setup
			it.ADMIN_initialise();
			
			Integer from_uid = it.ADMIN_createUser(name,country);			
			it.ADMIN_activateUser(from_uid);

			Integer from_sid = it.USER_login(from_uid);
			String from_account_number = it.USER_requestAccount(from_uid,from_sid);			
			it.ADMIN_approveOpenAccount(from_uid, from_account_number);
			it.ts.getUserInfo(from_uid).depositTo(from_account_number, setInitialBalanceForGoodWithdraw);
			it.USER_logout(from_uid, from_sid);
			
			Integer to_uid = it.ADMIN_createUser(name+"2",country);			
			it.ADMIN_activateUser(to_uid);
			Integer to_sid = it.USER_login(to_uid);
			String to_account_number = it.USER_requestAccount(to_uid,from_sid);			
			it.ADMIN_approveOpenAccount(to_uid, to_account_number);
			it.USER_logout(to_uid, to_sid);
			
			//Test Starts here
			Integer login_from_sid = it.USER_login(from_uid);
			
			it.USER_transferToOtherAccount(from_uid, login_from_sid, from_account_number, to_uid, to_account_number, amountToWithdraw);
			
			assertTrue(it.ts.initialised);
			assertTrue(!it.ts.getUserInfo(from_uid).getSession(login_from_sid).isSessionClosed());
			assertTrue(it.ts.getUserInfo(from_uid).getAccount(from_account_number).getOpened());
			
			assertTrue(it.ts.getUserInfo(from_uid).getAccount(from_account_number).getBalance() == (setInitialBalanceForGoodWithdraw - (amountToWithdraw + it.ts.charges(from_uid, amountToWithdraw))));
			assertTrue(it.ts.getUserInfo(to_uid).getAccount(to_account_number).getBalance() == (amountToWithdraw));
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void User_TransferFromOwnAccounts_Test_Pass(){
		try {
			//Setup
			it.ADMIN_initialise();
			
			Integer uid = it.ADMIN_createUser(name,country);			
			it.ADMIN_activateUser(uid);

			Integer sid = it.USER_login(uid);
			String account_number = it.USER_requestAccount(uid,sid);			
			it.ADMIN_approveOpenAccount(uid, account_number);
			it.ts.getUserInfo(uid).depositTo(account_number, setInitialBalanceForGoodWithdraw);
			
			String account_number_2 = it.USER_requestAccount(uid,sid);			
			it.ADMIN_approveOpenAccount(uid, account_number_2);
			
			it.USER_logout(uid, sid);
						
			//Test Starts here
			Integer login_from_sid = it.USER_login(uid);
			
			it.USER_transferOwnAccounts(uid, login_from_sid, account_number, account_number_2, amountToDeposit);
			
			assertTrue(it.ts.initialised);
			assertTrue(!it.ts.getUserInfo(uid).getSession(login_from_sid).isSessionClosed());
			assertTrue(it.ts.getUserInfo(uid).getAccount(account_number).getOpened());
			assertTrue(it.ts.getUserInfo(uid).getAccount(account_number_2).getOpened());
			
			assertTrue(it.ts.getUserInfo(uid).getAccount(account_number).getBalance() == (setInitialBalanceForGoodWithdraw - (amountToDeposit)));
			assertTrue(it.ts.getUserInfo(uid).getAccount(account_number_2).getBalance() == (amountToDeposit));
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void NoWhitelistBeforeThreeTransfersPass_Test_Pass(){
		try {
			it.ADMIN_initialise();
			Integer uid_receiver = it.ADMIN_createUser(name,country);
			it.ADMIN_activateUser(uid_receiver);
			
			Integer sid_receiver = it.USER_login(uid_receiver); 
			String account_number_receiver = it.USER_requestAccount(uid_receiver, sid_receiver);
			it.ADMIN_approveOpenAccount(uid_receiver, account_number_receiver);
			it.USER_logout(uid_receiver, sid_receiver);

			Integer uid = it.ADMIN_createUser("Sandy","Senegal");
			it.ADMIN_activateUser(uid);
			Integer sid = it.USER_login(uid);
			String account_number = it.USER_requestAccount(uid,sid);
			it.ADMIN_approveOpenAccount(uid, account_number);
			it.USER_logout(uid,sid);
			
			it.ADMIN_greylistUser(uid);

			for (Integer i=0; i<2; i++) {
				sid = it.USER_login(uid);
				it.USER_depositFromExternal(uid,sid,account_number,1000);
				it.USER_depositFromExternal(uid,sid,account_number,100);
				it.USER_logout(uid,sid);
			}

			it.ADMIN_whitelistUser(uid);		
			assertTrue(it.ts.getUserInfo(uid).isWhitelisted());
		} catch (Exception e) {
			assertTrue(false);
		}			
	}
	
	@Test
	public void NoMoreThanTenAccountsPerSession_TEST_Pass(){
		try {
			it.ADMIN_initialise();
			Integer uid = it.ADMIN_createUser(name,country);
			it.ADMIN_activateUser(uid);
			
			Integer sid = it.USER_login(uid);
			for (Integer i=0; i<10; i++) {
				String account_number = it.USER_requestAccount(uid,sid);
				assertTrue(account_number !=null);
				if (i%2==0) it.ADMIN_approveOpenAccount(uid, account_number);					
				if (i%9==5) {
					it.USER_logout(uid,sid);
					sid = it.USER_login(uid);
				}
			}
			it.USER_logout(uid,sid);
		} catch (Exception e) {
			assertTrue(false);
		}		
	}
	
		@Test
	public void NoMoreThanThreeActiveSessionsAtOnce_Test_Pass(){
		try {
			it.ADMIN_initialise();
			Integer uid = it.ADMIN_createUser(name,country);
			it.ADMIN_activateUser(uid);
			
			for (Integer j=0; j<2; j++) {
				it.USER_login(uid);
			}
			
			int tempCounter = 0;
			for(int j = 0; j< it.getTransactionSystem().getUserInfo(uid).getSessions().size(); j++){
				if(!it.getTransactionSystem().getUserInfo(uid).getSessions().get(j).isSessionClosed()){
					tempCounter++;
				}
				assertEquals(true,tempCounter <=3);			
			}
		} catch (Exception e) {
			assertTrue(false);
		}	
	}	
	
}
