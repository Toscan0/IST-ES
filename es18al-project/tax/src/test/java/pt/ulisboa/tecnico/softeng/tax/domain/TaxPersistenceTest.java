package pt.ulisboa.tecnico.softeng.tax.domain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Test;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.Atomic.TxMode;
import pt.ist.fenixframework.FenixFramework;

public class TaxPersistenceTest {
	
	private static final String BUYER_NIF = "123456789";
	private static final String BUYER_NAME = "José Vendido";
	private static final String BUYER_ADDRESS = "Somewhere";
	
	private static final String SELLER_NIF = "987654321";
	private static final String SELLER_NAME = "Manuel Comprado";
	private static final String SELLER_ADDRESS = "Anywhere";
	
	private static final String ITEM = "FOOD";
	private static final int VALUE = 10;
	private static final int TAX = 23;
	
	private final LocalDate date = new LocalDate(2018, 02, 13);
	
	private IRS irs;
	
	@Test
	public void success() {
		atomicProcess();
		atomicAssert();
	}

	@Atomic(mode = TxMode.WRITE)
	public void atomicProcess() {
		irs = new IRS();
		
		Buyer buyer = new Buyer();
		buyer.setIrs(irs);
		buyer.setNIF(BUYER_NIF);
		buyer.setName(BUYER_NAME);
		buyer.setAddress( BUYER_ADDRESS);
		
		Seller seller = new Seller();
		seller.setIrs(irs);
		seller.setNIF(SELLER_NIF);
		seller.setName(SELLER_NAME);
		seller.setAddress(SELLER_ADDRESS);

		irs.addTaxPayer(buyer);
		irs.addTaxPayer(seller);
		
		ItemType itemType = new ItemType(irs, ITEM, TAX);
		irs.addItemType(itemType);
		
		Invoice invoice = new Invoice(VALUE, this.date, itemType, seller, buyer);
		
		seller.addInvoice(invoice);
		buyer.addInvoice(invoice);
	}

	@Atomic(mode = TxMode.READ)
	public void atomicAssert() {
		
		//IRS verification
		assertEquals(2, irs.getTaxPayerSet().size());
		assertEquals(1, irs.getItemTypeSet().size());
		
		List<TaxPayer> taxPayers = new ArrayList<>(irs.getTaxPayerSet());
		
		TaxPayer buyer = (Buyer) taxPayers.get(0);
		assertEquals(BUYER_NIF, buyer.getNIF());
		assertEquals(BUYER_NAME, buyer.getName());
		assertEquals(BUYER_ADDRESS, buyer.getAddress());

		TaxPayer seller = (Seller) taxPayers.get(1);
		assertEquals(SELLER_NIF, seller.getNIF());
		assertEquals(SELLER_NAME, seller.getName());
		assertEquals(SELLER_ADDRESS, seller.getAddress());
		

		List<ItemType> itemTypes = new ArrayList<>(irs.getItemTypeSet());
		ItemType item = itemTypes.get(0);
		assertEquals(ITEM, item.getName());
		assertEquals(TAX, item.getTax());
		/*
		 List<Invoice> sellerInvoices = new ArrayList<>(seller.getInvoiceSet());
		Invoice sellerInvoice = sellerInvoices.get(0);
		List<Invoice> buyerInvoices = new ArrayList<>(buyer.getInvoiceSet());
		Invoice buyerInvoice = buyerInvoices.get(0);
		
		assertEquals(VALUE, buyerInvoice.getValue(), 0.0f);
		assertEquals(date, buyerInvoice.getDate());
		assertEquals(item, buyerInvoice.getItemType());
		assertEquals(seller, buyerInvoice.getSeller());
		assertEquals(buyer, buyerInvoice.getBuyer());
		
		assertEquals(VALUE, sellerInvoice.getValue(), 0.0f);
		assertEquals(date, sellerInvoice.getDate());
		assertEquals(item, sellerInvoice.getItemType());
		assertEquals(seller, sellerInvoice.getSeller());
		assertEquals(buyer, sellerInvoice.getBuyer());*/
	}

	@After
	@Atomic(mode = TxMode.WRITE)
	public void tearDown() {
		IRS irs = FenixFramework.getDomainRoot().getIrs();
		irs.delete();		
	}
	
	
	
	
	
	
	
}
