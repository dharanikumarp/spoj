package sbank.tries;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;

public class Main {

	private static final int ACC_NUM_SIZE = 26;
	private static final int ACC_NUM_SIZE_WITH_FORMATTING = ACC_NUM_SIZE + 6;

	private static final boolean PRINT_TIMINGS = false;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in), 16384);

		long startTime, endTime;
		// Buffer to capture the timings.
		StringBuilder timingsLogger = new StringBuilder(1024);

		byte numTestCases = Byte.parseByte(br.readLine());

		for (byte i = 0; i < numTestCases; i++) {
			int numAccounts = Integer.parseInt(br.readLine());

			timingsLogger.append("iteration ").append(i)
					.append(", numAccounts ").append(numAccounts).append('\n');
			startTime = System.currentTimeMillis();

			AccountsTrie trie = new Main().new AccountsTrie();
			for (int j = 0; j < numAccounts; j++) {
				String accNum = br.readLine();
				trie.insert(accNum);
			}
			if (i != numTestCases - 1) {
				br.readLine();
			}

			endTime = System.currentTimeMillis();
			timingsLogger.append("Time to parse and insert strings ")
					.append(endTime - startTime).append('\n');

			startTime = System.currentTimeMillis();
			Queue<String> allAccounts = trie.accountsWithRecurrence();
			int size = allAccounts.size();

			timingsLogger.append("size of all accounts : ").append(size)
					.append('\n');

			StringBuilder sb = new StringBuilder(size * (32 + 2));

			while (!allAccounts.isEmpty()) {
				sb.append(allAccounts.dequeue()).append('\n');
			}

			System.out.print(sb.toString());

			if (i != numTestCases - 1) {
				System.out.println();
			}
			endTime = System.currentTimeMillis();
			timingsLogger.append("Time to print the strings ")
					.append(endTime - startTime).append('\n');
		}
		br.close();

		if (PRINT_TIMINGS) {
			System.out.println("timings " + timingsLogger.toString());
		}
	}

	public class AccountsTrie {

		// To track the size of the trie (number of keys)
		private int size;

		private Node root;

		// 11 because we can include space character as part of sorting at the
		// RADIX location in the array.
		public static final int RADIX = 11;

		public static final int ASCII_CODE_ZERO = 48;

		private class Node {
			public Node[] links = new Node[RADIX];
			// Stores the number of recurrence of each account number
			public int recurrence = 0;
		}

		/**
		 * Insert a new account number if not present in the TRIE else increment
		 * the value.
		 * 
		 * @param accountNumber
		 */
		public void insert(String accountNumber) {
			root = insert(root, accountNumber, 0);
		}

		/**
		 * Recursive implementation for the insert with update of recurrence
		 * when the same account number is inserted again.
		 * 
		 * @param x
		 * @param accountNumber
		 * @param depth
		 * @return the newly allocated node
		 */
		private Node insert(Node x, String accountNumber, int depth) {
			if (x == null) {
				x = new Node();
			}

			if (depth < accountNumber.length()) {
				char c = accountNumber.charAt(depth);
				int index = c != ' ' ? c - ASCII_CODE_ZERO : RADIX - 1;
				x.links[index] = insert(x.links[index], accountNumber,
						depth + 1);
			} else {
				// We have reached the end of account number
				size++;
				x.recurrence++;
			}
			return x;
		}

		/**
		 * @return the {@link Iterable} of sorted account identifiers
		 */
		public Queue<String> accountsWithRecurrence() {
			Queue<String> list = new Queue<String>();
			if (root != null) {
				collect(root, list, new StringBuilder(
						ACC_NUM_SIZE_WITH_FORMATTING));
			}
			return list;
		}

		/**
		 * Recursive implementation to collect the keys in the natural order
		 * 
		 * @param x
		 * @param strings
		 * @param prefix
		 */
		private void collect(Node x, Queue<String> strings, StringBuilder prefix) {
			if (x.recurrence == 0) {
				for (int i = 0; i < x.links.length; i++) {
					if (x.links[i] != null) {
						if (i == RADIX - 1) {
							prefix.append(' ');
						} else {
							prefix.append(i);
						}
						collect(x.links[i], strings, prefix);
						prefix.deleteCharAt(prefix.length() - 1);
					}
				}
			} else {
				prefix.append(x.recurrence);
				strings.enqueue(prefix.toString());
				prefix.deleteCharAt(prefix.length() - 1);
			}
		}

		public int getSize() {
			return this.size;
		}
	}

	public class Queue<Item> {
		private int N; // number of elements on queue
		private Node<Item> first; // beginning of queue
		private Node<Item> last; // end of queue

		// helper linked list class
		private class Node<Item1> {
			private Item1 item;
			private Node<Item1> next;
		}

		/**
		 * Initializes an empty queue.
		 */
		public Queue() {
			first = null;
			last = null;
			N = 0;
		}

		/**
		 * Is this queue empty?
		 * 
		 * @return true if this queue is empty; false otherwise
		 */
		public boolean isEmpty() {
			return first == null;
		}

		/**
		 * Returns the number of items in this queue.
		 * 
		 * @return the number of items in this queue
		 */
		public int size() {
			return N;
		}

		/**
		 * Adds the item to this queue.
		 * 
		 * @param item
		 *            the item to add
		 */
		public void enqueue(Item item) {
			Node<Item> oldlast = last;
			last = new Node<Item>();
			last.item = item;
			last.next = null;
			if (isEmpty()) {
				first = last;
			} else {
				oldlast.next = last;
			}
			N++;
		}

		/**
		 * Removes and returns the item on this queue that was least recently
		 * added.
		 * 
		 * @return the item on this queue that was least recently added
		 * @throws java.util.NoSuchElementException
		 *             if this queue is empty
		 */
		public Item dequeue() {
			if (isEmpty())
				throw new NoSuchElementException("Queue underflow");
			Item item = first.item;
			first = first.next;
			N--;
			if (isEmpty()) {
				last = null; // to avoid loitering
			}
			return item;
		}
	}
}
