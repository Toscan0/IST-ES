package pt.ulisboa.tecnico.softeng.car.services.local;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.FenixFramework;
import pt.ulisboa.tecnico.softeng.car.domain.Car;
import pt.ulisboa.tecnico.softeng.car.domain.Vehicle;
import pt.ulisboa.tecnico.softeng.car.domain.Motorcycle;
import pt.ulisboa.tecnico.softeng.car.domain.RentACar;
import pt.ulisboa.tecnico.softeng.car.domain.Renting;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.VehicleData;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.RentACarData;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.RentingData;
import pt.ist.fenixframework.Atomic.TxMode;

public class CarInterface {
	
	@Atomic(mode = TxMode.READ)
	public static List<RentACarData> getRentACars() {
		return FenixFramework.getDomainRoot().getRentACarSet().stream().map(r -> new RentACarData(r))
				.collect(Collectors.toList());
	}
	
	@Atomic(mode = TxMode.WRITE)
	public static void createRentACar(RentACarData rentACarData) {
		new RentACar(rentACarData.getName(), rentACarData.getNif(), rentACarData.getIban());
	}
	
	@Atomic(mode = TxMode.READ)
	public static RentACarData getRentACarDataByCode(String code) {
		RentACar rentACar = getRentACarByCode(code);
		
		if(rentACar != null) {
			return new RentACarData(rentACar);
		}
		
		return null;
	}
	
	private static RentACar getRentACarByCode(String code) {
		return FenixFramework.getDomainRoot().getRentACarSet().stream().filter(r -> r.getCode().equals(code)).findFirst()
			.orElse(null);	
	}
	
	
	
}