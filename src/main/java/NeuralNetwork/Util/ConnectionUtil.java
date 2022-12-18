package NeuralNetwork.Util;

import NeuralNetwork.Connection;

import java.util.AbstractMap;
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
    public static int doesConnectionExist(int idNodeIn, int idNodeOut){
        for(Integer key :connectionTable.keySet())
        if(connectionTable.containsKey(idNodeIn)){
            if(connectionTable.get(idNodeIn).containsKey(idNodeOut)){
                return connectionTable.get(idNodeIn).get(idNodeOut);
            }
        }
        return -1;
    }
    private static void addConnection(int idNodeIn, int idNodeOut, int innovationNumber){
        if(connectionTable.containsKey(idNodeIn))
            connectionTable.get(idNodeIn).put(idNodeOut, innovationNumber);
        else
            connectionTable.put(idNodeIn, new Hashtable<>(){{put(idNodeOut, innovationNumber);}});
    }
    public static Connection createConnection(int idNodeIn, int idNodeOut, double weight, boolean enabled, boolean isReccurent)
    {
        int innovationNumber = doesConnectionExist(idNodeIn, idNodeOut);
        if(innovationNumber==-1) {
            innovationNumber = innovationNumberCount++;
            addConnection(idNodeIn, idNodeOut, innovationNumber);
        }
        return new Connection(innovationNumber, idNodeIn, idNodeOut, weight, enabled, isReccurent);
    }

    public static List<Connection> deepCopyListConnection(List<Connection> connectionList){
        List<Connection> newConList = new ArrayList<>();
        for(Connection con : connectionList){
            newConList.add(new Connection(con));
        }
        return newConList;
    }
}
