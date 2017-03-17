import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GeneticLoop {

	private final int TOTAL_SIZE = 50;
	private final String SOLUTION = "Be not afraid of greatness: some are born great. Some have pickles in their pockets.";
	private final double MUTATION_RATE = .01;

	private boolean done = false;

	private Random rand;
	private List<CatGenetics> population;
	private List<CatGenetics> matingPool;
	private String alphabet = "abcdefghijklmnopqrstuvwxyz";
	private String symbols = ",.?/!@#$%^&*()_+-=`~'\":;<> ";
	private String numbers = "0123456879";
	private String character = alphabet + alphabet.toUpperCase() + numbers + symbols;
	private final int DNA_LENGTH = SOLUTION.length();

	public GeneticLoop() {
		population = new ArrayList<CatGenetics>();
		rand = new Random();
		population = initPopulation(TOTAL_SIZE);
		//run();
	}

	public int run() {
		int generations = 0;
		while (!done) {
			generations++;
			evaluateFitness();
			if(done)
				break;
			allowView();
			//System.out.println("CURRENT POPULATION:");
			printList(population, false);
			populateMatingPoolRoulette();
			//System.out.println("MATING POOL:");
			printList(matingPool, false);
			repopulate();
		}
		//System.out.println("---- END OF EVOLUTION ----");
		printList(population, false);
		allowView();
		//System.out.println("Took " + generations + " generations.");
		return generations;
	}
	
	private void allowView(){
		System.out.println("\n\nTrue:" + SOLUTION);
		System.out.printf("\nTop 5 contenders:\t\tMax Fitness:\t%d\t\tComplete:\t%.4f%%\n", DNA_LENGTH*DNA_LENGTH, (double)(100*population.get(0).actualFitness/(DNA_LENGTH*DNA_LENGTH)));
		Collections.sort(population, new CatComparator());
		for(int i = 0 ; i < 5; i++)
			System.out.println(population.get(i).dnaForPrint() + "\t,\t" + population.get(i).actualFitness);
/*		try {
		    Thread.sleep(10);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}*/
	}

	private List<CatGenetics> initPopulation(int popSize) {
		String randomString = "";
		for (int i = 0; i < popSize; i++) {
			randomString = "";
			for (int j = 0; j < DNA_LENGTH; j++)
				randomString += character.charAt(rand.nextInt(character.length()));
			char[] dna = new char[DNA_LENGTH];
			for (int j = 0; j < DNA_LENGTH; j++) {
				dna[j] = randomString.charAt(j);
			}
			population.add(new CatGenetics(dna));
		}
		return population;
	}

	private void repopulate() {
		//System.out.println("REPOPULATING");
		population.clear();
		//System.out.println("Reproducing from mating pool.");
		for (int i = 0; i < TOTAL_SIZE; i++) {
			//System.out.println("Currently adding member #" + (i+1) + "/" + TOTAL_SIZE);
			population.add(reproduce());
		}
	}

	private CatGenetics reproduce() {
		char[] newDNA = new char[DNA_LENGTH];
		CatGenetics[] c = {rouletteWheelSpin(), rouletteWheelSpin()};
		
		for(int i = 0; i < c.length; i++){
			while(c[i] == null)
				c[i] = rouletteWheelSpin();
		}
		
		//System.out.println("Roulette Wheel has spun.");
		//System.out.println("Result 1: " + c[0].dnaForPrint());
		//System.out.println("Result 2: " + c[1].dnaForPrint());
		
		for (int i = 0; i < DNA_LENGTH; i++) {
			if (rand.nextBoolean()) {
				newDNA[i] = c[0].getDna()[i];
			} else
				newDNA[i] = c[1].getDna()[i];
		}
		return new CatGenetics(mutation(newDNA));
	}
	
	private char[] mutation(char[] newDNA){
		for(int i = 0; i < newDNA.length; i++){
			if(rand.nextDouble() <= MUTATION_RATE)
				newDNA[i] = character.charAt(rand.nextInt(character.length()));
		}
		return newDNA;
	}

	private CatGenetics rouletteWheelSpin() {
		double sum = 0;
		double spin = rand.nextDouble();
		for (CatGenetics c : matingPool) {
			sum += c.getFitness();
			if (sum >= spin) {
				return c;
			}
		}
		return null;
	}
	
	private void fitnessByRank(){
		Collections.sort(population, new CatComparator());
		for(int i = 0; i < TOTAL_SIZE; i++){
			population.get(i).setFitness(TOTAL_SIZE - i);
		}
	}
	
	private CatGenetics rankingSpin() {
		int totalFitness = 0;
		for(CatGenetics c : matingPool)
			totalFitness += c.getFitness();
		int storage = 0;
		int randInt = rand.nextInt(totalFitness) + 1;
		for(int i = matingPool.size(); i > 0; i--){
			if(totalFitness <= randInt){
				storage = i;
				break;
			}
			totalFitness -= i;
			
		}
		return matingPool.get(matingPool.size() - storage);
	}
	
	private List<CatGenetics> populateMatingPoolRank() {
		fitnessByRank();
		matingPool = new ArrayList<CatGenetics>();
		for(int i = 0; i < TOTAL_SIZE; i++)
			matingPool.add(population.get(i));
		//System.out.println("mating pool returned");
		return matingPool;
	} 
	
	private List<CatGenetics> populateMatingPoolRoulette() {
		matingPool = new ArrayList<CatGenetics>();
		List<CatGenetics> temp = new ArrayList<CatGenetics>();
		for(int i = 0; i < TOTAL_SIZE; i++){
			if(population.get(i).getFitness() > 0)
				temp.add(population.get(i));
		}
		double[] fit = new double[temp.size()];
		for(int i = 0; i < temp.size(); i++){
			fit[i] = temp.get(i).getFitness() * TOTAL_SIZE;
		}
		//System.out.println("fit length " + fit.length);
		
		for(int i = 0; i < fit.length; i++){
			//System.out.println("fit length" + fit[i]);
			for(int j = 0; j < (int)fit[i]; j++){
				//System.out.println("wearwawawrawr");
				matingPool.add(temp.get(i));
				//System.out.println("matingPool[" + i + "].getFitness() = " + temp.get(i).getFitness());
			}
		}
		
		System.out.println("Mating pool current size: " + matingPool.size());
		while(matingPool.size() < TOTAL_SIZE){
			switch(matingPool.size() % 3){
			case 0:
				matingPool.add(temp.get(0));
				break;
			case 1:
				matingPool.add(temp.get(1));
				break;
			case 2:
				matingPool.add(temp.get(2));
				break;
			}
		}
		Collections.sort(matingPool, new CatComparator());
		System.out.println("Mating pool size returned: " + matingPool.size());
		return matingPool;
	}

	private void printList(List<CatGenetics> list, boolean print) {
		Collections.sort(list, new CatComparator());
		if(print)
			for (CatGenetics c : list)
				System.out.println(c.dnaForPrint() + ", " + c.getFitness());
	}

	private void evaluateFitness() {
		int fitness = 0;
		for (CatGenetics c : population) {
			fitness = 0;
			for (int i = 0; i < DNA_LENGTH; i++)
				if (c.getDna()[i] == SOLUTION.charAt(i))
					fitness++;
			c.setFitness((double) fitness*fitness);
			c.actualFitness = (double)fitness*fitness;
			if (fitness == DNA_LENGTH) {
				done = true;
				break;
			}
		}
		if (!done)
			normalizeFitness();
	}

	private void normalizeFitness() {
		double totalFitness = 0;
		for (CatGenetics c : population) {
			totalFitness += c.getFitness();
		}
		//System.out.println("Total Fitness was " + totalFitness);
		for (CatGenetics c : population) {
			c.setFitness(c.getFitness() / totalFitness);
		}
	}

}
