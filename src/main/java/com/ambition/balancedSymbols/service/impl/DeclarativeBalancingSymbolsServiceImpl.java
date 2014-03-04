package com.ambition.balancedSymbols.service.impl;

import java.util.Set;
import java.util.Stack;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ambition.balancedSymbols.rule.api.IRule;
import com.ambition.balancedSymbols.rule.api.IRuleResult;
import com.ambition.balancedSymbols.service.api.IBalancingSymbolsService;

@Component
public class DeclarativeBalancingSymbolsServiceImpl implements IBalancingSymbolsService
{
	@Resource(name="rules")
	public Set<IRule> rules;
	
	public boolean evaluate(String input) {
		
		Stack<Character> stack = new Stack<Character>();
		
		// TODO check finished once in the beginning, not every run
		for ( IRule rule : rules )
		{ 
			if ( !rule.getFinished() )
				throw new IllegalStateException("Rule not defined completely.");
		}
		
		char[] inputToArray = input.toCharArray();
		char lastChar;
		char currentChar;
		
		// TODO Global rule
		if ( inputToArray.length > 0 )
		{
			// TODO test for opening character or refactor
			lastChar = inputToArray[0];
			stack.push( lastChar );
			
			for (int inputToArrayIndex=1; inputToArrayIndex<inputToArray.length; inputToArrayIndex++ ) 
			{
				currentChar = inputToArray[ inputToArrayIndex ];
				
				for ( IRule rule : rules )
				{
					IRuleResult result = rule.satisfied( currentChar, lastChar, stack );
					
					if ( result.getFinished() && !result.getResult() )
						return false;
				}
				
				lastChar = currentChar;
			}
		}
		
		return stack.empty();
	}
}
