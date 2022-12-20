import GeneticAlgorithm.Crossover.CrossOver;
import GeneticAlgorithm.Crossover.CrossOverKeepFromBest;
import GeneticAlgorithm.FitnessComparison.XORComparison;
import GeneticAlgorithm.FitnessComputation.FitnessComputation;
import GeneticAlgorithm.GeneticAlgorithm;
import GeneticAlgorithm.Selection.Selection;
import GeneticAlgorithm.Selection.TournamentSelection;
import NeuralNetwork.ActivationFunction.ActivationFunction;
import NeuralNetwork.ActivationFunction.SigmoidFunction;

public class Program {
    public static void main(String[] args) {
        GeneticAlgorithm ga = new GeneticAlgorithm(
        0.2, 0.05,
        0.5, 0.5, 0.1,
        3.95,0.3, 1000, new TournamentSelection(2),
                new TournamentSelection(2), new CrossOverKeepFromBest(), "XOR",
        2, 1, 0, new SigmoidFunction(),
                new SigmoidFunction(), 4, new XORComparison()
        );
        FitnessComputation fc =ga.runAlgorithm(100);
        fc.debugCompute();
    }
}
