package um.edu.mt.fits.dsp0.api;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import um.edu.mt.fits.dsp0.api.Interface;
import um.edu.mt.fits.dsp0.exceptions.*;
import um.edu.mt.fits.dsp0.user.UserInfo;

public class InterfaceITFail {
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
	
	@Test(expected = InvalidFieldsException.class)
	public void ADMIN_createUser_Test_Fail_InvalidFieldException() throws InvalidFieldsException{
		it.ADMIN_createUser("",country);
	}
	
	@Test(expected = InvalidFieldsException.class)
	public void ADMIN_createUser_Test_Fail_InvalidFieldException2() throws InvalidFieldsException{
		it.ADMIN_createUser(name,"");		
	}
	
	@Test(expected = UserInfoNotFoundException.class)
	public void ADMIN_activateUser_Test_Fail_UserDoesNotExist() throws UserInfoNotFoundException{
		it.ADMIN_activateUser(01);
	}
		
	@Test(expected = UserInfoNotFoundException.class)
	public void ADMIN_disableUser_Test_Fail_UserDoesNotExist() throws UserInfoNotFoundException{
		it.ADMIN_disableUser(01);
	}
		
	@Test(expected = UserInfoNotFoundException.class)
	public void ADMIN_blacklistUser_Test_Fail_UserDoesNotExist() throws UserInfoNotFoundException{
		it.ADMIN_blacklistUser(01);
	}
	
	@Test(expected = UserInfoNotFoundException.class)
	public void ADMIN_greylistUser_Test_Fail_UserDoesNotExist() throws UserInfoNotFoundException{
		it.ADMIN_greylistUser(01);
	}
	
	@Test(expected = UserInfoNotFoundException.class)
	public void ADMIN_whitelistUser_Test_Fail_UserDoesNotExist() throws UserInfoNotFoundException{
		it.ADMIN_whitelistUser(01);
	}

	@Test(expected = UserInfoNotFoundException.class)
	public void ADMIN_makeGoldUser_Test_Fail_UserDoesNotExist() throws UserInfoNotFoundException{
		it.ADMIN_makeGoldUser(01);
	}
	
	@Test
	public void ADMIN_makeGoldUser_Test_Fail_UserNotFromArgentina() throws UserInfoNotFoundException, InvalidFieldsException{
		Integer uid = it.ADMIN_createUser(name, "Malta");	
		it.ADMIN_makeGoldUser(uid);		
		
		assertTrue(!it.ts.getUserInfo(uid).isGoldUser());
	}
	
	@Test(expected = UserInfoNotFoundException.class)
	public void ADMIN_makeSilverUser_Test_Fail_UserDoesNotExist() throws UserInfoNotFoundException{
		it.ADMIN_makeSilverUser(01);
	}
	
	@Test(expected = UserInfoNotFoundException.class)
	public void ADMIN_makeNormalUser_Test_Fail_UserDoesNotExist() throws UserInfoNotFoundException{
		it.ADMIN_makeNormalUser(01);
	}
		
	@Test(expected = FailedLoginException.class)
	public void User_Login_Test__Fail_TSNotInitializedBeforeLoginReq() throws InvalidFieldsException, UserInfoNotFoundException, FailedLoginException{
		Integer uid = it.ADMIN_createUser(name,country);
		it.ADMIN_activateUser(uid);

		assertTrue(!it.getTransactionSystem().getInitialised());		
		it.USER_login(uid);//Throws the exception
	}
	
	@Test(expected = FailedLoginException.class)
	public void User_Login_Test__Fail_UserDoesNotExist() throws FailedLoginException{	
		it.ADMIN_initialise();
		
		assertTrue(it.getTransactionSystem().getInitialised());		
		it.USER_login(01);//Throws the exception
	}
	
	@Test(expected = FailedLoginException.class)
	public void User_Login_Test__Fail_AccountNotActivated() throws InvalidFieldsException, UserInfoNotFoundException, FailedLoginException{
		Integer uid = it.ADMIN_createUser(name,country);

		assertTrue(!it.getTransactionSystem().getInitialised());		
		it.USER_login(uid);//Throws the exception
	}
		
	@Test(expected = UserInfoNotFoundException.class)
	public void User_Logout_Test_Fail_UserDoesNotExist() throws UserInfoNotFoundException, SessionNotFoundException{
		it.ADMIN_initialise();
		
		it.USER_logout(01, 02);
	}
	
