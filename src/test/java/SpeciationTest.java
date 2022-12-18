import FitnessComparison.XORComparison;
import FitnessComputation.XORFitness;
import GeneticAlgorithm.Speciation.Specie;
import GeneticAlgorithm.Speciation.SpecieUtil;
import NeuralNetwork.ActivationFunction.SigmoidFunction;
import NeuralNetwork.*;
import NeuralNetwork.Util.ConnectionUtil;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SpeciationTest {
    private double trimTo5Digits(double entry){
        int tmp = (int) (entry*100000);
        return ((double)tmp)/100000;
    }
    @Test
    public void testNumberOfExcessive(){
        NeuralNetwork nn1 = new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1));
        nn1.initialize(1);
        nn1.breakConnection(1);
        NeuralNetwork nn2 = new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1));
        nn2.initialize(1);
        nn2.breakConnection(2);
        nn2.breakConnection(3);
        List<Connection> connection1 = ConnectionUtil.deepCopyListConnection(nn1.getConnectionList());
        List<Connection> connection2 = ConnectionUtil.deepCopyListConnection(nn2.getConnectionList());
        connection1.sort((Comparator.comparingInt(o -> o.getInnovationNumber())));
        connection2.sort((Comparator.comparingInt(o -> o.getInnovationNumber())));
        int result = SpecieUtil.getNumberOfExcessiveConnection(connection1, connection2);
        assert result==3;
    }


    @Test
    public void testNumberOfDisjoint(){
        NeuralNetwork nn1 = new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1));
        nn1.initialize(1);
        nn1.breakConnection(1);
        NeuralNetwork nn2 = new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1));
        nn2.initialize(1);
        nn2.breakConnection(2);
        nn2.breakConnection(3);
        List<Connection> connection1 = ConnectionUtil.deepCopyListConnection(nn1.getConnectionList());
        List<Connection> connection2 = ConnectionUtil.deepCopyListConnection(nn2.getConnectionList());
        connection1.sort((Comparator.comparingInt(o -> o.getInnovationNumber())));
        connection2.sort((Comparator.comparingInt(o -> o.getInnovationNumber())));
        int result = SpecieUtil.getNumberOfDisjointConnection(connection1, connection2);
        assert result==1;
    }

    @Test
    public void testNumberOfLongestGenomes(){
        NeuralNetwork nn1 = new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1));
        nn1.initialize(1);
        nn1.breakConnection(1);
        NeuralNetwork nn2 = new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1));
        nn2.initialize(1);
        nn2.breakConnection(2);
        nn2.breakConnection(3);
        List<Connection> connection1 = ConnectionUtil.deepCopyListConnection(nn1.getConnectionList());
        List<Connection> connection2 = ConnectionUtil.deepCopyListConnection(nn2.getConnectionList());
        connection1.sort((Comparator.comparingInt(o -> o.getInnovationNumber())));
        connection2.sort((Comparator.comparingInt(o -> o.getInnovationNumber())));
        int result = SpecieUtil.getNumberOfDisjointConnection(connection1, connection2);
        assert result==5;
    }

    @Test
    public void testWeight(){
        NeuralNetwork nn1 = new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1));
        nn1.initialize(1);
        nn1.breakConnection(1);
        int index = nn1.findConnectionByInnovationNumber(5);
        nn1.getConnectionList().get(index).setWeight(1.5);
        NeuralNetwork nn2 = new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1));
        nn2.initialize(1);
        nn2.breakConnection(2);
        nn2.breakConnection(3);
        index = nn2.findConnectionByInnovationNumber(5);
        nn2.getConnectionList().get(index).setWeight(0.5);

        List<Connection> connection1 = ConnectionUtil.deepCopyListConnection(nn1.getConnectionList());
        List<Connection> connection2 = ConnectionUtil.deepCopyListConnection(nn2.getConnectionList());
        connection1.sort((Comparator.comparingInt(o -> o.getInnovationNumber())));
        connection2.sort((Comparator.comparingInt(o -> o.getInnovationNumber())));
        double result = SpecieUtil.getMeanAbsoluteDifferenceWeight(connection1, connection2);
        assert result==1.0;
    }
    @Test
    public void testComparison(){
        NeuralNetwork nn1 = new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1));
        nn1.initialize(1);
        nn1.breakConnection(1);
        int index = nn1.findConnectionByInnovationNumber(5);
        nn1.getConnectionList().get(index).setWeight(1.5);
        NeuralNetwork nn2 = new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1));
        nn2.initialize(1);
        nn2.breakConnection(2);
        nn2.breakConnection(3);
        index = nn2.findConnectionByInnovationNumber(5);
        nn2.getConnectionList().get(index).setWeight(0.5);
        double result = SpecieUtil.getDifferenceBetweenTwoIndividuals(nn1, nn2);
        System.out.println("Result : " + result);
        assert trimTo5Digits(result)== 1.20000;
    }

    @Test
    public void testAverageFitness(){
        XORFitness xo1 = new XORFitness(
                new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo1.setFitness(5); ///=> Adj F = 1
        XORFitness xo2 = new XORFitness( new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo2.setFitness(2.5);  ///=> Adj F = 0.5
        XORFitness xo3 = new XORFitness(new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo3.setFitness(3); ///=> Adj F = 0.6
        XORFitness xo4 = new XORFitness(new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo4.setFitness(10); ///=> Adj F = 2
        XORFitness xo5 = new XORFitness( new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo5.setFitness(8); ///=> Adj F = 1.6

        XORFitness xo6 = new XORFitness( new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo6.setFitness(6);  ///=> Adj F = 2
        XORFitness xo7 = new XORFitness(new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo7.setFitness(5); ///=> Adj F = 1.666
        XORFitness xo8 = new XORFitness( new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo8.setFitness(2.5); ///=> Adj F = 0.833333

        Specie spe1 = new Specie(2,Arrays.asList(xo1, xo2, xo3, xo4, xo5), new XORComparison());
        Specie spe2 = new Specie(3,Arrays.asList(xo6,xo7,xo8), new XORComparison());

        spe1.computeAdjustedFitness();
        spe2.computeAdjustedFitness();

        List<XORFitness> lxo1 = (List<XORFitness>) spe1.getIndividuals();
        List<XORFitness> lxo2 = (List<XORFitness>) spe2.getIndividuals();
        assert lxo1.get(0).getAdjustedFitness()==1;
        assert lxo1.get(1).getAdjustedFitness()==0.5;
        assert lxo1.get(2).getAdjustedFitness()==0.6;
        assert lxo1.get(3).getAdjustedFitness()==2;
        assert lxo1.get(4).getAdjustedFitness()==1.6;

        assert lxo2.get(0).getAdjustedFitness()==2;
        assert trimTo5Digits(lxo2.get(1).getAdjustedFitness())==1.66666;
        assert trimTo5Digits(lxo2.get(2).getAdjustedFitness())==0.83333;

    }

    @Test
    public void testAverageAdjustedFitness(){
        XORFitness xo1 = new XORFitness(
                new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo1.setFitness(5); ///=> Adj F = 1
        XORFitness xo2 = new XORFitness( new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo2.setFitness(2.5);  ///=> Adj F = 0.5
        XORFitness xo3 = new XORFitness(new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo3.setFitness(3); ///=> Adj F = 0.6
        XORFitness xo4 = new XORFitness(new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo4.setFitness(10); ///=> Adj F = 2
        XORFitness xo5 = new XORFitness( new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo5.setFitness(8); ///=> Adj F = 1.6

        XORFitness xo6 = new XORFitness( new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo6.setFitness(6);  ///=> Adj F = 2
        XORFitness xo7 = new XORFitness(new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo7.setFitness(5); ///=> Adj F = 1.666
        XORFitness xo8 = new XORFitness( new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo8.setFitness(2.5); ///=> Adj F = 0.833333

        Specie spe1 = new Specie(2,Arrays.asList(xo1, xo2, xo3, xo4, xo5), new XORComparison());
        Specie spe2 = new Specie(3,Arrays.asList(xo6,xo7,xo8), new XORComparison());
        spe1.computeAdjustedFitness();
        spe2.computeAdjustedFitness();
        spe1.computeAverageAdjustedFitness();
        spe2.computeAverageAdjustedFitness();
        assert spe1.getAverageFitness() == 1.14;
        assert spe2.getAverageFitness()==1.5;
    }
    @Test
    public void testGlobalAverage(){
        XORFitness xo1 = new XORFitness(
                new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo1.setFitness(5); ///=> Adj F = 1
        XORFitness xo2 = new XORFitness( new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo2.setFitness(2.5);  ///=> Adj F = 0.5
        XORFitness xo3 = new XORFitness(new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo3.setFitness(3); ///=> Adj F = 0.6
        XORFitness xo4 = new XORFitness(new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo4.setFitness(10); ///=> Adj F = 2
        XORFitness xo5 = new XORFitness( new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo5.setFitness(8); ///=> Adj F = 1.6

        XORFitness xo6 = new XORFitness( new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo6.setFitness(6);  ///=> Adj F = 2
        XORFitness xo7 = new XORFitness(new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo7.setFitness(5); ///=> Adj F = 1.666
        XORFitness xo8 = new XORFitness( new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo8.setFitness(2.5); ///=> Adj F = 0.833333

        Specie spe1 = new Specie(2,Arrays.asList(xo1, xo2, xo3, xo4, xo5), new XORComparison());
        Specie spe2 = new Specie(3,Arrays.asList(xo6,xo7,xo8), new XORComparison());
        double globalAverage= SpecieUtil.getGlobalAverage(Arrays.asList(spe1, spe2));
        System.out.println(globalAverage);
        assert trimTo5Digits(globalAverage)==1.31999;
    }

    @Test
    public void testGettNumberTotalOfIndividuals(){
        XORFitness xo1 = new XORFitness(
                new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo1.setFitness(5); ///=> Adj F = 1
        XORFitness xo2 = new XORFitness( new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo2.setFitness(2.5);  ///=> Adj F = 0.5
        XORFitness xo3 = new XORFitness(new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo3.setFitness(3); ///=> Adj F = 0.6
        XORFitness xo4 = new XORFitness(new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo4.setFitness(10); ///=> Adj F = 2
        XORFitness xo5 = new XORFitness( new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo5.setFitness(8); ///=> Adj F = 1.6

        XORFitness xo6 = new XORFitness( new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo6.setFitness(6);  ///=> Adj F = 2
        XORFitness xo7 = new XORFitness(new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo7.setFitness(5); ///=> Adj F = 1.666
        XORFitness xo8 = new XORFitness( new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo8.setFitness(2.5); ///=> Adj F = 0.833333

        Specie spe1 = new Specie(2,Arrays.asList(xo1, xo2, xo3, xo4, xo5), new XORComparison());
        Specie spe2 = new Specie(3,Arrays.asList(xo6,xo7,xo8), new XORComparison());
        assert SpecieUtil.getNumberTotalOfIndividuals(Arrays.asList(spe1,spe2))==8;
    }

    @Test
    public void testOffspring(){
        XORFitness xo1 = new XORFitness(
                new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo1.setFitness(5); ///=> Adj F = 1
        XORFitness xo2 = new XORFitness( new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo2.setFitness(2.5);  ///=> Adj F = 0.5
        XORFitness xo3 = new XORFitness(new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo3.setFitness(3); ///=> Adj F = 0.6
        XORFitness xo4 = new XORFitness(new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo4.setFitness(10); ///=> Adj F = 2
        XORFitness xo5 = new XORFitness( new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo5.setFitness(8); ///=> Adj F = 1.6

        XORFitness xo6 = new XORFitness( new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo6.setFitness(6);  ///=> Adj F = 2
        XORFitness xo7 = new XORFitness(new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo7.setFitness(5); ///=> Adj F = 1.666
        XORFitness xo8 = new XORFitness( new NeuralNetwork(2,1,0, new SigmoidFunction(1), new SigmoidFunction(1)));
        xo8.setFitness(2.5); ///=> Adj F = 0.833333

        Specie spe1 = new Specie(2,Arrays.asList(xo1, xo2, xo3, xo4, xo5), new XORComparison());
        Specie spe2 = new Specie(3,Arrays.asList(xo6,xo7,xo8), new XORComparison());
        double globalAverage= SpecieUtil.getGlobalAverage(Arrays.asList(spe1, spe2));
        spe1.computeOffSpringAllowed(globalAverage); //4.31818
        spe2.computeOffSpringAllowed(globalAverage); // 3.40909
        assert Math.round(spe1.getOffSpringAllowed())==4;
        assert Math.round(spe2.getOffSpringAllowed())==3;

    }
}
