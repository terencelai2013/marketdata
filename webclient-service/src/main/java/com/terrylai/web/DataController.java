package com.terrylai.web;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DataController {
	
	protected Logger logger = Logger.getLogger(DataController.class.getName());
	
	@Autowired
	DataRepository dataRepository;
	
	@RequestMapping("/")
	public String home(){
		return "index";
	}
	
	@RequestMapping("/symbolList")
	public String symbolList(Model model) {
		model.addAttribute("names", dataRepository.getAllName());
		return "symbolList";
	}
	
	@RequestMapping("/symbolDetails")
	public String nameDetails(@RequestParam("symbol") String symbol, Model model) {
		logger.info("TERRY symbol:" + symbol);
		model.addAttribute("names", dataRepository.getName(symbol));
		model.addAttribute("symbol", symbol);
		return "symbolDetails";
	}
}
