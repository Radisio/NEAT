package NeuralNetwork;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class ConnectionUtil {
    private static int innovationNumberCount = 1;
    public static Hashtable<Integer, Hashtable<Integer,Integer>> connectionTable = new Hashtable<>();

    /**
     *
     * @param idNodeIn
     * @param idNodeOut
     * @return -1 if not exist / id Connection if exist
     */
    private static int doesConnectionExist(int idNodeIn, int idNodeOut){
        if(connectionTable.contains(idNodeIn)){
            if(connectionTable.get(idNodeIn).contains(idNodeOut)){
                return connectionTable.get(idNodeIn).get(idNodeOut);
            }
        }
        return -1;
    }
    public static Connection createConnection(int idNodeIn, int idNodeOut, double weight, boolean enabled, boolean isReccurent)
    {
        int innovationNumber = doesConnectionExist(idNodeIn, idNodeOut);
        if(innovationNumber==-1)
            innovationNumber = innovationNumberCount++;
        return new Connection(innovationNumber, idNodeIn, idNodeOut, weight, enabled, isReccurent);
    }
}
