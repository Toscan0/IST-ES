package pt.ulisboa.tecnico.softeng.broker.interfaces;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.car.dataobjects.RentingData;
import pt.ulisboa.tecnico.softeng.car.domain.RentACar;
import pt.ulisboa.tecnico.softeng.car.domain.Renting;
import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;

public interface CarInterface {

	public static Renting getRenting(String reference) {
		return RentACar.getRenting(reference);
	}
	
	public static Renting getRentingData(String reference) {
		return RentACar.getRenting(reference);
	}
	/* Waiting that static rent is implemented in RentACar*/
	/*public static Renting rentVehicle(String drivingLicense, LocalDate begin, LocalDate end, String buyerNif, String buyerIban){
		return RentACar.rentVehicle(drivingLicense, begin, end, buyerNif, buyerIban);
	 }
	 */
	 
}
