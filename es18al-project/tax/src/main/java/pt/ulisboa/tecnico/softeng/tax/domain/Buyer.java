package pt.ulisboa.tecnico.softeng.tax.domain;

//import pt.ist.fenixframework.FenixFramework;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;


public class Buyer extends Buyer_Base {
	private final static int PERCENTAGE = 5;

	/*public Buyer(IRS irs, String NIF, String name, String address) {
		super(irs, NIF, name, address);
	}*/

	public double taxReturn(int year) {
		if (year < 1970) {
			throw new TaxException();
		}

		double result = 0;
		for (Invoice invoice : getInvoiceSet()) {
			if (!invoice.isCancelled() && invoice.getDate().getYear() == year) {
				result = result + invoice.getIVA() * PERCENTAGE / 100;
			}
		}
		return result;
	}
	public Invoice getInvoiceByReference(String invoiceReference) {
		if (invoiceReference == null || invoiceReference.isEmpty()) {
			throw new TaxException();
		}

		for (Invoice invoice : getInvoiceSet()) {
			if (invoice.getReference().equals(invoiceReference)) {
				return invoice;
			}
		}
		return null;
	}

	@Override
	public void delete() {
		setIrs(null);
		
		for (Invoice invoices : getInvoiceSet()) {
			invoices.delete();
		}

		deleteDomainObject();
	}
}