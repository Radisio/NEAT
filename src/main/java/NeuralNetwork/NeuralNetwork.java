package NeuralNetwork;


import NeuralNetwork.ActivationFunction.ActivationFunction;
import NeuralNetwork.Util.ConnectionUtil;
import NeuralNetwork.Util.NodeUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NeuralNetwork {
    private List<Node> nodeList;
    private List<Connection> connectionList;
    private int nodeIdCounter;
    private int inputNode, outputNode, hiddenNode;
    private ActivationFunction hiddenLayerActivationFunction;
    private ActivationFunction outputLayerActivationFunction;
    private boolean recursiveEnable;
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
        this.recursiveEnable = false;
    }

    public NeuralNetwork(List<Node> nodeList, List<Connection> connectionList, int nodeIdCounter, int inputNode,
                         int outputNode, int hiddenNode, ActivationFunction hiddenLayerActivationFunction,
                         ActivationFunction outputLayerActivationFunction, boolean recursiveEnable) {
        this.nodeList = nodeList;
        this.connectionList = connectionList;
        this.nodeIdCounter = nodeIdCounter;
        this.inputNode = inputNode;
        this.outputNode = outputNode;
        this.hiddenNode = hiddenNode;
        this.hiddenLayerActivationFunction = hiddenLayerActivationFunction;
        this.outputLayerActivationFunction = outputLayerActivationFunction;
        this.recursiveEnable = recursiveEnable;
    }
    public NeuralNetwork(NeuralNetwork nn){
        this.nodeList = NodeUtil.deepCopyListNode(nn.getNodeList());
        this.connectionList = ConnectionUtil.deepCopyListConnection(nn.getConnectionList());
        this.nodeIdCounter = nn.getNodeIdCounter();
        this.inputNode = nn.getInputNode();
        this.outputNode = nn.getOutputNode();
        this.hiddenNode = nn.getHiddenNode();
        this.hiddenLayerActivationFunction = nn.getHiddenLayerActivationFunction();
        this.outputLayerActivationFunction = nn.getOutputLayerActivationFunction();
        this.recursiveEnable = nn.isRecursiveEnable();
    }

    private void initializeNodeList(){
        for(int i = 0;i<inputNode;i++)
        {
            nodeList.add(new Node(nodeIdCounter++,NodeType.INPUT,1,0,0, 0));
        }
        nodeList.add(new Node(nodeIdCounter++,NodeType.BIAS,1,1,1,0));
        for(int i = 0;i<hiddenNode;i++)
        {
            nodeList.add(new Node(nodeIdCounter++,NodeType.HIDDEN,2,0,0, 1.0/2.0));
        }
        for(int i = 0;i<outputNode;i++)
        {
            nodeList.add(new Node(nodeIdCounter++,NodeType.OUTPUT,3,0,0,1));
        }

    }

    private void initializeConnectionList(double percentageConnexion){
        Random r = new Random();
        int index = inputNode+1;
        if(hiddenNode>0)
        {
            for(int i=0;i<hiddenNode;i++, index++)
            {
                for(int j=0;j<inputNode+1;j++){
                    if(r.nextDouble()<=percentageConnexion)
                        connectionList.add(ConnectionUtil.createConnection(j,index, r.nextGaussian(),true, false));
                }
            }
            int indexHiden = inputNode+1;
            for(int i =0;i<outputNode;i++,index++)
            {
                for(int j=0;j<hiddenNode;j++, indexHiden++){
                    if(r.nextDouble()<=percentageConnexion)
                        connectionList.add(ConnectionUtil.createConnection(indexHiden,index, r.nextGaussian(),true, false));
                }
            }
        }
        else{
            for(int i =0;i<outputNode;i++,index++)
            {
                for(int j=0;j<inputNode+1;j++){
                    if(r.nextDouble()<=percentageConnexion)
                        connectionList.add(ConnectionUtil.createConnection(j,index, r.nextGaussian(),true, false));
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
        List<Connection> connectionListCopy = new ArrayList<Connection>(connectionList);
        int size = nodeList.size();
        int indexStart = recursiveEnable?0:inputNode+1;
        for(int i=indexStart;i<size;i++)
        {
            for(int j = 0;j<connectionListCopy.size();j++)
            {
                if(connectionListCopy.get(j).getIdNodeOut() == nodeList.get(i).getId())
                {
                    nodeList.get(i).setSumInput(nodeList.get(i).getSumInput()+(nodeList.get(
                            connectionList.get(j).getIdNodeIn()).getSumOutput() * connectionList.get(j).getWeight())
                    );
                }
                if(nodeList.get(i).getType()==NodeType.OUTPUT)
                    nodeList.get(i).setSumOutput(outputLayerActivationFunction.threshold(nodeList.get(i).getSumInput()));
                else
                    nodeList.get(i).setSumOutput(hiddenLayerActivationFunction.threshold(nodeList.get(i).getSumInput()));

            }
        }
    }
    public int findConnectionByInnovationNumber(int innovationNumber){
        int size = connectionList.size();
        for(int i =0; i<size;i++){
            if(connectionList.get(i).getInnovationNumber()==innovationNumber){
                return i;
            }
        }
        return -1;
    }
    private void insertNewNode(Node newNode){
        int size = nodeList.size();
        for(int i =inputNode+1;i<size;i++)
        {
            if(nodeList.get(i).getPosition()>newNode.getPosition())
            {
                nodeList.add(i, newNode);
            }
        }
    }
    ///region Mutation
    public void breakConnection(int innovationNumberToBreak){
        int index = findConnectionByInnovationNumber(innovationNumberToBreak);
        if(index==-1)
        {
            throw new RuntimeException("Connection " + innovationNumberToBreak + " does not exist");
        }
        connectionList.get(index).setEnabled(false);
        double newPosition = (nodeList.get(connectionList.get(index).getIdNodeIn()).getPosition()+
                nodeList.get(connectionList.get(index).getIdNodeOut()).getPosition())/2.0;
        Node newNode = new Node(nodeIdCounter++, NodeType.HIDDEN, 2, 0,0, newPosition);
        insertNewNode(newNode);
        connectionList.add(ConnectionUtil.createConnection(connectionList.get(index).getIdNodeIn(), newNode.getId(),
                connectionList.get(index).getWeight(),true,false));
        Random r = new Random();
        connectionList.add(ConnectionUtil.createConnection(newNode.getId(),connectionList.get(index).getIdNodeOut(),
                r.nextGaussian(),true,false));
    }
    public void mutateAddWeight(int indexConnectionToChange){
        double weight = connectionList.get(indexConnectionToChange).getWeight();
        connectionList.get(indexConnectionToChange).setWeight(weight + (weight*0.2));
    }
    public void mutateSubWeight(int indexConnectionToChange){
        double weight = connectionList.get(indexConnectionToChange).getWeight();
        connectionList.get(indexConnectionToChange).setWeight(weight - (weight*0.2));
    }
    public void mutateNewWeight(int indexConnectionToChange){
        Random r = new Random();
        connectionList.get(indexConnectionToChange).setWeight(r.nextGaussian());
    }
    private boolean doesConnectionExist(int idNodeIn, int idNodeOut){
        for(Connection con : connectionList){
            if(con.getIdNodeIn() == idNodeIn)
                if(con.getIdNodeOut()== idNodeOut)
                    return true;
        }
        return false;
    }
    private boolean isConnectionPossible(){
        for(int i =0;i<nodeList.size();i++)
        {
            for(int j = 0;j<nodeList.size();j++)
            {
                if(nodeList.get(i).getId()!=nodeList.get(j).getId()) {
                    if(recursiveEnable || nodeList.get(i).getPosition()>nodeList.get(j).getId())
                    {
                        if(ConnectionUtil.doesConnectionExist(nodeList.get(i).getId(), nodeList.get(j).getId())!=-1){
                            if(!doesConnectionExist(nodeList.get(i).getId(), nodeList.get(j).getId()))
                                return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    public boolean addConnection(){
        if(!isConnectionPossible()){
            return false;
        }
        List<Integer> listNodeIn;
        List<Integer> listNodeOut;
        if(recursiveEnable)
        {
            listNodeIn = IntStream.range(0, nodeList.size()).boxed().collect(Collectors.toList());
            listNodeOut = IntStream.range(0, nodeList.size()).boxed().collect(Collectors.toList());
        }else{
            listNodeIn = IntStream.range(0, nodeList.size()-outputNode).boxed().collect(Collectors.toList());
            listNodeOut = IntStream.range(inputNode, nodeList.size()).boxed().collect(Collectors.toList());
        }
        Collections.shuffle(listNodeIn);
        Collections.shuffle(listNodeOut);
        Random r = new Random();
        for(Integer indexIn : listNodeIn)
            for(Integer indexOut : listNodeOut){
                if(nodeList.get(indexIn).getPosition()!=nodeList.get(indexOut).getPosition()) {
                    if (!doesConnectionExist(nodeList.get(indexIn).getId(), nodeList.get(indexOut).getId())) {
                        if (nodeList.get(indexIn).getPosition() > nodeList.get(indexOut).getPosition() && recursiveEnable)
                            connectionList.add(ConnectionUtil.createConnection(nodeList.get(indexIn).getId(), nodeList.get(indexOut).getId(),
                                    r.nextGaussian(), true, true));
                        else{
                            connectionList.add(ConnectionUtil.createConnection(nodeList.get(indexIn).getId(), nodeList.get(indexOut).getId(),
                                    r.nextGaussian(), true, false));
                        }
                    }
                }
            }
        return true;
    }
    ///endregion


    public List<Double> getOutputs(){
        int outputLayer = nodeList.size()-outputNode;
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

    public boolean isRecursiveEnable() {
        return recursiveEnable;
    }

    public void setRecursiveEnable(boolean recursiveEnable) {
        this.recursiveEnable = recursiveEnable;
    }

    public int getNodeIdCounter() {
        return nodeIdCounter;
    }

    public void setNodeIdCounter(int nodeIdCounter) {
        this.nodeIdCounter = nodeIdCounter;
    }

    public int getInputNode() {
        return inputNode;
    }

    public void setInputNode(int inputNode) {
        this.inputNode = inputNode;
    }

    public int getOutputNode() {
        return outputNode;
    }

    public void setOutputNode(int outputNode) {
        this.outputNode = outputNode;
    }

    public int getHiddenNode() {
        return hiddenNode;
    }

    public void setHiddenNode(int hiddenNode) {
        this.hiddenNode = hiddenNode;
    }

    public ActivationFunction getHiddenLayerActivationFunction() {
        return hiddenLayerActivationFunction;
    }

    public void setHiddenLayerActivationFunction(ActivationFunction hiddenLayerActivationFunction) {
        this.hiddenLayerActivationFunction = hiddenLayerActivationFunction;
    }

    public ActivationFunction getOutputLayerActivationFunction() {
        return outputLayerActivationFunction;
    }

    public void setOutputLayerActivationFunction(ActivationFunction outputLayerActivationFunction) {
        this.outputLayerActivationFunction = outputLayerActivationFunction;
    }
}
