package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class AdventureConstructorMethodTest {
	private static final int AGE = 20;
	private static final int AMOUNT = 300;
	private static final String IBAN = "BK011234567";
	private Broker broker;
	private BClient bclient;
	private final LocalDate begin = new LocalDate(2016, 12, 19);
	private final LocalDate end = new LocalDate(2016, 12, 21);

	@Before
	public void setUp() {
		this.bclient = new BClient(IBAN, "NIF", 19);
		this.broker = new Broker("BR01", "eXtremeADVENTURE", "NIFSELLER", "NIFBUYER");
	}

	
	
	@Test
	public void success() {
		Adventure adventure = new Adventure(this.broker, this.begin, this.end, bclient, AMOUNT, true);

		Assert.assertEquals(this.broker, adventure.getBroker());
		Assert.assertEquals(this.begin, adventure.getBegin());
		Assert.assertEquals(this.end, adventure.getEnd());
		Assert.assertEquals(19, adventure.getBClient().getAge());
		Assert.assertEquals(IBAN, adventure.getBClient().getIBAN());
		Assert.assertEquals(300, adventure.getAmount());
		Assert.assertTrue(this.broker.hasAdventure(adventure));

		Assert.assertNull(adventure.getPaymentConfirmation());
		Assert.assertNull(adventure.getActivityConfirmation());
		Assert.assertNull(adventure.getRoomConfirmation());
		Assert.assertNull(adventure.getVehicleCancellation());
		Assert.assertNull(adventure.getVehicleConfirmation());
		
		Assert.assertNotNull(adventure.getID());


	}
	
	@Test
	public void successtwo() {
		Adventure adventure = new Adventure(this.broker, this.begin, this.end, bclient, AMOUNT, true);
		
		adventure.setType("TYPE");
		adventure.setInvoiceReference("REF");
		//adventure.setVehicleCancellation("ddd");
		adventure.setRentVehicle(false);
		adventure.setCancelledInvoice(false);
		
		Assert.assertEquals("TYPE", adventure.getType());
		Assert.assertEquals("REF", adventure.getInvoiceReference());

	}
	
	
	@Test
	public void successCancelRoom() {
		Adventure adventure = new Adventure(this.broker, this.begin, this.end, bclient, AMOUNT, true);
		
		adventure.cancelRoom();
	}
	
	@Test
	public void successCancelActivity() {
		Adventure adventure = new Adventure(this.broker, this.begin, this.end, bclient, AMOUNT, true);
		
		adventure.cancelActivity();
	}
	
	@Test
	public void successCancelPayment() {
		Adventure adventure = new Adventure(this.broker, this.begin, this.end, bclient, AMOUNT, true);
		
		adventure.cancelPayment();
	}

	@Test
	public void successIsCancelledInvoice() {
		Adventure adventure = new Adventure(this.broker, this.begin, this.end, bclient, AMOUNT, true);
		
		adventure.isCancelledInvoice();
	}
	
	@Test(expected = BrokerException.class)
	public void nullBroker() {
		new Adventure(null, this.begin, this.end, bclient, AMOUNT, true);
	}

	@Test(expected = BrokerException.class)
	public void nullBegin() {
		new Adventure(this.broker, null, this.end, bclient, AMOUNT, true);
	}

	@Test(expected = BrokerException.class)
	public void nullEnd() {
		new Adventure(this.broker, this.begin, null, bclient, AMOUNT, true);
	}

	@Test
	public void successEqual18() {
		bclient = new BClient(IBAN, "NIF", 18);
		Adventure adventure = new Adventure(this.broker, this.begin, this.end, bclient, AMOUNT, true);

		Assert.assertEquals(this.broker, adventure.getBroker());
		Assert.assertEquals(this.begin, adventure.getBegin());
		Assert.assertEquals(this.end, adventure.getEnd());
		Assert.assertEquals(18, adventure.getBClient().getAge());
		Assert.assertEquals(IBAN, adventure.getBClient().getIBAN());
		Assert.assertEquals(300, adventure.getAmount());
		Assert.assertTrue(this.broker.hasAdventure(adventure));

		Assert.assertNull(adventure.getPaymentConfirmation());
		Assert.assertNull(adventure.getActivityConfirmation());
		Assert.assertNull(adventure.getRoomConfirmation());
	}

	@Test(expected = BrokerException.class)
	public void lessThan18Age() {
		bclient = new BClient(IBAN, "NIF", 17);
		new Adventure(this.broker, this.begin, this.end, bclient, AMOUNT, true);
	}

	@Test
	public void successEqual100() {
		bclient = new BClient(IBAN, "NIF", 100);
		Adventure adventure = new Adventure(this.broker, this.begin, this.end, bclient, AMOUNT, true);

		Assert.assertEquals(this.broker, adventure.getBroker());
		Assert.assertEquals(this.begin, adventure.getBegin());
		Assert.assertEquals(this.end, adventure.getEnd());
		Assert.assertEquals(100, adventure.getBClient().getAge());
		Assert.assertEquals(IBAN, adventure.getBClient().getIBAN());
		Assert.assertEquals(300, adventure.getAmount());
		Assert.assertTrue(this.broker.hasAdventure(adventure));

		Assert.assertNull(adventure.getPaymentConfirmation());
		Assert.assertNull(adventure.getActivityConfirmation());
		Assert.assertNull(adventure.getRoomConfirmation());
	}

	@Test(expected = BrokerException.class)
	public void over100() {
		bclient = new BClient(IBAN, "NIF", 101);
		new Adventure(this.broker, this.begin, this.end, bclient, AMOUNT, true);
	}

	@Test(expected = BrokerException.class)
	public void nullIBAN() {
		bclient = new BClient(null, "NIF", 19);
		new Adventure(this.broker, this.begin, this.end, bclient, AMOUNT, true);
	}

	@Test(expected = BrokerException.class)
	public void emptyIBAN() {
		bclient = new BClient("      ", "NIF", 19);
		new Adventure(this.broker, this.begin, this.end, bclient, AMOUNT, true);
	}

	@Test(expected = BrokerException.class)
	public void negativeAmount() {
		bclient = new BClient(IBAN, "NIF", 19);
		new Adventure(this.broker, this.begin, this.end, bclient, -100, true);
	}

	@Test
	public void success1Amount() {
		bclient = new BClient(IBAN, "NIF", 20);
		Adventure adventure = new Adventure(this.broker, this.begin, this.end, bclient, 1, true);

		Assert.assertEquals(this.broker, adventure.getBroker());
		Assert.assertEquals(this.begin, adventure.getBegin());
		Assert.assertEquals(this.end, adventure.getEnd());
		Assert.assertEquals(20, adventure.getBClient().getAge());
		Assert.assertEquals(IBAN, adventure.getBClient().getIBAN());
		Assert.assertEquals(1, adventure.getAmount());
		Assert.assertTrue(this.broker.hasAdventure(adventure));

		Assert.assertNull(adventure.getPaymentConfirmation());
		Assert.assertNull(adventure.getActivityConfirmation());
		Assert.assertNull(adventure.getRoomConfirmation());
	}

	@Test(expected = BrokerException.class)
	public void zeroAmount() {
		bclient = new BClient(IBAN, "NIF", 19);
		new Adventure(this.broker, this.begin, this.end, bclient, 0, true);
	}

	@Test
	public void successEqualDates() {
		bclient = new BClient(IBAN, "NIF", 20);
		Adventure adventure = new Adventure(this.broker, this.begin, this.begin, bclient, AMOUNT, true);

		Assert.assertEquals(this.broker, adventure.getBroker());
		Assert.assertEquals(this.begin, adventure.getBegin());
		Assert.assertEquals(this.begin, adventure.getEnd());
		Assert.assertEquals(20, adventure.getBClient().getAge());
		Assert.assertEquals(IBAN, adventure.getBClient().getIBAN());
		Assert.assertEquals(300, adventure.getAmount());
		Assert.assertTrue(this.broker.hasAdventure(adventure));

		Assert.assertNull(adventure.getPaymentConfirmation());
		Assert.assertNull(adventure.getActivityConfirmation());
		Assert.assertNull(adventure.getRoomConfirmation());
	}

	@Test(expected = BrokerException.class)
	public void inconsistentDates() {
		bclient = new BClient(IBAN, "NIF", 19);
		new Adventure(this.broker, this.begin, this.begin.minusDays(1), bclient, AMOUNT, true);
	}

	
	@After
	public void tearDown() {
		Broker.brokers.clear();
	}

}