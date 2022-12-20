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

    public static int getIndexNodeById(int id, List<Node> nodes){
        int index =-1;
        int size = nodes.size();
        for(int i =0;i<size;i++) {
            if (nodes.get(i).getId() == id) {
                index = i;
                break;
            }
        }
        return index;
    }
}
