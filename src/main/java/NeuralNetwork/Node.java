package NeuralNetwork;


public class Node {
    private int id;
    private NodeType type;
    private int layer;
    private double sumInput;
    private double sumOutput;
    private double position;
    public Node(int id, NodeType type, int layer, double sumInput, double sumOutput) {
        this.id = id;
        this.type = type;
        this.layer = layer;
        this.sumInput = sumInput;
        this.sumOutput = sumOutput;
        this.position=-1;
    }

    public Node(int id, NodeType type, int layer, double sumInput, double sumOutput, double position) {
        this.id = id;
        this.type = type;
        this.layer = layer;
        this.sumInput = sumInput;
        this.sumOutput = sumOutput;
        this.position = position;
    }

    public Node(Node n){
        this.id = n.getId();
        this.type = n.getType();
        this.layer = n.getLayer();
        this.sumInput = n.getSumInput();
        this.sumOutput = n.getSumOutput();
        this.position=n.getPosition();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public double getSumInput() {
        return sumInput;
    }

    public void setSumInput(double sumInput) {
        this.sumInput = sumInput;
    }

    public double getSumOutput() {
        return sumOutput;
    }

    public void setSumOutput(double sumOutput) {
        this.sumOutput = sumOutput;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }
}
