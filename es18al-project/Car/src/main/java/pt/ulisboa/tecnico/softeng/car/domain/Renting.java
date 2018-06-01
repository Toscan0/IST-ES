package pt.ulisboa.tecnico.softeng.car.domain;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class Renting {
	private Vehicle vehicle;
	private static int counter = 0;
	private final String reference;
	private String drivingLicense;
	private Integer kilometers;
	private final LocalDate begin;
	private final LocalDate end;
	
	public Renting(Vehicle vehicle, LocalDate begin, LocalDate end, String drivingLicence) {
		super();
		this.vehicle = vehicle;
		this.kilometers = vehicle.getKilometers();
		this.reference = vehicle.getPlate() + Integer.toString(++Renting.counter);
		this.begin = begin;
		this.end = end;
		this.drivingLicense = drivingLicence;
	}

	public Integer getKilometers() {
		return kilometers;
	}
	public String getReference() {
		return reference;
	}
	public LocalDate getBegin() {
		return begin;
	}
	public LocalDate getEnd() {
		return end;
	}
	public String getDrivingLicense() {
		return drivingLicense;
	}

	public void setDrivingLicense(String drivingLicense) {
		this.drivingLicense = drivingLicense;
	}
	
	
	public void checkout(Integer kilometers) {
		Integer result;
		if(kilometers == null || kilometers < 0 ) {
			throw new CarException();
		}
		result =  kilometers + this.getKilometers();
		this.vehicle.setKilometers(result);
	}
	
	public Vehicle getVehicle() {
		return vehicle;
	}

	public static int getCounter() {
		return counter;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public static void setCounter(int counter) {
		Renting.counter = counter;
	}

	public void setKilometers(Integer kilometers) {
		this.kilometers = kilometers;
	}

	boolean conflict(LocalDate begin, LocalDate end) {
		if (begin == null || end == null){
			throw new CarException();
		}
		else if (end.equals(begin)) {
			return true;
		}

		else if (end.isBefore(begin)) {
			throw new CarException();
		}

		else if (begin.equals(this.begin)){
			return true;
		}
		
		else if(begin.isAfter(this.begin) && begin.isBefore(this.end)){
			return true;
		}

		else if (end.equals(this.end)){
			return true;
		}
		
		else if(end.isBefore(this.end) && end.isAfter(this.begin)){
			return true;
		}

		else if ((begin.isBefore(this.begin) && end.isAfter(this.end))) {
			return true;
		}

		else{
			return false;
		}
	}
	

	
}