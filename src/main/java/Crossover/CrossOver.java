package Crossover;

import FitnessComputation.FitnessComputation;
import NeuralNetwork.NeuralNetwork;

public interface CrossOver {
    NeuralNetwork crossover(FitnessComputation fitnessComputation1, FitnessComputation fitnessComputation2);
}
