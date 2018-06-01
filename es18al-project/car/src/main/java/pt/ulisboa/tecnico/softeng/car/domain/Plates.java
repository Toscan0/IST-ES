package pt.ulisboa.tecnico.softeng.car.domain;


public class Plates extends Plates_Base{
	
	public Plates(String plate, Vehicle vehicle) {
		setVehicle(vehicle);
		setPlate(plate);
	}
	
	public void delete() {
		setVehicle(null);
		deleteDomainObject();
	}
	
}
