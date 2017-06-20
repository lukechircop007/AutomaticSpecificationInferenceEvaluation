package um.edu.mt.fits.dsp0.user;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import um.edu.mt.fits.dsp0.user.UserSession;

public class UserSessionUTPass {

	UserSession userSession = null; 

	Integer userId = 05;
	Integer sessionId = 123;
	
	@Before
	public void setUp() throws Exception {
		userSession = new UserSession(userId, sessionId);
	}
	
	@Test
	public void sessionId_Test_pass(){
		assertTrue(userSession.sid == sessionId);
	}
	
	@Test
	public void ownerId_Test_pass(){
		assertTrue(userSession.owner == userId);
	}
	
	@Test
	public void log_Test_pass(){
		assertTrue(userSession.log.equals(""));
	}
	
	@Test
	public void sessionClosed__True_Test_pass(){
		assertTrue(userSession.sessionClosed);
	}
	
	@Test
	public void getId_Test_pass(){
		assertTrue(userSession.getId() == sessionId);
	}
	
	@Test
	public void getOwner_Test_pass(){
		assertTrue(userSession.getOwner() == userId);
	}

	@Test
	public void getLog_Empty_Test_pass(){
		assertTrue(userSession.getLog().equals(""));				
	}
	
	@Test
	public void logFunc_Test_pass(){
		userSession.log("output");
		assertTrue(userSession.log.equals("output\n"));
	}
	
	@Test
	public void isSessionClosed_True_Test_pass(){
		assertTrue(userSession.isSessionClosed());
	}
	
	@Test
	public void isSessionClosed_False_Test_pass(){
		userSession.sessionClosed = false;
		assertTrue(!userSession.isSessionClosed());
	}
	
	@Test
	public void openSession_Test_pass(){
		userSession.openSession();
		assertTrue(!userSession.sessionClosed);
	}
	
	@Test
	public void closeSession_Test_pass(){
		userSession.openSession();
		userSession.closeSession();
		assertTrue(userSession.sessionClosed);
	}
	
}
