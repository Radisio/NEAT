package NeuralNetwork.ActivationFunction;

public class GaussianFunction implements ActivationFunction{
    @Override
    public double threshold(double entry) {
        return Math.exp(-(entry*entry));
    }
}
