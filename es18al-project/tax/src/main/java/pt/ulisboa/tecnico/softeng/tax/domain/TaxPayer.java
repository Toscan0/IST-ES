package pt.ulisboa.tecnico.softeng.tax.domain;

import java.util.HashSet;
import java.util.Set;

//import pt.ist.fenixframework.FenixFramework;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

public abstract class TaxPayer extends TaxPayer_Base{
	//protected final Set<Invoice> invoices = new HashSet<>();

	/*private final String NIF;
	private final String name;
	private final String address;*/

	/*public TaxPayer(IRS irs, String NIF, String name, String address) {
		checkArguments(irs, NIF, name, address);

		setNIF(NIF);
		setName(name);
		setAddress(address);

		//irs.addTaxPayer(this);
	}*/

	private void checkArguments(IRS irs, String NIF, String name, String address) {
		if (NIF == null || NIF.length() != 9) {
			throw new TaxException();
		}

		if (name == null || name.length() == 0) {
			throw new TaxException();
		}

		if (address == null || address.length() == 0) {
			throw new TaxException();
		}

		if (irs.getTaxPayerSet().contains(NIF)) {
			throw new TaxException();
		}

	}
	
	public abstract Invoice getInvoiceByReference(String invoiceReference) ;


	public abstract void delete();


}
