package GeneticAlgorithm.Speciation;

import NeuralNetwork.*;
import NeuralNetwork.Util.ConnectionUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SpecieUtil {
    public static double c1 = 1;
    public static double c2 = 1;
    public static double c3 = 0.4;
    /// Get E
    public static int getNumberOfExcessiveConnection(List<Connection> connection1, List<Connection> connection2)
    {
        int maxInnovationNumber1 = connection1.get(connection1.size()-1).getInnovationNumber();
        int maxInnovationNumber2 = connection2.get(connection2.size()-1).getInnovationNumber();
        int E = 0;
        if(maxInnovationNumber1!=maxInnovationNumber2) {
            if (maxInnovationNumber1 > maxInnovationNumber2) {
                for(Connection con : connection1){
                    if(con.getInnovationNumber()>maxInnovationNumber2)
                        E++;
                }
            }
            else {
                for(Connection con : connection2){
                    if(con.getInnovationNumber()>maxInnovationNumber1)
                        E++;
                }
            }
        }
        return E;
    }
    public static int getNumberOfDisjointConnection(List<Connection> connection1, List<Connection> connection2){
        List<Connection> connection1Tmp = ConnectionUtil.deepCopyListConnection(connection1);
        List<Connection> connection2Tmp = ConnectionUtil.deepCopyListConnection(connection2);
        int maxInnovationNumber1 = connection1.get(connection1.size()-1).getInnovationNumber();
        int maxInnovationNumber2 = connection2.get(connection2.size()-1).getInnovationNumber();
        int D = 0;
        boolean find = false;
        int minIn = Math.min(maxInnovationNumber1, maxInnovationNumber2);
        for(int i = 0;i<connection1Tmp.size();i++)
        {
            find = true;
            if(connection1Tmp.get(i).getInnovationNumber()<=minIn) {
                find=false;
                for (int j = 0; j < connection2Tmp.size(); j++) {
                    if(connection2Tmp.get(j).getInnovationNumber()>minIn)
                    {
                        connection2Tmp.remove(j);
                        j--;
                    }
                    if ((connection1Tmp.get(i).getInnovationNumber() == connection2Tmp.get(j).getInnovationNumber())) {
                        find = true;
                        connection2Tmp.remove(j);
                        break;
                    }
                }
            }
            if(find){
                connection1Tmp.remove(i);
                i--;
            }
        }
        D+=connection1Tmp.size();
        D+=connection2Tmp.size();
        return D;
    }
    public static double getMeanAbsoluteDifferenceWeight(List<Connection> connection1, List<Connection> connection2){
        List<Connection> connection2Tmp = ConnectionUtil.deepCopyListConnection(connection2);
        double absoluteDifference = 0.0;
        int count = 0;
        for(int i = 0;i<connection1.size();i++)
        {
            for(int j = 0;j<connection2Tmp.size();j++)
            {
                if(connection1.get(i).getInnovationNumber() == connection2Tmp.get(j).getInnovationNumber()
                        && connection1.get(i).isEnabled())
                {
                    if(connection2Tmp.get(j).isEnabled()) {
                        absoluteDifference += Math.abs(connection1.get(i).getWeight() - connection2Tmp.get(j).getWeight());
                        count++;
                    }
                    connection2Tmp.remove(j);
                    break;
                }
            }
        }
        return absoluteDifference/count;
    }
    public static int getNumberOfEnabledConnectionFromLargestGenomes(List<Connection> connection1, List<Connection> connection2)
    {
        int count1 =(int) connection1.stream().filter((connection -> connection.isEnabled())).count();
        int count2 =(int) connection2.stream().filter((connection -> connection.isEnabled())).count();
        return Math.max(count1, count2);
    }
    public static double getDifferenceBetweenTwoIndividuals(NeuralNetwork nn1, NeuralNetwork nn2)
    {
        List<Connection> connection1 = ConnectionUtil.deepCopyListConnection(nn1.getConnectionList());
        List<Connection> connection2 = ConnectionUtil.deepCopyListConnection(nn2.getConnectionList());
        connection1.sort((Comparator.comparingInt(o -> o.getInnovationNumber())));
        connection2.sort((Comparator.comparingInt(o -> o.getInnovationNumber())));
        int E = getNumberOfExcessiveConnection(connection1, connection2);
        int D = getNumberOfDisjointConnection(connection1, connection2);
        double W = getMeanAbsoluteDifferenceWeight(connection1, connection2);
        int N = getNumberOfEnabledConnectionFromLargestGenomes(connection1, connection2);
        System.out.println("E = " + E);
        System.out.println("D = " + D);
        System.out.println("W = " + W);
        System.out.println("N = " + N);
        return c1*((double)E/(double)N)+c2*((double)D/(double)N)+c3*W;

    }

    public static double getGlobalAverage(List<Specie> species){
        double average = 0.0;
        int count = 0;
        for(Specie spe : species){
            spe.computeAdjustedFitness();
            spe.computeAverageAdjustedFitness();
            average += spe.getAverageFitness();
            count++;
        }
        return average/(double)count;
    }

    public static int getNumberTotalOfIndividuals(List<Specie> species){
        int count =0;
        for(Specie spe: species)
        {
            count+=spe.getIndividuals().size();
        }
        return count;
    }
}
