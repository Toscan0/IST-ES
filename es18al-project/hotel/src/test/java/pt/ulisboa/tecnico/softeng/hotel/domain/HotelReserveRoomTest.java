package pt.ulisboa.tecnico.softeng.hotel.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.dataobjects.RoomBookingData;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class HotelReserveRoomTest {
	private final LocalDate arrival = new LocalDate(2016, 12, 19);
	private final LocalDate departure = new LocalDate(2016, 12, 24);
	private Hotel hotel;
	private Room room;
	private Booking booking;

	@Before
	public void setUp() {
		this.hotel = new Hotel("XPTO123", "Lisboa");
		this.room = new Room(this.hotel, "01", Type.SINGLE);
	}

	//Single room - success
	@Test
	public void successSingleRoom() {
		
		//Room room = new Room(this.hotel, "01", Type.SINGLE);
		String reference = this.hotel.reserveRoom(Type.SINGLE, this.arrival, this.departure);

		assertNotNull(reference);
	}
	
	@Test (expected = HotelException.class)
	public void noHotels() {
		Hotel.hotels.clear();
		String reference = this.hotel.reserveRoom(Type.SINGLE, this.arrival, this.departure);
	}
	
	//Null room
	@Test(expected = HotelException.class)
	public void nullType() {
		String reference =  this.hotel.reserveRoom(null, this.arrival, this.departure);
	}
	
	//Null arrival
	@Test(expected = HotelException.class)
	public void nullArrival() {
		String reference =  this.hotel.reserveRoom(Type.SINGLE, null, this.departure);
	}
	
	//Null departure 
	@Test(expected = HotelException.class)
	public void nullDeparture() {
		String reference = this.hotel.reserveRoom(Type.SINGLE, this.arrival, null);
	}

	//Null dates 
	@Test(expected = HotelException.class)
	public void nullDates() {
		String reference = this.hotel.reserveRoom(Type.SINGLE, null, null);
	}

	//Null type & arrival 
	@Test(expected = HotelException.class)
	public void nullTypeArrival() {
		String reference = this.hotel.reserveRoom(null, null, this.departure);
	}
	
	//Null type & departure
	@Test(expected = HotelException.class)
	public void nullTypeDeparture() {
		String reference = this.hotel.reserveRoom(null,  this.arrival, null);
	}
	// All Null
	@Test(expected = HotelException.class)
	public void allNull() {
		String reference = this.hotel.reserveRoom(null,  null, null);
	}
	
	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}
	
	

}
