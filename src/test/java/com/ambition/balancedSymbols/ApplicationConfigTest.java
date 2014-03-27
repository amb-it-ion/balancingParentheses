package com.ambition.balancedSymbols;

import java.io.BufferedReader;
import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import com.ambition.balancedSymbols.runner.impl.SingleThreadBalancingCommandLineRunnerImpl;

@ComponentScan( excludeFilters = { @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = SingleThreadBalancingCommandLineRunnerImpl.class) }, 
			    basePackages = { "com.ambition.balancedSymbols" })
@Configuration
public class ApplicationConfigTest extends ApplicationConfig {

	@Bean
	@Override
	public BufferedReader dataIn() {
		BufferedReader bufferedReader = org.mockito.Mockito
				.mock(BufferedReader.class);

		try {
			org.mockito.Mockito.when(bufferedReader.readLine())
			
					.thenReturn("()")
					.thenReturn("[]")
					.thenReturn("{}")
					.thenReturn("(()")
					.thenReturn("{}}")
			
					.thenReturn("({})")
					.thenReturn("({)}")
					
					.thenReturn("({})")
					.thenReturn("([])")
					.thenReturn("(())")
					
					.thenReturn("{[]}")
					.thenReturn("{()}")
					.thenReturn("{{}}") // wrong task
					
					.thenReturn("[()]")
					.thenReturn("[{}]")
					.thenReturn("[[]]")
					.thenReturn("[[[]]]")
					.thenReturn("[([])]")
					
					.thenReturn("[()()]")
					.thenReturn("{[][()()]}")
					.thenReturn("()()")
					.thenReturn("")
					.thenReturn(SingleThreadBalancingCommandLineRunnerImpl.EXIT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bufferedReader;
	}

	@Bean
	public boolean[] resultsOut() {
		return new boolean[] { 
				
				true, 
				true, 
				true, 
				false, 
				false, 
				
				true, 
				false, 
				
				true, 
				false, 
				false,
				
				true, 
				false, 
				false, 
				
				true, 
				true, 
				true, 
				true, 
				false, 
				
				true, 
				true, 
				true,
				false };
	}
}
