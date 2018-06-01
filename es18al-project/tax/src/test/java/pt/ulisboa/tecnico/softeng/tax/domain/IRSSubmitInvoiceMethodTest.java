package pt.ulisboa.tecnico.softeng.tax.domain;

import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;
import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;

public class IRSSubmitInvoiceMethodTest {
	
	private TaxPayer buyer;
	private TaxPayer seller;
	private float value = 1;
	private final LocalDate goodDate = new LocalDate(1970, 1, 1);
	private final String random = "random";
	private ItemType i1;
		
	@Before
	public void SetUp(){
		i1 = new ItemType(random, 20);
		buyer =  new Buyer("123456789", "name1", "adress1");
		seller = new Seller("023456789", "name2", "adress2");

	}
	
	@Test 
	public void success() {

		InvoiceData invoiceData = new InvoiceData(seller.getNIF(), buyer.getNIF(), random, value, goodDate);
		assertNotNull(IRS.submitInvoice(invoiceData));
		
	}

	@Test (expected = TaxException.class)
	public void wrongSellerNIFTest() {
		
		InvoiceData invoiceData = new InvoiceData("123000000",buyer.getNIF(), random, value, goodDate);
		IRS.submitInvoice(invoiceData);
		
	}
	
	@Test (expected = TaxException.class)
	public void wrongBuyerNIFTest() {
		
		InvoiceData invoiceData = new InvoiceData(seller.getNIF(),"123000000000", random, value, goodDate);
		IRS.submitInvoice(invoiceData);
	}

	@Test (expected = TaxException.class)
	public void wrongItemTest() {
		
		InvoiceData invoiceData = new InvoiceData(seller.getNIF(),buyer.getNIF(), "teste", value, goodDate);
		IRS.submitInvoice(invoiceData);
	}
	
	@After
	public void tearDown() {
		IRS.itemtypes.clear();
		TaxPayer.invoices.clear();
		IRS.taxpayers.clear();
	}
	
}
