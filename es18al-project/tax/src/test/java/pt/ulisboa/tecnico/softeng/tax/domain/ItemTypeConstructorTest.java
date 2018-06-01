package pt.ulisboa.tecnico.softeng.tax.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ist.fenixframework.FenixFramework;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class ItemTypeConstructorTest   extends RollbackTestAbstractClass  {
	private static final String CAR = "CAR";
	private static final int TAX = 23;

	private IRS irs;
	
	@Override
	public void populate4Test() {
		this.irs = new IRS();
	}
	
	@Test
	public void success() {
		IRS irs = FenixFramework.getDomainRoot().getIrs();

		ItemType itemType = new ItemType(irs, CAR, TAX);

		assertEquals(CAR, itemType.getName());
		assertEquals(TAX, itemType.getTax());
		assertNotNull(this.irs.getItemTypeByName(CAR));

		assertEquals(itemType, irs.getItemTypeByName(CAR));
	}

	@Test
	public void uniqueName() {
		ItemType itemType = new ItemType(this.irs, CAR, TAX);

		try {
			new ItemType(this.irs, CAR, TAX);
			fail();
		} catch (TaxException te) {
			assertEquals(itemType, this.irs.getItemTypeByName(CAR));
		}
	}

	@Test(expected = TaxException.class)
	public void nullItemType() {
		new ItemType(this.irs, null, TAX);
	}

	@Test(expected = TaxException.class)
	public void emptyItemType() {
		new ItemType(this.irs, "", TAX);
	}

	@Test(expected = TaxException.class)
	public void negativeTax() {
		new ItemType(this.irs, CAR, -34);
	}

	public void zeroTax() {
		new ItemType(this.irs, CAR, 0);
	}


}
