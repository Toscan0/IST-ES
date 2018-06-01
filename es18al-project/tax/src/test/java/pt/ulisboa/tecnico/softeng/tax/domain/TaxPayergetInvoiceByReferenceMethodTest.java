package pt.ulisboa.tecnico.softeng.tax.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;


public class TaxPayergetInvoiceByReferenceMethodTest {
	private static final String NIF1 = "123123987";
	private static final String NAME1 = "Maria";
	private static final String ADRESS1 = "Lisboa, Sete Rios rua 20 nº 12";
	private static final String NIF2 = "123123988";
	private static final String NAME2 = "Joao";
	private static final String ADRESS2 = "Lisboa, Alameda rua Almirante Reis nº 20";
	private final LocalDate date = new LocalDate(2007, 11, 1);
	
	@Test
	public void sucess() { 
		Seller vendedor = new Seller(NIF2, NAME2, ADRESS2);
		Buyer comprador = new Buyer(NIF1, NAME1, ADRESS1);
		ItemType it1 = new ItemType("viagem", 10);
		Invoice i1 = new Invoice(200, date, it1, vendedor, comprador);
		Invoice i2 = TaxPayer.getInvoiceByReference(i1.getReference());
		Assert.assertEquals(i1, i2);
	}
	
	@Test
	public void nullReference() { 
		Invoice invoice = TaxPayer.getInvoiceByReference("1002abc");
		Assert.assertEquals(invoice, null);
	}
	
	@After
	public void tearDown() {
		IRS.taxpayers.clear();
		TaxPayer.invoices.clear();
		
	}
}
