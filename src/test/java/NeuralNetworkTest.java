import NeuralNetwork.*;
import NeuralNetwork.ActivationFunction.SigmoidFunction;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class NeuralNetworkTest {

    @Test
    public void testNeuralNetworkInitilizationWithHidden(){
        NeuralNetwork nn = new NeuralNetwork(2,1,1, new SigmoidFunction(5), new SigmoidFunction(5));
        nn.initialize(1);
        List<Node> nodeList = nn.getNodeList();
        assert nodeList.size()==5;
        assert nodeList.get(0).getType()==NodeType.INPUT;
        assert nodeList.get(0).getPosition()==0;
        assert nodeList.get(1).getType()==NodeType.INPUT;
        assert nodeList.get(1).getPosition()==0;
        assert nodeList.get(2).getType()==NodeType.BIAS;
        assert nodeList.get(2).getPosition()==0;
        assert nodeList.get(3).getType()==NodeType.HIDDEN;
        assert nodeList.get(3).getPosition()==1.0/2.0;
        assert nodeList.get(4).getType()==NodeType.OUTPUT;
        assert nodeList.get(4).getPosition()==1;

        List<Connection> connectionList = nn.getConnectionList();
        assert connectionList.size() ==4;
        assert connectionList.get(0).getIdNodeIn()==0;
        assert connectionList.get(0).getIdNodeOut()==3;

        assert connectionList.get(1).getIdNodeIn()==1;
        assert connectionList.get(1).getIdNodeOut()==3;

        assert connectionList.get(2).getIdNodeIn()==2;
        assert connectionList.get(2).getIdNodeOut()==3;

        assert connectionList.get(3).getIdNodeIn()==3;
        assert connectionList.get(3).getIdNodeOut()==4;
    }

    @Test
    public void testNeuralNetworkInitilizationWithoutHidden(){
        NeuralNetwork nn = new NeuralNetwork(2,1,0, new SigmoidFunction(5), new SigmoidFunction(5));
        nn.initialize(1);
        List<Node> nodeList = nn.getNodeList();
        assert nodeList.size()==4;
        assert nodeList.get(0).getType()==NodeType.INPUT;
        assert nodeList.get(0).getPosition()==0;
        assert nodeList.get(1).getType()==NodeType.INPUT;
        assert nodeList.get(1).getPosition()==0;
        assert nodeList.get(2).getType()==NodeType.BIAS;
        assert nodeList.get(2).getPosition()==0;
        assert nodeList.get(3).getType()==NodeType.OUTPUT;
        assert nodeList.get(3).getPosition()==1;

        List<Connection> connectionList = nn.getConnectionList();
        assert connectionList.size() ==3;
        assert connectionList.get(0).getIdNodeIn()==0;
        assert connectionList.get(0).getIdNodeOut()==3;

        assert connectionList.get(1).getIdNodeIn()==1;
        assert connectionList.get(1).getIdNodeOut()==3;

        assert connectionList.get(2).getIdNodeIn()==2;
        assert connectionList.get(2).getIdNodeOut()==3;

    }
    public double roundUpTo9Digits(double entry){
        return (double)Math.round(entry*1000000000d)/1000000000d;
    }
    @Test
    public void testEvaluateNetworkWithoutHidden(){
        NeuralNetwork nn = new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1));
        nn.initialize(1);
        nn.getConnectionList().get(0).setWeight(0.87);
        nn.getConnectionList().get(1).setWeight(0.56);
        nn.getConnectionList().get(2).setWeight(2.15);
        nn.loadInput(Arrays.asList(0.5,0.4));
        nn.runNetwork();
        assert roundUpTo9Digits(nn.getOutputs().get(0))==0.943160234;
    }

    @Test
    public void testEvaluateNetworkWithtHidden(){
        NeuralNetwork nn = new NeuralNetwork(2,1,1, new SigmoidFunction(1), new SigmoidFunction(1));
        nn.initialize(1);
        nn.getConnectionList().get(0).setWeight(0.87);
        nn.getConnectionList().get(1).setWeight(0.56);
        nn.getConnectionList().get(2).setWeight(2.15);
        nn.getConnectionList().get(3).setWeight(1.87);
        nn.loadInput(Arrays.asList(0.5,0.4));
        nn.runNetwork();
        assert roundUpTo9Digits(nn.getOutputs().get(0))==0.853673658;
    }
    @Test
    public void testBreakConnection(){
        NeuralNetwork nn = new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1));
        nn.initialize(1);
        nn.breakConnection(1);
        List<Node> nodeList = nn.getNodeList();
        assert nodeList.size()==5;
        assert nodeList.get(0).getType()==NodeType.INPUT;
        assert nodeList.get(0).getPosition()==0;

        assert nodeList.get(1).getType()==NodeType.INPUT;
        assert nodeList.get(1).getPosition()==0;

        assert nodeList.get(2).getType()==NodeType.BIAS;
        assert nodeList.get(2).getPosition()==0;

        assert nodeList.get(3).getType()==NodeType.HIDDEN;
        assert nodeList.get(3).getPosition()==1.0/2.0;

        assert nodeList.get(4).getType()==NodeType.OUTPUT;
        assert nodeList.get(4).getPosition()==1;

        List<Connection> connectionList = nn.getConnectionList();
        assert connectionList.size() ==5;
        assert connectionList.get(0).getIdNodeIn()==0;
        assert connectionList.get(0).getIdNodeOut()==3;
        assert !connectionList.get(0).isEnabled();

        assert connectionList.get(1).getIdNodeIn()==1;
        assert connectionList.get(1).getIdNodeOut()==3;
        assert connectionList.get(1).isEnabled();

        assert connectionList.get(2).getIdNodeIn()==2;
        assert connectionList.get(2).getIdNodeOut()==3;
        assert connectionList.get(2).isEnabled();

        assert connectionList.get(3).getIdNodeIn()==0;
        assert connectionList.get(3).getIdNodeOut()==4;
        assert connectionList.get(3).isEnabled();

        assert connectionList.get(4).getIdNodeIn()==4;
        assert connectionList.get(4).getIdNodeOut()==3;
        assert connectionList.get(4).isEnabled();
    }
}
