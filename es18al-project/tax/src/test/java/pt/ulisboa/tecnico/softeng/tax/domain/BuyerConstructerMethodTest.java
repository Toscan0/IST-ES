package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;


public class BuyerConstructerMethodTest {
	private static final String NIF1 = "123123987"; //9 digitos
	private static final String NIF2 = "12312398";  //apenas 8 digitos
	private static final String NIF3 = "1231239876"; // 10 digitos
	private static final String NAME = "Joao";
	private static final String ADRESS = "Lisboa, Alameda rua Almirante Reis nº 20";
	
	
	@Test
	public void sucess() {
		Buyer comprador = new Buyer(NIF1, NAME, ADRESS);
		Assert.assertEquals(comprador.getNIF(), NIF1);
		Assert.assertEquals(comprador.getName(), NAME);
		Assert.assertEquals(comprador.getAdress(),ADRESS);
	}
	
	@Test(expected = TaxException.class)
	public void alreadythere() {
		Buyer comprador = new Buyer(NIF1, NAME, ADRESS);
		Buyer comprador2 = new Buyer(NIF1, NAME, ADRESS);

	}
	@Test(expected = TaxException.class)
	public void blanckName() {
		Buyer comprador = new Buyer(NIF1, "", ADRESS);

	}
	@Test(expected = TaxException.class)
	public void spaceName() {
		Buyer comprador = new Buyer(NIF1, " ", ADRESS);

	}@Test(expected = TaxException.class)
	public void blanckAdress() {
		Buyer comprador = new Buyer(NIF1, NAME, "");

	}
	@Test(expected = TaxException.class)
	public void spaceAdress() {
		Buyer comprador = new Buyer(NIF1, NAME, " ");

	}
	@Test(expected = TaxException.class)
	public void biggerNif() {
		Buyer comprador = new Buyer(NIF2, NAME, ADRESS);

	}
	@Test(expected = TaxException.class)
	public void smallerNif() {
		Buyer comprador = new Buyer(NIF3, NAME, ADRESS);
	}
	
	@Test(expected = TaxException.class)
	public void nullName() {
		Buyer comprador = new Buyer(NIF1, null, ADRESS);
	}
	@Test(expected = TaxException.class)
	public void nullAdress() {
		Buyer comprador = new Buyer(NIF1, NIF1, null);
	}
	
	@After
	public void tearDown() {
		IRS.taxpayers.clear();
	}

	
}
