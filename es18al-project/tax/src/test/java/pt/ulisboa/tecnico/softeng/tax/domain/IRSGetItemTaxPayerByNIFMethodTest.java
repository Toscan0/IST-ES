package pt.ulisboa.tecnico.softeng.tax.domain;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

public class IRSGetItemTaxPayerByNIFMethodTest {

	@Test
	public void succes() {
		String nif = "123000123";
		@SuppressWarnings("unused")
		Buyer buyer = new Buyer(nif,"Balao","rua");
		assertNotNull(IRS.getItemTaxPayerByNIF(nif));
	}
	
	@Test 
	public void failure() {
		String nif = "123000123";
		assertEquals(IRS.getItemTaxPayerByNIF(nif), null);
	}

	@After
	public void tearDown() {
		IRS.taxpayers.clear();
	}
}