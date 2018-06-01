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

@Controller
@RequestMapping(value = "/buyers")
public class buyerController {
	private static Logger logger = LoggerFactory.getLogger(buyerController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String buyerForm(Model model) {
		logger.info("buyerForm");
		model.addAttribute("buyer", new BuyerData());
		model.addAttribute("buyers", TaxInterface.getBuyers());
		return "buyers";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String buyerSubmit(Model model, @ModelAttribute BuyerData buyerData) {
		logger.info("buyerSubmit name:{}, nif:{}", buyerData.getName(), buyerData.getNIF(), 
				buyerData.getAdress());

		try {
			TaxInterface.createBuyer(buyerData);
		} catch (TaxException be) {
			model.addAttribute("error", "Error: it was not possible to create the buyer");
			model.addAttribute("buyer", buyerData);
			model.addAttribute("buyers", TaxInterface.getBuyers());
			return "buyers";
		}

		return "redirect:/buyers";
	}
	
	@RequestMapping(value = "/{nif}/details", method = RequestMethod.GET)
	public String buyerDetails(Model model, @PathVariable String nif) {
		logger.info("buyerDetails buyernif:{}", nif);
		try {
			model.addAttribute("buyer", TaxInterface.getBuyerbyNif(nif));
			return "showinvoices";
		}catch(TaxException e) {
			model.addAttribute("error", "Error: it was not possible to move to details");
			model.addAttribute("buyer", TaxInterface.getBuyerbyNif(nif));
			return "buyers";
		}	
	}
	
	
	@RequestMapping(value = "/{nif}/receiveperyear", method = RequestMethod.GET)
	public String buyerReceiveperYear(Model model, @PathVariable String nif) {
		logger.info("buyerReceivesperYear buyernif:{}", nif);
		try {
			model.addAttribute("buyer", TaxInterface.getBuyerbyNif(nif));
			model.addAttribute("returns", TaxInterface.getBuyersReturn(nif));
			return "receives";
		}catch(TaxException e) {
			model.addAttribute("error", "Error: it was not possible to move to receives per year");
			model.addAttribute("buyer", TaxInterface.getBuyerbyNif(nif));
			return "buyers";
		}	
	}
	
}
