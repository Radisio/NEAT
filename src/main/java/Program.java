import GeneticAlgorithm.Crossover.CrossOver;
import GeneticAlgorithm.Crossover.CrossOverKeepFromBest;
import GeneticAlgorithm.FitnessComparison.XORComparison;
import GeneticAlgorithm.FitnessComputation.FitnessComputation;
import GeneticAlgorithm.GeneticAlgorithm;
import GeneticAlgorithm.Selection.Selection;
import GeneticAlgorithm.Selection.TournamentSelection;
import NeuralNetwork.ActivationFunction.ActivationFunction;
import NeuralNetwork.ActivationFunction.GaussianFunction;
import NeuralNetwork.ActivationFunction.SigmoidFunction;
import NeuralNetwork.Connection;

public class Program {
    public static void main(String[] args) {
        GeneticAlgorithm ga = new GeneticAlgorithm(
        0.04, 0.1,
        0.3, 0.3, 0.1,
        3.6,0.4, 50, new TournamentSelection(2),
                new TournamentSelection(2), new CrossOverKeepFromBest(), "XOR",
        2, 1, 0, /*new SigmoidFunction(4.9)*/new GaussianFunction(),
                /*new SigmoidFunction(4.9)*/new GaussianFunction(), 0.3, new XORComparison(),
                5, 0.3
        );
        FitnessComputation fc =ga.runAlgorithm(5000);
        System.out.println("FC Score : " + fc.getFitness());
        System.out.println("FC Node : " + fc.getNeuralNetwork().getNodeList().size());
        for(Connection con : fc.getNeuralNetwork().getConnectionList())
        {
            System.out.println("Con ("+con.getInnovationNumber()+") :");
            System.out.println("In : " + con.getIdNodeIn());
            System.out.println("Out : " + con.getIdNodeOut());
            System.out.println("Weight : " + con.getWeight());
            System.out.println("Enabled ? : " + con.isEnabled());
        }
        fc.debugCompute();
    }
}
