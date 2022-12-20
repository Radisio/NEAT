package GeneticAlgorithm.Crossover;

import GeneticAlgorithm.FitnessComputation.FitnessComputation;
import NeuralNetwork.NeuralNetwork;

public interface CrossOver {
    NeuralNetwork crossover(FitnessComputation fitnessComputation1, FitnessComputation fitnessComputation2);
}
