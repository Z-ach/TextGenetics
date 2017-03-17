
public class CatGenetics {
	
	private char[] dna;
	private double fitness;
	public double actualFitness;
	
	public CatGenetics(char[] dna){
		this.setDna(dna);
	}

	public char[] getDna() {
		return dna;
	}

	public void setDna(char[] dna) {
		this.dna = dna;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public String dnaForPrint(){
		String temp = "";
		for(int i = 0; i < dna.length; i++){
			temp += dna[i];
		}
		return temp;
	}
	
}
