package com.ambition.balancedSymbols.rule.api;

import java.util.Stack;




public interface IRule {
	
	boolean satisfied(char currentChar, char lastChar, Stack<Character> stack);
}
