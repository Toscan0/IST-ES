package pt.ulisboa.tecnico.softeng.car.domain;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;

public abstract class Vehicle extends Vehicle_Base{
	private static Logger logger = LoggerFactory.getLogger(Vehicle.class);

	private static String plateFormat = "..-..-..";
	

	public final Set<Renting> rentings = new HashSet<>();

	public void init(String plate, int kilometers, double price, RentACar rentACar) {
		logger.debug("Vehicle plate: {}", plate);
		checkArguments(plate, kilometers, rentACar);
		
		Plates pl = new Plates(plate.toUpperCase(), this);
		
		setPlate(plate.toUpperCase());
		setKilometers(kilometers);
		setPrice(price);
		setRentacar(rentACar);
		addPlates(pl);

		rentACar.addVehicle(this);
	}
	public Vehicle() {}

	private void checkArguments(String plate, int kilometers, RentACar rentACar) {
		//System.out.println(plate);
		if (kilometers < 0) {
			throw new CarException();
		} else if (rentACar == null) {
			throw new CarException();
		} else if (plate == null || !plate.matches(plateFormat)) {
			throw new CarException();
		} else {
			setRentacar(rentACar);
			for(Vehicle v :getRentacar().getVehicleSet()) {
				for (Plates p : v.getPlatesSet()) {
					if (p.getPlate().equals(plate.toUpperCase())) {
						throw new CarException();
					}
				}
			}
		}
	}


	public void addKilometers(int kilometers) {
		if (kilometers < 0) {
			throw new CarException();
		}
		setKilometers(getKilometers() + kilometers);
	}


	public RentACar getRentACar() {
		return getRentacar();
	}

	public boolean isFree(LocalDate begin, LocalDate end) {
		if (begin == null || end == null) {
			throw new CarException();
		}
		for (Renting renting : getRentingSet()) {
			if (renting.conflict(begin, end)) {
				return false;
			}
		}
		return true;
	}

	
	
	public Set<Renting> getRentings(){
		return getRentingSet();
	}
	
	/**
	 * Lookup for a <code>Renting</code> with the given reference.
	 *
	 * @param reference
	 * @return Renting with the given reference
	 */
	public Renting getRenting(String reference) {
		return getRentingSet()
				.stream()
				.filter(renting -> renting.getReference().equals(reference)
                        || renting.isCancelled() && renting.getCancellationReference().equals(reference))
				.findFirst()
				.orElse(null);
	}

	/**
	 * @param drivingLicense
	 * @param begin
	 * @param end
	 * @return
	 */
	public Renting rent(String drivingLicense, LocalDate begin, LocalDate end, String buyerNIF, String buyerIBAN) {
		if (!isFree(begin, end)) {
			throw new CarException();
		}

		Renting renting = new Renting(drivingLicense, begin, end, this, buyerNIF, buyerIBAN);
		this.addRenting(renting);

        this.getRentACar().getProcessor().submitRenting(renting);


        return renting;
	}
	
	public void delete() {
		setRentacar(null);
		for (Plates plate : getPlatesSet()) {
			plate.delete();
		}
		for (Renting renting : getRentingSet()) {
			renting.delete();
		}
		deleteDomainObject();
	}
}
