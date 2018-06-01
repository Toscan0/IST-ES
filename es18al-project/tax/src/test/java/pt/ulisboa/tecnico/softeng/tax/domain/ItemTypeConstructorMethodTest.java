package pt.ulisboa.tecnico.softeng.tax.domain;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class ItemTypeConstructorMethodTest {

	private String itemTypeReference = "random";
	private int tax = 10;
	
	
	@Test
	public void success() {
		
		new ItemType(this.itemTypeReference, this.tax);
	}
	
	@Test
	public void repeatedReferenceTest(){
		new ItemType(this.itemTypeReference, this.tax);
		try {
			new ItemType(this.itemTypeReference, this.tax);
			fail();
		} catch (TaxException te) {
			Assert.assertEquals(1, IRS.itemtypes.size());
		}
		
	}

	@Test (expected = TaxException.class)
	public void nullReferenceTest() {
		
		new ItemType(null, this.tax);
	}
	

	@Test (expected = TaxException.class)
	public void emptyReferenceTest() {
		
		new ItemType("", this.tax);
	}
	

	@Test (expected = TaxException.class)
	public void spaceReferenceTest() {
		
		new ItemType(" ", this.tax);
	}
	
	@Test (expected = TaxException.class)
	public void taxNegativeTest(){
		new ItemType(this.itemTypeReference, -1);	
	}

	@After
	public void tearDown() {
		IRS.itemtypes.clear();
	}

}
