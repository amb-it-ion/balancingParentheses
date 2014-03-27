package com.ambition.balancedSymbols.service.impl;

import java.util.Set;
import java.util.Stack;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ambition.balancedSymbols.rule.api.IRule;
import com.ambition.balancedSymbols.service.api.IBalancingSymbolsService;

@Component
public class DeclarativeBalancingSymbolsServiceImpl implements
		IBalancingSymbolsService {
	@Resource(name = "rules")
	public Set<IRule> rules;

	public boolean evaluate(String input, boolean paralell) {

		Stack<Character> stack = new Stack<Character>();

		// take copy
		char[] inputToArray = input.toCharArray();

		char lastChar = '\u0000';
		char currentChar;

		if (inputToArray.length > 0) {
			lastChar = inputToArray[0];
			stack.push(lastChar);
		}
		int inputToArrayIndex = 1;

		do {
			Stream<IRule> rulesStream = rules.stream();

			if (paralell)
				rulesStream = rulesStream.parallel();
			else
				rulesStream = rulesStream.sequential();

			try {
				currentChar = inputToArray[inputToArrayIndex];
			} catch (ArrayIndexOutOfBoundsException e) {
				// special case: inputToArray.length == 0
				currentChar = '\u0000';
			}

			final char lastCharFinal = lastChar;
			final char currentCharFinal = currentChar;

			if (rulesStream
					.filter(rule -> !rule.satisfied(currentCharFinal,
							lastCharFinal, stack)).findFirst().isPresent())
				return false;

			lastChar = currentChar;

			inputToArrayIndex++;
		} while (inputToArrayIndex < inputToArray.length);

		return stack.empty();
	}
}
