package pt.ulisboa.tecnico.softeng.car.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Test;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.FenixFramework;
import pt.ist.fenixframework.Atomic.TxMode;


public class CarPersistenceTest {
	private static final String ACTIVITY_NAME = "Activity_Name";
	private static final String PROVIDER_NAME = "Wicket";
	private static final String PROVIDER_CODE = "A12345";
	private static final String IBAN = "IBAN";
	private static final String NIF = "NIF";
	private static final String BUYER_IBAN = "IBAN2";
	private static final String BUYER_NIF = "NIF2";
	private static final int CAPACITY = 25;
	private static final String CARPLATE = "AB-12-CD";
	private static final String MOTOPLATE = "12-AB-34";
	private static final String DRIVERLICENSE = "abcde123";

	private final LocalDate begin = new LocalDate(2017, 04, 01);
	private final LocalDate end = new LocalDate(2017, 04, 15);
	private final LocalDate begin2 = new LocalDate(2017, 04, 20);
	private final LocalDate end2 = new LocalDate(2017, 04, 30);

	@Test
	public void success() {
		atomicProcess();
		atomicAssert();
	}

	@Atomic(mode = TxMode.WRITE)
	public void atomicProcess() {
		RentACar rentacar = new RentACar(PROVIDER_NAME, NIF, IBAN);
		Car car = new Car(CARPLATE, 180, 65, rentacar);
		new Renting(DRIVERLICENSE, begin, end, car, BUYER_NIF, BUYER_IBAN);
	}

	@SuppressWarnings("deprecation")
	@Atomic(mode = TxMode.READ)
	public void atomicAssert() {
		assertEquals(1, FenixFramework.getDomainRoot().getRentacarSet().size());

		List<RentACar> providers = new ArrayList<>(FenixFramework.getDomainRoot().getRentacarSet());
		RentACar provider = providers.get(0);

		assertTrue(provider.getCode().startsWith(NIF));
		assertEquals(PROVIDER_NAME, provider.getName());
		assertEquals(NIF, provider.getNif());
		assertEquals(IBAN, provider.getIban());
		assertEquals(1, provider.getAvailableVehicles(Car.class,begin2, end2).size());
		
		
		List<Vehicle> vehicles = new ArrayList<>(provider.getVehicleSet());
		Vehicle vehicle = vehicles.get(0);

		assertEquals(180, vehicle.getKilometers());
		assertEquals(CARPLATE, vehicle.getPlate());
		assertEquals(1, vehicle.getRentings().size());
		
		List<Renting> rentings = new ArrayList<>(vehicle.getRentingSet());
		Renting renting = rentings.get(0);

		assertNotNull(renting.getReference());
		assertNull(renting.getCancel());
		assertNull(renting.getCancellationDate());
	}

	@After
	@Atomic(mode = TxMode.WRITE)
	public void tearDown() {
		for (RentACar rentacar : FenixFramework.getDomainRoot().getRentacarSet()) {
			rentacar.delete();
		}
	}

}
