package sbank;

import java.util.Random;
import java.util.Scanner;

/**
 * Generate test data to verify SBANK problem in SPOJ
 * 
 * The program will generate random test data once and shuffle the same into set
 * of numTestCases.
 * 
 * Invoke the program as follows to redirect the text to a file.
 * 
 * java DataGenerator <numTestcases> <numRecordsPerTestCase> > <file>
 * 
 * for example java DataGenerator 5 100000 > testcase1.txt
 * 
 * @author dharanikumar
 */
public class DataGenerator {
	public static final int ACC_NUM = 26;
	public static final int ACC_NUM_FORMATTED = 26 + 7;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int numTestCases = sc.nextInt();
		int recordsPerTestCase = sc.nextInt();

		Random randRadix = new Random();

		System.out.println(numTestCases);

		for (int i = 0; i < numTestCases; i++) {

			// Create buffer to hold generated account numbers including new
			// line characters
			StringBuilder sb = new StringBuilder((numTestCases + 1)
					* (ACC_NUM_FORMATTED + 2));

			sb.append(numTestCases).append('\n');

			for (int j = 0; j < recordsPerTestCase; j++) {
				sb.append(generateAccountNumber(randRadix)).append('\n');
			}
			System.out.println(sb.toString());
		}

		sc.close();
	}

	private static String generateAccountNumber(Random randRadix) {
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
}
