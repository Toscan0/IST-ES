package pt.ulisboa.tecnico.softeng.hotel.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class BookingConstructorTest {
	private final LocalDate arrival = new LocalDate(2016, 12, 19);
	private final LocalDate departure = new LocalDate(2016, 12, 21);
	private final double price1 = 124.5;
	private final double price2 = 224.5;
	private Hotel hotel;

	@Before
	public void setUp() {
		this.hotel = new Hotel("XPTO123", "Londres", "NIF", "IBAN", price1, price2);
	}

	@Test
	public void success() {
		Booking booking = new Booking(this.hotel, this.arrival, this.departure, "NIF", "IBAN", price1);

		Assert.assertTrue(booking.getReference().startsWith(this.hotel.getCode()));
		Assert.assertTrue(booking.getReference().length() > Hotel.CODE_SIZE);
		Assert.assertEquals(this.arrival, booking.getArrival());
		Assert.assertEquals(this.departure, booking.getDeparture());
	}

	@Test(expected = HotelException.class)
	public void nullHotel() {
		new Booking(null, this.arrival, this.departure, "NIF", "IBAN", price1);
	}

	@Test(expected = HotelException.class)
	public void nullArrival() {
		new Booking(this.hotel, null, this.departure, "NIF", "IBAN", price1);
	}

	@Test(expected = HotelException.class)
	public void nullDeparture() {
		new Booking(this.hotel, this.arrival, null, "NIF", "IBAN", price1);
	}

	@Test(expected = HotelException.class)
	public void departureBeforeArrival() {
		new Booking(this.hotel, this.arrival, this.arrival.minusDays(1), "NIF", "IBAN", price1);
	}

	@Test
	public void arrivalEqualDeparture() {
		new Booking(this.hotel, this.arrival, this.arrival, "NIF", "IBAN", price1);
	}


	@Test(expected = HotelException.class)
	public void nullNif() {
		new Booking(null, this.arrival, this.departure, null, "IBAN", price1);
	}
	

	@Test(expected = HotelException.class)
	public void emptyNif() {
		new Booking(null, this.arrival, this.departure, "", "IBAN", price1);
	}
	

	@Test(expected = HotelException.class)
	public void spaceNif() {
		new Booking(null, this.arrival, this.departure, "  ", "IBAN", price1);
	}
	

	@Test(expected = HotelException.class)
	public void nullIban() {
		new Booking(null, this.arrival, this.departure, "NIF", null, price1);
	}
	

	@Test(expected = HotelException.class)
	public void emptyIban() {
		new Booking(null, this.arrival, this.departure, "NIF", "", price1);
	}
	
	@Test(expected = HotelException.class)
	public void spaceIban() {
		new Booking(null, this.arrival, this.departure, "NIF", "  ", price1);
	}
	
	@Test(expected = HotelException.class)
	public void zeroPrice() {
		new Booking(null, this.arrival, this.departure, "NIF", "IBAN", 0);
	}
	
	@Test(expected = HotelException.class)
	public void negativePrice() {
		new Booking(null, this.arrival, this.departure, "NIF", "IBAN", -1.23);
	}
	
	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}

}
