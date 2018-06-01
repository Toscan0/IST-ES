package pt.ulisboa.tecnico.softeng.broker.domain;

import static org.junit.Assert.*;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AdventureSetStateMethodTest {
	private Adventure adventure;
	private static final int AGE = 20;
	private static final int AMOUNT = 300;
	private static final String IBAN = "BK011234567";
	private Broker broker;
	private final LocalDate begin = new LocalDate(2016, 12, 19);
	private final LocalDate end = new LocalDate(2016, 12, 21);

	@Before
	public void setUp() {
		this.broker = new Broker("BR01", "eXtremeADVENTURE");
		adventure = new Adventure(this.broker, this.begin, this.end, AGE, IBAN, AMOUNT);
	}

	@Test
	public void successStateProcessPayment() {
		String txt = "LINDO";
		adventure.setPaymentConfirmation(txt);
		assertEquals(txt,adventure.getPaymentConfirmation());
		adventure.setPaymentCancellation(txt);
		assertEquals(txt,adventure.getPaymentCancellation());
		//assertEquals(true,adventure.cancelPayment());
	}
	@Test
	public void successStateProcessPaymentCancelation() {
		String txt = "LINDO";
		adventure.setPaymentConfirmation(txt);
		assertTrue(adventure.cancelPayment());
	}
	@Test
	public void successStateProcessActivity() {
		String txt = "LINDO";
		adventure.setActivityConfirmation(txt);
		assertEquals(txt,adventure.getActivityConfirmation());
		adventure.setActivityCancellation(txt);
		assertEquals(txt,adventure.getActivityCancellation());
	}
	@Test
	public void successStateProcessRoomCancellation() {
		String txt = "LINDO";
		adventure.setRoomConfirmation(txt);
		assertEquals(txt,adventure.getRoomConfirmation());
		adventure.setRoomCancellation(txt);
		assertEquals(txt,adventure.getRoomCancellation());	
	}
	@Test
	public void successStateProcessSeGPayment() {
		adventure.setState(Adventure.State.PROCESS_PAYMENT);
		assertEquals(Adventure.State.PROCESS_PAYMENT, adventure.getState());

	}
	@Test
	public void successStateReserveActivity() {
		adventure.setState(Adventure.State.RESERVE_ACTIVITY);
		assertEquals(Adventure.State.RESERVE_ACTIVITY, adventure.getState());
	}
	@Test
	public void successStateBookRoom() {
		adventure.setState(Adventure.State.BOOK_ROOM);
		assertEquals(Adventure.State.BOOK_ROOM, adventure.getState());
	}
	@Test
	public void successStateUndo() {
		adventure.setState(Adventure.State.UNDO);
		assertEquals(Adventure.State.UNDO, adventure.getState());
	}
	@Test
	public void successStateCancelled() {
		adventure.setState(Adventure.State.CANCELLED);
		assertEquals(Adventure.State.CANCELLED, adventure.getState());
	}
	/*@Test
	public void successStateDefault() {
		try {
			adventure.setState(null);
			Assert.fail();
		}
		catch (BrokerException be) {
			Assert.assertEquals(Adventure.State.CANCELLED, adventure.getState());
		}
	}*/

	@After
	public void tearDown() {
		Broker.brokers.clear();
	}
}