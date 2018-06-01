package pt.ulisboa.tecnico.softeng.broker.domain;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class Broker {
	private static Logger logger = LoggerFactory.getLogger(Broker.class);

	public static Set<Broker> brokers = new HashSet<>();

	private final String code;
	private final String name;

	private final String nifSeller;
	private final String nifBuyer;
	private final Set<Adventure> adventures = new HashSet<>();
	private final Set<BulkRoomBooking> bulkBookings = new HashSet<>();

	/*public Broker(String code, String name) {
		checkCode(code);
		this.code = code;

		checkName(name);
		this.name = name;

		Broker.brokers.add(this);
	}*/

	public Broker(String code, String name, String nifSeller, String nifBuyer) {
		checkCode(code);
		this.code = code;
		
		checkName(name);
		this.name = name;
		
		checkNifSeller(nifSeller);
		this.nifSeller = nifSeller;
		
		checkNifBuyer(nifBuyer);
		this.nifBuyer = nifBuyer;
		
		Broker.brokers.add(this);
	}

	private void checkCode(String code) {
		if (code == null || code.trim().length() == 0) {
			throw new BrokerException();
		}

		for (Broker broker : Broker.brokers) {
			if (broker.getCode().equals(code)) {
				throw new BrokerException();
			}
		}
	}
	

	private void checkNifSeller(String nif) {
		if (nif == null || nif.trim().length() == 0) {
			throw new BrokerException();
		}

		for (Broker broker : Broker.brokers) {
			if (broker.getNifSeller().equals(nif)) {
				throw new BrokerException();
			}
		}
	}
	
	private void checkNifBuyer(String nif) {
		if (nif == null || nif.trim().length() == 0) {
			throw new BrokerException();
		}

		for (Broker broker : Broker.brokers) {
			if (broker.getNifBuyer().equals(nif)) {
				throw new BrokerException();
			}
		}
	}

	private void checkName(String name) {
		if (name == null || name.trim().length() == 0) {
			throw new BrokerException();
		}
	}

	String getCode() {
		return this.code;
	}

	String getName() {
		return this.name;
	}

	public String getNifSeller() {
		return nifSeller;
	}

	public String getNifBuyer() {
		return nifBuyer;
	}

	public int getNumberOfAdventures() {
		return this.adventures.size();
	}

	public void addAdventure(Adventure adventure) {
		this.adventures.add(adventure);
	}

	public boolean hasAdventure(Adventure adventure) {
		return this.adventures.contains(adventure);
	}

	public void bulkBooking(int number, LocalDate arrival, LocalDate departure, String buyerNif, String buyerIban) {
		BulkRoomBooking bulkBooking = new BulkRoomBooking(number, arrival, departure, buyerNif, buyerIban);
		this.bulkBookings.add(bulkBooking);
		bulkBooking.processBooking();
	}

}
