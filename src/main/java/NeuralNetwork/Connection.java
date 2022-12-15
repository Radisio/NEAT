package NeuralNetwork;

public class Connection {
    private int innovationNumber;
    private int idNodeIn;
    private int idNodeOut;
    private double weight;
    private boolean enabled;
    private boolean isRecurrent;

    public Connection(int innovationNumber, int idNodeIn, int idNodeOut, double weight, boolean enabled, boolean isRecurrent) {
        this.innovationNumber = innovationNumber;
        this.idNodeIn = idNodeIn;
        this.idNodeOut = idNodeOut;
        this.weight = weight;
        this.enabled = enabled;
        this.isRecurrent = isRecurrent;
    }

    public int getInnovationNumber() {
        return innovationNumber;
    }

    public void setInnovationNumber(int innovationNumber) {
        this.innovationNumber = innovationNumber;
    }

    public int getIdNodeIn() {
        return idNodeIn;
    }

    public void setIdNodeIn(int idNodeIn) {
        this.idNodeIn = idNodeIn;
    }

    public int getIdNodeOut() {
        return idNodeOut;
    }

    public void setIdNodeOut(int idNodeOut) {
        this.idNodeOut = idNodeOut;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isRecurrent() {
        return isRecurrent;
    }

    public void setRecurrent(boolean recurrent) {
        isRecurrent = recurrent;
    }
}
