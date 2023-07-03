package com.pennant.prodmtr.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pennant.prodmtr.model.Dto.FunctionalUnitdto;
import com.pennant.prodmtr.model.Input.FunctionalUnitinput;
import com.pennant.prodmtr.service.Interface.FunctionalUnitService;

@Controller
public class FunctionalUnitController {
	private static final Logger logger = LogManager.getLogger(FunctionalUnitController.class);

	FunctionalUnitService Funitservice;

	@Autowired
	public FunctionalUnitController(FunctionalUnitService Funitservice) {
		this.Funitservice = Funitservice;
		// TODO Auto-generated constructor stub
	}

	// Method for getting functional units by using Module ID
	@RequestMapping(value = "/funitsbymodlId", method = RequestMethod.GET)
	public String getModuleDetailsByProjId(@RequestParam("modId") Integer modId, Model model) {
		logger.info("Functional unit jsp called");
		logger.debug("funitid: {}", modId);

		List<FunctionalUnitdto> funits = Funitservice.getFunctionalunitByModId(modId);
		logger.debug("funit data: {}", funits);
		model.addAttribute("funitdto", funits);
		return "funitsbymodlId";
	}

	// Method for creating functional unit in module
	@RequestMapping(value = "/createfunit", method = RequestMethod.GET)
	public String createnewFunit() {
		logger.info("createfunit GET request called");
		return "createfunit";
	}

	// Method for getting data from jsp page to create a new Functional unit
	@RequestMapping(value = "/createFunitsuccess", method = RequestMethod.POST)
	public String Createmodulesuccess(@Validated FunctionalUnitinput Funitinput, Model model) {
		logger.info("createFunitsuccess POST request called");
		logger.debug("FunctionalUnitinput: {}", Funitinput);

		Funitservice.createFunit(Funitinput);
		Integer modId = Funitinput.getModlId();

		logger.debug("Redirecting to funitsbymodlId?modId={}", modId);
		return "redirect:/funitsbymodlId?modId=" + modId;
	}

}
