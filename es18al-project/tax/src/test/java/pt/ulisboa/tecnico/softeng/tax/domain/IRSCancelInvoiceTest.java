package pt.ulisboa.tecnico.softeng.tax.domain;

import static org.junit.Assert.assertEquals;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;

public class IRSCancelInvoiceTest {
	
	private static final String SELLER_NIF = "123456789";
	private static final String BUYER_NIF = "987654321";
	private static final String FOOD = "FOOD";
	private static final int VALUE = 16;
	private final LocalDate date = new LocalDate(2018, 02, 13);
	private InvoiceData invoiceData;
	private String invoiceReference;

	private IRS irs;

	@Before
	public void setUp() {
		this.irs = IRS.getIRS();
		new Seller(this.irs, SELLER_NIF, "José Vendido", "Somewhere");
		new Buyer(this.irs, BUYER_NIF, "Manuel Comprado", "Anywhere");
		new ItemType(this.irs, FOOD, VALUE);
		invoiceData = new InvoiceData(SELLER_NIF, BUYER_NIF, FOOD, VALUE, this.date);
		invoiceReference = IRS.submitInvoice(invoiceData);
	}
	
	@Test
	public void success(){
		
		IRS.cancelInvoice(this.invoiceReference);
		
		assertEquals(this.irs.getTaxPayerByNIF(BUYER_NIF).invoices.size(), 1);
		assertEquals(IRS.getInvoiceByReference(invoiceReference).getCancellationDate(), new LocalDate());
		assertEquals(IRS.getInvoiceByReference(invoiceReference).isCancelled(), true);
		
	}

	@Test
	public void nullInvoiceReference(){
		
		IRS.cancelInvoice(null);

		assertEquals(this.irs.getTaxPayerByNIF(BUYER_NIF).invoices.size(), 1);
		assertEquals(IRS.getInvoiceByReference(invoiceReference).getCancellationDate(), null);
		assertEquals(IRS.getInvoiceByReference(invoiceReference).isCancelled(), false);
		
	}
	
	@Test
	public void emptyInvoiceReference(){
		
		IRS.cancelInvoice("");

		assertEquals(this.irs.getTaxPayerByNIF(BUYER_NIF).invoices.size(), 1);
		assertEquals(IRS.getInvoiceByReference(invoiceReference).getCancellationDate(), null);
		assertEquals(IRS.getInvoiceByReference(invoiceReference).isCancelled(), false);
		
	}

	@Test
	public void spaceInvoiceReference(){
		
		IRS.cancelInvoice("  ");

		assertEquals(this.irs.getTaxPayerByNIF(BUYER_NIF).invoices.size(), 1);
		assertEquals(IRS.getInvoiceByReference(invoiceReference).getCancellationDate(), null);
		assertEquals(IRS.getInvoiceByReference(invoiceReference).isCancelled(), false);
		
	}
	

	@Test
	public void wrongInvoiceReference(){
		
		IRS.cancelInvoice("wrong");

		assertEquals(this.irs.getTaxPayerByNIF(BUYER_NIF).invoices.size(), 1);
		assertEquals(IRS.getInvoiceByReference(invoiceReference).getCancellationDate(), null);
		assertEquals(IRS.getInvoiceByReference(invoiceReference).isCancelled(), false);
		
	}
	
	@After
	public void tearDown() {
		this.irs.clearAll();
	}
	
}
