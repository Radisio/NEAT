import GeneticAlgorithm.FitnessComparison.XORComparison;
import org.junit.Test;

public class FitnessComparison {

    @Test
    public void testComparison(){
        double fitness1 = 2.0;
        double fitness2 = 3.5;
        XORComparison fc = new XORComparison();
        assert fc.fitness1Better(fitness2,fitness1);

    }
}
