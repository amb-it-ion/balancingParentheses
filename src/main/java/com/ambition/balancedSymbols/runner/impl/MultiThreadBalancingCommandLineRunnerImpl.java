package com.ambition.balancedSymbols.runner.impl;

import java.io.BufferedReader;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ambition.balancedSymbols.runner.api.IBalancingCommandLineRunner;
import com.ambition.balancedSymbols.service.api.IBalancingSymbolsService;

@Component
public class MultiThreadBalancingCommandLineRunnerImpl implements
		IBalancingCommandLineRunner {

	public static final String EXIT = "exit";

	@Resource(name="service")
	IBalancingSymbolsService balancingService;
	
	@Resource(name="dataIn")
	BufferedReader dataIn;
	
	@Override
	public void run(String... args) throws Exception {		
		String line;
		int lineNumber = 0;
		
        while ( ( line = dataIn.readLine() ) != EXIT)
        {
        	System.out.println( new Integer( lineNumber++ ).toString() + balancingService.evaluate( line ) );
        }
	}
}