	@Test(expected = SessionNotFoundException.class)
	public void User_Logout_Test_Fail_SessionDoesNotExist() throws UserInfoNotFoundException, SessionNotFoundException, InvalidFieldsException{
		it.ADMIN_initialise();
		Integer uid = it.ADMIN_createUser(name,country);
		it.ADMIN_activateUser(uid);
		
		it.USER_logout(uid, 02);
	}
		
	@Test(expected = UserInfoNotFoundException.class)
	public void User_freazeUserAccount_Test_Fail_UserDoesNotExist() throws UserInfoNotFoundException{
		it.USER_freezeUserAccount(01,02);
	}
		
	@Test(expected = UserAccountNotFoundException.class)
	public void ADMIN_approveOpenAccount_Test_Fail_IncorrectAccountDetails() throws InvalidFieldsException, UserInfoNotFoundException, FailedLoginException, UserAccountNotFoundException{
		
		it.ADMIN_initialise();
		Integer uid = it.ADMIN_createUser(name,country);
		it.ADMIN_activateUser(uid);
		
		Integer sid = it.USER_login(uid);
					
		it.ADMIN_approveOpenAccount(uid, "3526");			
	}
		
	@Test(expected = UserInfoNotFoundException.class)
	public void User_depositFromExternal_Test_Fail_UserDoesNotExist() throws UserInfoNotFoundException, UserAccountNotFoundException, InvalidFieldsException, UserNotLoggedInException{
		it.USER_depositFromExternal(01,	05, "35469", 100.0);
	}
	
	@Test
	public void User_depositFromExternal_Test_Fail_NotLoggedIn() throws UserInfoNotFoundException, UserAccountNotFoundException, InvalidFieldsException, UserNotLoggedInException{
		Integer uid = -1;
		Integer sid = -1;
		String account_number = "";
		
		try {
			it.ADMIN_initialise();
			
			uid = it.ADMIN_createUser(name,country);			
			it.ADMIN_activateUser(uid);
				
			sid = it.USER_login(uid);
			account_number = it.USER_requestAccount(uid,sid);			
			it.ADMIN_approveOpenAccount(uid, account_number);

			it.USER_logout(uid, sid);
			
		} catch (Exception e) {
			assertTrue(false);
		}
		
		exception.expect(UserNotLoggedInException.class);
		  
		assertTrue(it.ts.initialised);
		assertTrue(it.ts.getUserInfo(uid).getSession(sid).isSessionClosed());
		assertTrue(it.ts.getUserInfo(uid).getAccount(account_number).getOpened());
		it.USER_depositFromExternal(uid, sid, account_number, amountToDeposit);

		assertTrue(it.ts.getUserInfo(uid).getAccount(account_number).getBalance() == 0.0);
			
	}
		
	@Test
	public void User_depositFromExternal_Test_Fail_IncorrectValue() throws UserInfoNotFoundException, UserAccountNotFoundException, InvalidFieldsException, UserNotLoggedInException{
		Integer uid = -1;
		Integer sid = -1;
		String account_number = "";
		
		try {
			it.ADMIN_initialise();
			
			uid = it.ADMIN_createUser(name,country);			
			it.ADMIN_activateUser(uid);
				
			sid = it.USER_login(uid);
			account_number = it.USER_requestAccount(uid,sid);			
			it.ADMIN_approveOpenAccount(uid, account_number);
			
		} catch (Exception e) {
			assertTrue(false);
		}
		
		exception.expect(InvalidFieldsException.class);
		  
		assertTrue(it.ts.initialised);
		assertTrue(!it.ts.getUserInfo(uid).getSession(sid).isSessionClosed());
		assertTrue(it.ts.getUserInfo(uid).getAccount(account_number).getOpened());
		
		it.USER_depositFromExternal(uid, sid, account_number, (-1 * amountToDeposit));

		assertTrue(it.ts.getUserInfo(uid).getAccount(account_number).getBalance() == 0.0);
	}
	
	@Test
	public void User_depositFromExternal_Test_Fail_UnOpenedAccount() throws UserInfoNotFoundException, UserAccountNotFoundException, InvalidFieldsException, UserNotLoggedInException{
		Integer uid = -1;
		Integer sid = -1;
		String account_number = "";
		
		try {
			it.ADMIN_initialise();
			
			uid = it.ADMIN_createUser(name,country);			
			it.ADMIN_activateUser(uid);
				
			sid = it.USER_login(uid);
			account_number = it.USER_requestAccount(uid,sid);	
			
		} catch (Exception e) {
			assertTrue(false);
		}
		
		exception.expect(InvalidFieldsException.class);
		  
		assertTrue(it.ts.initialised);
		assertTrue(!it.ts.getUserInfo(uid).getSession(sid).isSessionClosed());
		assertTrue(!it.ts.getUserInfo(uid).getAccount(account_number).getOpened());
		
		it.USER_depositFromExternal(uid, sid, account_number, amountToDeposit);

		assertTrue(it.ts.getUserInfo(uid).getAccount(account_number).getBalance() == 0.0);
	}
		
