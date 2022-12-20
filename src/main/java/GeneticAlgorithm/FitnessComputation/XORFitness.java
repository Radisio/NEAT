package GeneticAlgorithm.FitnessComputation;

import NeuralNetwork.NeuralNetwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class XORFitness extends FitnessComputation{
    public XORFitness(NeuralNetwork neuralNetwork) {
        super(neuralNetwork);
    }

    @Override
    public void computeFitness() {
        List<List<Double>> entries = new ArrayList<>();
        entries.add(Arrays.asList(1.0,1.0, 0.0));
        entries.add(Arrays.asList(0.0,0.0, 0.0));
        entries.add(Arrays.asList(1.0,0.0, 1.0));
        entries.add(Arrays.asList(0.0,1.0, 1.0));
        Collections.shuffle(entries);
        double error = 0.0;
        for(int i =0;i<4;i++)
        {
            neuralNetwork.loadInput(entries.get(i).subList(0,2));
            neuralNetwork.runNetwork();
            double output = neuralNetwork.getOutputs().get(0);
            error+= Math.abs(entries.get(i).get(2)-output);

        }
        this.fitness = 4-error;
    }
    @Override
    public void debugCompute(){
        List<List<Double>> entries = new ArrayList<>();
        entries.add(Arrays.asList(1.0,1.0, 0.0));
        entries.add(Arrays.asList(0.0,0.0, 0.0));
        entries.add(Arrays.asList(1.0,0.0, 1.0));
        entries.add(Arrays.asList(0.0,1.0, 1.0));
        //Collections.shuffle(entries);
        double error = 0.0;
        for(int i =0;i<4;i++)
        {
            neuralNetwork.loadInput(entries.get(i).subList(0,2));
            neuralNetwork.runNetwork();
            double output = neuralNetwork.getOutputs().get(0);
            error+= Math.abs(entries.get(i).get(2)-output);
            System.out.println("Inputs ("+entries.get(i).get(0)+"-" + entries.get(i).get(1)+") -> Output : " + output);
        }
    }


}
