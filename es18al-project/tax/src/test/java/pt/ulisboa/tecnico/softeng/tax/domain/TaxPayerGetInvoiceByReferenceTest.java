package pt.ulisboa.tecnico.softeng.tax.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ist.fenixframework.FenixFramework;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;


public class TaxPayerGetInvoiceByReferenceTest  extends RollbackTestAbstractClass{
	private static final String SELLER_NIF = "123456789";
	private static final String BUYER_NIF = "987654321";
	private static final String FOOD = "FOOD";
	private static final int VALUE = 16;
	private static final int TAX = 23;
	private final LocalDate date = new LocalDate(2018, 02, 13);

	private Seller seller;
	private Buyer buyer;
	private ItemType itemType;
	private Invoice invoice;

	private IRS irs;
	
	@Override
	public void populate4Test() {
		this.irs = new IRS();
		this.buyer = new Buyer();
		this.buyer.setIrs(this.irs);
		this.buyer.setAddress( "Anywhere");
		this.buyer.setName("Manuel Comprado");
		this.buyer.setNIF(BUYER_NIF);
		
		this.seller = new Seller();
		this.seller.setIrs(this.irs);
		this.seller.setAddress("Somewhere");
		this.seller.setName("José Vendido");
		this.seller.setNIF(SELLER_NIF);
		/*this.seller = new Seller(irs, SELLER_NIF, "José Vendido", "Somewhere");
		this.buyer = new Buyer(irs, BUYER_NIF, "Manuel Comprado", "Anywhere");*/
		this.itemType = new ItemType(this.irs, FOOD, TAX);
		this.invoice = new Invoice(VALUE, this.date, this.itemType, this.seller, this.buyer);
	}
	
	/*
	@Before
	public void setUp() {
		this.irs = FenixFramework.getDomainRoot().getIrs();
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
		/this.seller = new Seller(irs, SELLER_NIF, "José Vendido", "Somewhere");
		//this.buyer = new Buyer(irs, BUYER_NIF, "Manuel Comprado", "Anywhere");
		this.itemType = new ItemType(this.irs, FOOD, TAX);
		this.invoice = new Invoice(VALUE, this.date, this.itemType, this.seller, this.buyer);
	}*/
	
	@Test
	public void success() {
		assertEquals(this.invoice, this.seller.getInvoiceByReference(this.invoice.getReference()));
	}

	@Test(expected = TaxException.class)
	public void nullReference() {
		this.seller.getInvoiceByReference(null);
	}

	@Test(expected = TaxException.class)
	public void emptyReference() {
		this.seller.getInvoiceByReference("");
	}

	@Test
	public void desNotExist() {
		assertNull(this.seller.getInvoiceByReference(BUYER_NIF));
	}
/*
	@After
	public void tearDown() {
		FenixFramework.getDomainRoot().getIrs().clearAll();
	}*/

}
