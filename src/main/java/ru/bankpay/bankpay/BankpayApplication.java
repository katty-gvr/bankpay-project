package ru.bankpay.bankpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BankpayApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankpayApplication.class, args);
	}
}
