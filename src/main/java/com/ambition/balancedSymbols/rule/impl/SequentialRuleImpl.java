package com.ambition.balancedSymbols.rule.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;

import com.ambition.balancedSymbols.rule.api.IRule;

public class SequentialRuleImpl implements IRule, Comparable<SequentialRuleImpl>  {
	
	private SequentialRuleImpl root;
	private HashMap<Character,SortedSet<SequentialRuleImpl>> ruleMap;
	private HashSet<SequentialRuleImpl> allRules;
	
	/**
	 * Root Constructor
	 */
	public SequentialRuleImpl() {
		this.root = this;
		this.ruleMap = new HashMap<Character,SortedSet<SequentialRuleImpl>>();
		this.allRules = new HashSet<SequentialRuleImpl>();
	}
	
	/**
	 * Child Constructor
	 */
	public SequentialRuleImpl(SequentialRuleImpl root) {
		this.root = root;
		this.root.register( this );
	}

	
	private void register(SequentialRuleImpl sequentialRuleImpl) {
		allRules.add( sequentialRuleImpl );
	}

	// -------------------------- Construct DSL -------------------------- //

	/**
	 * Adds and returns a new sequential rule. When completed, each rule gets 
	 * merged into the ruleMap for better performance.
	 */
	public SequentialRuleImpl add() {
		return new SequentialRuleImpl( root );
	}
	
	
	
	private char pre;
	
	/**
	 * Rule is defined to hold after this character occurs. 
	 * Can only be set once.
	 */
	public SequentialRuleImpl after(char c) {
		if ( pre != 0 )
			throw new IllegalStateException("Rule already declared after.");
		else
			pre = c;
		
		finishConstruction();
		
		return this;
	}

	
	
	private boolean must = false;
	
	/**
	 * Rule is MUST be satisfied for validation - mandatory. 
	 * Can't be both obligatory and mandatory or set twice.
	 */
	public SequentialRuleImpl must() {
		if ( may )
			throw new IllegalStateException("Rule already declared may.");
		else
			must = true;
		
		finishConstruction();
		
		return this;
	}

	
	
	private boolean may = false;

	/**
	 * Rule is MAY be satisfied for validation - obligatory.
	 * Can't be both obligatory and mandatory or set twice.
	 */
	public SequentialRuleImpl may() {
		if ( must )
			throw new IllegalStateException("Rule already declared must.");
		else
			may = true;
		
		finishConstruction();
		
		return this;
	}

	
	private boolean later;
	private char post;

	/**
	 * Characters follows directly after.
	 * Can't be both directly and later or set twice.
	 */
	public SequentialRuleImpl directly(char c) {
		
		if ( post != 0 )
			throw new IllegalStateException("Rule already declared directly/later.");
		else if ( c == 0 )
			throw new IllegalArgumentException("Null character not accepted");
		
		post = c;
		later = false;
		
		finishConstruction();
		
		return this;
	}
	
	
	/**
	 * Characters follows later after.
	 * Can't be both directly and later or set twice.
	 */
	public SequentialRuleImpl later(char c) {
		// TODO DRY UP
		if ( post != 0 )
			throw new IllegalStateException("Rule already declared directly/later.");
		else if ( c == 0 )
			throw new IllegalArgumentException("Null character not accepted");
		
		post = c;
		later = true;
		
		finishConstruction();
		
		return this;
	}
	
	
	// TODO
	boolean satisfyLaterOrdered = true;
	
	/**
	 * later rules must preserve order
	 */
	public void satisfyLaterOrdered() {
		this.satisfyLaterOrdered = true;
	}
	

	
	// -------------------------- Merge -------------------------- //
	
	private boolean finished;
	
	private void finishConstruction()
	{
		finished = pre != 0
			&& ( may || must )
			&& ( post != 0 );
		
		if ( finished )
			root.merge( this );
	}
	
	
	/**
	 * child rules get merged into the root rule, so one hash gets constructed out of
	 * all rules instead of having to check each rule
	 */
	private void merge(SequentialRuleImpl sequentialRuleImpl) {

		SortedSet<SequentialRuleImpl> rulesPost = ruleMap.get(sequentialRuleImpl.post);
		
		// rules are handled whilst looking backward (to left), thus sort by rule post symbol
		if ( rulesPost == null )
		{
			rulesPost = new TreeSet<SequentialRuleImpl>();
			ruleMap.put( sequentialRuleImpl.post , rulesPost );
		}
		rulesPost.add( sequentialRuleImpl );
		
		// TODO test
		if ( sequentialRuleImpl.must )
		{
			SortedSet<SequentialRuleImpl> rulesPre = ruleMap.get(sequentialRuleImpl.pre);
			
			// obligatory rules must be called for stacking also
			if ( rulesPre == null )
			{
				rulesPre = new TreeSet<SequentialRuleImpl>();
				ruleMap.put( sequentialRuleImpl.pre , rulesPre );
			}
			rulesPre.add( sequentialRuleImpl );
			
			for ( SequentialRuleImpl rule : allRules )
			{
				if ( rule != sequentialRuleImpl && rule.must && rule.pre == sequentialRuleImpl.pre )
					throw new IllegalStateException("Only one obligatory rule can be set at a time");
			}
		}
		
	}

	// TODO Use Builder instead
	/**
	 * sanity check for completeness of rule definition
	 * 
	 * root rule checks all childs, childs check themselves.
	 */
	public boolean getFinished() {
		
		if ( this.root == this )
		{	
			for ( SequentialRuleImpl sequentialRule : allRules )
			{
				if ( !sequentialRule.finished )
					return false;
			}
			
			return true;
		}
		else
			return finished;
	}
	
	

	@Override
	public int compareTo(SequentialRuleImpl o) {
		
		int result = 1;
		
		if ( this.must && !o.must )
			result = -1;
		else if ( !this.must && o.must )
			result = 1;
		
		return result;
	}

	
	// -------------------------- Evaluate -------------------------- //

	public boolean satisfied(char currentChar, char lastChar, Stack<Character> stack) {
		
		boolean satisfiedSequence = false;
		
		// no related rules for this char found, all good
		if ( !ruleMap.containsKey( currentChar ) )
			return true;
		
		for ( SequentialRuleImpl rule : ruleMap.get( currentChar ))
		{
			if ( rule.must || !satisfiedSequence )
			{
				// stack
				if ( rule.later )
				{
					if ( rule.pre == currentChar  )
					{
						// stacking the current char will only help to satisfy the closing symbol
						stack.push( currentChar );
					}
					else if ( rule.satisfyLaterOrdered )
					{
						if ( !stack.empty() && rule.pre == stack.pop() )
							satisfiedSequence = true;
					}
					else
					{
						if ( !stack.empty() && stack.remove( new Character( rule.pre )))
							satisfiedSequence = true;
					}
				}
				// lastChar
				else
				{
					if ( rule.pre == lastChar && rule.post == currentChar )
						satisfiedSequence = true;
				}
			}
			
			// TODO test sorted order, test break
			// Assumes sorted order: must first
			if ( !rule.must && satisfiedSequence )
				break;
		}
		
		return satisfiedSequence;
	}
}
