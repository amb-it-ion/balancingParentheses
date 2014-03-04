package com.ambition.balancedSymbols.rule.impl;

import java.util.Stack;

import com.ambition.balancedSymbols.rule.api.IRuleResult;

public class RuleResultImpl implements IRuleResult {

	private Stack<Character> remaining;
	boolean finished;
	boolean result;
	
	RuleResultImpl( Stack<Character> remaining, boolean finished, boolean result )
	{
		this.remaining = remaining;
		this.finished = finished;
		this.result = result;
	}
	
	@Override
	public Stack<Character> getRemaining() {
		return remaining;
	}

	@Override
	public boolean getFinished() {
		return finished;
	}

	@Override
	public boolean getResult() {
		return result;
	}

}
