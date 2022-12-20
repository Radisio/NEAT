package GeneticAlgorithm.FitnessComputation;

import NeuralNetwork.ActivationFunction.ActivationFunction;
import NeuralNetwork.NeuralNetwork;

import java.util.ArrayList;
import java.util.List;

public class FitnessComputationUtil {

    public static List<FitnessComputation> generate(String name,int size, NeuralNetwork nn){
        List<FitnessComputation> returnedList = new ArrayList<>();
        switch(name.toUpperCase()){
            case "XOR":{
                for(int i =0;i<size;i++) {
                    returnedList.add(new XORFitness(nn));
                }
                break;
            }
            case "GAME":{
                throw new RuntimeException("Not implemented yet");
            }
            default:
                throw new RuntimeException("Name unknown");
        }
        return returnedList;
    }
    public static List<FitnessComputation> generate(String name,int size, int inputNode, int outputNode, int hiddenNode, ActivationFunction hiddenLayerActivationFunction,
                                                    ActivationFunction outputLayerActivationFunction){
        List<FitnessComputation> returnedList = new ArrayList<>();
        NeuralNetwork nn;
        switch(name.toUpperCase()){
            case "XOR":{
                for(int i =0;i<size;i++) {
                    nn = new NeuralNetwork(inputNode,outputNode, hiddenNode, hiddenLayerActivationFunction, outputLayerActivationFunction);
                    nn.initialize(1.0);
                    returnedList.add(new XORFitness(nn));
                }
                break;
            }
            case "GAME":{
                throw new RuntimeException("Not implemented yet");
            }
            default:
                throw new RuntimeException("Name unknown");
        }
        return returnedList;
    }
}
