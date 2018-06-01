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


public class RentACarGetRentingDataMethodTest {
	private RentACar rent;
	
	private final String plate = "23-XX-22";
	private final String plate2 = "29-XX-11";
	private final String name = "NAME1";
	private final String reference = "12345";
	private Renting renting;
	private Renting renting2;
	private Car car;
	private Motorcycle moto;
	private final String drivingLicense = "CARTA01";
	private static final LocalDate BEGIN = new LocalDate(2016, 12, 19);
	private static final LocalDate END = new LocalDate(2016, 12, 21);
	
	
	
	@Before
	public void setUp() {
		rent = new RentACar(name);
		this.car = new Car("23-XX-22", 500, rent);
		
		this.moto = new Motorcycle("24-XX-25", 70, rent);
		this.renting = this.car.rent(drivingLicense, BEGIN, END );
		this.renting2 = this.moto.rent(drivingLicense, BEGIN, END );
	}
	
	@Test
	public void sucess() {

		RentingData data = RentACar.getRentingData(this.renting.getReference());
		
		assertEquals(this.renting.getReference(), data.getReference());
		assertEquals(this.car.getPlate(), data.getPlate());
		assertEquals(this.rent.getCode(), data.getRentACarCode());
		assertEquals(this.renting.getDrivingLicense(), data.getDrivingLicense());
		assertEquals(this.renting.getBegin(), data.getBegin());
		assertEquals(this.renting.getEnd(), data.getEnd());
	}
	
	@Test
	public void sucess2() {

		RentingData data = RentACar.getRentingData(this.renting2.getReference());
		
		assertEquals(this.renting2.getReference(), data.getReference());
		assertEquals(this.moto.getPlate(), data.getPlate());
		assertEquals(this.rent.getCode(), data.getRentACarCode());
		assertEquals(this.renting2.getDrivingLicense(), data.getDrivingLicense());
		assertEquals(this.renting2.getBegin(), data.getBegin());
		assertEquals(this.renting2.getEnd(), data.getEnd());
	}
	
	@Test(expected = CarException.class)
	public void failureReference() {
		RentACar.getRentingData("falha");
	}
	

	@Test(expected = CarException.class)
	public void nullReference() {
		RentACar.getRentingData(null);
	}
	
	@Test(expected = CarException.class)
	public void noReference() {
		RentACar.getRentingData(" ");
	}
	
	@After
	public void after() {
		RentACar.rentACars.clear();
	}
}
