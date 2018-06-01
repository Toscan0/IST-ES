package pt.ulisboa.tecnico.softeng.tax.domain;

import static org.junit.Assert.assertTrue;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ist.fenixframework.FenixFramework;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class IRSCancelInvoiceMethodTest extends RollbackTestAbstractClass {
	private static final String SELLER_NIF = "123456789";
	private static final String BUYER_NIF = "987654321";
	private static final String FOOD = "FOOD";
	private static final int VALUE = 16;
	private final LocalDate date = new LocalDate(2018, 02, 13);

	private IRS irs;
	private String reference;
	Invoice invoice;
	
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
		ItemType itemType = new ItemType(this.irs, FOOD, VALUE);
		this.invoice = new Invoice(30.0, this.date, itemType, seller, buyer);
		this.reference = this.invoice.getReference();
	}

	@Test
	public void success() {
		IRS.cancelInvoice(this.reference);

		assertTrue(this.invoice.isCancelled());
	}

	@Test(expected = TaxException.class)
	public void nullReference() {
		IRS.cancelInvoice(null);
	}

	@Test(expected = TaxException.class)
	public void emptyReference() {
		IRS.cancelInvoice("   ");
	}

	@Test(expected = TaxException.class)
	public void referenceDoesNotExist() {
		IRS.cancelInvoice("XXXXXXXX");
	}

}
