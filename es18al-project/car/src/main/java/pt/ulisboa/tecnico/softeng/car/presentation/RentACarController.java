package pt.ulisboa.tecnico.softeng.car.presentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pt.ulisboa.tecnico.softeng.car.exception.CarException;
import pt.ulisboa.tecnico.softeng.car.services.local.CarInterface;
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.RentACarData;

@Controller
@RequestMapping(value = "/rentacars")
public class RentACarController {
	private static Logger logger = LoggerFactory.getLogger(RentACarController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String rentACarForm(Model model) {
		logger.info("rentacarForm");
		model.addAttribute("rentacar", new RentACarData());
		model.addAttribute("rentacars", CarInterface.getRentACars());
		return "rentacars";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String rentACarSubmit(Model model, @ModelAttribute RentACarData rentACarData) {
		logger.info("rentacarSubmit name:{}, code{}", rentACarData.getName(), rentACarData.getCode(), rentACarData.getNif(), rentACarData.getIban());
		
		try {
			CarInterface.createRentACar(rentACarData);
		} catch (CarException be) {
			model.addAttribute("error", "Error: it was impossible to create the RentACar");
			model.addAttribute("rentacar", new RentACarData());
			model.addAttribute("rentacars", CarInterface.getRentACars());
			return "rentacars";
		}
		
		return "redirect:/rentacars";
		
	}
	
}
