package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class VehicleIsFreeMethodTest {
	private final LocalDate begin = new LocalDate(2016, 12, 19);
	private final LocalDate end = new LocalDate(2016, 12, 24);
	private String plateCar = "20-XX-30";
	private String plateMotorcycle = "20-ZZ-30";
	private String drivingLicense = "CARTA01";
	private RentACar rentACar;
	private Car car;
	private Motorcycle motorcycle;
	
	
	@Before
	public void setUp() {
		rentACar = new RentACar("ANYNAME");
		car = new Car(plateCar, 30, rentACar);
		motorcycle = new Motorcycle(plateMotorcycle, 30, rentACar);
	}
		
	@Test
	public void sucessCar() {
		car.rent(drivingLicense, begin, end);		
		assertTrue(car.isFree(new LocalDate(2016, 12, 9), new LocalDate(2016, 12, 15)));
	}
	@Test
	public void sucessMotorcycle() {
		motorcycle.rent(drivingLicense, begin, end);		
		assertTrue(motorcycle.isFree(new LocalDate(2016, 12, 9), new LocalDate(2016, 12, 15)));
	}
	
	@Test(expected = CarException.class)
	public void wrongLicenseCar() {
		car.rent("91FD", begin, end);
		car.isFree(begin, end);
	}
	@Test(expected = CarException.class)
	public void wrongLicenseMotorcycle() {
		motorcycle.rent("01FD", begin, end);
		motorcycle.isFree(begin, end);
	}
	
	
	@Test(expected = CarException.class)
	public void beginNullCar() {
		car.rent(drivingLicense, begin, end);
		car.isFree(null, end);
	}
	@Test(expected = CarException.class)
	public void beginNullMotorcycle() {
		motorcycle.rent(drivingLicense, begin, end);
		motorcycle.isFree(null, end);
	}
	@Test(expected = CarException.class)
	public void endNullCar() {
		car.rent(drivingLicense, begin, end);
		
		car.isFree(begin, null);
	}
	@Test(expected = CarException.class)
	public void endNullMotorcycle() {
		motorcycle.rent(drivingLicense, begin, end);
		motorcycle.isFree(begin, null);
	}
	
	@Test(expected = CarException.class)
	public void bothNullCar() {
		car.rent(drivingLicense, begin, end);
		
		car.isFree(null, null);
	}
	@Test(expected = CarException.class)
	public void bothNullMotorcycle() {
		motorcycle.rent(drivingLicense, begin, end);
		motorcycle.isFree(null, null);
	}
	
	//second if of conflict method
	@Test
	public void endEqualsBeginCar() {
		car.rent(drivingLicense, begin, end);
		
		assertFalse(car.isFree(new LocalDate(2016, 12, 23), new LocalDate(2016, 12, 23)));
	}
	@Test
	public void endEqualsBeginMotorcycle() {
		motorcycle.rent(drivingLicense, begin, end);
		
		assertFalse(motorcycle.isFree(new LocalDate(2016, 12, 23), new LocalDate(2016, 12, 23)));
	}
	
	//third if of conflict method
	@Test(expected = CarException.class)
	public void endBeforeBeginCar() {
		car.rent(drivingLicense, begin, end);;
		
		assertFalse(car.isFree(new LocalDate(2016, 12, 15), new LocalDate(2016, 12, 9)));
	}
	@Test(expected = CarException.class)
	public void endBeforeBeginMotorcycle() {
		motorcycle.rent(drivingLicense, begin, end);;
		
		assertFalse(motorcycle.isFree(new LocalDate(2016, 12, 15), new LocalDate(2016, 12, 9)));
	}
	@Test
	public void endBeforeEndCar() {
		car.rent(drivingLicense, begin, end);
		assertFalse(car.isFree(new LocalDate(2016, 12, 18), new LocalDate(2016, 12, 25)));
	}
	@Test
	public void endBeforeEndMotorcycle() {
		motorcycle.rent(drivingLicense, begin, end);
		assertFalse(motorcycle.isFree(new LocalDate(2016, 12, 18), new LocalDate(2016, 12, 25)));
	}
	
	//6 if
	@Test
	public void beginBeforeBeginEAfterBeforeAfterCar() {
		car.rent(drivingLicense, begin, end);
		assertFalse(car.isFree(new LocalDate(2016, 12, 9), end));
	}
	@Test
	public void beginBeforeBeginEAfterBeforeAfterMotorcycle() {
		motorcycle.rent(drivingLicense, begin, end);
		assertFalse(motorcycle.isFree(new LocalDate(2016, 12, 9), end));
	}
	
	@After
	public void after() {
		RentACar.rentACars.clear();
	}
}