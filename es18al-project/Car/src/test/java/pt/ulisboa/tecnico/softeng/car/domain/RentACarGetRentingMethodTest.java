package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Set;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;
import pt.ulisboa.tecnico.softeng.car.domain.Car;
import pt.ulisboa.tecnico.softeng.car.domain.Motorcycle;
import pt.ulisboa.tecnico.softeng.car.domain.RentACar;
import pt.ulisboa.tecnico.softeng.car.dataobjects.RentingData;


public class RentACarGetRentingMethodTest {
	private RentACar rent;
	
	private final String plate = "23-XX-22";
	private final String plate2 = "29-XX-11";
	private final String name = "NAME1";
	private final String reference = "12345";
	private Renting renting;
	private Car car;
	private Motorcycle moto;
	private final String drivingLicense = "CARTA36";
	private static final LocalDate BEGIN = new LocalDate(2016, 12, 19);
	private static final LocalDate END = new LocalDate(2016, 12, 21);
	
	
	
	@Before
	public void setUp() {
		rent = new RentACar(name);
		this.car = new Car("23-XX-22", 500, rent);
		
		this.moto = new Motorcycle("24-XX-25", 70, rent);
		this.renting = this.car.rent(drivingLicense, BEGIN, END );
	}
	
	@Test
	public void sucess() {

		assertEquals(this.renting, rent.getRenting(this.renting.getReference()));
	}
	
	
	@Test
	public void failure() {

		assertNull(rent.getRenting("falha"));
	}
	
	@Test(expected = CarException.class)
	public void nullReference() {
		rent.getRenting(null);
		
	}
	
	@Test(expected = CarException.class)
	public void noReference() {
		rent.getRenting(" ");
	}
	
	@After
	public void after() {
		RentACar.rentACars.clear();
	}
}
