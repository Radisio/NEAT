package NeuralNetwork.ActivationFunction;

public class ReLUFunction implements ActivationFunction{
    @Override
    public double threshold(double entry) {
        return entry>=0?entry:0;
    }
}
