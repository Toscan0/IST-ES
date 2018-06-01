package pt.ulisboa.tecnico.softeng.tax.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ist.fenixframework.FenixFramework;
import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class IRSSubmitInvoiceTest  extends RollbackTestAbstractClass  {
	private static final String SELLER_NIF = "123456789";
	private static final String BUYER_NIF = "987654321";
	private static final String FOOD = "FOOD";
	private static final int VALUE = 16;
	private final LocalDate date = new LocalDate(2018, 02, 13);

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
		
		ItemType itemType = new ItemType(irs, FOOD, VALUE);
		
		this.irs.addTaxPayer(buyer);
		this.irs.addTaxPayer(seller);

		this.irs.addItemType(itemType);
	}

	@Test
	public void success() {
		InvoiceData invoiceData = new InvoiceData(SELLER_NIF, BUYER_NIF, FOOD, VALUE, this.date);
		String invoiceReference = this.irs.submitInvoice(invoiceData);

		Invoice invoice = this.irs.getTaxPayerByNIF(SELLER_NIF).getInvoiceByReference(invoiceReference);

		assertEquals(invoiceReference, invoice.getReference());
		assertEquals(SELLER_NIF, invoice.getSeller().getNIF());
		assertEquals(BUYER_NIF, invoice.getBuyer().getNIF());
		assertEquals(FOOD, invoice.getItemType().getName());
		assertEquals(VALUE, invoice.getValue(), 0.0000);
		assertEquals(this.date, invoice.getDate());
	}

	@Test(expected = TaxException.class)
	public void nullSellerNIF() {
		InvoiceData invoiceData = new InvoiceData(null, BUYER_NIF, FOOD, VALUE, this.date);
		this.irs.submitInvoice(invoiceData);
	}

	@Test(expected = TaxException.class)
	public void emptySellerNIF() {
		InvoiceData invoiceData = new InvoiceData("", BUYER_NIF, FOOD, VALUE, this.date);
		this.irs.submitInvoice(invoiceData);
	}

	@Test(expected = TaxException.class)
	public void nullBuyerNIF() {
		InvoiceData invoiceData = new InvoiceData(SELLER_NIF, null, FOOD, VALUE, this.date);
		this.irs.submitInvoice(invoiceData);
	}

	@Test(expected = TaxException.class)
	public void emptyBuyerNIF() {
		InvoiceData invoiceData = new InvoiceData(SELLER_NIF, "", FOOD, VALUE, this.date);
		this.irs.submitInvoice(invoiceData);
	}

	@Test(expected = TaxException.class)
	public void nullItemType() {
		InvoiceData invoiceData = new InvoiceData(SELLER_NIF, BUYER_NIF, null, VALUE, this.date);
		//assertNull(this.irs.submitInvoice(invoiceData));
		this.irs.submitInvoice(invoiceData);
	}

	@Test(expected = TaxException.class)
	public void emptyItemType() {
		InvoiceData invoiceData = new InvoiceData(SELLER_NIF, BUYER_NIF, "", VALUE, this.date);
		this.irs.submitInvoice(invoiceData);
	}

	@Test(expected = TaxException.class)
	public void zeroValue() {
		InvoiceData invoiceData = new InvoiceData(SELLER_NIF, BUYER_NIF, FOOD, 0.0f, this.date);
		this.irs.submitInvoice(invoiceData);
	}

	@Test(expected = TaxException.class)
	public void negativeValue() {
		InvoiceData invoiceData = new InvoiceData(SELLER_NIF, BUYER_NIF, FOOD, -23.7f, this.date);
		this.irs.submitInvoice(invoiceData);
	}

	@Test(expected = TaxException.class)
	public void nullDate() {
		InvoiceData invoiceData = new InvoiceData(SELLER_NIF, BUYER_NIF, FOOD, VALUE, null);
		this.irs.submitInvoice(invoiceData);
	}

	@Test(expected = TaxException.class)
	public void before1970() {
		InvoiceData invoiceData = new InvoiceData(SELLER_NIF, BUYER_NIF, FOOD, VALUE, new LocalDate(1969, 12, 31));
		this.irs.submitInvoice(invoiceData);
	}

	@Test
	public void equal1970() {
		InvoiceData invoiceData = new InvoiceData(SELLER_NIF, BUYER_NIF, FOOD, VALUE, new LocalDate(1970, 01, 01));
		this.irs.submitInvoice(invoiceData);
	}

}
