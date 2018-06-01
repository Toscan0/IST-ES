package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class BrokerConstructorMethodTest {
	private final LocalDate begin = new LocalDate(2016, 12, 19);
	private final LocalDate end = new LocalDate(2016, 12, 21);
	
	@Test
	public void success() {
		Broker broker = new Broker("BR01", "WeExplore", "NIFSELLER", "NIFBUYER");

		Assert.assertEquals("BR01", broker.getCode());
		Assert.assertEquals("WeExplore", broker.getName());
		Assert.assertEquals(0, broker.getNumberOfAdventures());
		Assert.assertTrue(Broker.brokers.contains(broker));
	}

	@Test
	public void nullCode() {
		try {
			new Broker(null, "WeExplore", "NIFSELLER", "NIFBUYER");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void emptyCode() {
		try {
			new Broker("", "WeExplore", "NIFSELLER", "NIFBUYER");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void blankCode() {
		try {
			new Broker("  ", "WeExplore", "NIFSELLER", "NIFBUYER");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void uniqueCode() {
		Broker broker = new Broker("BR01", "WeExplore", "NIFSELLER", "NIFBUYER");

		try {
			new Broker("BR01", "WeExploreX", "NIFSELLER", "NIFBUYER");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(1, Broker.brokers.size());
			Assert.assertTrue(Broker.brokers.contains(broker));
		}
	}

	@Test
	public void nullName() {
		try {
			new Broker("BR01", null, "NIFSELLER", "NIFBUYER");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void emptyName() {
		try {
			new Broker("BR01", "", "NIFSELLER", "NIFBUYER");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void blankName() {
		try {
			new Broker("BR01", "    ", "NIFSELLER", "NIFBUYER");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void nullNifSeller() {
		try {
			new Broker("BR01", "WeExploreX", null, "NIFBUYER");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test(expected = BrokerException.class)
	public void sameNIFSeller() {
		new Broker("BR01", "WeExploreX", "NIFSELLER", "NIFBUYER");
		new Broker("BR02", "WeExploreX2", "NIFSELLER", "NIFBUYER2");
	}
	
	@Test(expected = BrokerException.class)
	public void sameNIFBUYER() {
		new Broker("BR01", "WeExploreX", "NIFSELLER", "NIFBUYER");
		new Broker("BR02", "WeExploreX2", "NIFSELLER2", "NIFBUYER");
	}
	@Test(expected = BrokerException.class)
	public void sameNIFSelerBuyer() {
		new Broker("BR01", "WeExploreX", "NIFSELLER2", "NIFBUYER2");
		new Broker("BR02", "WeExploreX2", "NIFSELLER2", "NIFBUYER2");
	}
	
	@Test
	public void sucessDiferenteNifs() {
		new Broker("BR01", "WeExploreX", "NIFSELLER", "NIFBUYER");
		new Broker("BR02", "WeExploreX2", "NIFSELLER2", "NIFBUYER2");
	}
	
	@Test
	public void sucessBoolBoking() {
		Broker broker = new Broker("BR01", "WeExploreX", "NIFSELLER", "NIFBUYER");
		
		broker.bulkBooking( 20, begin, end, broker.getNifBuyer(), "buyerIban");
	}
	
	@Test
	public void blankNifSeller() {
		try {
			new Broker("BR01", "WeExploreX", "", "NIFBUYER");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}
	
	@Test
	public void nullNifBuyer() {
		try {
			new Broker("BR01", "WeExploreX", "NIFSELLER", null);
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@Test
	public void blankNifBuyer() {
		try {
			new Broker("BR01", "WeExploreX", "NIFSELLER", "");
			Assert.fail();
		} catch (BrokerException be) {
			Assert.assertEquals(0, Broker.brokers.size());
		}
	}

	@After
	public void tearDown() {
		Broker.brokers.clear();
	}

}
