import java.util.Comparator;

public class CatComparator implements Comparator<CatGenetics>{

	public int compare(CatGenetics c1, CatGenetics c2) {
		if(c1.getFitness() > c2.getFitness())
			return -1;
		else if(c1.getFitness() < c2.getFitness())
			return 1;
		return c1.dnaForPrint().compareTo(c2.dnaForPrint());
	}

}
