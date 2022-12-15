package NeuralNetwork;


public class Node {
    private int id;
    private NodeType type;
    private int layer;
    private double sumInput;
    private double sumOutput;

    public Node(int id, NodeType type, int layer, double sumInput, double sumOutput) {
        this.id = id;
        this.type = type;
        this.layer = layer;
        this.sumInput = sumInput;
        this.sumOutput = sumOutput;
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
}
