import java.util.Random;

public class Cat {

	/*
	 * takes on average 17547.61429 times to succeed
	 * 
	 * note: 26*26*26 = 17,576
	 * 
	 * STATS:
	 * Ran 100000 times
	 * Average count: 17547.61429
	 * Time taken: 322949 ms
	 * Average time per try: 3 ms
	 */

	private String goal = "aaaa";
	
	private String character = "abcdefghijklmnopqrstuvwxyz ";
	private Random rand;
	private double counter, total, average;

	public Cat(int times) {
		rand = new Random();
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < times; i++) {
			if (i % 50 == 0 && i != 0) {
				System.out.println("Times run so far: " + i);
				System.out.println("Elapsed time: " + (System.currentTimeMillis() - startTime) + " ms\n");
			}
			total += makeCat();
		}
		average = total / times;
		long timeTaken = System.currentTimeMillis() - startTime;
		System.out.println("STATS:");
		System.out.println("Ran " + times + " times.");
		System.out.println("Average count: " + average);
		System.out.println("Time taken: " + timeTaken + " ms");
		System.out.println("Average time per try: " + (timeTaken / times) + " ms");
		System.out.println("Attemps per ms: " + (total/timeTaken));
	}

	private double makeCat() {
		String cat = "";
		counter = 0;
		while (!cat.equals(goal)) {
			cat = "";
			counter++;
			for (int i = 0; i < goal.length(); i++)
				cat += character.charAt(rand.nextInt(character.length()));
		}
		return counter;
	}

}
