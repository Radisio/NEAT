package GeneticAlgorithm.FitnessComparison;

public class XORComparison implements FitnessComparison{
    @Override
    public boolean fitness1Better(double fitness1, double fitness2) {
        return Double.compare(fitness1, fitness2)>0;
    }

    @Override
    public int order(double fintess1, double fitness2) {
        int output = Double.compare(fintess1, fitness2);
        return Integer.compare(0, output);
    }

    @Override
    public double worstFitness() {
        return -1;
    }
}
