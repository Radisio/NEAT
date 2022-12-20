package GeneticAlgorithm.FitnessComputation;

import NeuralNetwork.NeuralNetwork;

public abstract class FitnessComputation {
    protected NeuralNetwork neuralNetwork;
    protected double fitness;
    protected double adjustedFitness;

    public FitnessComputation(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }

    public abstract void computeFitness();
    public abstract void debugCompute();
    public NeuralNetwork getNeuralNetwork() {
        return neuralNetwork;
    }

    public void setNeuralNetwork(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getAdjustedFitness() {
        return adjustedFitness;
    }

    public void setAdjustedFitness(double adjustedFitness) {
        this.adjustedFitness = adjustedFitness;
    }



}
