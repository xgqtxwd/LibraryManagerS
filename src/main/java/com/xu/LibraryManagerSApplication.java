package com.xu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class LibraryManagerSApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagerSApplication.class, args);
    }


}
