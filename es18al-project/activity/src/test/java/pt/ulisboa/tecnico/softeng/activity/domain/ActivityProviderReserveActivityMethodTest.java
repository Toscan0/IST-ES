package pt.ulisboa.tecnico.softeng.activity.domain;

import static org.junit.Assert.assertNotNull;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.activity.exception.ActivityException;

public class ActivityProviderReserveActivityMethodTest {
	private ActivityProvider provider;
	private ActivityOffer offer;
	
	private static final int MIN_AGE = 18;
	private static final int MAX_AGE = 100;
	private static final int AGE = 40;
	private static final LocalDate BEGIN = new LocalDate(2016, 12, 19);
	private static final LocalDate END = new LocalDate(2016, 12, 21);
	
	@Before
	public void setUp() {
		this.provider = new ActivityProvider("XtremX", "ExtremeAdventure");
		
	}
	
	@Test
	public void sucess() {
		Activity activity = new Activity(this.provider, "Bush Walking", 18, 80, 3);
		this.offer = new ActivityOffer(activity, BEGIN, END);
		Booking  booking = new Booking(this.provider, this.offer);
		String reference = ActivityProvider.reserveActivity(BEGIN, END, AGE);
		
		assertNotNull(reference);
	}
	
	@Test
	public void MinAge() {
		Activity activity = new Activity(this.provider, "Bush Walking", 18, 80, 3);
		this.offer = new ActivityOffer(activity, BEGIN, END);
		Booking  booking = new Booking(this.provider, this.offer);
		String reference = ActivityProvider.reserveActivity(BEGIN, END, MIN_AGE);
		
		assertNotNull(reference);
	}
	
	//ERROR MAX_AGE
	@Test
	public void MaxAge() {
		Activity activity = new Activity(this.provider, "Bush Walking", 18, 80, 3);
		this.offer = new ActivityOffer(activity, BEGIN, END);
		Booking  booking = new Booking(this.provider, this.offer);
		String reference = ActivityProvider.reserveActivity(BEGIN, END, MIN_AGE);
		
		assertNotNull(reference);
	}

	
	
	
	/* Activity error */
	@Test(expected = ActivityException.class)
	public void EndBeforeBeginBookingActivity() {
		
		Activity activity = new Activity(this.provider, "Bush Walking", 18, 80, 3);
		this.offer = new ActivityOffer(activity, BEGIN, END);
		
		Booking  booking = new Booking(this.provider, this.offer);
		
		String reference = ActivityProvider.reserveActivity(END, BEGIN, AGE);
	}
	
	@Test(expected = ActivityException.class)
	public void InvalidMinAge() {
		
		Activity activity = new Activity(this.provider, "Bush Walking", 18, 80, 3);
		this.offer = new ActivityOffer(activity, BEGIN, END);
		
		Booking  booking = new Booking(this.provider, this.offer);
		
		String reference = ActivityProvider.reserveActivity(END, BEGIN, -1);
	}
	
	/* Qual e a idade maior ?? */
	@Test(expected = ActivityException.class)
	public void InvalidMaxAge() {
		
		Activity activity = new Activity(this.provider, "Bush Walking", 18, 80, 3);
		this.offer = new ActivityOffer(activity, BEGIN, END);
		
		Booking  booking = new Booking(this.provider, this.offer);
		
		String reference = ActivityProvider.reserveActivity(END, BEGIN, -1);
	}
	
	
	@Test(expected = ActivityException.class)
	public void nullMinAge() {
		
		Activity activity = new Activity(this.provider, "Bush Walking", 18, 80, 3);
		this.offer = new ActivityOffer(activity, BEGIN, END);
		
		Booking  booking = new Booking(this.provider, this.offer);
		
		String reference = ActivityProvider.reserveActivity(null, BEGIN, AGE);
	}
	
	@Test(expected = ActivityException.class)
	public void nullMaxAge() {
		
		Activity activity = new Activity(this.provider, "Bush Walking", 18, 80, 3);
		this.offer = new ActivityOffer(activity, BEGIN, END);
		
		Booking  booking = new Booking(this.provider, this.offer);
		
		String reference = ActivityProvider.reserveActivity(END, null, AGE);
	}
	
	@Test(expected = ActivityException.class)
	public void nullBothAge() {
		
		Activity activity = new Activity(this.provider, "Bush Walking", 18, 80, 3);
		this.offer = new ActivityOffer(activity, BEGIN, END);
		
		Booking  booking = new Booking(this.provider, this.offer);
		
		String reference = ActivityProvider.reserveActivity(null, null, AGE);
	}
	
	
	/* Booking error */
	@Test(expected = ActivityException.class)
	public void notOffer() {
		
		Booking  booking = new Booking(this.provider, null);
		
		ActivityProvider.reserveActivity(BEGIN, END, AGE);
	}
	

	@After
	public void tearDown() {
		ActivityProvider.providers.clear();
	}

}
