package FitnessComparison;

public class XORComparison implements FitnessComparison{
    @Override
    public boolean fitness1Better(double fitness1, double fitness2) {
        return Double.compare(fitness1, fitness2)==1;
    }
}
