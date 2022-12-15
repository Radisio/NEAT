package NeuralNetwork.ActivationFunction;

public class HyperbolicTangentFunction implements ActivationFunction{
    @Override
    public double threshold(double entry) {
        return (Math.exp(entry)-Math.exp(-entry))/(Math.exp(entry)+Math.exp(-entry));
    }
}
