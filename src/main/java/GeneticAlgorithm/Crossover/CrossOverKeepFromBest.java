package GeneticAlgorithm.Crossover;

import GeneticAlgorithm.FitnessComputation.FitnessComputation;
import NeuralNetwork.NeuralNetwork;

import java.util.Random;

public class CrossOverKeepFromBest implements CrossOver{
    @Override
    public NeuralNetwork crossover(FitnessComputation fitnessComputation1, FitnessComputation fitnessComputation2) {
        NeuralNetwork bestNN, otherNN;
        if(fitnessComputation1.getFitness()>fitnessComputation2.getFitness()){
            bestNN = new NeuralNetwork(fitnessComputation1.getResetedNeuralNetwork());
            otherNN = new NeuralNetwork(fitnessComputation2.getResetedNeuralNetwork());
        }
        else if(fitnessComputation2.getFitness()>fitnessComputation1.getFitness())
        {
            bestNN = new NeuralNetwork(fitnessComputation2.getResetedNeuralNetwork());
            otherNN = new NeuralNetwork(fitnessComputation1.getResetedNeuralNetwork());
        }
        else{
            /// Random
            Random r = new Random();
            if(r.nextDouble()>0.5)
            {
                bestNN = new NeuralNetwork(fitnessComputation1.getResetedNeuralNetwork());
                otherNN = new NeuralNetwork(fitnessComputation2.getResetedNeuralNetwork());
            }
            else{
                bestNN = new NeuralNetwork(fitnessComputation2.getResetedNeuralNetwork());
                otherNN = new NeuralNetwork(fitnessComputation1.getResetedNeuralNetwork());
            }
        }
        int size1 = bestNN.getConnectionList().size();
        int size2 = otherNN.getConnectionList().size();
        Random r = new Random();
        for(int i = 0; i<size1;i++)
        {
            int innovationNumberBestNN = bestNN.getConnectionList().get(i).getInnovationNumber();
            for(int j = 0; j<size2;j++)
            {
                if(otherNN.getConnectionList().get(j).getInnovationNumber()==innovationNumberBestNN)
                {
                    if(r.nextDouble()<0.5){
                        bestNN.getConnectionList().get(i).setWeight(otherNN.getConnectionList().get(j).getWeight());
                        bestNN.getConnectionList().get(i).setEnabled(otherNN.getConnectionList().get(j).isEnabled());
                    }
                    break;
                }
            }
        }
        return bestNN;
    }
}
