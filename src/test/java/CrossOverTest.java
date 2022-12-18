import Crossover.CrossOver;
import Crossover.CrossOverKeepFromBest;
import FitnessComputation.XORFitness;
import NeuralNetwork.ActivationFunction.SigmoidFunction;
import NeuralNetwork.NeuralNetwork;
import org.junit.Test;

public class CrossOverTest {

    @Test
    public void CrossOverKeepFromBest1(){
        NeuralNetwork nn1 = new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1));
        nn1.initialize(1);
        nn1.breakConnection(1);
        NeuralNetwork nn2 = new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1));
        nn2.initialize(1);
        nn2.breakConnection(1);
        nn2.breakConnection(3);

        XORFitness fittest = new XORFitness(nn1);
        fittest.setFitness(50);
        XORFitness notFittest = new XORFitness(nn2);
        notFittest.setFitness(15);
        CrossOver co = new CrossOverKeepFromBest();
        System.out.println("Length n1" + nn1.getConnectionList().size());
        System.out.println("Length n2" + nn2.getConnectionList().size());
        NeuralNetwork nnCrossed = co.crossover(fittest, notFittest);
        assert nnCrossed.getConnectionList().size()==fittest.getNeuralNetwork().getConnectionList().size();
        assert nnCrossed.getNodeList().size()==fittest.getNeuralNetwork().getNodeList().size();

        assert nnCrossed.getConnectionList().get(0).getInnovationNumber()==1;

        assert nnCrossed.getConnectionList().get(1).getInnovationNumber()==2;

        assert nnCrossed.getConnectionList().get(2).getInnovationNumber()==3;

        assert nnCrossed.getConnectionList().get(3).getInnovationNumber()==4;
        assert nnCrossed.getConnectionList().get(3).getIdNodeIn()==0;
        assert nnCrossed.getConnectionList().get(3).getIdNodeOut()==4;

        assert nnCrossed.getConnectionList().get(4).getInnovationNumber()==5;
        assert nnCrossed.getConnectionList().get(4).getIdNodeIn()==4;
        assert nnCrossed.getConnectionList().get(4).getIdNodeOut()==3;

    }
    @Test
    public void CrossOverKeepFromBest2() {
        NeuralNetwork nn1 = new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1));
        nn1.initialize(1);
        nn1.breakConnection(1);
        NeuralNetwork nn2 = new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1));
        nn2.initialize(1);
        nn2.breakConnection(1);
        nn2.breakConnection(3);

        XORFitness fittest = new XORFitness(nn2);
        fittest.setFitness(50);
        XORFitness notFittest = new XORFitness(nn1);
        notFittest.setFitness(15);
        CrossOver co = new CrossOverKeepFromBest();
        NeuralNetwork nnCrossed = co.crossover(fittest, notFittest);
        assert nnCrossed.getConnectionList().size()==fittest.getNeuralNetwork().getConnectionList().size();
        assert nnCrossed.getNodeList().size()==fittest.getNeuralNetwork().getNodeList().size();

        assert nnCrossed.getConnectionList().get(0).getInnovationNumber()==1;

        assert nnCrossed.getConnectionList().get(1).getInnovationNumber()==2;

        assert nnCrossed.getConnectionList().get(2).getInnovationNumber()==3;

        assert nnCrossed.getConnectionList().get(3).getInnovationNumber()==4;
        assert nnCrossed.getConnectionList().get(3).getIdNodeIn()==0;
        assert nnCrossed.getConnectionList().get(3).getIdNodeOut()==4;

        assert nnCrossed.getConnectionList().get(4).getInnovationNumber()==5;
        assert nnCrossed.getConnectionList().get(4).getIdNodeIn()==4;
        assert nnCrossed.getConnectionList().get(4).getIdNodeOut()==3;


        assert nnCrossed.getConnectionList().get(5).getInnovationNumber()==6;
        assert nnCrossed.getConnectionList().get(5).getIdNodeIn()==2;
        assert nnCrossed.getConnectionList().get(5).getIdNodeOut()==5;

        assert nnCrossed.getConnectionList().get(6).getInnovationNumber()==7;
        assert nnCrossed.getConnectionList().get(6).getIdNodeIn()==5;
        assert nnCrossed.getConnectionList().get(6).getIdNodeOut()==3;
    }
}
