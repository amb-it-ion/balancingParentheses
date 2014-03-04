package com.ambition.balancedSymbols.rule.api;

import java.util.Stack;

public interface IRuleResult {
	Stack<Character> getRemaining();
	boolean getFinished();
	boolean getResult();
}