	@Test
	public void User_payToExternal_Test_Fail_NotLoggedIn() throws UserInfoNotFoundException{
		Integer uid = -1;
		Integer sid = -1;
		String account_number = "";
		try {
			it.ADMIN_initialise();
			
			uid = it.ADMIN_createUser(name,country);			
			it.ADMIN_activateUser(uid);
			
			sid = it.USER_login(uid);
			account_number = it.USER_requestAccount(uid,sid);			
			it.ADMIN_approveOpenAccount(uid, account_number);
			
			it.ts.getUserInfo(uid).getAccount(account_number).deposit(setInitialBalanceForGoodWithdraw);
				
			it.USER_logout(uid, sid);
		} catch (Exception e) {
			assertTrue(false);
		}

		assertTrue(it.ts.initialised);
		assertTrue(it.ts.getUserInfo(uid).getSession(sid).isSessionClosed());
		assertTrue(it.ts.getUserInfo(uid).getAccount(account_number).getOpened());
		assertTrue(!it.USER_payToExternal(uid, sid, account_number, amountToWithdraw));
		assertTrue(it.ts.getUserInfo(uid).getAccount(account_number).getBalance() == setInitialBalanceForGoodWithdraw);
	}
	
	@Test
	public void User_payToExternal_Test_Fail_UnOpenAccount() throws UserInfoNotFoundException{
		Integer uid = -1;
		Integer sid = -1;
		String account_number = "";
		
		try {
			it.ADMIN_initialise();
			
			uid = it.ADMIN_createUser(name,country);			
			it.ADMIN_activateUser(uid);
			
			sid = it.USER_login(uid);
			account_number = it.USER_requestAccount(uid,sid);
			
			it.ts.getUserInfo(uid).getAccount(account_number).deposit(setInitialBalanceForGoodWithdraw);
				
			it.USER_closeAccount(uid, sid, account_number);
			
		} catch (Exception e) {
			assertTrue(false);
		}

		assertTrue(it.ts.initialised);
		assertTrue(!it.ts.getUserInfo(uid).getSession(sid).isSessionClosed());
		assertTrue(!it.ts.getUserInfo(uid).getAccount(account_number).getOpened());
		assertTrue(!it.USER_payToExternal(uid, sid, account_number, amountToWithdraw));
		assertTrue(it.ts.getUserInfo(uid).getAccount(account_number).getBalance() == setInitialBalanceForGoodWithdraw);
	}
	
	@Test
	public void User_payToExternal_Test_Fail_InsufficientFunds() throws UserInfoNotFoundException{
		Integer uid = -1;
		Integer sid = -1;
		String account_number = "";
		try {
			it.ADMIN_initialise();
			
			uid = it.ADMIN_createUser(name,country);			
			it.ADMIN_activateUser(uid);
			
			sid = it.USER_login(uid);
			account_number = it.USER_requestAccount(uid,sid);			
			it.ADMIN_approveOpenAccount(uid, account_number);
			
			it.ts.getUserInfo(uid).getAccount(account_number).deposit(setInitialBalanceForBadWithdraw);
				
		} catch (Exception e) {
			assertTrue(false);
		}

		assertTrue(it.ts.initialised);
		assertTrue(!it.ts.getUserInfo(uid).getSession(sid).isSessionClosed());
		assertTrue(it.ts.getUserInfo(uid).getAccount(account_number).getOpened());
		assertTrue(!it.USER_payToExternal(uid, sid, account_number, amountToWithdraw));
		assertTrue(it.ts.getUserInfo(uid).getAccount(account_number).getBalance() == setInitialBalanceForBadWithdraw);
	}

