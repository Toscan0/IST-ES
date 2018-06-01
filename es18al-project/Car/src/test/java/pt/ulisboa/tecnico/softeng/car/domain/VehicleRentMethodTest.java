package pt.ulisboa.tecnico.softeng.car.domain;


import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;


public class VehicleRentMethodTest {
	private Car car1;
	private Car car2;
	private Motorcycle moto;
	private Motorcycle moto2;
	
	private static final String DRIVER_LICENSE = "BK01";
	
	private final LocalDate begin = new LocalDate(2016, 12, 19);
	private final LocalDate end = new LocalDate(2016, 12, 24);
	
	@Before
	public void setUp() {
		RentACar rentACar = new RentACar("IST");
		this.car1 = new Car("00-CR-79", 15000, rentACar);
		this.car2 = new Car("10-CR-79", 15000, rentACar);
		this.moto = new Motorcycle("00-CR-80", 1000, rentACar);
		this.moto2 = new Motorcycle("00-CR-81", 1000, rentACar);
		this.car2.rent( DRIVER_LICENSE, begin, end);
		this.moto2.rent( DRIVER_LICENSE, begin, end);
	}
	
	@Test
	public void successCar() {
		Renting renting = this.car1.rent( DRIVER_LICENSE, begin, end);

		assertEquals(1, this.car1.getNumberOfRentings());
		assertTrue(renting.getReference().length() > 0);
		Assert.assertEquals(this.begin, renting.getBegin());
		Assert.assertEquals(this.end, renting.getEnd());
		Assert.assertEquals(this.DRIVER_LICENSE, renting.getDrivingLicense());
	}
	
	@Test
	public void successMotorcycle() {
		Renting renting = this.moto.rent( DRIVER_LICENSE, begin, end);

		assertEquals(1, this.moto.getNumberOfRentings());
		assertTrue(renting.getReference().length() > 0);
		Assert.assertEquals(this.begin, renting.getBegin());
		Assert.assertEquals(this.end, renting.getEnd());
		Assert.assertEquals(this.DRIVER_LICENSE, renting.getDrivingLicense());
	}
	
	@Test(expected = CarException.class)
	public void noDriverLicense() {
		this.car1.rent( null, begin, end);
	}
	
	@Test(expected = CarException.class)
	public void emptyDriverLicense() {
		this.car1.rent( "", begin, end);
	}
	
	@Test(expected = CarException.class)
	public void wrongFormatLicense() {
		this.car1.rent( "02FD", begin, end);
	}

	@Test(expected = CarException.class)
	public void nullBegin() {
		this.car1.rent( DRIVER_LICENSE, null, end);
	}
	
	@Test(expected = CarException.class)
	public void nullEnd() {
		this.car1.rent( DRIVER_LICENSE, begin, null);
	}
	
	@Test(expected = CarException.class)
	public void carAlreadyRented() {
		this.car2.rent( DRIVER_LICENSE, begin, end);
	}
	
	@Test(expected = CarException.class)
	public void motoAlreadyRented() {
		this.moto2.rent( DRIVER_LICENSE, begin, end);
	}
	
	@After
	public void tearDown() {
		RentACar.rentACars.clear();
	}

}
