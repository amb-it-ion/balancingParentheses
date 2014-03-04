package com.ambition.balancedSymbols;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ambition.balancedSymbols.runner.impl.SingleThreadBalancingCommandLineRunnerImpl;
import com.ambition.balancedSymbols.service.api.IBalancingSymbolsService;
import com.ambition.balancedSymbols.service.impl.DeclarativeBalancingSymbolsServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfigTest.class, loader=SpringApplicationContextLoader.class)
public class ApplicationTests {
	
	@Resource(name="service")
	DeclarativeBalancingSymbolsServiceImpl declarativeBalancingSymbolsService;
	
	@Resource(name="resultsOut")
	boolean[] resultsOut;

	@Resource(name="dataIn")
	BufferedReader dataIn;
	
	@Test
	public void assignSheetExamples() {
        
       String line;
       int lineNumber = 0;
       
       try {
    	   while ( ( line = dataIn.readLine() ) != SingleThreadBalancingCommandLineRunnerImpl.EXIT )
    	   {
    		   if ( resultsOut[lineNumber++] )
    			   Assert.assertTrue( line + " should have evaluated true, but returned in false instead", declarativeBalancingSymbolsService.evaluate( line ));
    		   else
    			   Assert.assertFalse( line + " should have evaluated false, but returned in true instead", declarativeBalancingSymbolsService.evaluate( line ));
    	   }

	   } catch (IOException e) {
		   e.printStackTrace();
	   }
    }
}
