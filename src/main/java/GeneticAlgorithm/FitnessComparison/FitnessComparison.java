package GeneticAlgorithm.FitnessComparison;

public interface FitnessComparison {

    boolean fitness1Better(double fitness1, double fitness2);
    int order(double fintess1, double fitness2);
}
