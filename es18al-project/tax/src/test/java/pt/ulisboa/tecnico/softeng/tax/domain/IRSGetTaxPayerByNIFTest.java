package pt.ulisboa.tecnico.softeng.tax.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ist.fenixframework.FenixFramework;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class IRSGetTaxPayerByNIFTest extends RollbackTestAbstractClass  {
	private static final String SELLER_NIF = "123456789";
	private static final String BUYER_NIF = "987654321";

	private IRS irs;

	@Override
	public void populate4Test() {
		this.irs = new IRS();
		Buyer buyer = new Buyer();
		buyer.setIrs(this.irs);
		buyer.setAddress( "Anywhere");
		buyer.setName("Manuel Comprado");
		buyer.setNIF(BUYER_NIF);
		
		Seller seller = new Seller();
		seller.setIrs(this.irs);
		seller.setAddress("Somewhere");
		seller.setName("José Vendido");
		seller.setNIF(SELLER_NIF);
	}

	@Test
	public void successBuyer() {
		TaxPayer taxPayer = this.irs.getTaxPayerByNIF(BUYER_NIF);

		assertNotNull(taxPayer);
		assertEquals(BUYER_NIF, taxPayer.getNIF());
	}

	@Test
	public void successSeller() {
		TaxPayer taxPayer = this.irs.getTaxPayerByNIF(SELLER_NIF);

		assertNotNull(taxPayer);
		assertEquals(SELLER_NIF, taxPayer.getNIF());
	}

	@Test (expected = TaxException.class)
	public void nullNIF() {
		TaxPayer taxPayer = this.irs.getTaxPayerByNIF(null);

		assertNull(taxPayer);
	}

	@Test (expected = TaxException.class)
	public void emptyNIF() {
		TaxPayer taxPayer = this.irs.getTaxPayerByNIF("");

		assertNull(taxPayer);
	}

	@Test 
	public void doesNotExist() {
		TaxPayer taxPayer = this.irs.getTaxPayerByNIF("122456789");

		assertNull(taxPayer);
	}

	/*@After
	public void tearDown() {
		this.irs.clearAll();
	}*/
}
