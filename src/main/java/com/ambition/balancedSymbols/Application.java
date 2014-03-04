package com.ambition.balancedSymbols;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import com.ambition.balancedSymbols.runner.impl.MultiThreadBalancingCommandLineRunnerImpl;
import com.ambition.balancedSymbols.runner.impl.SingleThreadBalancingCommandLineRunnerImpl;

@ComponentScan(excludeFilters = { @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = MultiThreadBalancingCommandLineRunnerImpl.class) }, basePackages = { "com.ambition.balancedSymbols" })
@EnableAutoConfiguration
public class Application {
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        
        printBeans(args);
    }

	private static void printBeans(String[] args) {
		System.out.println("Let's inspect the beans provided by Spring Boot:");

        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
	}
}
