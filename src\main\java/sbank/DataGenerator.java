package sbank;

import java.util.Random;

/**
 * Generate random test data to verify SBANK problem in SPOJ The algorithm
 * creates a seed for every test case and generate random strings by altering a
 * number at random position using the previous seed.
 * 
 * 
 * Invoke the program as follows to redirect the text to a file. java
 * DataGenerator <numTestcases> <numRecordsPerTestCase> > <file>
 * 
 * for example java DataGenerator 5 100000 > testcase1.txt
 * 
 * @author dharanikumar
 */
public class DataGenerator {
	public static final int ACC_NUM = 26;

	// Including the space at the end of the account number
	public static final int ACC_NUM_FORMATTED = 26 + 7;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int numTestCases = Integer.valueOf(args[0]);
		int recordsPerTestCase = Integer.valueOf(args[1]);

		Random randRadix = new Random();
		Random randPosition = new Random();

		System.out.println(numTestCases);

		for (int i = 0; i < numTestCases; i++) {

			// Create buffer to hold generated account numbers including new
			// line characters
			StringBuilder sb = new StringBuilder((numTestCases + 1)
					* (ACC_NUM_FORMATTED + 2));
			String seed = getRandAccNum(randRadix);

			sb.append(recordsPerTestCase).append('\n');
			sb.append(seed).append('\n');

			for (int j = 1; j < recordsPerTestCase; j++) {
				String accNum = generateAccountNumber(randRadix, randPosition,
						seed);
				sb.append(accNum).append('\n');
				seed = accNum;
			}
			System.out.println(sb.toString());
		}
	}

	/**
	 * Generate a random account number by iterating over all possible positions
	 * and using a Random generator as input.
	 * 
	 * @param randRadix
	 *            random generator for generating radix
	 * @return String having the formatted account number.
	 */
	private static String getRandAccNum(Random randRadix) {
		StringBuilder sb = new StringBuilder(ACC_NUM_FORMATTED);

		for (int i = 0; i < ACC_NUM_FORMATTED - 1; i++) {
			if (i == ACC_NUM_FORMATTED - 2 || i == 26 || i == 21 || i == 16
					|| i == 11 || i == 2) {
				sb.append(' ');
			} else {
				sb.append(randRadix.nextInt(10));
			}
		}
		return sb.toString();
	}

	/**
	 * Generates a random account number using a seed account number by randomly
	 * changing a value at a position.
	 * 
	 * @param randRadix
	 *            Random generator used to generate numbers between 0 to 9
	 * @param randPosition
	 *            Random generator used to generate positions between 0 to 32
	 * @param prevAccountNumber
	 *            Seed used for generating new number
	 * @return String having the formatted account number.
	 */
	private static String generateAccountNumber(Random randRadix,
			Random randPosition, String prevAccountNumber) {

		String accountString = null;

		while (true) {
			int i = randPosition.nextInt(ACC_NUM_FORMATTED - 1);

			// Ignore the places where ' ' character is present.
			if (i == ACC_NUM_FORMATTED - 2 || i == 26 || i == 21 || i == 16
					|| i == 11 || i == 2) {
				continue;
			} else {
				int number = randRadix.nextInt(10);

				if (number == Integer.valueOf(prevAccountNumber.charAt(i))) {
					continue;
				} else {
					char[] accountNumber = prevAccountNumber.toCharArray();
					accountNumber[i] = Integer.valueOf(number).toString()
							.charAt(0);
					accountString = new String(accountNumber);
					break;
				}
			}
		}

		return accountString;
	}
}
