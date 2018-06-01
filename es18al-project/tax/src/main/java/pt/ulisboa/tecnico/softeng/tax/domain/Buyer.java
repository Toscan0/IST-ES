package pt.ulisboa.tecnico.softeng.tax.domain;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;


public class Buyer extends TaxPayer {
	public Buyer(String NIF, String name, String adress) {
		super(NIF, name, adress);
		IRS.taxpayers.put(NIF,this);
	} 

	public float taxReturn(int YEAR) {
		float returntax = 0;
		int ninvoices = 0;
		if (YEAR >= 1970) {
			for (Invoice inv : TaxPayer.invoices.values()) {
				if (this == inv.getBuyer() && YEAR == inv.getDate().getYear()) {
					returntax += 0.05* inv.getiVA();
					ninvoices++;
				}
			}
			if (ninvoices == 0) throw new TaxException();
			return returntax; 
		}
		else throw new TaxException();
	}
}
