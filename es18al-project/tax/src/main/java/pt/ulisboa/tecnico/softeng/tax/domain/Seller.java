package pt.ulisboa.tecnico.softeng.tax.domain;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public class Seller extends TaxPayer{
	public Seller(String NIF, String name, String adress) {
		super(NIF, name, adress);
		IRS.taxpayers.put(NIF,this);
	}

	public float toPay(int YEAR) {
		float paytax = 0;
		int ninvoices = 0;
		if (YEAR >= 1970) {
			for (Invoice inv : TaxPayer.invoices.values()) {
				if (this == inv.getSeller() && YEAR == inv.getDate().getYear()) {
					paytax += 0.05* inv.getiVA();
					ninvoices++;
				}
			}
			if (ninvoices == 0) throw new TaxException();
			return paytax; 
		}
		else throw new TaxException();
	}
}
