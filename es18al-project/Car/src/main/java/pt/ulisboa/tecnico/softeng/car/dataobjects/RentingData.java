package pt.ulisboa.tecnico.softeng.car.dataobjects;

import org.joda.time.LocalDate;
import pt.ulisboa.tecnico.softeng.car.domain.Renting;
import pt.ulisboa.tecnico.softeng.car.domain.Car;
import pt.ulisboa.tecnico.softeng.car.domain.Motorcycle;

public class RentingData {
	
	private String reference;
	private String plate;
	private String drivingLicense;
	private String rentACarCode;
	private LocalDate begin;
	private LocalDate end;
	
	public RentingData() {
	}
	
	public RentingData(Car car, Renting renting) {
		this.reference = renting.getReference();
		this.plate = car.getPlate();
		this.drivingLicense = renting.getDrivingLicense();
		this.rentACarCode = car.getRentACar().getCode();
		this.begin = renting.getBegin();
		this.end = renting.getEnd();
	}
	
	public RentingData(Motorcycle moto, Renting renting) {
		this.reference = renting.getReference();
		this.plate = moto.getPlate();
		this.drivingLicense = renting.getDrivingLicense();
		this.rentACarCode = moto.getRentACar().getCode();
		this.begin = renting.getBegin();
		this.end = renting.getEnd();
	}
	
	/*public RentingData(String reference, String plate, String drivingLicense, String rentACarCode, LocalDate begin,
			LocalDate end) {
		this.reference = reference;
		this.plate = plate;
		this.drivingLicense = drivingLicense;
		this.rentACarCode = rentACarCode;
		this.begin = begin;
		this.end = end;
	}*/
	
	public String getReference() {
		return reference;
	}
	public String getPlate() {
		return plate;
	}
	public String getDrivingLicense() {
		return drivingLicense;
	}
	public String getRentACarCode() {
		return rentACarCode;
	}
	public LocalDate getBegin() {
		return begin;
	}
	public LocalDate getEnd() {
		return end;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public void setPlate(String plate) {
		this.plate = plate;
	}
	public void setDrivingLicense(String drivingLicense) {
		this.drivingLicense = drivingLicense;
	}
	public void setRentACarCode(String rentACarCode) {
		this.rentACarCode = rentACarCode;
	}
	public void setBegin(LocalDate begin) {
		this.begin = begin;
	}
	public void setEnd(LocalDate end) {
		this.end = end;
	}
}
