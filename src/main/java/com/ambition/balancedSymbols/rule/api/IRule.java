package com.ambition.balancedSymbols.rule.api;

import java.util.Stack;

import com.ambition.balancedSymbols.rule.impl.SequentialRuleImpl;



public interface IRule {
	
	IRuleResult satisfied(char currentChar, char lastChar, Stack<Character> stack);
	SequentialRuleImpl add();
	boolean getFinished();
}
