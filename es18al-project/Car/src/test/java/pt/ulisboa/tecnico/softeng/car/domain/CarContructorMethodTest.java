package pt.ulisboa.tecnico.softeng.car.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

import org.junit.After;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;


public class CarContructorMethodTest {
	private RentACar rentACar;
	
	private static final String CAR_PLATE = "99-HN-52";
	private static final Integer CAR_KM = 15000;
	
	
	@Before
	public void setUp() {
		this.rentACar = new RentACar("XPTO123");
	}

	@Test
	public void success() {
		Car car = new Car(CAR_PLATE, CAR_KM, this.rentACar);

		assertEquals(this.rentACar, car.getRentACar());
		assertEquals(CAR_PLATE, car.getPlate());
		Assert.assertEquals(15000, car.getKilometers());
	}
	@Test(expected= CarException.class)
	public void nullPlate() {
		Car car = new Car(null, CAR_KM, this.rentACar);
	}
	
	@Test(expected= CarException.class)
	public void emptyPlate() {
		Car car = new Car("", CAR_KM, this.rentACar);
	}
	
	@Test(expected= CarException.class)
	public void invalidPlate1() {
		Car car = new Car("12345678", CAR_KM, this.rentACar);
	}
	@Test(expected= CarException.class)
	public void invalidPlate2() {
		Car car = new Car("12-45678", CAR_KM, this.rentACar);
	}
	@Test(expected= CarException.class)
	public void invalidPlate3() {
		Car car = new Car("12456-78", CAR_KM, this.rentACar);
	}
	@Test(expected= CarException.class)
	public void invalidPlate4() {
		Car car = new Car("12-45-781", CAR_KM, this.rentACar);
	}
	@Test(expected= CarException.class)
	public void invalidPlate5() {
		Car car = new Car("12-425-78", CAR_KM, this.rentACar);
	}
	@Test(expected= CarException.class)
	public void invalidPlate6() {
		Car car = new Car("212-45-78", CAR_KM, this.rentACar);
	}
	
	
	@After
	public void tearDown() {
		RentACar.rentACars.clear();
	}
	
	
}
