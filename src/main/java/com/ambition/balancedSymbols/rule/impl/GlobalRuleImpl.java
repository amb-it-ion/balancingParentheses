package com.ambition.balancedSymbols.rule.impl;

import java.util.Stack;

import com.ambition.balancedSymbols.rule.api.IRule;

public class GlobalRuleImpl implements IRule {

	private char not;

	private boolean satisfied;
	
	public boolean satisfied(char currentChar ) {
		satisfied = ( currentChar != not );
		
		return satisfied;
	}


	public GlobalRuleImpl not(char not) {
		this.not = not;

		return this;
	}

	@Override
	public boolean satisfied(char currentChar, char lastChar,
			Stack<Character> stack) {
		return satisfied( currentChar );
	}
}
