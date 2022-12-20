import GeneticAlgorithm.Crossover.CrossOver;
import GeneticAlgorithm.Crossover.CrossOverKeepFromBest;
import GeneticAlgorithm.FitnessComparison.GameComparison;
import GeneticAlgorithm.FitnessComparison.XORComparison;
import GeneticAlgorithm.FitnessComputation.FitnessComputation;
import GeneticAlgorithm.GeneticAlgorithm;
import GeneticAlgorithm.Selection.Selection;
import GeneticAlgorithm.Selection.TournamentSelection;
import NeuralNetwork.ActivationFunction.ActivationFunction;
import NeuralNetwork.ActivationFunction.GaussianFunction;
import NeuralNetwork.ActivationFunction.IdentityFunction;
import NeuralNetwork.ActivationFunction.SigmoidFunction;
import NeuralNetwork.Connection;

public class Program {

    private static void XOR(){
        GeneticAlgorithm ga = new GeneticAlgorithm(
                0.04, 0.1,
                0.3, 0.3, 0.1,
                3.95,0.4, 50, new TournamentSelection(2),
                new TournamentSelection(2), new CrossOverKeepFromBest(), "XOR",
                2, 1, 0, new SigmoidFunction(4.9),
                /*new SigmoidFunction(4.9)*/new GaussianFunction(), 0.3, new XORComparison(),
                5, 0.3
        );
        FitnessComputation fc =ga.runAlgorithm(5000);

        fc.debugCompute();
    }
    private static void Game(){
        GeneticAlgorithm ga = new GeneticAlgorithm(
                0.04, 0.1,
                0.3, 0.3, 0.1,
                0,0.4, 10, new TournamentSelection(2),
                new TournamentSelection(2), new CrossOverKeepFromBest(), "GAME",
                404, 8, 0, new SigmoidFunction(4.9),
                /*new SigmoidFunction(4.9)*/new IdentityFunction(), 0.3, new GameComparison(),
                5, 0.3
        );
        FitnessComputation fc =ga.runAlgorithm(10);
        System.out.println("FINI");
        fc.debugCompute();
    }
    public static void main(String[] args) {
        Game();
    }
}
