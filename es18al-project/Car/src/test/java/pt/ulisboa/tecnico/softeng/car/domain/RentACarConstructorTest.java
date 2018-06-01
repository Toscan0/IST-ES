package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RentACarConstructorTest {
	private final String name1 = "OPEL";
	private final String name2 = "Fiat";
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void sucess() {
		RentACar rent = new RentACar(name1);
		
		assertNotNull(rent.getName());
		assertNotNull(rent.getCode());
	}
	
	@Test
	public void sucessTwoRent() {
		RentACar rent1 = new RentACar(name1);
		RentACar rent2 = new RentACar(name2);
		
		assertNotNull(rent1.getName());
		assertNotNull(rent2.getCode());
		assertNotNull(rent1.getName());
		assertNotNull(rent2.getCode());
		assertTrue(rent1.getCode() != rent2.getCode());
	}
	
	
	@Test(expected = CarException.class)
	public void nullName() {
		RentACar rent = new RentACar(null);
	}
	
	@Test(expected = CarException.class)
	public void emptyName() {
		RentACar rent = new RentACar("");
	}
	
	@Test(expected = CarException.class)
	public void sameName() {
		RentACar rent1 = new RentACar(name1);
		RentACar rent2 = new RentACar(name1);
	}
	
	
	@After
	public void after() {
		RentACar.rentACars.clear();
	}
	
}
