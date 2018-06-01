package pt.ulisboa.tecnico.softeng.hotel.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Set;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class HotelBulkBookingMethodTest {
	private final LocalDate arrival = new LocalDate(2016, 12, 19);
	private final LocalDate departure = new LocalDate(2016, 12, 21);
	private Hotel hotel;
	private final double price1 = 124.5;
	private final double price2 = 224.5;

	@Before
	public void setUp() {
		this.hotel = new Hotel("XPTO123", "Paris", "NIF", "IBAN", price1, price2);
		new Room(this.hotel, "01", Type.DOUBLE);
		new Room(this.hotel, "02", Type.SINGLE);
		new Room(this.hotel, "03", Type.DOUBLE);
		new Room(this.hotel, "04", Type.SINGLE);

		this.hotel = new Hotel("XPTO124", "Paris", "NIF2", "IBAN", price1, price2);
		new Room(this.hotel, "01", Type.DOUBLE);
		new Room(this.hotel, "02", Type.SINGLE);
		new Room(this.hotel, "03", Type.DOUBLE);
		new Room(this.hotel, "04", Type.SINGLE);
	}

	@Test
	public void success() {
		Set<String> references = Hotel.bulkBooking(2, this.arrival, this.departure, "NIF", "IBAN");

		assertEquals(2, references.size());
	}

	@Test(expected = HotelException.class)
	public void zeroNumber() {
		Hotel.bulkBooking(0, this.arrival, this.departure, "NIF", "IBAN");
	}

	@Test(expected = HotelException.class)
	public void noRooms() {
		Hotel.hotels.clear();
		this.hotel = new Hotel("XPTO124", "Paris", "NIF", "IBAN", price1, price2);

		Hotel.bulkBooking(3, this.arrival, this.departure, "NIF", "IBAN");
	}

	@Test
	public void OneNumber() {
		Set<String> references = Hotel.bulkBooking(1, this.arrival, this.departure, "NIF", "IBAN");

		assertEquals(1, references.size());
	}

	@Test(expected = HotelException.class)
	public void nullArrival() {
		Hotel.bulkBooking(2, null, this.departure, "NIF", "IBAN");
	}

	@Test(expected = HotelException.class)
	public void nullDeparture() {
		Hotel.bulkBooking(2, this.arrival, null, "NIF", "IBAN");
	}
	
	@Test
	public void reserveAll() {
		Set<String> references = Hotel.bulkBooking(8, this.arrival, this.departure, "NIF", "IBAN");

		assertEquals(8, references.size());
	}

	@Test
	public void reserveAllPlusOne() {
		try {
			Hotel.bulkBooking(9, this.arrival, this.departure, "NIF", "IBAN");
			fail();
		} catch (HotelException he) {
			assertEquals(8, Hotel.getAvailableRooms(8, this.arrival, this.departure).size());
		}
	}

	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}

}
