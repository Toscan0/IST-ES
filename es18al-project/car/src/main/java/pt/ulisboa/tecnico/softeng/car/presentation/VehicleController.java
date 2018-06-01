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
import pt.ulisboa.tecnico.softeng.car.services.local.dataobjects.VehicleData;

@Controller
@RequestMapping(value = "/vehicles")
public class VehicleController {
	private static Logger logger = LoggerFactory.getLogger(VehicleController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String vehicleForm(Model model) {
		logger.info("vehicleForm");
		model.addAttribute("vehicle", new VehicleData());
		return "vehicles";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String vehicleSubmit(Model model, @ModelAttribute VehicleData carData, @PathVariable String code) {
		logger.info("vehicleSubmit plate:{}", carData.getPlate(), carData.getKilometers(), carData.getPrice());
		try {
			CarInterface.createVehicle(carData, code);
		} catch (CarException be) {
			model.addAttribute("error", "Error: it was not possible to create the vehicle");
			model.addAttribute("vehicle", carData);
			model.addAttribute("buyers", CarInterface.getVehicles(code));
			return "buyers";
		}

		return "redirect:/buyers";
	}
	
}
