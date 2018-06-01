package pt.ulisboa.tecnico.softeng.broker.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.broker.domain.Adventure;
import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;
import pt.ulisboa.tecnico.softeng.broker.services.local.BrokerInterface;
import pt.ulisboa.tecnico.softeng.broker.services.local.dataobjects.AdventureData;
import pt.ulisboa.tecnico.softeng.broker.services.local.dataobjects.BrokerData;
import pt.ulisboa.tecnico.softeng.broker.services.local.dataobjects.BrokerData.CopyDepth;
import pt.ulisboa.tecnico.softeng.broker.services.local.dataobjects.ClientData;

@Controller
@RequestMapping(value = "/brokers/{brokerCode}/clients/{iban}/adventures")
public class AdventureController {
	private static Logger logger = LoggerFactory.getLogger(AdventureController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String showAdventures(Model model, @PathVariable String brokerCode, @PathVariable String iban) {
		logger.info("showAdventures code:{}", brokerCode, iban);

		BrokerData brokerData = BrokerInterface.getBrokerDataByCode(brokerCode, CopyDepth.ADVENTURES);
		ClientData clientData = BrokerInterface.getClientDataByIban(iban, CopyDepth.ADVENTURES);
		
		if (brokerData == null) {
			model.addAttribute("error", "Error: it does not exist a broker with the code " + brokerCode + "with iban:"+ iban);
			model.addAttribute("broker", new BrokerData());
			model.addAttribute("brokers", BrokerInterface.getBrokers());
			return "brokers";
		} 
		
		else if (clientData == null) {
			model.addAttribute("error", "Error: it does not exist a broker with the code " + brokerCode + "with iban:"+ iban);
			model.addAttribute("client", new ClientData());
			model.addAttribute("clients", BrokerInterface.getClients());
			return "brokers";
		}
		else {
			model.addAttribute("adventure", new AdventureData());
			model.addAttribute("client", clientData);
			model.addAttribute("broker", brokerData);
			return "adventures";
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submitAdventure(Model model, @PathVariable String brokerCode, @PathVariable String iban, 
			@ModelAttribute AdventureData adventureData) {
		logger.info("adventureSubmit brokerCode:{}, iban:{}, begin:{}, end:{}, age:{}, iban:{}, amount:{}", brokerCode, iban, 
				adventureData.getBegin(), adventureData.getEnd(), adventureData.getAge() , adventureData.getIban(),
				adventureData.getAmount());

		try {
			BrokerInterface.createAdventure(brokerCode, iban, adventureData);
		} catch (BrokerException be) {
			model.addAttribute("error", "Error: it was not possible to create the adventure");
			model.addAttribute("adventure", adventureData);
			model.addAttribute("client", BrokerInterface.getClientDataByIban(iban, CopyDepth.ADVENTURES));
			model.addAttribute("broker", BrokerInterface.getBrokerDataByCode(brokerCode, CopyDepth.ADVENTURES));
			return "adventures";
		}

		return "redirect:/brokers/" + brokerCode + "/clients/" + iban +"/adventures";
	}

}
