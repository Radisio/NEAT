package GameUtil;

import Game.Const.DesignConst;
import Game.Creature.Creature;
import Game.Environment.Environment;
import Game.Game;
import Game.Movement.MovementConst;

import java.util.ArrayList;
import java.util.List;

public class GameNeat {
    public static List<Double> gameToNNInput(Game game, int nbInput)
    {
        Environment env = game.getEnvironment();
        int nodeEnv = env.getBoard().length*env.getBoard()[0].length;
        if(nodeEnv+4 > nbInput)
            throw new RuntimeException("Input layer too small");
        int nLine = env.getBoard().length;
        int nCol = env.getBoard()[0].length;
        List<Double> returnedList = new ArrayList<>();
        for(int i =0;i<nLine;i++)
        {
            for(int j =0;j<nCol;j++)
            {
                if(env.getBoard()[i][j].getOccupation()== DesignConst.EMPTY)
                    returnedList.add(0.0);
                else
                    returnedList.add(1.0);
            }
        }
        if(nodeEnv<nbInput-4)
        {
            int difference = nbInput-nodeEnv-4;
            for(int i =0;i<difference;i++)
            {
                returnedList.add(1.0);
            }
        }
        returnedList.add(game.getCreaturePosition().x*1.0);
        returnedList.add(game.getCreaturePosition().y*1.0);
        returnedList.add(env.getEndingPos().x*1.0);
        returnedList.add(env.getEndingPos().y*1.0);
        return returnedList;
    }

    private static int getMax(List<Double> outputs){
        int index = -1;
        double bestProba = -Integer.MAX_VALUE;
        for(int i = 0;i<outputs.size();i++)
        {
            if(outputs.get(i)>bestProba){
                bestProba = outputs.get(i);
                index = i;
            }
        }
        return index;
    }

    public static MovementConst movementBasedOnOutput(List<Double> outputs)
    {

        int index = getMax(outputs);
        switch(index){
            case 0:
                return MovementConst.UP;
            case 1:
                return MovementConst.UP_RIGHT;
            case 2: return MovementConst.RIGHT;
            case 3: return MovementConst.DOWN_RIGHT;
            case 4: return MovementConst.DOWN;
            case 5: return MovementConst.DOWN_LEFT;
            case 6: return MovementConst.LEFT;
            case 7: return MovementConst.UP_LEFT;
        }
        return null;
    }
}
