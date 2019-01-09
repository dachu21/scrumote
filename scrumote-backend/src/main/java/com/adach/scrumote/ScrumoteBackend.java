package com.adach.scrumote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ScrumoteBackend {

  public static void main(String[] args) {
    SpringApplication.run(ScrumoteBackend.class, args);
  }
}
