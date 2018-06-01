package pt.ulisboa.tecnico.softeng.broker.domain;

import static org.junit.Assert.*;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.domain.Hotel;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;

public class BulkRoomBookingGetReferenceMethodTest {
	private final LocalDate arrival = new LocalDate(2018, 3, 6);
	private final LocalDate departure = new LocalDate(2018, 4, 6);
	private int number = 1;
	private Hotel hotel;
	private String STRING = "Random";
	private String REFERENCE = "SINGLE";

	@Before
	public void setUp() {
		this.hotel = new Hotel("XPTO123", "Paris");		
	}
	
	@Test
	public void bookingCancelled() {
		number = 0;
		BulkRoomBooking booking = new BulkRoomBooking(number, arrival, departure);
		booking.processBooking(); //1excep
		booking.processBooking(); //2excep
		booking.processBooking(); //3excep -- cancelled true
		assertEquals(null,booking.getReference(STRING));
	}
	
	@Test
	public void noReferences() {
		BulkRoomBooking booking = new BulkRoomBooking(number, arrival, departure);
		assertEquals(null,booking.getReference(STRING));
	}
	
	@Test
	public void sucess() {
		new Room(this.hotel, "01", Type.SINGLE);
		new Room(this.hotel, "02", Type.SINGLE);
		BulkRoomBooking booking = new BulkRoomBooking(number, arrival, departure);
		booking.processBooking();
		assertNotNull(booking.getReference(REFERENCE));
	}
	
	@Test
	public void wrongType() {
		new Room(this.hotel, "01", Type.SINGLE);
		new Room(this.hotel, "02", Type.SINGLE);
		BulkRoomBooking booking = new BulkRoomBooking(number, arrival, departure);
		booking.processBooking();
		assertEquals(null,booking.getReference("DOUBLE"));
	}
	
	@Test
	public void catchHotelException() {
		new Room(this.hotel, "01", Type.SINGLE);
		BulkRoomBooking booking = new BulkRoomBooking(number, arrival, departure);
		booking.processBooking();
		Hotel.hotels.clear();
		assertEquals(null,booking.getReference("DOUBLE"));
	}
	
	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}

}
