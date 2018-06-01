package pt.ulisboa.tecnico.softeng.tax.domain;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

import java.util.Set;
import java.util.HashSet;

public class ItemType {
	
	private int tax;
	private Set<Invoice> invoices = new HashSet<>();

	public ItemType(String itemType, int tax){
		if(checkArguments (itemType, tax)){
			this.tax = tax;
			IRS.itemtypes.put(itemType, this);
		}
		else{
			throw new TaxException();
		}
	}

	public boolean checkArguments(String itemType, int tax){
		if(itemType != null && itemType != "" && itemType != " " && tax > -1 && IRS.getItemTypeByName(itemType) == null)
			return true;
		return false;
	}
	
	public void addInvoice(Invoice inv) {
		this.invoices.add(inv);
	}
	
	public int getTax() {
		return tax;
	}

	public void setTax(int tax) {
		this.tax = tax;
	}
	
	public Set<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(Set<Invoice> invoices) {
		this.invoices = invoices;
	}
	
}
