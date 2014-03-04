package com.ambition.balancedSymbols;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ambition.balancedSymbols.rule.api.IRule;
import com.ambition.balancedSymbols.rule.impl.GlobalRuleImpl;
import com.ambition.balancedSymbols.rule.impl.SequentialRuleImpl;
import com.ambition.balancedSymbols.service.api.IBalancingSymbolsService;
import com.ambition.balancedSymbols.service.impl.DeclarativeBalancingSymbolsServiceImpl;
import com.ambition.balancedSymbols.service.impl.ImperativeBalancingSymbolsServiceImpl;

@Configuration
public class ApplicationConfig {

	@Bean
	public BufferedReader dataIn(){
		 InputStreamReader inputData = new InputStreamReader(System.in);
		 return new BufferedReader(inputData);
	}

	@Autowired
	DeclarativeBalancingSymbolsServiceImpl declarativeBalancingSymbolsService;
	
	@Autowired
	ImperativeBalancingSymbolsServiceImpl imperativeBalancingSymbolsService;
	
	@Bean
	public IBalancingSymbolsService service(){
		return declarativeBalancingSymbolsService;
	}
	
	@Bean
	public Set<IRule> rules(){
	 
		Set<IRule> rules = new HashSet<IRule>();
		SequentialRuleImpl sequentialRuleImpl = new SequentialRuleImpl();
		GlobalRuleImpl globalRuleImpl = new GlobalRuleImpl();
		
    	// Each type of bracket needs to be first opened then closed
		sequentialRuleImpl.add().after('(').must().later(')');
		sequentialRuleImpl.add().after('[').must().later(']');
		sequentialRuleImpl.add().after('{').must().later('}');
    	
		// You can only close the last bracket that was opened
		sequentialRuleImpl.satisfyLaterOrdered();
		
    	// Inside parenthesis () only Braces {} are allowed
		sequentialRuleImpl.add().after('(').may().directly('{');
		
		// Inside Braces {} only Square brackets [] are allowed
		sequentialRuleImpl.add().after('{').may().directly('[');
		
		// Any bracket can appear (directly) inside square brackets []!
		sequentialRuleImpl.add().after('[').may().directly('{');
		sequentialRuleImpl.add().after('[').may().directly('(');
		sequentialRuleImpl.add().after('[').may().directly('[');
		
		// You can use a list of braces where a single one is allowed of that type!
		sequentialRuleImpl.add().after(']').may().directly('[');	// wrong task
		sequentialRuleImpl.add().after(')').may().directly('(');	// wrong task
		sequentialRuleImpl.add().after('}').may().directly('{');	// wrong task
		
    	// An empty string is not valid expression!
		globalRuleImpl.not("").must().once();
		
		rules.add( sequentialRuleImpl );
		// TODO 
		// rules.add( globalRuleImpl );
	 
    	return rules;
	}
}
