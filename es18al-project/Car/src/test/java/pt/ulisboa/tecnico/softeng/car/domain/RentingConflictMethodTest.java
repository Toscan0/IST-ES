package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.joda.time.LocalDate;
import java.util.Set;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class RentingConflictMethodTest {
	private final LocalDate begin = new LocalDate(2016, 12, 19);
	private final LocalDate end = new LocalDate(2016, 12, 24);
	private String plateCar = "20-XX-30";
	private String plateMoto = "20-YY-30";
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
		motorcycle = new Motorcycle(plateMoto, 30, rentACar);
	}
	
	
	@Test
	public void sucessCar() {
		Renting renting = new Renting(car, begin, end, drivingLicense);
		
		conlfictResult = renting.conflict(new LocalDate(2016, 12, 9), new LocalDate(2016, 12, 15));
		assertFalse(conlfictResult);
	}
	@Test
	public void sucessMoto() {
		Renting renting = new Renting(motorcycle, begin, end, drivingLicense);
		
		conlfictResult = renting.conflict(new LocalDate(2016, 12, 9), new LocalDate(2016, 12, 15));
		assertFalse(conlfictResult);
	}
	
	@Test(expected = CarException.class)
	public void beginNullCar() {
		Renting renting = new Renting(car, begin, end, drivingLicense);
		
		conlfictResult = renting.conflict(null, end);
	}
	@Test(expected = CarException.class)
	public void beginNullMoto() {
		Renting renting = new Renting(motorcycle, begin, end, drivingLicense);
		
		conlfictResult = renting.conflict(null, end);
	}
	@Test(expected = CarException.class)
	public void endNullCar() {
		Renting renting = new Renting(car, begin, end, drivingLicense);
		
		conlfictResult = renting.conflict(begin, null);
	}
	@Test(expected = CarException.class)
	public void endNullMoto() {
		Renting renting = new Renting(motorcycle, begin, end, drivingLicense);
		
		conlfictResult = renting.conflict(begin, null);
	}
	@Test(expected = CarException.class)
	public void bothNullCar() {
		Renting renting = new Renting(car, begin, end,drivingLicense);
		
		conlfictResult = renting.conflict(null, null);
	}
	@Test(expected = CarException.class)
	public void bothNullMoto() {
		Renting renting = new Renting(motorcycle, begin, end,drivingLicense);
		
		conlfictResult = renting.conflict(null, null);
	}
	
	@Test
	public void endEqualsBeginCar() {
		Renting renting = new Renting(car, begin, end, drivingLicense);
		
		conlfictResult = renting.conflict(new LocalDate(2016, 12, 23), new LocalDate(2016, 12, 23));
		assertTrue(conlfictResult);
	}
	@Test
	public void endEqualsBeginMoto() {
		Renting renting = new Renting(motorcycle, begin, end, drivingLicense);
		
		conlfictResult = renting.conflict(new LocalDate(2016, 12, 23), new LocalDate(2016, 12, 23));
		assertTrue(conlfictResult);
	}
	
	@Test(expected = CarException.class)
	public void endBeforeBeginCar() {
		Renting renting = new Renting(car, begin, end, drivingLicense);
		
		conlfictResult = renting.conflict(new LocalDate(2016, 12, 15), new LocalDate(2016, 12, 9));
		assertTrue(conlfictResult);
	}
	@Test(expected = CarException.class)
	public void endBeforeBeginMoto() {
		Renting renting = new Renting(motorcycle, begin, end, drivingLicense);
		
		conlfictResult = renting.conflict(new LocalDate(2016, 12, 15), new LocalDate(2016, 12, 9));
		assertTrue(conlfictResult);
	}
	
	@Test
	public void begingAfterBeginBeforeEndCar() {
		Renting renting = new Renting(car, begin, end, drivingLicense);
		conlfictResult = renting.conflict(new LocalDate(2016, 12, 23), new LocalDate(2016, 12, 24));
		assertTrue(conlfictResult);
	}
	@Test
	public void begingAfterBeginBeforeEndMoto() {
		Renting renting = new Renting(motorcycle, begin, end, drivingLicense);
		conlfictResult = renting.conflict(new LocalDate(2016, 12, 23), new LocalDate(2016, 12, 24));
		assertTrue(conlfictResult);
	}
	
	@Test
	public void endBeforeEndAfterBeginCar() {
		Renting renting = new Renting(car, begin, end, drivingLicense);
		conlfictResult = renting.conflict(new LocalDate(2016, 12, 9), new LocalDate(2016, 12, 23));
		assertTrue(conlfictResult);
	}
	@Test
	public void endBeforeEndAfterBeginMoto() {
		Renting renting = new Renting(motorcycle, begin, end, drivingLicense);
		conlfictResult = renting.conflict(new LocalDate(2016, 12, 9), new LocalDate(2016, 12, 23));
		assertTrue(conlfictResult);
	}
	
	@Test
	public void beginBeforeBeginEndAfterEndCar() {
		Renting renting = new Renting(car, begin, end, drivingLicense);
		conlfictResult = renting.conflict(new LocalDate(2016, 12, 9), new LocalDate(2016, 12, 25));
		assertTrue(conlfictResult);
	}
	@Test
	public void beginBeforeBeginEndAfterEndMoto() {
		Renting renting = new Renting(motorcycle, begin, end, drivingLicense);
		conlfictResult = renting.conflict(new LocalDate(2016, 12, 9), new LocalDate(2016, 12, 25));
		assertTrue(conlfictResult);
	}

	@Test
	public void endBeforeEndCar() {
		Renting renting = new Renting(car, begin, end, drivingLicense);
		
		conlfictResult = renting.conflict(new LocalDate(2016, 12, 18), new LocalDate(2016, 12, 25));
		assertTrue(conlfictResult);
	}
	public void endBeforeEndMoto() {
		Renting renting = new Renting(motorcycle, begin, end, drivingLicense);
		
		conlfictResult = renting.conflict(new LocalDate(2016, 12, 18), new LocalDate(2016, 12, 25));
		assertTrue(conlfictResult);
	}
	
	@Test
	public void beginBeforeBeginEAfterBeforeAfterCar() {
		Renting renting = new Renting(car, begin, end, drivingLicense);
		
		conlfictResult = renting.conflict(new LocalDate(2016, 12, 9), end);
		assertTrue(conlfictResult);
	}
	@Test
	public void beginBeforeBeginEAfterBeforeAfterMoto() {
		Renting renting = new Renting(motorcycle, begin, end, drivingLicense);
		
		conlfictResult = renting.conflict(new LocalDate(2016, 12, 9), end);
		assertTrue(conlfictResult);
	}


	@After
	public void after() {
		RentACar.rentACars.clear();
	}
}
