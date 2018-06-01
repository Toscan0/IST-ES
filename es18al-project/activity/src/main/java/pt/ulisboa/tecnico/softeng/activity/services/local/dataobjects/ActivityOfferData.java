package pt.ulisboa.tecnico.softeng.activity.services.local.dataobjects;

import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import pt.ulisboa.tecnico.softeng.activity.domain.ActivityOffer;

public class ActivityOfferData {
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate begin;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate end;
	private Integer capacity;
	private double amount;
	private List<ActivityReservationData> reservations;
	/*private String code; codigo alternativo para Feix.framework*/
	private String reference;
	private String providerName;
	private String providerCode;
	private String activityCode;

	public ActivityOfferData() {
	}

	public ActivityOfferData(ActivityOffer offer) {
		this.begin = offer.getBegin();
		this.end = offer.getEnd();
		this.capacity = offer.getCapacity();
		this.amount = offer.getAmount();
		this.reservations = offer.getBookingSet().stream().map(b -> new ActivityReservationData(b))
				.collect(Collectors.toList());
		/*this.code = offer.getExternalId(); codigo alternativo usando a solucao do rpof*/
		this.reference = offer.getReference();
		this.providerName = offer.getActivity().getActivityProvider().getName();
		this.providerCode = offer.getActivity().getActivityProvider().getCode();
		this.activityCode = offer.getActivity().getCode();
		
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public LocalDate getBegin() {
		return this.begin;
	}

	public void setBegin(LocalDate begin) {
		this.begin = begin;
	}

	public LocalDate getEnd() {
		return this.end;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}

	public Integer getCapacity() {
		return this.capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public List<ActivityReservationData> getReservations() {
		return this.reservations;
	}

	public void setReservations(List<ActivityReservationData> reservations) {
		this.reservations = reservations;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
