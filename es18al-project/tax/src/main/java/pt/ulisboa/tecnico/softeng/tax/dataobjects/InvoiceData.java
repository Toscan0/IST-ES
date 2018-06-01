package pt.ulisboa.tecnico.softeng.tax.dataobjects;

import org.joda.time.LocalDate;

import pt.ulisboa.tecnico.softeng.tax.domain.IRS;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class InvoiceData {
	
	private String sellerNIF;
	private String buyerNIF;
	private String itemType;
	private float value;
	private LocalDate date;

	public InvoiceData(String sellerNIF, String	buyerNIF, String itemType, float value, LocalDate date){
		if(checkArguments(sellerNIF, buyerNIF, itemType, value, date)){
			this.sellerNIF = sellerNIF;
			this. buyerNIF = buyerNIF;
			this.itemType = itemType;
			this.value = value;
			this.date = date;
		}else{
			throw new TaxException();
		}
	}
	
	public boolean checkArguments(String sellerNIF, String	buyerNIF, String itemType, float value, LocalDate date){
		if(sellerNIF != null && sellerNIF.length() == 9 && buyerNIF != null && buyerNIF.length() == 9 &&
		   IRS.getItemTypeByName(itemType) != null && date.isAfter(new LocalDate(1969,12,31)))
			return true;
		else 
			return false;
	}
	
	public String getSellerNIF() {
		return sellerNIF;
	}

	public void setSellerNIF(String sellerNIF) {
		this.sellerNIF = sellerNIF;
	}

	public String getBuyerNIF() {
		return buyerNIF;
	}

	public void setBuyerNIF(String buyerNIF) {
		this.buyerNIF = buyerNIF;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}	
	
	
}
