package com.codingcure.callService.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PricingController {
	
	@GetMapping("/getPrice/{fundId}")
	public double getPrice(@PathVariable String fundId) {
		double price = 0.0;
		int id = Integer.parseInt(fundId);
		if (id == 123) {
			price = 129.45;
		}
		else if (id == 124) {
			price = 134.78;
		}
		return price;
	}

}
