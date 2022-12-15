package NeuralNetwork;


import NeuralNetwork.ActivationFunction.ActivationFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NeuralNetwork {
    private List<Node> nodeList;
    private List<Connection> connectionList;
    private int nodeIdCounter;
    private int inputNode, outputNode, hiddenNode;
    private ActivationFunction hiddenLayerActivationFunction;
    private ActivationFunction outputLayerActivationFunction;
    public NeuralNetwork(int inputNode, int outputNode, int hiddenNode, ActivationFunction hiddenLayerActivationFunction,
                         ActivationFunction outputLayerActivationFunction){
        nodeList =new ArrayList<>();
        connectionList = new ArrayList<>();
        nodeIdCounter=0;
        this.inputNode = inputNode;
        this.outputNode = outputNode;
        this.hiddenNode = hiddenNode;
        this.hiddenLayerActivationFunction = hiddenLayerActivationFunction;
        this.outputLayerActivationFunction = outputLayerActivationFunction;
    }
    private void initializeNodeList(){
        for(int i = 0;i<inputNode;i++)
        {
            nodeList.add(new Node(nodeIdCounter++,NodeType.INPUT,1,0,0));
        }
        nodeList.add(new Node(nodeIdCounter++,NodeType.BIAS,1,1,1));
        for(int i = 0;i<outputNode;i++)
        {
            nodeList.add(new Node(nodeIdCounter++,NodeType.OUTPUT,3,0,0));
        }
        for(int i = 0;i<hiddenNode;i++)
        {
            nodeList.add(new Node(nodeIdCounter++,NodeType.HIDDEN,2,0,0));
        }
    }

    private void initializeConnectionList(double percentageConnexion){
        int lastIndex = nodeIdCounter-1;
        Random r = new Random();
        if(hiddenNode>0) {
            // Connection between the hidden nodes and the input nodes
            for (int i = lastIndex, j=0; j<hiddenNode; i--,j++, lastIndex--) {
                for(int k = 0; k<inputNode+1;k++)
                {
                    if(r.nextDouble()<=percentageConnexion)
                        connectionList.add(ConnectionUtil.createConnection(k,i, r.nextGaussian(),true, false));
                }
            }
            // Connections between the output nodes and the hidden nodes
            int lastIndexHidden = nodeIdCounter-1;
            for (int i = lastIndex, j=0; j<outputNode; i--,j++) {
                for(int k = 0, a = lastIndexHidden; k<hiddenNode;k++, a--)
                {
                    if(r.nextDouble()<=percentageConnexion)
                        connectionList.add(ConnectionUtil.createConnection(a,i, r.nextGaussian(),true, false));
                }
            }
        }
        else{
            for (int i = lastIndex, j=0; j<outputNode; i--,j++, lastIndex--) {
                for(int k = 0; k<inputNode+1;k++)
                {
                    if(r.nextDouble()<=percentageConnexion)
                        connectionList.add(ConnectionUtil.createConnection(k,i, r.nextGaussian(),true, false));
                }
            }
        }
    }


    public void initialize(double percentageConnexion){
        initializeNodeList();
        initializeConnectionList(percentageConnexion);
    }
    public void loadInput(List<Double> input){
        if(input.size()!=inputNode)
            throw new RuntimeException("Input's length doesn't match");
        for(int i =0;i<inputNode;i++)
        {
            nodeList.get(i).setSumInput(input.get(i));
            nodeList.get(i).setSumOutput(input.get(i));
        }
    }

    public void runNetwork(){
        int lastIndex = inputNode+1+outputNode+1;
        ///Calcul couche entrée (s'il y a des
        // Calcul couche cachée
        for(int i = lastIndex;i<nodeList.size();i++)
        {
            System.out.println("Node layer : " + nodeList.get(i).getLayer());
            nodeList.get(i).setSumInput(0);
            for(int j = 0; j<connectionList.size();j++)
            {
                if(connectionList.get(j).getIdNodeOut() == nodeList.get(i).getId())
                {
                    nodeList.get(i).setSumInput(nodeList.get(i).getSumInput()+nodeList.get(connectionList.get(j).getIdNodeOut()).getSumOutput()*connectionList.get(j).getWeight());
                }
            }
            nodeList.get(i).setSumOutput(hiddenLayerActivationFunction.threshold(nodeList.get(i).getSumInput()));
        }
        //calcul couche sortie
        int outputLayer = inputNode+1;
        for(int i = outputLayer,j=0; j<outputNode;j++,i++)
        {
            for(int k = 0;k<connectionList.size();k++)
            {
                if(connectionList.get(k).getIdNodeOut() == nodeList.get(i).getId())
                {
                    nodeList.get(i).setSumInput(nodeList.get(i).getSumInput()+nodeList.get(connectionList.get(k).getIdNodeOut()).getSumOutput()*connectionList.get(k).getWeight());
                }
            }
            nodeList.get(i).setSumOutput(outputLayerActivationFunction.threshold(nodeList.get(i).getSumInput()));
        }
    }

    public List<Double> getOutputs(){
        int outputLayer = inputNode+1;
        List<Double> returnedList = new ArrayList<>();
        for(int i = outputLayer,j=0; j<outputNode;j++,i++)
        {
            returnedList.add(nodeList.get(i).getSumOutput());
        }
        return returnedList;
    }


    public List<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public List<Connection> getConnectionList() {
        return connectionList;
    }

    public void setConnectionList(List<Connection> connectionList) {
        this.connectionList = connectionList;
    }
}
