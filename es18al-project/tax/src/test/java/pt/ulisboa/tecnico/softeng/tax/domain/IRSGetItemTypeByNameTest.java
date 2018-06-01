package pt.ulisboa.tecnico.softeng.tax.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ist.fenixframework.FenixFramework;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class IRSGetItemTypeByNameTest extends RollbackTestAbstractClass {
	private static final String FOOD = "FOOD";
	private static final int VALUE = 16;

	private IRS irs;

	@Override
	public void populate4Test() {
		this.irs = new IRS();
		new ItemType(this.irs, FOOD, VALUE);
	}
	
	@Test
	public void success() {
		ItemType itemType = this.irs.getItemTypeByName(FOOD);

		assertNotNull(itemType.getName());
		assertEquals(FOOD, itemType.getName());
	}

	@Test(expected = TaxException.class)
	public void nullName() {
		ItemType itemType = this.irs.getItemTypeByName(null);

		assertNull(itemType);
	}

	@Test (expected = TaxException.class)
	public void emptyName() {
		ItemType itemType = this.irs.getItemTypeByName("");

		assertNull(itemType);
	}

	@Test 
	public void doesNotExistName() {
		ItemType itemType = this.irs.getItemTypeByName("CAR");

		assertNull(itemType);
	}

	/*@After
	public void tearDown() {
		this.irs.clearAll();
	}*/
}
