package pt.ulisboa.tecnico.softeng.car.domain;

import java.util.HashSet;
import java.util.Set;
import org.joda.time.LocalDate;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.car.dataobjects.RentingData;


public class RentACar {
	public static Set<RentACar> rentACars = new HashSet<>();
		
	private static int counter = 0;
	private final String name;
	private final String code;
	private final Set<Vehicle> vehicles = new HashSet<>();
	
	
	public RentACar(String name) {
		checkArguments(name);
		
		this.code = Integer.toString(++RentACar.counter);
		this.name = name;
		
		RentACar.rentACars.add(this);
	}

	private void checkArguments(String name) {
		if (name == null || name.trim().equals("")) {
			throw new CarException();
		}
		
		for (RentACar rentACar : rentACars) {
			if (rentACar.getName().equals(name)) {
				throw new CarException();
			}
		}
	}
	
	public String getName() {
		return this.name;
	}

	public String getCode() {
		return this.code;
	}
	
	void addVehicle(Vehicle vehicle) {
		if (hasVehicle(vehicle.getPlate())) {
			throw new CarException();
		}

		this.vehicles.add(vehicle);
	} 
	public boolean hasVehicle(String plate) {
		for (Vehicle vehicle : this.vehicles) {
			if (vehicle.getPlate().equals(plate)) {
				return true;
			}
		}
		return false;
	}
	
	int getNumberOfVehicles() {
		return this.vehicles.size();
	}
	
	public Set<Vehicle> getAllAvailableMotorcycles(LocalDate begin, LocalDate end){
		Set<Vehicle> motorcyclesToReturn = new HashSet<>();		
		for (RentACar rentACar : rentACars) {
			for (Vehicle vehicle : rentACar.vehicles) {
				if (vehicle instanceof Motorcycle) {
					if (vehicle.isFree(begin, end)) {
						motorcyclesToReturn.add(vehicle);
					}
				}
			}
		}
		return motorcyclesToReturn;
	}
	
	public static Set<Vehicle> getAllAvailableCars(LocalDate begin, LocalDate end) {
		Set<Vehicle> carsToReturn = new HashSet<>();
		for (RentACar rentACar : rentACars) {
			for (Vehicle vehicle : rentACar.vehicles) {
				if (vehicle instanceof Car) {
					if (vehicle.isFree(begin, end)) {
						carsToReturn.add(vehicle);
					}
				}
			}
		}	
		return carsToReturn;
	}
	
	public Renting getRenting(String reference){
		if(reference == null || reference == " ") {
			throw new CarException();
		}
		for (Vehicle vehicle : this.vehicles) {
			Renting renting = vehicle.getRenting(reference);
			if (renting != null) {
				return renting;
			}
		}
		return null;
	}
	
	public static RentingData getRentingData(String reference) {
		if(reference == null || reference == " ") {
			throw new CarException();
		}
		for (RentACar rentACar : rentACars) {
			for (Vehicle vehicle : rentACar.vehicles) {
				Renting renting = vehicle.getRenting(reference); 
				if (renting != null) {
					if (vehicle instanceof Motorcycle) {
						Motorcycle moto = (Motorcycle) vehicle;
						return new RentingData(moto, renting);
					}
					Car car = (Car) vehicle;
					return new RentingData(car, renting); 
					}
				}
			}
			throw new CarException();
		}
		
}
