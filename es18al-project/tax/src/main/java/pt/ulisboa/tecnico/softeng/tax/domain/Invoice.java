package pt.ulisboa.tecnico.softeng.tax.domain;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class Invoice {
	private String reference;
	private float value;
	private float iVA;
	private LocalDate date;
	
	private TaxPayer buyer;
	private TaxPayer seller;
	private ItemType itemType;
	
	
	public Invoice( float value, LocalDate date, ItemType itemType, TaxPayer seller, TaxPayer buyer ) {
		if(checkArguments(date, seller, buyer, itemType)){
			this.value = value;
			this.iVA = value * itemType.getTax() / 100;
			this.date = date;
			this.seller = seller;
			this.buyer = buyer;
			this.reference = Integer.toString(TaxPayer.invoices.size() + 1);
			TaxPayer.invoices.put(this.reference, this);
			itemType.addInvoice(this);
		}
		else
			throw new TaxException();
	}

	
	public boolean checkArguments(LocalDate date, TaxPayer seller, TaxPayer buyer, ItemType itemType){
		
		if(date.isAfter(new LocalDate(1969,12,31)) && seller != null && buyer != null && itemType != null)
			return true;
		return false;
	}
	
	public TaxPayer getBuyer() {
		return buyer;
	}

	public void setBuyer(TaxPayer buyer) {
		this.buyer = buyer;
	}

	public TaxPayer getSeller() {
		return seller;
	}

	public void setSeller(TaxPayer seller) {
		this.seller = seller;
	}

	public ItemType getItem_type() {
		return itemType;
	}

	public void setItem_type(ItemType item_type) {
		this.itemType = item_type;
	}

	public String getReference() {
		return reference;
	}
	
	public float getValue() {
		return value;
	}
	
	public float getiVA() {
		return iVA;
	}
	
	public void setiVA(float iVA) {
		this.iVA = iVA;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	public void setValue(float value) {
		this.value = value;
	}
}
