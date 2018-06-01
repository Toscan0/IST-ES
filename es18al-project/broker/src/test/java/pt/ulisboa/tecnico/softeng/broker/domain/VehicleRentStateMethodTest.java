package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Delegate;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import pt.ulisboa.tecnico.softeng.broker.domain.Adventure.State;
import pt.ulisboa.tecnico.softeng.broker.exception.RemoteAccessException;
import pt.ulisboa.tecnico.softeng.broker.interfaces.CarInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.HotelInterface;
import pt.ulisboa.tecnico.softeng.car.domain.Renting;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;
import pt.ulisboa.tecnico.softeng.car.exception.CarException;


@RunWith(JMockit.class)
public class VehicleRentStateMethodTest {
	private static final String IBAN = "BK01987654321";
	private static final int AMOUNT = 300;
	private static final int AGE = 20;
	private static final String VEHICLE_CONFIRMATION = "VehicleConfirmation";
	private static final LocalDate arrival = new LocalDate(2016, 12, 19);
	private static final LocalDate departure = new LocalDate(2016, 12, 21);
	private Adventure adventure;
	private static final String NIF = "NIF";
	private BClient bclient;
	private Renting rent;
	
	@Injectable
	private Broker broker;
	
	@Before
	public void setUp() {
		bclient = new BClient(IBAN, "NIF", AGE);
		this.adventure = new Adventure(this.broker, arrival, arrival, bclient, AMOUNT, true);
		this.adventure.setState(State.RENT_VEHICLE);
	}
	
	/*@Test
	public void successRentVehicle(@Mocked final CarInterface carInterface) {
		new Expectations() {
			{
				CarInterface.getRenting("Reference");
				this.result = VEHICLE_CONFIRMATION;
			}
		};

		this.adventure.process();
		Assert.assertEquals(State.PROCESS_PAYMENT, this.adventure.getState());
		
	}*/
	
	@Test
	public void CarException(@Mocked final CarInterface carInterface) {
		new Expectations() {
			{
				CarInterface.getRenting("Reference");
				this.result = new CarException();
			}
		};

		this.adventure.process();

		Assert.assertEquals(State.UNDO, this.adventure.getState());
	}
	
	@Test
	public void singleRemoteAccessException(@Mocked final CarInterface carInterface) {
		new Expectations() {
			{
				CarInterface.getRenting("Reference");
				this.result = new RemoteAccessException();
			}
		};

		this.adventure.process();

		Assert.assertEquals(State.RENT_VEHICLE, this.adventure.getState());
	}
	
	@Test
	public void maxRemoteAccessException(@Mocked final CarInterface carInterface) {
		new Expectations() {
			{
				CarInterface.getRenting("Reference");
				this.result = new RemoteAccessException();
				this.times = VehicleRentState.MAX_REMOTE_ERRORS;
			}
		};

		for (int i = 0; i < VehicleRentState.MAX_REMOTE_ERRORS; i++) {
			this.adventure.process();
		}

		Assert.assertEquals(State.UNDO, this.adventure.getState());
	}
	
	/*@Test
	public void maxMinusOneRemoteAccessException(@Mocked final CarInterface carInterface) {
		new Expectations() {
			{
				CarInterface.getRenting("Reference");
				this.result = new RemoteAccessException();
				this.times = VehicleRentState.MAX_REMOTE_ERRORS;
			}
		};

		for (int i = 0; i < VehicleRentState.MAX_REMOTE_ERRORS - 1; i++) {
			this.adventure.process();
		}

		Assert.assertEquals(State.RENT_VEHICLE, this.adventure.getState());
	}*/
	
	/*@Test
	public void fiveRemoteAccessExceptionOneSuccess(@Mocked final CarInterface carInterface) {
		new Expectations() {
			{
				CarInterface.getRenting("Reference");
				this.result = new Delegate() {
					int i = 0;

					public String delegate() {
						if (this.i < 5) {
							this.i++;
							throw new RemoteAccessException();
						} else {
							return VEHICLE_CONFIRMATION;
						}
					}
				};
				this.times = 6;
			}
		};

		this.adventure.process();
		this.adventure.process();
		this.adventure.process();
		this.adventure.process();
		this.adventure.process();
		this.adventure.process();

		Assert.assertEquals(State.CANCELLED, this.adventure.getState());
		
	}*/
	
	@Test
	public void oneRemoteAccessExceptionOneActivityException(@Mocked final CarInterface carInterface) {
		new Expectations() {
			{
				CarInterface.getRenting("Reference");
				this.result = new Delegate() {
					int i = 0;

					public String delegate() {
						if (this.i < 1) {
							this.i++;
							throw new RemoteAccessException();
						} else {
							throw new CarException();
						}
					}
				};
				this.times = 2;
			}
		};

		this.adventure.process();
		this.adventure.process();

		Assert.assertEquals(State.UNDO, this.adventure.getState());
	}


}
