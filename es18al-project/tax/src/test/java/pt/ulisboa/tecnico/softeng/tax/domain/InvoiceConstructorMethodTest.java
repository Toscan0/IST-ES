package pt.ulisboa.tecnico.softeng.tax.domain;

import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.domain.Invoice;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;


public class InvoiceConstructorMethodTest {
	
	private ItemType itemType;
	private TaxPayer buyer;
	private TaxPayer seller;
	private float value = 1;
	private Invoice invoice;
	private final LocalDate goodDate = new LocalDate(1970, 1, 1);
	private final LocalDate wrongDate = new LocalDate(1969, 12, 31);
		
	@Before
	public void SetUp(){
		
		this.buyer =  new Buyer("123456789", "name1", "adress1");
		this.seller = new Seller("023456789", "name2", "adress2");
		this.itemType = new ItemType("random", 10);
		
	}
	
	@Test 
	public void success() {
		
		invoice = new Invoice( this.value, this.goodDate, this.itemType, this.seller, this.buyer );
		assertEquals(TaxPayer.invoices.size(), 1);
		assertEquals(TaxPayer.invoices.get(Integer.toString(TaxPayer.invoices.size())), invoice);
		assertEquals(TaxPayer.invoices.get(Integer.toString(TaxPayer.invoices.size())).getDate(), this.goodDate);
		assertEquals(TaxPayer.invoices.get(Integer.toString(TaxPayer.invoices.size())).getBuyer(), this.buyer);
		assertEquals(TaxPayer.invoices.get(Integer.toString(TaxPayer.invoices.size())).getSeller(), this.seller);
		
	}

	@Test (expected = TaxException.class)
	public void dateBefore1970Test() {
		
		new Invoice( this.value, this.wrongDate, this.itemType, this.seller, this.buyer );
		
	}
	
	@Test (expected = TaxException.class)
	public void nullItemTypeReferenceTest() {
		
		new Invoice( this.value, this.goodDate, null, this.seller, this.buyer );
		
	}

	@Test (expected = TaxException.class)
	public void nullSellerTest() {
		
		new Invoice( this.value, this.goodDate, this.itemType, null, this.buyer );
		
	}

	@Test (expected = TaxException.class)
	public void nullBuyerTest(){
		
		new Invoice( this.value, this.goodDate, this.itemType, this.seller, null );
		
	}
	
	@After
	public void tearDown() {
		IRS.itemtypes.clear();
		TaxPayer.invoices.clear();
		IRS.taxpayers.clear();
	}
	
}
