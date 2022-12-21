package GeneticAlgorithm.FitnessComputation;

import Game.Creature.Creature;
import Game.Environment.Environment;
import Game.Environment.EnvironmentBuilder;
import Game.Environment.EnvironmentUtil;
import Game.Game;
import NeuralNetwork.NeuralNetwork;

import java.util.ArrayList;
import java.util.List;

public class GameFitness extends FitnessComputation{
    List<Environment> env;
    public GameFitness (NeuralNetwork nn){
        super(nn);
        env = new ArrayList<>();
        EnvironmentBuilder eb = new EnvironmentBuilder();
        eb.setNbLine(20);
        eb.setNbCol(20);
        boolean firstQuart = true;
        for(int i =0;i<3;i++)
        {
            eb.setStartingFirstQuart(firstQuart);
            env.add(eb.build());
            firstQuart=!firstQuart;
        }
    }

    @Override
    public void computeFitness() {
        this.fitness =0;
        double score = 0.0;
        for(int i =0;i<3;i++)
        {
            Game g = new Game(env.get(i), new Creature(null));
            g.startTrainNeat(this.neuralNetwork, 100);
            score += g.getScore();
        }
        this.fitness = score/3;
    }

    @Override
    public void debugCompute() {
        this.fitness =0;
        double score = 0.0;
        for(int i =0;i<3;i++)
        {
            Game g = new Game(env.get(i), new Creature(null));
            try {
                g.startTrainNeatDisplay(this.neuralNetwork,100);
                score += g.getScore();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Environment envBonus = EnvironmentUtil.newEnvironmentFromFilePos("D:\\Master2\\Projet2\\NEAT\\src\\main\\resources\\EnvironmentConfigs\\test.txt");
        Game g = new Game(envBonus, new Creature(null));
        try {
            g.startTrainNeatDisplay(neuralNetwork,100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //this.fitness = score/3;
    }
}
