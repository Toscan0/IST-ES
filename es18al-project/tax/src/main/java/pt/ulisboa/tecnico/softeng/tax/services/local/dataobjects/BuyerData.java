package pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects;

import java.util.List;
import java.util.stream.Collectors;

import pt.ulisboa.tecnico.softeng.tax.domain.Buyer;

public class BuyerData {
	

	private String name;
	private String NIF;
	private String adress;
	private List<InvoiceData> invoices;
	private double toreceive;
	//lista de invoices
	
	public BuyerData() {}
	
	public BuyerData(Buyer buyer) {
		this.name = buyer.getName();
		this.NIF = buyer.getNif();
		this.adress = buyer.getAddress();

		this.invoices = buyer.getInvoiceSet().stream().sorted((c1, c2) -> c1.getBuyer().getNif().compareTo(c2.getBuyer().getNif()))
				.map(c -> new InvoiceData(c.getSeller().getNif() ,c.getBuyer().getNif()  ,c.getItemType().getName() ,c.getValue(), c.getDate())).collect(Collectors.toList());
		 
	}
	
	public double getToreceive() {
		return toreceive;
	}

	public void setToreceive(double toreceive) {
		this.toreceive = toreceive;
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
