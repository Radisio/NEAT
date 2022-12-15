import NeuralNetwork.*;
import org.junit.Test;

import java.util.List;

public class NeuralNetworkTest {

    @Test
    public void testNeuralNetworkInitilizationWithHidden(){
        NeuralNetwork nn = new NeuralNetwork(2,1,1);
        nn.initialize(1);
        List<Node> nodeList = nn.getNodeList();
        assert nodeList.size()==5;
        assert nodeList.get(0).getType()==NodeType.INPUT;
        assert nodeList.get(1).getType()==NodeType.INPUT;
        assert nodeList.get(2).getType()==NodeType.BIAS;
        assert nodeList.get(3).getType()==NodeType.OUTPUT;
        assert nodeList.get(4).getType()==NodeType.HIDDEN;

        List<Connection> connectionList = nn.getConnectionList();
        assert connectionList.size() ==4;
        assert connectionList.get(0).getIdNodeIn()==0;
        assert connectionList.get(0).getIdNodeOut()==4;

        assert connectionList.get(1).getIdNodeIn()==1;
        assert connectionList.get(1).getIdNodeOut()==4;

        assert connectionList.get(2).getIdNodeIn()==2;
        assert connectionList.get(2).getIdNodeOut()==4;

        assert connectionList.get(3).getIdNodeIn()==4;
        assert connectionList.get(3).getIdNodeOut()==3;
    }

    @Test
    public void testNeuralNetworkInitilizationWithoutHidden(){
        NeuralNetwork nn = new NeuralNetwork(2,1,0);
        nn.initialize(1);
        List<Node> nodeList = nn.getNodeList();
        assert nodeList.size()==4;
        assert nodeList.get(0).getType()==NodeType.INPUT;
        assert nodeList.get(1).getType()==NodeType.INPUT;
        assert nodeList.get(2).getType()==NodeType.BIAS;
        assert nodeList.get(3).getType()==NodeType.OUTPUT;

        List<Connection> connectionList = nn.getConnectionList();
        assert connectionList.size() ==3;
        assert connectionList.get(0).getIdNodeIn()==0;
        assert connectionList.get(0).getIdNodeOut()==3;

        assert connectionList.get(1).getIdNodeIn()==1;
        assert connectionList.get(1).getIdNodeOut()==3;

        assert connectionList.get(2).getIdNodeIn()==2;
        assert connectionList.get(2).getIdNodeOut()==3;

    }
}
