package um.edu.mt.fits.dsp0.api;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import um.edu.mt.fits.dsp0.user.UserInfo;

public class TransactionSystemITPass {


	TransactionSystem ts = null;
	String name = "Freddy";
	String country = "Russia";
	
	
	@Before
	public void setUp() throws Exception {
		ts = new TransactionSystem();
	}

	@Test
	public void uponCreation_Test() {
		assertTrue(!ts.initialised);
	}
	
	@Test
	public void initialise_Test_Pass(){
		ts.initialise();
		assertTrue(ts.initialised);
	}

	@Test
	public void getInitialised_Test_Pass_Init(){
		ts.initialise();
		assertTrue(ts.getInitialised());
	}
	
	@Test
	public void getInitialised_Test_Pass_NotInit(){
		assertTrue(!ts.getInitialised());
	}

	@Test
	public void addUser_Test_Pass(){
		Integer uid = ts.addUser(name, country);
		assertTrue(ts.users.size() == 1);
		
		UserInfo usr = ts.users.get(0);
		
		assertTrue(uid == 1);
		assertTrue(usr.getName().equals(name));
		assertTrue(usr.getCountry().equals(country));
	}

	@Test
	public void getUsers_Test_Pass_NoUsers(){
		assertTrue(ts.getUsers().size() == 0);
	}
	
	@Test
	public void getUsers_Test_Pass_OneUser(){
		assertTrue(ts.getUsers().size() == 0);
		
		ts.addUser(name, country);
		
		ArrayList<UserInfo> users = ts.getUsers();
		
		assertTrue(users.size() == 1);
		assertTrue(users.get(0).getName().equals(name));
		assertTrue(users.get(0).getCountry().equals(country));
		
	}
	
	@Test
	public void safeToAddUser_Test_Pass_ValidValues(){
		assertTrue(ts.safeToAddUser(name, country));
	}

	@Test
	public void safeToAddUser_Test_Pass_InvalidName(){
		assertTrue(!ts.safeToAddUser("", country));
	}

	@Test
	public void safeToAddUser_Test_Pass_InvalidCountry(){
		assertTrue(!ts.safeToAddUser(name, ""));
	}
	
	@Test
	public void charges_Test_Pass_Normal_SmallAmount(){
		Integer uid = ts.addUser(name, country);		
		ts.getUserInfo(uid).makeNormalUser();
		
		assertTrue(ts.charges(uid, 25.0) == 2.0);
	}
	
	@Test
	public void charges_Test_Pass_Normal_GreatAmount(){
		Integer uid = ts.addUser(name, country);		
		ts.getUserInfo(uid).makeNormalUser();
		
		assertTrue(ts.charges(uid, 2500.0) == 125.0);
	}
	
	@Test
	public void charges_Test_Pass_Gold_SmallAmount(){
		Integer uid = ts.addUser(name, country);		
		ts.getUserInfo(uid).makeGoldUser();
		
		assertTrue(ts.charges(uid, 25.0) == 0.0);
	}

	@Test
	public void charges_Test_Pass_Gold_AboveHundred(){
		Integer uid = ts.addUser(name, country);		
		ts.getUserInfo(uid).makeGoldUser();
		
		assertTrue(ts.charges(uid, 250.0) == 5.0);
	}
	
	@Test
	public void charges_Test_Pass_Gold_AboveThousand(){
		Integer uid = ts.addUser(name, country);		
		ts.getUserInfo(uid).makeGoldUser();
		
		assertTrue(ts.charges(uid, 25000.0) == 250.0);
	}

	@Test
	public void charges_Test_Pass_Silver_LessThanThousand(){
		Integer uid = ts.addUser(name, country);		
		ts.getUserInfo(uid).makeSilverUser();
		
		assertTrue(ts.charges(uid, 250.0) == 7.5);
	}
	
	@Test
	public void charges_Test_Pass_Silver_OverThousand(){
		Integer uid = ts.addUser(name, country);		
		ts.getUserInfo(uid).makeSilverUser();
		
		assertTrue(ts.charges(uid, 2500.0) == 50.0);
	}

}
