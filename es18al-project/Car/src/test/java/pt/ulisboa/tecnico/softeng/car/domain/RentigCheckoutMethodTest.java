package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import static org.junit.Assert.assertNotNull;


import java.util.Set;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RentigCheckoutMethodTest {
	private final LocalDate begin = new LocalDate(2016, 12, 19);
	private final LocalDate end = new LocalDate(2016, 12, 24);
	private String plateCar = "20-XX-30";
	private String plateMotorcycle = "20-ZZ-30";
	private String drivingLicense = "CARTA";
	private RentACar rentACar;
	private Renting renting;
	private Car car;
	private Motorcycle motorcycle;
	private boolean conlfictResult;
	
	@Before
	public void setUp() {
		rentACar = new RentACar("ANYNAME");
		car = new Car(plateCar, 30, rentACar);
		motorcycle = new Motorcycle(plateMotorcycle,30,rentACar);
	}
	
	@Test
	public void sucessCar() {
		this.renting = new Renting(car, begin, end, drivingLicense);
		renting.checkout(5);
		
		assertEquals(35, this.renting.getVehicle().getKilometers());
	}
	
	@Test
	public void sucessMoto() {
		this.renting = new Renting(motorcycle, begin, end, drivingLicense);
		renting.checkout(5);

		assertEquals(35, this.renting.getVehicle().getKilometers());
	}
	
	@Test(expected = CarException.class)
	public void nullKilometersCar() {
		this.renting = new Renting(car, begin, end, drivingLicense);
		renting.checkout(null);
	}
	
	@Test(expected = CarException.class)
	public void nullKilometersMoto() {
		this.renting = new Renting(motorcycle, begin, end, drivingLicense);
		renting.checkout(null);
	}
	
	@Test(expected = CarException.class)
	public void negativeKilometersCar() {
		this.renting = new Renting(car, begin, end, drivingLicense);
		renting.checkout(-1);
	}
	
	@Test(expected = CarException.class)
	public void negativeKilometersMoto() {
		this.renting = new Renting(motorcycle, begin, end, drivingLicense);
		renting.checkout(-1);
	}
	
	@After
	public void after() {
		RentACar.rentACars.clear();
	}
}
	