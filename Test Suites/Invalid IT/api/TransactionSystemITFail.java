package um.edu.mt.fits.dsp0.api;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import um.edu.mt.fits.dsp0.user.UserInfo;

public class TransactionSystemITFail {


	TransactionSystem ts = null;
	String name = "Freddy";
	String country = "Russia";
	
	
	@Before
	public void setUp() throws Exception {
		ts = new TransactionSystem();
	}

	@Test
	public void charges_Test_Fail_UserDoesNotExist(){
		assertTrue(ts.charges(03, 250.0)== -1);
	}

}
