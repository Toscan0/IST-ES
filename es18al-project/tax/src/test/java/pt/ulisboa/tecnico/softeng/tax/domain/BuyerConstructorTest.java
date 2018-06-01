package pt.ulisboa.tecnico.softeng.tax.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ist.fenixframework.FenixFramework;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class BuyerConstructorTest extends RollbackTestAbstractClass {
	private static final String ADDRESS = "Somewhere";
	private static final String NAME = "José Vendido";
	private static final String NIF = "123456789";
	private Buyer buyer;
	private TaxPayer tp;

	IRS irs;

	@Override
	public void populate4Test() {
		this.irs = new IRS();
	}
	
	@Test
	public void success() {	
		
		this.buyer = new Buyer();
		this.buyer.setIrs(this.irs);
		this.buyer.setAddress(ADDRESS);
		this.buyer.setName(NAME);
		this.buyer.setNIF(NIF);
		this.irs.addTaxPayer(buyer);

		assertEquals(NIF, buyer.getNIF());
		assertEquals(NAME, buyer.getName());
		assertEquals(ADDRESS, buyer.getAddress());

		assertEquals(buyer,this.irs.getTaxPayerByNIF(NIF));
	}

	@Test
	public void uniqueNIF() {
		Buyer seller = new Buyer();
		seller.setIrs(this.irs);
		seller.setNIF(NIF);
		seller.setName(NAME);
		seller.setAddress(ADDRESS);

		try {
			Buyer buyer = new Buyer();
			buyer.setIrs(this.irs);
			buyer.setNIF(NIF);
			buyer.setName(NAME);
			buyer.setAddress(ADDRESS);
			//fail();
		} catch (TaxException ie) {
			assertEquals(seller, this.irs.getTaxPayerByNIF(NIF));
		}
	}
	
	@Test
	public void nullNIF() {
		Buyer buyer = new Buyer();
		buyer.setIrs(this.irs);
		buyer.setNIF(null);
		buyer.setName(NAME);
		buyer.setAddress(ADDRESS);
	}

	@Test
	public void emptyNIF() {
		//new Buyer(this.irs, "", NAME, ADDRESS);
		Buyer buyer = new Buyer();
		buyer.setIrs(this.irs);
		buyer.setNIF("");
		buyer.setName(NAME);
		buyer.setAddress(ADDRESS);
	}

	@Test
	public void nonNineDigitsNIF() {
		//new Buyer(this.irs, "12345678", NAME, ADDRESS);
		Buyer buyer = new Buyer();
		buyer.setIrs(this.irs);
		buyer.setNIF( "12345678");
		buyer.setName(NAME);
		buyer.setAddress(ADDRESS);
	}

	@Test
	public void nullName() {
		//new Buyer(this.irs, NIF, null, ADDRESS);
		Buyer buyer = new Buyer();
		buyer.setIrs(this.irs);
		buyer.setNIF(NIF);
		buyer.setName(null);
		buyer.setAddress(ADDRESS);
	}

	@Test
	public void emptyName() {
		//new Buyer(this.irs, NIF, "", ADDRESS);
		Buyer buyer = new Buyer();
		buyer.setIrs(this.irs);
		buyer.setNIF(NIF);
		buyer.setName("");
		buyer.setAddress(ADDRESS);
	}

	@Test
	public void nullAddress() {
		//new Buyer(this.irs, NIF, NAME, null);
		Buyer buyer = new Buyer();
		buyer.setIrs(this.irs);
		buyer.setNIF(NIF);
		buyer.setName(NAME);
		buyer.setAddress(null);
	}

	@Test
	public void emptyAddress() {
		Buyer buyer = new Buyer();
		buyer.setIrs(this.irs);
		buyer.setNIF(NIF);
		buyer.setName(NAME);
		buyer.setAddress("");
	}
}