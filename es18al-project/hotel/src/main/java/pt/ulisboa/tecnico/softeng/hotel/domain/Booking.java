package pt.ulisboa.tecnico.softeng.hotel.domain;


import org.joda.time.Days;
import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class Booking {
	private static int counter = 0;
	
	private static final String type = "HOTEL";
	private final Hotel hotelProvider;
	private final String reference;
	private final String providerNif;
	private final LocalDate arrival;
	private final LocalDate departure;
	private final LocalDate date;
	private final String nif;
	private final String iban;
	private final double amount;
	private String paymentReference;
	private String invoiceReference;
	private boolean cancelledInvoice = false;
	private String cancellation;
	private LocalDate cancellationDate;
	private String cancelledPaymentReference = null;

	Booking(Hotel hotel, LocalDate arrival, LocalDate departure, String buyerNif, String buyerIban, double priceRoom) {
		checkArguments(hotel, arrival, departure, buyerNif, buyerIban, priceRoom);
		
		this.hotelProvider = hotel;
		this.providerNif = hotel.getNif();
		this.reference = hotel.getCode() + Integer.toString(++Booking.counter);
		this.arrival = arrival;
		this.departure = departure;
		this.nif = buyerNif;
		this.iban = buyerIban;
		long daysBetween = Days.daysBetween(arrival, departure).getDays();
		this.amount = daysBetween * priceRoom;
		this.date = arrival;
	}

	private void checkArguments(Hotel hotel, LocalDate arrival, LocalDate departure, String buyerNIF, String buyerIban, double price) {
		if (hotel == null || arrival == null || departure == null || buyerNIF == null || buyerNIF.trim().length() == 0 || buyerIban == null
				|| buyerIban.trim().length() == 0 || price <= 0) {
			throw new HotelException();
		}

		if (departure.isBefore(arrival)) {
			throw new HotelException();
		}
	}

	public String getNif() {
		return nif;
	}

	public String getIban() {
		return iban;
	}

	public String getReference() {
		return this.reference;
	}

	public String getCancellation() {
		return this.cancellation;
	}

	public LocalDate getArrival() {
		return this.arrival;
	}

	public LocalDate getDeparture() {
		return this.departure;
	}

	public LocalDate getCancellationDate() {
		return this.cancellationDate;
	}

	boolean conflict(LocalDate arrival, LocalDate departure) {
		if (isCancelled()) {
			return false;
		}

		if (arrival.equals(departure)) {
			return true;
		}

		if (departure.isBefore(arrival)) {
			throw new HotelException();
		}

		if ((arrival.equals(this.arrival) || arrival.isAfter(this.arrival)) && arrival.isBefore(this.departure)) {
			return true;
		}

		if ((departure.equals(this.departure) || departure.isBefore(this.departure))
				&& departure.isAfter(this.arrival)) {
			return true;
		}

		if ((arrival.isBefore(this.arrival) && departure.isAfter(this.departure))) {
			return true;
		}

		return false;
	}

	public String cancel() {
		this.cancellation = this.reference + "CANCEL";
		this.cancellationDate = new LocalDate();
		this.hotelProvider.getProcessor().submitBooking(this);
		return this.cancellation;
	}

	public boolean isCancelled() {
		return this.cancellation != null;
	}

	public String getPaymentReference() {
		return this.paymentReference;
	}


	public double getAmount() {
		return this.amount;
	}

	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	public String getProviderNif() {
		return this.providerNif;
	}


	public void setInvoiceReference(String invoiceReference) {
		this.invoiceReference = invoiceReference;
	}

	public Object getCancelledPaymentReference() {
		return this.cancelledPaymentReference;
	}

	public String getInvoiceReference() {
		return this.invoiceReference;
	}

	public void setCancelledInvoice(boolean b) {
		this.cancelledInvoice = b;
	}
	
	public boolean isCancelledInvoice() {
		return this.cancelledInvoice;
	}

	public void setCancelledPaymentReference(String cancelPayment) {
		this.cancelledPaymentReference = cancelPayment;
	}

	public LocalDate getDate() {
		return this.arrival;
	}

	public String getType() {
		return this.type;
	}

}
