package pt.ulisboa.tecnico.softeng.tax.domain;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

public class IRSGetItemTypeByNameMethodTest {

	@Test
	public void succes() {
		String txt = "LINDO";
		@SuppressWarnings("unused")
		ItemType item = new ItemType(txt,11);
		assertNotNull(IRS.getItemTypeByName(txt));
	}
	
	@Test
	public void failure() {
		String txt = "LINDO";
		IRS.getItemTypeByName(txt);
	} 

	@After
	public void tearDown() {
		IRS.itemtypes.clear();
	}
}