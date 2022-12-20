package GeneticAlgorithm.FitnessComparison;

public class XORComparison implements FitnessComparison{
    @Override
    public boolean fitness1Better(double fitness1, double fitness2) {
        return Double.compare(fitness1, fitness2)>0;
    }

    @Override
    public int order(double fintess1, double fitness2) {
        return Double.compare(fintess1, fitness2);
    }
}
