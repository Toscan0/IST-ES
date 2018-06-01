package pt.ulisboa.tecnico.softeng.broker.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.activity.domain.Activity;
import pt.ulisboa.tecnico.softeng.activity.domain.ActivityOffer;
import pt.ulisboa.tecnico.softeng.activity.domain.ActivityProvider;
import pt.ulisboa.tecnico.softeng.bank.domain.Account;
import pt.ulisboa.tecnico.softeng.bank.domain.Bank;
import pt.ulisboa.tecnico.softeng.bank.domain.Client;
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.hotel.domain.Hotel;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;

public class AdventureProcessMethodTest {
	private final LocalDate begin = new LocalDate(2016, 12, 19);
	private final LocalDate end = new LocalDate(2016, 12, 21);
	private Broker broker;
	private String IBAN;
	private BClient bclient;
	private final double price1 = 124.5;
	private final double price2 = 224.5;

	@Before
	public void setUp() {
		this.broker = new Broker("BR01", "eXtremeADVENTURE", "NIFSELLER", "NIFBUYER");

		Bank bank = new Bank("Money", "BK01");
		Client client = new Client(bank, "António");
		Account account = new Account(bank, client);
		this.IBAN = account.getIBAN();
		account.deposit(1000);

		Hotel hotel = new Hotel("XPTO123", "Paris", "NIF", "IBAN", price1, price2);
		new Room(hotel, "01", Type.SINGLE);

		ActivityProvider provider = new ActivityProvider("XtremX", "ExtremeAdventure", "NIF", "IBAN");
		Activity activity = new Activity(provider, "Bush Walking", 18, 80, 10);
		new ActivityOffer(activity, this.begin, this.end, 30);
		new ActivityOffer(activity, this.begin, this.begin, 30);
		
		this.bclient = new BClient(this.IBAN, "NIF", 19);
	}

	@Test
	public void success() {
		Adventure adventure = new Adventure(this.broker, this.begin, this.end, bclient, 300, true);

		adventure.process();
		adventure.process();
		adventure.process();
		
		
		Assert.assertEquals(State.PROCESS_PAYMENT, adventure.getState());
		
		//assertNotNull(adventure.getPaymentConfirmation());
		assertNotNull(adventure.getRoomConfirmation());
		assertNotNull(adventure.getActivityConfirmation());
	}

	@Test
	public void successNoHotelBooking() {
		Adventure adventure = new Adventure(this.broker, this.begin, this.begin, bclient, 300, true);

		adventure.process();
		adventure.process();
		
		Assert.assertEquals(State.PROCESS_PAYMENT, adventure.getState());
	
		//assertNotNull(adventure.getPaymentConfirmation());
		assertNotNull(adventure.getActivityConfirmation());
	}

	@After
	public void tearDown() {
		Bank.banks.clear();
		Hotel.hotels.clear();
		ActivityProvider.providers.clear();
		Broker.brokers.clear();
	}
}
