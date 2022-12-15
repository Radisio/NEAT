package NeuralNetwork.ActivationFunction;

public class BipolarSigmoidFunction implements ActivationFunction{
    private double k;
    public BipolarSigmoidFunction(){
        this.k = 0;
    }
    public BipolarSigmoidFunction(double k){
        this.k = k;
    }
    @Override
    public double threshold(double entry) {
        return (1-Math.exp(-k*entry))/(1+Math.exp(-k*entry));
    }
}
