package main;

import main.numbers.JIntegerNaturalNumber;

public class MultiplicationPersistenceApplication {
	public static void main(String[] args) {

		MultiplicationPersistenceCalculator multiplicationPersistence = new MultiplicationPersistenceCalculator();
	//	multiplicationPersistence.findNumbersWithBiggestMultiplicativePersistence();
 
		multiplicationPersistence.findNumberHavingMultiplicative(new JIntegerNaturalNumber(277777788888899L));
	}

}
