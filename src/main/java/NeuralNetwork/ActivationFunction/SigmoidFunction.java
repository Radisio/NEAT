package NeuralNetwork.ActivationFunction;

public class SigmoidFunction implements ActivationFunction{
    private double k;
    public SigmoidFunction(){
        this.k = 0;
    }
    public SigmoidFunction(double k){
        this.k = k;
    }
    @Override
    public double threshold(double entry) {
        return 1/(1+Math.exp(-(k*entry)));
    }
}
