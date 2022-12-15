package NeuralNetwork.ActivationFunction;

public class StepFunction implements ActivationFunction{
    private double theta;
    public StepFunction()
    {
        theta=0;
    }
    public StepFunction(double theta){
        this.theta=theta;
    }
    @Override
    public double threshold(double entry) {
        return entry>=theta?1:0;
    }
}
