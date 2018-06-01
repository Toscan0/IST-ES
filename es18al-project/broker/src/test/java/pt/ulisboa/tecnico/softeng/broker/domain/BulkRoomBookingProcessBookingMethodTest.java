package pt.ulisboa.tecnico.softeng.broker.domain;

import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashSet;

//import org.junit.BeforeClass;
import org.joda.time.LocalDate;
import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Assert;
import org.junit.Test;


import static org.junit.Assert.assertNotNull;

//import java.util.HashSet;
//import java.util.Set;

//import pt.ulisboa.tecnico.softeng.activity.domain.ActivityProvider;
//import pt.ulisboa.tecnico.softeng.bank.domain.Bank;
//import pt.ulisboa.tecnico.softeng.broker.exception.RemoteAccessException;
//import pt.ulisboa.tecnico.softeng.broker.interfaces.HotelInterface;
//import pt.ulisboa.tecnico.softeng.hotel.dataobjects.RoomBookingData;
import pt.ulisboa.tecnico.softeng.hotel.domain.Hotel;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;
//import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;


import pt.ulisboa.tecnico.softeng.broker.domain.BulkRoomBooking;
//import pt.ulisboa.tecnico.softeng.broker.interfaces.HotelInterface;

public class BulkRoomBookingProcessBookingMethodTest {
	private final LocalDate arrival = new LocalDate(2018, 3, 6);
	private final LocalDate departure = new LocalDate(2018, 4, 6);
	private int number;
	private Hotel hotel;


	@Before
	public void setUp() {
		this.hotel = new Hotel("XPTO123", "Paris");		
	}
	
	@Test
	public void successNoRooms() {
		//primeira excepcao 
		number = 1;
		BulkRoomBooking booking = new BulkRoomBooking(number, arrival, departure);
		booking.processBooking();
		assertEquals( booking.getReferences(), new HashSet<>() ); //reference esta vazio []
		assertNotNull(booking.getNumber());
		assertNotNull(booking.getArrival());
		assertNotNull(booking.getDeparture());
	}
	
	
	@Test
	public void successNumberlessOne() {
		//segunda excepcao
		number = 0;
		BulkRoomBooking booking = new BulkRoomBooking(number, arrival, departure);
		booking.processBooking();
		assertEquals( booking.getReferences(), new HashSet<>() ); //reference esta vazio []
		assertNotNull(booking.getNumber());
		assertNotNull(booking.getArrival());
		assertNotNull(booking.getDeparture());
	}
	
	
	@Test
	public void success() {
		//success a reservar quarto
		new Room(this.hotel, "01", Type.SINGLE);
		new Room(this.hotel, "02", Type.SINGLE);
		new Room(this.hotel, "03", Type.SINGLE);
		number = 2;
		BulkRoomBooking booking = new BulkRoomBooking(number, arrival, departure);
		booking.processBooking();
		
		assertNotNull(booking.getNumber());
		assertNotNull(booking.getArrival());
		assertNotNull(booking.getDeparture());
		
		//assertEquals( booking.getReferences(), HotelInterface.bulkBooking(number, arrival, departure) );
	}
	
	@Test
	public void successNumberBiggerRooms() {
		//terceira excepcao
		number = 2;
		new Room(this.hotel, "04", Type.SINGLE);
		BulkRoomBooking booking = new BulkRoomBooking(number, arrival, departure);
		booking.processBooking();
		assertEquals( booking.getReferences(), new HashSet<>() ); //reference esta vazio []
		assertNotNull(booking.getNumber());
		assertNotNull(booking.getArrival());
		assertNotNull(booking.getDeparture());
	}
	
	@Test
	public void successBookingCancelled() {
		number = 0;
		BulkRoomBooking booking = new BulkRoomBooking(number, arrival, departure);
		booking.processBooking(); //1excep
		booking.processBooking(); //2excep
		booking.processBooking(); //3excep -- cancelled true
		booking.processBooking(); //cancelled retorna deve dar success
		assertEquals( booking.getReferences(), new HashSet<>() ); //reference esta vazio []
		assertNotNull(booking.getNumber());
		assertNotNull(booking.getArrival());
		assertNotNull(booking.getDeparture());
	}
	
	
	
	
	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}
	
}
