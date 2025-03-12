package com.marketanalysis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marketanalysis.service.postgres.repository.UserRepository;

@SpringBootApplication
@RestController
@RequestMapping("/")
public class MarketAnalysisApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketAnalysisApplication.class, args);
	}

	@GetMapping("/public")
	public static String check() {
		return "I am allive";
	}

	@Autowired
    private UserRepository usersRepository;

    @GetMapping("/dbCheck")
    public String send() {
		try{
        	return usersRepository.findById(1).get().getUsername();
		} catch (Exception e) {
			return "Not exists";
		}
    }

}
