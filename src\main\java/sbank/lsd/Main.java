package sbank.lsd;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static final int ACC_SIZE = 26;
	private static final int ACC_NUM_SIZE_WITH_FORMATTING = ACC_SIZE + 6;

	private static final boolean PRINT_TIMINGS = false;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in), 16384);

		long startTime, endTime;
		long overAllStartTime = System.currentTimeMillis();

		// Buffer to capture the timings.
		StringBuilder sbTimingsLog = new StringBuilder(1024);
		byte numTestCases = Byte.parseByte(br.readLine());

		for (byte i = 0; i < numTestCases; i++) {

			int numAccounts = Integer.parseInt(br.readLine());
			sbTimingsLog.append(
					"iteration " + i + ", numAccounts " + numAccounts).append(
					'\n');

			startTime = System.currentTimeMillis();
			Map<String, AccountNumber> countMap = new HashMap<String, AccountNumber>(
					numAccounts);

			for (int j = 0; j < numAccounts; j++) {
				String accNum = br.readLine();
				AccountNumber valueInMap = countMap.get(accNum);

				if (valueInMap == null) {
					AccountNumber digits = new Main().new AccountNumber(accNum);
					countMap.put(accNum, digits);
				} else {
					valueInMap.incrementCount();
				}
			}

			if (i != numTestCases - 1) {
				br.readLine();
			}

			AccountNumber[] accounts = new AccountNumber[countMap.size()];
			countMap.values().toArray(accounts);
			endTime = System.currentTimeMillis();
			sbTimingsLog.append("Time to parse & count recurrences ")
					.append(endTime - startTime).append('\n');

			startTime = System.currentTimeMillis();
			lsdSort(accounts);
			endTime = System.currentTimeMillis();

			sbTimingsLog.append("Time taken to sort the data using lsd ")
					.append(endTime - startTime).append('\n');

			startTime = System.currentTimeMillis();

			StringBuilder sb = new StringBuilder(countMap.size()
					* (ACC_NUM_SIZE_WITH_FORMATTING + 2));

			for (int j = 0; j < accounts.length; j++) {
				sb.append(accounts[j]).append('\n');
			}

			System.out.println(sb.toString());
			if (i != numTestCases - 1) {
				System.out.println();
			}

			endTime = System.currentTimeMillis();
			sbTimingsLog.append("time taken to print the output ")
					.append(endTime - startTime).append('\n');
		}

		sbTimingsLog.append("Overall time taken is ")
				.append(System.currentTimeMillis() - overAllStartTime)
				.append('\n');

		if (PRINT_TIMINGS) {
			System.out.println(sbTimingsLog.toString());
		}
	}

	public class AccountNumber {
		private byte[] digits;
		private String str;
		private int recurrence = 1;

		public AccountNumber(String accNum) {
			digits = new byte[ACC_SIZE];
			int j = 0;
			for (int i = accNum.length() - 1; i >= 0; i--) {
				if (accNum.charAt(i) != ' ') {
					int num = ((int) accNum.charAt(i) - 48);
					this.digits[j++] = (byte) num;
				}
			}
		}

		public int valueAt(int location) {
			return this.digits[location];
		}

		public boolean less(AccountNumber that, int fromIndex) {
			boolean less = false;
			for (int i = fromIndex; i < ACC_SIZE; i++) {
				if (this.digits[i] < that.digits[i]) {
					less = true;
					break;
				}
			}
			return less;
		}

		@Override
		public String toString() {
			if (str == null) {
				// prevent buffer doubling by initializing to required size.
				StringBuilder sb = new StringBuilder(
						ACC_NUM_SIZE_WITH_FORMATTING + 2);

				int i = ACC_SIZE - 1;

				sb.append(digits[i--]);
				sb.append(digits[i--]);
				sb.append(' ');

				for (int j = 0; j < 8; j++) {
					sb.append(digits[i--]);
				}

				for (int j = i, k = 0; j >= 0; j--, k++) {
					if (k % 4 == 0) {
						sb.append(' ');
					}
					sb.append(digits[j]);
				}
				sb.append(' ');
				sb.append(recurrence);
				str = sb.toString();
			}
			return str;
		}

		public void incrementCount() {
			recurrence++;
		}
	}

	public static void lsdSort(AccountNumber[] a) {
		int N = a.length;
		int R = 10; // Radix
		AccountNumber[] aux = new AccountNumber[N];

		for (int d = 0; d < ACC_SIZE; d++) {
			// sort by key-indexed counting on dth character

			// compute frequency counts
			int[] count = new int[R + 1];
			for (int i = 0; i < N; i++)
				count[a[i].valueAt(d) + 1]++;

			// compute cumulates
			for (int r = 0; r < R; r++)
				count[r + 1] += count[r];

			// move data
			for (int i = 0; i < N; i++)
				aux[count[a[i].valueAt(d)]++] = a[i];

			// copy back
			for (int i = 0; i < N; i++)
				a[i] = aux[i];
		}
	}
}