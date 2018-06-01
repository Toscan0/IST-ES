package pt.ulisboa.tecnico.softeng.tax.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.tax.exception.TaxException;
import pt.ulisboa.tecnico.softeng.tax.services.local.TaxInterface;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.BuyerData;
import pt.ulisboa.tecnico.softeng.tax.services.local.dataobjects.SellerData;

@Controller
@RequestMapping(value = "/sellers")
public class sellerController {
	private static Logger logger = LoggerFactory.getLogger(sellerController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String sellerForm(Model model) {
		logger.info("sellerForm");
		model.addAttribute("seller", new SellerData());
		model.addAttribute("sellers", TaxInterface.getSellers());
		return "sellers";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String sellerSubmit(Model model, @ModelAttribute SellerData sellerData) {
		logger.info("sellerSubmit name:{}, nif:{}, adress:{}", sellerData.getName(), sellerData.getNIF(), 
				sellerData.getAdress());

		try {
			TaxInterface.createSeller(sellerData);
		} catch (TaxException be) {
			model.addAttribute("error", "Error: it was not possible to create the seller");
			model.addAttribute("seller", sellerData);
			model.addAttribute("sellers", TaxInterface.getSellers());
			return "sellers";
		}

		return "redirect:/sellers";
	}
	
	@RequestMapping(value = "/{nif}/details", method = RequestMethod.GET)
	public String sellerDetails(Model model, @PathVariable String nif) {
		logger.info("sellerDetails sellernif:{}", nif);
		try {
			model.addAttribute("seller", TaxInterface.getSellerbyNif(nif));
			return "showinvoicesseller";
		}catch(TaxException e) {
			model.addAttribute("error", "Error: it was not possible to move to details");
			model.addAttribute("seller", TaxInterface.getSellerbyNif(nif));
			return "sellers";
		}
	}
	
	@RequestMapping(value = "/{nif}/payperyear", method = RequestMethod.GET)
	public String sellerPayperYear(Model model, @PathVariable String nif) {
		logger.info("sellerPayperYear sellernif:{}", nif);
		try {
			model.addAttribute("seller", TaxInterface.getSellerbyNif(nif));
			model.addAttribute("returns", TaxInterface.getSellersPay(nif));
			return "topay";
		}catch(TaxException e) {
			model.addAttribute("error", "Error: it was not possible to move to pay per year");
			model.addAttribute("seller", TaxInterface.getSellerbyNif(nif));
			return "sellers";
		}	
	}
}
