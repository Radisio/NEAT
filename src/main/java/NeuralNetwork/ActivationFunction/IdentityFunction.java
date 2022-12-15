package NeuralNetwork.ActivationFunction;

public class IdentityFunction implements ActivationFunction{
    @Override
    public double threshold(double entry) {
        return entry;
    }
}
