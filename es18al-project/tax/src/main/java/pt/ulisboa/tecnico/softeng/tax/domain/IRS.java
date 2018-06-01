package pt.ulisboa.tecnico.softeng.tax.domain;

import pt.ulisboa.tecnico.softeng.tax.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;

import java.util.HashMap;
import java.util.Map;

public class IRS {
	
	private static IRS irs = null;
	
	public static Map<String,ItemType> itemtypes = new HashMap<String,ItemType>();
	public static Map<String,TaxPayer> taxpayers = new HashMap<String,TaxPayer>();
	
	
	public static ItemType getItemTypeByName( String name) {
		return IRS.itemtypes.get(name);
	}
	public static TaxPayer getItemTaxPayerByNIF( String nif){
		TaxPayer tp = IRS.taxpayers.get(nif);
		return tp;
	}

	protected IRS(){
		//Singleton
	}
	
	public static String submitInvoice(InvoiceData invoiceData) throws TaxException{
		ItemType item = IRS.itemtypes.get(invoiceData.getItemType());
		TaxPayer buyer =  IRS.taxpayers.get(invoiceData.getBuyerNIF());
		TaxPayer seller =  IRS.taxpayers.get(invoiceData.getSellerNIF());
		Invoice invoice = new Invoice( invoiceData.getValue(), invoiceData.getDate(), item, seller, buyer );		
		return invoice.getReference();
	}
	
	public static IRS getIRS() {
		if(irs == null) {
	         irs = new IRS();
	      }
		return irs;
	}

}
