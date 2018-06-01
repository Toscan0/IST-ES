package pt.ulisboa.tecnico.softeng.tax.domain;

import java.util.HashMap;
import java.util.Map;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;


public class TaxPayer {
	private String NIF;
	private String name;
	private String adress;
	public static Map<String,Invoice> invoices = new HashMap<String,Invoice>();
	
	public TaxPayer(String NIF, String name, String adress) {
		if (NIF.length() == 9 && checkargument(name) && checkargument(adress) && !IRS.taxpayers.containsKey(NIF)) {
			this.NIF = NIF;
			this.name = name;
			this.adress = adress;
		}
		else
			throw new TaxException();
	}
	private boolean checkargument(String arg) {
		if ( arg != null && arg!=" " && arg!="" )
			return true;
		return false;
	}
	
	
	public static Invoice getInvoiceByReference(String INVOICE_REFERENCE) {
		return TaxPayer.invoices.get(INVOICE_REFERENCE);
	}

	public String getNIF() {
		return NIF;
	}

	public void setNIF(String nIF) {
		NIF = nIF;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}
}
