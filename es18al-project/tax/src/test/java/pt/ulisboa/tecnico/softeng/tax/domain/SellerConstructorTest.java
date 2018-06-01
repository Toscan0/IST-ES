package pt.ulisboa.tecnico.softeng.tax.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ist.fenixframework.FenixFramework;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class SellerConstructorTest extends RollbackTestAbstractClass{
	private static final String ADDRESS = "Somewhere";
	private static final String NAME = "José Vendido";
	private static final String NIF = "123456789";
	private Seller seller;
	private TaxPayer tp;

	IRS irs;

	@Override
	public void populate4Test() {
		this.irs = new IRS();
	}
	
	@Test
	public void success() {	
		
		this.seller = new Seller();
		this.seller.setIrs(this.irs);
		this.seller.setAddress(ADDRESS);
		this.seller.setName(NAME);
		this.seller.setNIF(NIF);
		this.irs.addTaxPayer(seller);

		assertEquals(NIF, seller.getNIF());
		assertEquals(NAME, seller.getName());
		assertEquals(ADDRESS, seller.getAddress());

		assertEquals(seller,this.irs.getTaxPayerByNIF(NIF));
	}

	@Test
	public void uniqueNIF() {
		Seller seller = new Seller();
		seller.setIrs(this.irs);
		seller.setNIF(NIF);
		seller.setName(NAME);
		seller.setAddress(ADDRESS);

		try {
			Seller seller2 = new Seller();
			seller2.setIrs(this.irs);
			seller2.setNIF(NIF);
			seller2.setName(NAME);
			seller2.setAddress(ADDRESS);
			//fail();
		} catch (TaxException ie) {
			assertEquals(seller, this.irs.getTaxPayerByNIF(NIF));
		}
	}
	
	@Test
	public void nullNIF() {
		Seller seller = new Seller();
		seller.setIrs(this.irs);
		seller.setNIF(null);
		seller.setName(NAME);
		seller.setAddress(ADDRESS);
	}

	@Test
	public void emptyNIF() {
		Seller seller = new Seller();
		seller.setIrs(this.irs);
		seller.setNIF("");
		seller.setName(NAME);
		seller.setAddress(ADDRESS);
	}

	@Test
	public void nonNineDigitsNIF() {
		Seller seller = new Seller();
		seller.setIrs(this.irs);
		seller.setNIF( "12345678");
		seller.setName(NAME);
		seller.setAddress(ADDRESS);
	}

	@Test
	public void nullName() {
		//new Buyer(this.irs, NIF, null, ADDRESS);
		Seller seller = new Seller();
		seller.setIrs(this.irs);
		seller.setNIF(NIF);
		seller.setName(null);
		seller.setAddress(ADDRESS);
	}

	@Test
	public void emptyName() {
		//new Buyer(this.irs, NIF, "", ADDRESS);
		Seller seller = new Seller();
		seller.setIrs(this.irs);
		seller.setNIF(NIF);
		seller.setName("");
		seller.setAddress(ADDRESS);
	}

	@Test
	public void nullAddress() {
		//new Buyer(this.irs, NIF, NAME, null);
		Seller seller = new Seller();
		seller.setIrs(this.irs);
		seller.setNIF(NIF);
		seller.setName(NAME);
		seller.setAddress(null);
	}

	@Test
	public void emptyAddress() {
		Seller seller = new Seller();
		seller.setIrs(this.irs);
		seller.setNIF(NIF);
		seller.setName(NAME);
		seller.setAddress("");
	}

}