	@Test
	public void User_transferToOtherAccount_Test_Fail_InsufficientFunds(){
		try {
			//Setup
			it.ADMIN_initialise();
			
			Integer from_uid = it.ADMIN_createUser(name,country);			
			it.ADMIN_activateUser(from_uid);

			Integer from_sid = it.USER_login(from_uid);
			String from_account_number = it.USER_requestAccount(from_uid,from_sid);			
			it.ADMIN_approveOpenAccount(from_uid, from_account_number);
			it.ts.getUserInfo(from_uid).depositTo(from_account_number, setInitialBalanceForBadWithdraw);
			it.USER_logout(from_uid, from_sid);
			
			Integer to_uid = it.ADMIN_createUser(name+"2",country);			
			it.ADMIN_activateUser(to_uid);
			Integer to_sid = it.USER_login(to_uid);
			String to_account_number = it.USER_requestAccount(to_uid,from_sid);			
			it.ADMIN_approveOpenAccount(to_uid, to_account_number);
			it.USER_logout(to_uid, to_sid);
			
			//Test Starts here
			Integer login_from_sid = it.USER_login(from_uid);
			
			assertTrue(!it.USER_transferToOtherAccount(from_uid, login_from_sid, from_account_number, to_uid, to_account_number, amountToWithdraw));
			
			assertTrue(it.ts.initialised);
			assertTrue(!it.ts.getUserInfo(from_uid).getSession(login_from_sid).isSessionClosed());
			assertTrue(it.ts.getUserInfo(from_uid).getAccount(from_account_number).getOpened());
			
			assertTrue(it.ts.getUserInfo(from_uid).getAccount(from_account_number).getBalance() == setInitialBalanceForBadWithdraw);
			assertTrue(it.ts.getUserInfo(to_uid).getAccount(to_account_number).getBalance() == 0.0);
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void User_transferToOtherAccount_Test_Fail_InvalidToUser() throws UserInfoNotFoundException, UserAccountNotFoundException, InvalidFieldsException{
		Integer from_uid = -1,from_sid = -1;
		String from_account_number = "";
		Integer login_from_sid = -1;
		try {
			//Setup
			it.ADMIN_initialise();
			
			from_uid = it.ADMIN_createUser(name,country);			
			it.ADMIN_activateUser(from_uid);

			from_sid = it.USER_login(from_uid);
			from_account_number = it.USER_requestAccount(from_uid,from_sid);			
			it.ADMIN_approveOpenAccount(from_uid, from_account_number);
			it.ts.getUserInfo(from_uid).depositTo(from_account_number, setInitialBalanceForGoodWithdraw);
			it.USER_logout(from_uid, from_sid);
			
			//Test Starts here
			login_from_sid = it.USER_login(from_uid);
			
		} catch (Exception e) {
			assertTrue(false);
		}
		
		exception.expect(UserInfoNotFoundException.class);
		
		it.USER_transferToOtherAccount(from_uid, login_from_sid, from_account_number, 02, "45765", amountToWithdraw);
		
		assertTrue(it.ts.initialised);
		assertTrue(!it.ts.getUserInfo(from_uid).getSession(login_from_sid).isSessionClosed());		
		assertTrue(it.ts.getUserInfo(from_uid).getAccount(from_account_number).getBalance() == (setInitialBalanceForGoodWithdraw ));
	}
		
	@Test
	public void User_TransferFromOwnAccounts_Test_Fail_FromAccountInvalid() throws UserInfoNotFoundException, UserAccountNotFoundException{
		Integer uid = -1, sid = -1;
		String account_number = "", account_number_2 = "";
		Integer login_from_sid = -1;
		try {
			//Setup
			it.ADMIN_initialise();
			
			uid = it.ADMIN_createUser(name,country);			
			it.ADMIN_activateUser(uid);

			sid = it.USER_login(uid);
			account_number = "09876";		
			
			account_number_2 = it.USER_requestAccount(uid,sid);			
			it.ADMIN_approveOpenAccount(uid, account_number_2);
			
			it.USER_logout(uid, sid);
						
			//Test Starts here
			login_from_sid = it.USER_login(uid);
			
		} catch (Exception e) {
			assertTrue(false);
		}

		exception.expect(UserAccountNotFoundException.class);
		
		it.USER_transferOwnAccounts(uid, login_from_sid, account_number, account_number_2, amountToDeposit);
		
		assertTrue(it.ts.initialised);
		assertTrue(!it.ts.getUserInfo(uid).getSession(login_from_sid).isSessionClosed());
		assertTrue(it.ts.getUserInfo(uid).getAccount(account_number_2).getOpened());
		
		assertTrue(it.ts.getUserInfo(uid).getAccount(account_number_2).getBalance() == 0.0);
	}

	@Test
	public void User_TransferFromOwnAccounts_Test_Fail_ToAccountInvalid() throws UserInfoNotFoundException, UserAccountNotFoundException{
		Integer uid = -1, sid = -1;
		String account_number = "", account_number_2 = "";
		Integer login_from_sid = -1;
		
		try {
			//Setup
			it.ADMIN_initialise();
			
			uid = it.ADMIN_createUser(name,country);			
			it.ADMIN_activateUser(uid);

			sid = it.USER_login(uid);
			account_number = it.USER_requestAccount(uid,sid);			
			it.ADMIN_approveOpenAccount(uid, account_number);
			it.ts.getUserInfo(uid).depositTo(account_number, setInitialBalanceForGoodWithdraw);
			
			account_number_2 = "09876";					
			
			it.USER_logout(uid, sid);
						
			//Test Starts here
			login_from_sid = it.USER_login(uid);
			
		} catch (Exception e) {
			assertTrue(false);
		}

		exception.expect(UserAccountNotFoundException.class);
		
		it.USER_transferOwnAccounts(uid, login_from_sid, account_number, account_number_2, amountToDeposit);
		
		assertTrue(it.ts.initialised);
		assertTrue(!it.ts.getUserInfo(uid).getSession(login_from_sid).isSessionClosed());
		assertTrue(it.ts.getUserInfo(uid).getAccount(account_number).getOpened());
		
		assertTrue(it.ts.getUserInfo(uid).getAccount(account_number).getBalance() == setInitialBalanceForGoodWithdraw);
	}
	
	@Test
	public void User_TransferFromOwnAccounts_Test_Fail_InsufficientFunds(){
		try {
			//Setup
			it.ADMIN_initialise();
			
			Integer uid = it.ADMIN_createUser(name,country);			
			it.ADMIN_activateUser(uid);

			Integer sid = it.USER_login(uid);
			String account_number = it.USER_requestAccount(uid,sid);			
			it.ADMIN_approveOpenAccount(uid, account_number);
			it.ts.getUserInfo(uid).depositTo(account_number, setInitialBalanceForBadWithdraw);
			
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
			
			assertTrue(it.ts.getUserInfo(uid).getAccount(account_number).getBalance() == (setInitialBalanceForBadWithdraw));
			assertTrue(it.ts.getUserInfo(uid).getAccount(account_number_2).getBalance() == 0.0);
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void NoWhitelistBeforeThreeTransfersPass_Test_Fail(){
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

			for (Integer i=0; i<1; i++) {
				sid = it.USER_login(uid);
				it.USER_depositFromExternal(uid,sid,account_number,1000);
				it.USER_depositFromExternal(uid,sid,account_number,100);
				it.USER_logout(uid,sid);
			}

			it.ADMIN_whitelistUser(uid);		
			assertTrue(!it.ts.getUserInfo(uid).isWhitelisted());
		} catch (Exception e) {
			assertTrue(false);
		}			
	}
		
	@Test
	public void noMoreThanTenAccountsPerSessionFail(){
		try {
			it.ADMIN_initialise();
			Integer uid = it.ADMIN_createUser(name,country);
			it.ADMIN_activateUser(uid);
			
			Integer sid = it.USER_login(uid);
			
			for (Integer i=0; i<10; i++) {
				String account_number = it.USER_requestAccount(uid,sid);
				assertEquals(true, account_number != null);
				if (i%2==0) it.ADMIN_approveOpenAccount(uid, account_number);	
			}
			
			String account_number = it.USER_requestAccount(uid,sid);
			assertEquals(true, account_number == null);
			it.USER_logout(uid,sid);	
			
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void NoMoreThanThreeActiveSessionsAtOnce_Test_Fail() throws FailedLoginException{
		Integer uid = -1;
		try {
			it.ADMIN_initialise();
			uid = it.ADMIN_createUser(name,country);
			it.ADMIN_activateUser(uid);

		} catch (Exception e) {
			assertTrue(false);
		}	
		
		exception.expect(FailedLoginException.class);
		
		for (Integer j=0; j<4; j++) {
			it.USER_login(uid);
		}
		
		int tempCounter = 0;
		for(int j = 0; j< it.getTransactionSystem().getUserInfo(uid).getSessions().size(); j++){
			if(!it.getTransactionSystem().getUserInfo(uid).getSessions().get(j).isSessionClosed()){
				tempCounter++;
			}
			assertEquals(true,tempCounter <=3);			
		}
	}	
	
}
