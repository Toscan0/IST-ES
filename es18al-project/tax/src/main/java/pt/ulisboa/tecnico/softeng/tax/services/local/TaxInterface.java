package pt.ulisboa.tecnico.softeng.tax.services.local;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.FenixFramework;
import pt.ulisboa.tecnico.softeng.tax.domain.Buyer;
import pt.ulisboa.tecnico.softeng.tax.domain.IRS;
import pt.ulisboa.tecnico.softeng.tax.domain.Invoice;
import pt.ulisboa.tecnico.softeng.tax.domain.ItemType;
import pt.ulisboa.tecnico.softeng.tax.domain.Seller;
import pt.ulisboa.tecnico.softeng.tax.domain.TaxPayer;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.BuyerData;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.InvoiceData;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.ItemTypeData;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.ReturnData;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.SellerData;
import pt.ist.fenixframework.Atomic.TxMode;

public class TaxInterface {
	
	@Atomic(mode = TxMode.READ)
	public static List<BuyerData> getBuyers() {
		List<BuyerData> buyers = new ArrayList<>();
		Set<TaxPayer> payers = getirs().getTaxPayerSet();
		for (TaxPayer payer : payers) {
			if (payer instanceof Buyer) {
				BuyerData buyer = new BuyerData((Buyer)payer);
				buyers.add(buyer);
			}
		}
		return buyers;
	}
	
	@Atomic(mode = TxMode.READ)
	public static BuyerData getBuyerbyNif(String nif) {
		TaxPayer payer = getirs().getTaxPayerByNIF(nif);
		if (payer instanceof Buyer && payer.getNif().equals(nif)) {
			BuyerData buyer = new BuyerData((Buyer)payer);
			return buyer;
		}
		else return null;
	}
	
	@Atomic(mode = TxMode.READ)
	public static List<SellerData> getSellers() {
		List<SellerData> sellers = new ArrayList<>();
		Set<TaxPayer> payers = getirs().getTaxPayerSet();
		for (TaxPayer payer : payers) {
			if (payer instanceof Seller) {
				SellerData seller = new SellerData((Seller)payer);
				sellers.add(seller);
			}
		}
		return sellers;
	}
	
	@Atomic(mode = TxMode.READ)
	public static SellerData getSellerbyNif(String nif) {
		TaxPayer payer = getirs().getTaxPayerByNIF(nif);
		if (payer instanceof Seller && payer.getNif().equals(nif)) {
			SellerData seller = new SellerData((Seller)payer);
			return seller;
		}
		else return null;
	}
	
	@Atomic(mode = TxMode.READ)
	public static List<ItemTypeData> getItemTypes() {
		List<ItemTypeData> itemtypes = new ArrayList<>();
		for (ItemType item : getirs().getItemTypeSet()) {
			itemtypes.add(new ItemTypeData(item));
		}
		return itemtypes;
	}
	
	@Atomic(mode = TxMode.READ)
	public static ItemType getItemTypebyName(String name) {
		for (ItemType item : getirs().getItemTypeSet()) {
			if (item.getName() == name)
				return item;
		}
		return null;
	}
	
	@Atomic(mode = TxMode.READ)
	public static List<InvoiceData> getInvoices() {
		List<InvoiceData> invoices = new ArrayList<>();
		for (Invoice invoice : getirs().getInvoiceSet()) {
			invoices.add(new InvoiceData(invoice.getSeller().getNif(), invoice.getBuyer().getNif(),
					invoice.getItemType().getName(), invoice.getValue(), invoice.getDate()));
		}
		return invoices;
	}
	
	@Atomic(mode = TxMode.WRITE)
	public static void createBuyer(BuyerData buyerData) {
		new Buyer(getirs(),buyerData.getNIF(), buyerData.getName(), buyerData.getAdress());
	}
	
	@Atomic(mode = TxMode.WRITE)
	public static void createSeller(SellerData sellerData) {
		new Seller(getirs(),sellerData.getNIF(), sellerData.getName(), sellerData.getAdress());
	}
	
	@Atomic(mode = TxMode.WRITE)
	public static void createItemType(ItemTypeData itemtypeData) {
		new ItemType(getirs(),itemtypeData.getName(), itemtypeData.getTax());
	}
	
	@Atomic(mode = TxMode.WRITE)
	public static void createInvoice(InvoiceData invoicedata) {
		IRS.submitInvoice(invoicedata);

	}
	
	@Atomic(mode = TxMode.SPECULATIVE_READ)
	public static IRS getirs() {
		return IRS.getIRSInstance();
	}
	
	@Atomic(mode = TxMode.READ)
	public static List<ReturnData> getBuyersReturn(String nif) {
		List<ReturnData> returns = new ArrayList<ReturnData>();
		Double amount;
		TaxPayer payer = getirs().getTaxPayerByNIF(nif);
		if (payer instanceof Buyer) {
			for (int i = 1970; i<= LocalDateTime.now().getYear(); i++) {
				amount = 0.0;
				amount = ((Buyer) payer).taxReturn(i);
				if (amount.equals(0.0)) {}
					//ignore
				else returns.add(new ReturnData(i,amount));
			}
		}
		return returns;
	}
	
	@Atomic(mode = TxMode.READ)
	public static List<ReturnData> getSellersPay(String nif) {
		List<ReturnData> returns = new ArrayList<ReturnData>();
		Double amount;
		TaxPayer payer = getirs().getTaxPayerByNIF(nif);
		if (payer instanceof Seller) {
			for (int i = 1970; i<= LocalDateTime.now().getYear(); i++) {
				amount = 0.0;
				amount = ((Seller) payer).toPay(i);
				if (amount.equals(0.0)) {}
					//ignore
				else returns.add(new ReturnData(i,amount));
			}
		}
		return returns;
	}
	
}
