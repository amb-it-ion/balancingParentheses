package com.ambition.balancedSymbols.service.impl;

import java.util.EmptyStackException;
import java.util.Stack;

import org.springframework.stereotype.Component;

import com.ambition.balancedSymbols.service.api.IBalancingSymbolsService;

@Component
public class ImperativeBalancingSymbolsServiceImpl implements IBalancingSymbolsService
{
	public boolean evaluate( String input, boolean paralell ) {
		
		Stack<Character> stack = new Stack<Character>();
		char lastChar = '['; 
		
		try
		{
			for (char currentChar : input.toCharArray()) 
			{
				switch ( currentChar )
				{
					case '{':
						if ( lastChar == '(' || lastChar == '[' )
							stack.push(currentChar);
						else
							return false;
						break;
					case '[':
						if (lastChar == '{'|| lastChar == '[' )
							stack.push(currentChar);
						else
							return false;
					case '(':
						if (lastChar == '[')
							stack.push(currentChar);
						else
							return false;
						break;
					case '}':
						if (! ( stack.pop() == '{' ))
							return false;
						break;
					case ')':
						if (! ( stack.pop() =='(' ))
							return false;
						break;
					case ']':
						if (! ( stack.pop() =='[' ))
							return false;
						break;
					default:
						return false;
				}
				lastChar = currentChar;
			}
		}
		catch ( EmptyStackException e )
		{
			return false;
		}
		return true;
	}
}
