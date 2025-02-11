package com.codingcure.fundservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.codingcure.fundservice.entity.Fund;
import com.codingcure.fundservice.repositary.FundRepo;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@Service
public class FundService {
	
	@Autowired
	FundRepo fundRepo;
	
	@Autowired
	RestTemplate restTemplate;

	@CircuitBreaker(name = "pricingService", fallbackMethod = "getDefaultPrice")
	public Fund createFund(Fund fund) {
		try {
			System.out.println("create fund method call>>>>>");
			String url = "http://localhost:9094/getPrice/123";
			Double price = restTemplate.getForObject(url, Double.class);
			fund.setNav(price);
		} catch (RestClientException e) {
			throw new RuntimeException("pricing service is not avaialble", e);
		}
		 return fundRepo.save(fund);
		
	}
	
//	fallback method
	private Fund getDefaultPrice(Fund fund, Throwable t){
		System.out.println("Fall back method called: >>>"+t.getMessage());
		fund.setNav(135.98);
		return fundRepo.save(fund);
	}
	

	public Fund getFund(String fundId) {
		Fund fundInfo = null;
		 Optional<Fund> fund = fundRepo.findById(Integer.parseInt(fundId));
		 if(fund.isPresent()) {
			fundInfo = fund.get();
		 }
		 return fundInfo;
	}

}
