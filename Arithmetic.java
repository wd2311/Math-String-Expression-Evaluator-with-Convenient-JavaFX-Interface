package solver;

import java.util.ArrayList;

public class Arithmetic {

	ArrayList<Character> partOfNumber;
	
	public Arithmetic(){
		partOfNumber = new ArrayList<Character>();
		for(int i = 0; i < 10; i ++){
			partOfNumber.add((char) (i + 48));
		}//for
		partOfNumber.add('.');
		partOfNumber.add('-');
	}//Constructor
	
	public String eval(String expr){
		expr = removeSpaces(expr);
		expr = parentheses(expr);
		while(expr != exponents(expr)){
			expr = exponents(expr);
		}//while
		while(expr != multDiv(expr)){
			expr = multDiv(expr);
		}//while
		while(expr != addSubt(expr)){
			expr = addSubt(expr);
		}//while
		return expr;
	}//evaluate
	
	private String removeSpaces(String x){
		String newX = "";
		for(int i = 0; i < x.length(); i ++){
			if(x.charAt(i) != ' '){
				newX = newX + x.charAt(i);
			}//if
		}//for
		return newX;
	}//removeSpaces
	
	private String parentheses(String x){
		String parenthesesToBeEvaluated;
		int openCount = 0;
		int closeCount = 0;
		int lastOpenIndex = 0;
		for(int i = 0; i < x.length(); i ++){
			if(x.charAt(i) == '('){
				openCount += 1;
				for(int j = i + 1; j < x.length(); j ++){
					if(x.charAt(j) == '('){
						openCount += 1;
						lastOpenIndex = j;
					}//if
					if(x.charAt(j) == ')'){
						if(j + 1 < x.length()){
							if(x.charAt(j + 1) == '^'){
								String base = eval(x.substring(lastOpenIndex + 1, j));
								double b = Double.parseDouble(base);
								if(x.charAt(j + 2) == '('){
									/*
									 * search for end of parenthesis, and make 'power' equal to evaluate(x.substring(openIndex, closeIndex))
									 */
								}else{
									for(int k = 0; k < x.length(); k ++){
										/*
										 * search for end of 'power'
										 */
									}
								}
							}//if
						}//if
						closeCount += 1;
						if(openCount == closeCount){
							parenthesesToBeEvaluated = x.substring(i + 1, j);
							return x.substring(0, i) + eval(parenthesesToBeEvaluated) + parentheses(x.substring(j + 1, x.length()));
						}//if
					}//if
				}//for
			}//if
		}//for
		return x; //if there is no parenthesis magic to be done
	}//parentheses
	
	private String exponents(String x){
		String base = "";
		String power = "";
		for(int i = x.length() - 2; i >= 1; i --){
			if(x.charAt(i) == '^'){
				for(int j = i - 1; j >= 0; j --){
					
					if(x.charAt(j) == '-'){ // neg work
						base = x.substring(j + 1, i);
						break;
					}//if
					
					if(j == 0){
						base = x.substring(0, i);
						break;
					}else if(!partOfNumber.contains(x.charAt(j))){
						base = x.substring(j + 1, i);
						break;
					}//if else if
				}//for
				for(int j = i + 1; j < x.length(); j ++){
					
					if((j != i + 1) && (x.charAt(j) == '-')){ // neg work
						power = x.substring(i + 1, j);
						break;
					}//if
					
					if(j == (x.length() - 1)){
						power = x.substring(i + 1, j + 1);
						break;
					}else if(!partOfNumber.contains(x.charAt(j))){
						power = x.substring(i + 1, j);
						break;
					}//if else if
				}//for
				double b = Double.parseDouble(base);
				double p = Double.parseDouble(power);
				return x.substring(0, i - base.length()) + (Math.pow(b, p)) + x.substring(i + 1 + power.length() , x.length());
			}//if
		}//for
		return x;
	}//exponents
	
	private String multDiv(String x){
		String term1 = "";
		String term2 = "";
		for(int i = 1; i < x.length() - 1; i ++){
			if((x.charAt(i) == '*') || (x.charAt(i) == '/')){
				for(int j = i - 1; j >= 0; j --){ 
					
					if(x.charAt(j) == '-'){ // neg work
						term1 = x.substring(j + 1, i);
						break;
					}//if
					
					if(j == 0){
						term1 = x.substring(0, i);
						break;
					}else if(!partOfNumber.contains(x.charAt(j))){
						term1 = x.substring(j + 1, i);
						break;
					}//if else if
				}//for
				for(int j = i + 1; j < x.length(); j ++){
					
					if((j != i + 1) && (x.charAt(j) == '-')){ // neg work
						term2 = x.substring(i + 1, j);
						break;
					}//if
					
					if(j == (x.length() - 1)){
						term2 = x.substring(i + 1, j + 1);
						break;
					}else if(!partOfNumber.contains(x.charAt(j))){
						term2 = x.substring(i + 1, j);
						break;
					}//if else if
				}//for
				double t1 = Double.parseDouble(term1);
				double t2 = Double.parseDouble(term2);
				if(x.charAt(i) == '*'){
					return x.substring(0, i - term1.length()) + (t1*t2) + x.substring(i + 1 + term2.length() , x.length());
				}//if
				if(x.charAt(i) == '/'){
					return x.substring(0, i - term1.length()) + (t1/t2) + x.substring(i + 1 + term2.length() , x.length());
				}//if
			}//if
		}//for
		return x;
	}//multDiv
	
	private String addSubt(String x){
		String term1 = "";
		String term2 = "";
		for(int i = 1; i < x.length() - 1; i ++){
			if((x.charAt(i) == '+') || ((x.charAt(i) == '-') && partOfNumber.contains(x.charAt(i - 1)))){ // neg work
				for(int j = i - 1; j >= 0; j --){
					
					if(x.charAt(j) == '-'){ // neg work
						term1 = x.substring(j, i);
						break;
					}//if
					
					if(j == 0){
						term1 = x.substring(0, i);
						break;
					}else if(!partOfNumber.contains(x.charAt(j))){
						term1 = x.substring(j + 1, i);
						break;
					}//if else if
				}//for
				for(int j = i + 1; j < x.length(); j ++){
					
					if((j != i + 1) && (x.charAt(j) == '-')){ // neg work
						term2 = x.substring(i + 1, j);
						break;
					}//if
					
					if(j == (x.length() - 1)){
						term2 = x.substring(i + 1, j + 1);
						break;
					}else if(!partOfNumber.contains(x.charAt(j))){
						term2 = x.substring(i + 1, j);
						break;
					}//if else if
				}//for
				double t1 = Double.parseDouble(term1);
				double t2 = Double.parseDouble(term2);
				if(x.charAt(i) == '+'){
					return x.substring(0, i - term1.length()) + (t1+t2) + x.substring(i + 1 + term2.length() , x.length());
				}//if
				if(x.charAt(i) == '-'){
					return x.substring(0, i - term1.length()) + (t1-t2) + x.substring(i + 1 + term2.length() , x.length());
				}//if
			}//if
		}//for
		return x;
	}//addSubt
}//class