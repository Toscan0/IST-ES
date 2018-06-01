package pt.ulisboa.tecnico.softeng.car.domain;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public class Car extends Car_Base {

	
	public Car(String plate, int kilometers, double price, RentACar rentacar) {
		super.init(plate, kilometers, price, rentacar);

	}
	
}
