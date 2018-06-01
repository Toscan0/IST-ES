package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;

public class BClient {
	private final String IBAN;
	private final int age;
	private final String NIF;
	
	public BClient(String IBAN, String NIF, int age) {
		checkArguments(IBAN, NIF, age);
		
		this.IBAN = IBAN;
		this.NIF = NIF;
		this.age = age;
	}
	
	private void checkArguments(String IBAN, String NIF, int age){
		if(IBAN == null || NIF == null || IBAN.trim().length() == 0 || NIF.trim().length() == 0) {
			throw new BrokerException();
		}
		
		if (age < 18 || age > 100) {
			throw new BrokerException();
		}
	}
	public String getNIF() {
		return this.NIF;
	}
	
	public int getAge() {
		return this.age;
	}

	public String getIBAN() {
		return this.IBAN;
	}
	
}