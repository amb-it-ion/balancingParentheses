package com.ambition.balancedSymbols.rule.impl;

import java.util.Stack;

import com.ambition.balancedSymbols.rule.api.IRule;
import com.ambition.balancedSymbols.rule.api.IRuleResult;

public class GlobalRuleImpl implements IRule{
	
	@Override
	public IRuleResult satisfied(char currentChar, char lastChar, Stack<Character> stack) {
		// TODO Auto-generated method stub
		return null;
	}

	public GlobalRuleImpl not(String string) {
		// TODO Auto-generated method stub
		return this;
	}

	public GlobalRuleImpl must() {
		// TODO Auto-generated method stub
		return this;
	}

	public GlobalRuleImpl once() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public boolean getFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SequentialRuleImpl add() {
		// TODO Auto-generated method stub
		return null;
	}
}
