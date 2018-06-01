package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentACarGetAllAvailableMotorcyclesMethodTest {
private RentACar rent;
	
	private final String name = "NAME1";
	private static final String DRIVER_LICENSE = "BK01";
	private static final LocalDate BEGIN = new LocalDate(2016, 12, 19);
	private static final LocalDate END = new LocalDate(2016, 12, 21);
	private Motorcycle moto1;
	private Motorcycle moto2;
	private Car car;
	
	
	@Before
	public void setUp() {
		this.rent = new RentACar(name);
		this.moto1 = new Motorcycle("00-CR-79", 15000, this.rent);
		this.moto2 = new Motorcycle("00-CR-80", 15000, this.rent);
		this.car = new Car("00-CR-81", 1000, this.rent);
		Renting renting = this.moto2.rent( DRIVER_LICENSE, BEGIN, END);
	}
	
	@Test
	public void sucess() {
		Set<Vehicle> moto = rent.getAllAvailableMotorcycles(BEGIN, END);	
		assertNotNull(moto);
	}
	
	@Test(expected = CarException.class)
	public void endBeforeBegin() {
		rent.getAllAvailableMotorcycles(END, BEGIN);
	}

	@Test(expected = CarException.class)
	public void nullBegin() {
		rent.getAllAvailableMotorcycles(null, END);
	}
	
	@Test(expected = CarException.class)
	public void nullEnd() {
		rent.getAllAvailableMotorcycles(BEGIN, null);
	}

	@Test(expected = CarException.class)
	public void nullDates() {
		rent.getAllAvailableMotorcycles(null, null);
	}


	@After
	public void after() {
		RentACar.rentACars.clear();
	}
}