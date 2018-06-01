package pt.ulisboa.tecnico.softeng.car.domain;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.car.domain.Renting;


abstract class Vehicle {
	
	static final int CODE_SIZE = 8;
	
	private final RentACar rentACar;
	
	private final String plate;
	private Integer kilometers;
	private final Set<Renting> rentings = new HashSet<>();
	

	public Vehicle(String plate, int kilometers, RentACar rentACar) {
		checkArguments(plate, kilometers);
		
		this.plate = plate;
		this.kilometers = kilometers;
		this.rentACar = rentACar;
		this.rentACar.addVehicle(this);
	}

	public RentACar getRentACar() {
		return rentACar;
	}

	
	public String getPlate() {
		return plate;
	}
	
	public int getKilometers() {
		return kilometers;
	}
	
	public void setKilometers(Integer kilometers) {
		this.kilometers = kilometers;
	}
	
	int getNumberOfRentings() {
		return this.rentings.size();
	}
	
	boolean isFree(LocalDate begin, LocalDate end) {
		if (begin == null || end == null ){
			throw new CarException();
		}
		if (end.isBefore(begin)) {
			throw new CarException();
		}
		for (Renting renting : this.rentings) {
			if (renting.conflict(begin, end)) {
				return false;
			}
		}

		return true;
	}
	
	private void checkArguments(String plate, int kilometers) {
		if (plate == null || plate.trim().length() == 0 || kilometers  == 0) {
			throw new CarException();
		}
		
		String char3 = plate.charAt(2) + "";
		String char6 = plate.charAt(5) + "";
		if(plate.length() != 8 || !char3.equals("-") || !char6.equals("-")) {
			throw new CarException();
		}

		if (plate.length() != Vehicle.CODE_SIZE) {
			throw new CarException();
		}

	}

	public Renting rent(String drivingLicense, LocalDate begin, LocalDate end) {
		if (drivingLicense == null || drivingLicense == "" || begin == null || end == null ) {
			throw new CarException();
		}
		
		int size = drivingLicense.length();
		char zero = drivingLicense.charAt(0);
		char last = drivingLicense.charAt(size-1);
		if (Character.isDigit(zero) || !Character.isDigit(last)) {
			throw new CarException();
		}

		if (!isFree(begin, end)) {
			throw new CarException();
		}

		Renting renting = new Renting(this,  begin, end, drivingLicense );
		this.rentings.add(renting);

		return renting;
	}
	
	public Renting getRenting(String reference) {
		for (Renting renting : this.rentings) {
			if (renting.getReference().equals(reference)) {
				return renting;
			}
		}
		return null;
	}

	//public abstract State getType();
}
