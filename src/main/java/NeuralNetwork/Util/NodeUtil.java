package NeuralNetwork.Util;

import NeuralNetwork.Node;

import java.util.ArrayList;
import java.util.List;

public class NodeUtil {

    public static List<Node> deepCopyListNode( List<Node> lNode){
        List<Node> newNodeList = new ArrayList<>();
        for(Node n : lNode){
            newNodeList.add(new Node(n));
        }
        return newNodeList;
    }
}
