package pt.ulisboa.tecnico.softeng.tax.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ist.fenixframework.FenixFramework;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class InvoiceConstructorTest extends RollbackTestAbstractClass {
	private static final String SELLER_NIF = "123456789";
	private static final String BUYER_NIF = "987654321";
	private static final String FOOD = "FOOD";
	private static final int VALUE = 16;
	private static final int TAX = 23;
	private final LocalDate date = new LocalDate(2018, 02, 13);

	private Seller seller;
	private Buyer buyer;
	private ItemType itemType;

	IRS irs;

	@Override
	public void populate4Test() {
		this.irs = new IRS();
		this.buyer = new Buyer();
		this.buyer.setIrs(this.irs);
		this.buyer.setAddress( "Anywhere");
		this.buyer.setName("Manuel Comprado");
		this.buyer.setNIF(BUYER_NIF);
		
		this.seller = new Seller();;
		this.seller.setIrs(this.irs);
		this.seller.setAddress("Somewhere");
		this.seller.setName("José Vendido");
		this.seller.setNIF(SELLER_NIF);	
		this.itemType = new ItemType(this.irs, FOOD, TAX);
		
		this.irs.addTaxPayer(this.buyer);
		this.irs.addTaxPayer(this.seller);
	}


	@Test
	public void success() {
		Invoice invoice = new Invoice(VALUE, this.date, this.itemType, this.seller, this.buyer);

		assertNotNull(invoice.getReference());
		assertEquals(VALUE, invoice.getValue(), 0.0f);
		assertEquals(this.date, invoice.getDate());
		assertEquals(this.itemType, invoice.getItemType());
		assertEquals(this.seller, invoice.getSeller());
		assertEquals(this.buyer, invoice.getBuyer());
		assertEquals(VALUE * TAX / 100.0, invoice.getIVA(), 0.00001f);
		assertFalse(invoice.isCancelled());

		assertEquals(invoice, this.seller.getInvoiceByReference(invoice.getReference()));
		assertEquals(invoice, this.buyer.getInvoiceByReference(invoice.getReference()));
	}

	@Test(expected = TaxException.class)
	public void nullSeller() {
		new Invoice(VALUE, this.date, this.itemType, null, this.buyer);
	}

	@Test(expected = TaxException.class)
	public void nullBuyer() {
		new Invoice(VALUE, this.date, this.itemType, this.seller, null);
	}

	@Test(expected = TaxException.class)
	public void nullItemType() {
		new Invoice(VALUE, this.date, null, this.seller, this.buyer);
	}

	@Test(expected = TaxException.class)
	public void zeroValue() {
		new Invoice(0, this.date, this.itemType, this.seller, this.buyer);
	}

	@Test(expected = TaxException.class)
	public void negativeValue() {
		new Invoice(-23.6f, this.date, this.itemType, this.seller, this.buyer);
	}

	@Test(expected = TaxException.class)
	public void nullDate() {
		new Invoice(VALUE, null, this.itemType, this.seller, this.buyer);
	}

	@Test(expected = TaxException.class)
	public void before1970() {
		new Invoice(VALUE, new LocalDate(1969, 12, 31), this.itemType, this.seller, this.buyer);
	}

	public void equal1970() {
		new Invoice(VALUE, new LocalDate(1970, 01, 01), this.itemType, this.seller, this.buyer);
	}


}