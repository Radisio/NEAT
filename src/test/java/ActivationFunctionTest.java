import NeuralNetwork.ActivationFunction.*;
import org.junit.Test;

public class ActivationFunctionTest {

    private double trimTo5Digits(double entry){
        int tmp = (int) (entry*100000);
        return ((double)tmp)/100000;
    }
    @Test
    public void testBipolarSigmoidFunction(){
        BipolarSigmoidFunction bsf = new BipolarSigmoidFunction(1);
        double thresh = trimTo5Digits(bsf.threshold(0.2));

        assert thresh==0.09966;
    }
    @Test
    public void testHyperbolicTangentFunction(){
        HyperbolicTangentFunction htf = new HyperbolicTangentFunction();
        double thresh = trimTo5Digits(htf.threshold(0.2));
        assert thresh==0.19737;
    }
    @Test
    public void testIdentityFunction(){
        IdentityFunction ifun = new IdentityFunction();
        assert ifun.threshold(2)==2;
    }
    @Test
    public void testReLUFunction(){
        ReLUFunction rlu = new ReLUFunction();
        assert rlu.threshold(2)==2;
        assert rlu.threshold(-2)==0;
    }
    @Test
    public void testSigmoidFunction(){
        SigmoidFunction sf = new SigmoidFunction(1);
        double thresh = sf.threshold(0.2);
        thresh = trimTo5Digits(thresh);
        assert thresh==0.54983;

    }
    @Test
    public void testStepFunction(){
        StepFunction sf1 = new StepFunction();
        assert sf1.threshold(2)==1;
        assert sf1.threshold(-2)==0;

        StepFunction sf2 = new StepFunction(2);
        assert sf2.threshold(1)==0;
        assert sf2.threshold(3)==1;
    }
}
