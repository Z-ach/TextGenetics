
public class Main {

	public static void main(String[] args) {
		//Cat cat = new Cat(1);
		/*
		 * double total = 0; 
		 */
		long startTime = System.currentTimeMillis();
		int count = 1;
		int totalGens = 0;
		GeneticLoop gloop;
		for(int i = 0; i < count; i++){
			gloop = new GeneticLoop();
			totalGens +=gloop.run();
			if(i % 25 == 0 && i != 0)
				System.out.println("Reached " + i + " runs");
		}
		System.out.println("total gens: " + totalGens);
		System.out.println("average gens: " + ((double)totalGens/count));
		  long totalTime = System.currentTimeMillis() - startTime;
		  System.out.println("Times run: " + count);
		 System.out.println("Total runtime: " + totalTime + " ms");
		 System.out.println("Average runtime: " + (double)(totalTime/count) + " ms");

	}

}
