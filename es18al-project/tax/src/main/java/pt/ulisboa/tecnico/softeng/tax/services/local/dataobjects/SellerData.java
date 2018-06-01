package pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects;

import java.util.List;
import java.util.stream.Collectors;

import pt.ulisboa.tecnico.softeng.tax.domain.Seller;

public class SellerData {
	private String name;
	private String adress;
	private String NIF;
	private List<InvoiceData> invoices;
	private double topay;
	//lista de invoices
	
	public SellerData() {}
	

	public SellerData(Seller seller) {
		this.name = seller.getName();
		this.adress = seller.getAddress();
		this.NIF = seller.getNif();
		this.invoices = seller.getInvoiceSet().stream().sorted((c1, c2) -> c1.getBuyer().getNif().compareTo(c2.getBuyer().getNif()))
				.map(c -> new InvoiceData(c.getSeller().getNif() ,c.getBuyer().getNif()  ,c.getItemType().getName() ,c.getValue(), c.getDate())).collect(Collectors.toList());
		 
	}

	public double getTopay() {
		return topay;
	}

	public void setTopay(double topay) {
		this.topay = topay;
	}
	
	public List<InvoiceData> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<InvoiceData> invoices) {
		this.invoices = invoices;
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

	public String getNIF() {
		return NIF;
	}

	public void setNIF(String nIF) {
		NIF = nIF;
	}
}
