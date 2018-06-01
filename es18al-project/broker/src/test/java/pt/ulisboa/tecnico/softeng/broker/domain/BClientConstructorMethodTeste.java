package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class BClientConstructorMethodTeste {
	
	private static final int AGE = 20;
	private static final int AMOUNT = 300;
	private static final String IBAN = "BK011234567";
	private Broker broker;
	private BClient bclient;
	private final LocalDate begin = new LocalDate(2016, 12, 19);
	private final LocalDate end = new LocalDate(2016, 12, 21);

	@Before
	public void setUp() {
		
	}

	@Test
	public void success() {
		this.bclient = new BClient(IBAN, "NIF", AGE);
		
		Assert.assertEquals("NIF", bclient.getNIF());
		Assert.assertEquals(this.bclient.getIBAN(), IBAN);
		Assert.assertEquals(this.bclient.getAge(), AGE);
		Assert.assertEquals(this.bclient.getNIF(), "NIF");
	}
	
	@Test
	public void successGETNIF() {
		this.bclient = new BClient(IBAN, "NIF", AGE);
		String nif = bclient.getNIF();
		
		Assert.assertEquals("NIF", nif);
		Assert.assertEquals("NIF", bclient.getNIF());
	}

	@Test(expected = BrokerException.class)
	public void nullIban() {
		this.bclient = new BClient(null, "NIF", 19);
	}
	
	@Test(expected = BrokerException.class)
	public void emptyIban() {
		this.bclient = new BClient("  ", "NIF", 19);
	}
	
	@Test(expected = BrokerException.class)
	public void nullNif() {
		this.bclient = new BClient("  ", null, 19);
	}
	
	@Test(expected = BrokerException.class)
	public void lowAge() {
		this.bclient = new BClient("  ", "NIF", 17);
	}
	
	@Test(expected = BrokerException.class)
	public void highAge() {
		this.bclient = new BClient("  ", "NIF", 101);
	}
	
	@After
	public void tearDown() {
		Broker.brokers.clear();
	}
}
