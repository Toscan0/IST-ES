package pt.ulisboa.tecnico.softeng.car.domain;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class Motorcycle extends Motorcycle_Base {

	
	public Motorcycle(String plate, int kilometers, double price, RentACar rentacar) {
		super.init(plate, kilometers, price, rentacar);
	}
	

}
