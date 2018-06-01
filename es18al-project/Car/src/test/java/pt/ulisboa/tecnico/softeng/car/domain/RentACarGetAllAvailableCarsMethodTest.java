package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentACarGetAllAvailableCarsMethodTest {
private RentACar rent;
	
	private final String name = "NAME1";
	private static final String DRIVER_LICENSE = "BK01";
	private static final LocalDate BEGIN = new LocalDate(2016, 12, 19);
	private static final LocalDate END = new LocalDate(2016, 12, 21);
	private Car car1;
	private Car car2;
	private Motorcycle moto;
	
	
	@Before
	public void setUp() {
		this.rent = new RentACar(name);
		this.car1 = new Car("00-CR-79", 15000, this.rent);
		this.car2 = new Car("00-CR-80", 15000, this.rent);
		this.moto = new Motorcycle("00-CR-81", 1000, this.rent);
		Renting renting = this.car2.rent( DRIVER_LICENSE, BEGIN, END);
	}
	
	@Test
	public void sucess() {
		Set<Vehicle> car = rent.getAllAvailableCars(BEGIN, END);	
		assertNotNull(car);
	}
	
	@Test(expected = CarException.class)
	public void endBeforeBegin() {
		rent.getAllAvailableCars(END, BEGIN);
	}

	@Test(expected = CarException.class)
	public void nullBegin() {
		rent.getAllAvailableCars(null, END);
	}
	
	@Test(expected = CarException.class)
	public void nullEnd() {
		rent.getAllAvailableCars(BEGIN, null);
	}

	@Test(expected = CarException.class)
	public void nullDates() {
		rent.getAllAvailableCars(null, null);
	}


	@After
	public void after() {
		RentACar.rentACars.clear();
	}
}
