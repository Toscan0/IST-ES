package pt.ulisboa.tecnico.softeng.tax.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class SellerCnstructorMethodTest {
	private static final String NIF1 = "123123987"; //9 digitos
	private static final String NIF2 = "12312398";  //apenas 8 digitos
	private static final String NIF3 = "1231239876"; // 10 digitos
	private static final String NAME = "Joao";
	private static final String ADRESS = "Lisboa, Alameda rua Almirante Reis nº 20";
	
	
	@Test
	public void sucess() {
		Seller vendedor = new Seller(NIF1, NAME, ADRESS);
		Assert.assertEquals(vendedor.getNIF(), NIF1);
		Assert.assertEquals(vendedor.getName(), NAME);
		Assert.assertEquals(vendedor.getAdress(),ADRESS);
	}
	
	@Test(expected = TaxException.class)
	public void alreadythere() {
		Seller vendedor = new Seller(NIF1, NAME, ADRESS);
		Seller vendedor2 = new Seller(NIF1, NAME, ADRESS);

	}
	@Test(expected = TaxException.class)
	public void blanckName() {
		Seller vendedor = new Seller(NIF1, "", ADRESS);

	}
	@Test(expected = TaxException.class)
	public void spaceName() {
		Seller vendedor = new Seller(NIF1, " ", ADRESS);

	}@Test(expected = TaxException.class)
	public void blanckAdress() {
		Seller vendedor = new Seller(NIF1, NAME, "");

	}
	@Test(expected = TaxException.class)
	public void spaceAdress() {
		Seller vendedor = new Seller(NIF1, NAME, " ");

	}
	@Test(expected = TaxException.class)
	public void biggerNif() {
		Seller vendedor = new Seller(NIF2, NAME, ADRESS);

	}
	@Test(expected = TaxException.class)
	public void smallerNif() {
		Seller vendedor = new Seller(NIF3, NAME, ADRESS);
	}
	
	@Test(expected = TaxException.class)
	public void nullName() {
		Seller vendedor = new Seller(NIF1, null, ADRESS);
	}
	@Test(expected = TaxException.class)
	public void nullAdress() {
		Seller vendedor = new Seller(NIF1, NIF1, null);
	}
	@After
	public void tearDown() {
		IRS.taxpayers.clear();
	}

}
