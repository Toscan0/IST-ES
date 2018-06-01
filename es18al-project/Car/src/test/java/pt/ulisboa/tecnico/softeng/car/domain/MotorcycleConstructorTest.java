package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.assertEquals;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MotorcycleConstructorTest {
	private RentACar rentACar;
	
	private static final String CAR_PLATE = "99-HN-52";
	private static final Integer CAR_KM = 15000;
	
	
	@Before
	public void setUp() {
		this.rentACar = new RentACar("XPTO123");
	}

	@Test
	public void success() {
		Motorcycle motorcycle = new Motorcycle(CAR_PLATE, CAR_KM, this.rentACar);

		assertEquals(this.rentACar, motorcycle.getRentACar());
		assertEquals(CAR_PLATE, motorcycle.getPlate());
		Assert.assertEquals(15000, motorcycle.getKilometers());
	}
	
	@Test(expected= CarException.class)
	public void nullPlate() {
		Motorcycle motorcycle = new Motorcycle(null, CAR_KM, this.rentACar);
	}
	
	@Test(expected= CarException.class)
	public void emptyPlate() {
		Motorcycle motorcycle = new Motorcycle("", CAR_KM, this.rentACar);
	}
	
	@Test(expected= CarException.class)
	public void invalidPlate1() {
		Motorcycle motorcycle = new Motorcycle("12345678", CAR_KM, this.rentACar);
	}
	@Test(expected= CarException.class)
	public void invalidPlate2() {
		Motorcycle motorcycle = new Motorcycle("12-45678", CAR_KM, this.rentACar);
	}
	@Test(expected= CarException.class)
	public void invalidPlate3() {
		Motorcycle motorcycle = new Motorcycle("12456-78", CAR_KM, this.rentACar);
	}
	@Test(expected= CarException.class)
	public void invalidPlate4() {
		Motorcycle motorcycle = new Motorcycle("12-45-781", CAR_KM, this.rentACar);
	}
	@Test(expected= CarException.class)
	public void invalidPlate5() {
		Motorcycle motorcycle = new Motorcycle("12-425-78", CAR_KM, this.rentACar);
	}
	@Test(expected= CarException.class)
	public void invalidPlate6() {
		Motorcycle motorcycle = new Motorcycle("212-45-78", CAR_KM, this.rentACar);
	}

	@After
	public void tearDown() {
		RentACar.rentACars.clear();
	}
}
