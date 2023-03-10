package Game;

import Game.Const.DesignConst;
import Game.Creature.Creature;
import Game.Environment.Environment;
import Game.Environment.EnvironmentUtil;
import Game.MathUtil.MathUtil;
import Game.MathUtil.Position2D;
import Game.Movement.MovementConst;
import GameUtil.GameNeat;
import NeuralNetwork.NeuralNetwork;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Game {
    private Environment environment;
    private Creature creature;
    private double score;
    private Position2D creaturePosition;
    private AtomicBoolean end;
    private boolean debug;
    public Game(Environment environment, Creature creature) {
        this.environment = environment;
        this.creature = creature;
        this.creaturePosition=null;
        this.score = Double.MAX_VALUE;
        end = new AtomicBoolean(true);
    }

    public double getScore()
    {
        synchronized (this) {
            return this.score;
        }
    }

    public void computeScore(int nbMouvement)
    {
        synchronized (this) {
            this.score = MathUtil.euclidianDistance(this.creaturePosition, this.environment.getEndingPos())*0.66 + nbMouvement*0.33;


        }
    }

    private void display()
    {
        System.out.println("\rScore : " + this.score +"\r");
        System.out.println("\r"+this.environment + "\r");
    }

    public void setCreature(Creature creature) {
        this.creature = creature;
    }

    private void updateCreaturePosition(Position2D pos){
        if(this.environment.setCreaturePosition(pos, this.creaturePosition))
        {
            this.creaturePosition = pos;
        }
    }

    public boolean isEnd() {
        return end.get();
    }
    public boolean isRunning(){
        return !end.get();
    }

    public void setEnd(boolean end) {
        this.end.set(end);
    }


    public void startDebug(int maxNbTick, int time, TimeUnit unit) throws InterruptedException {
        updateCreaturePosition(this.environment.getStartingPos());
        int i = 0;
        int mouvementI = 0;
        computeScore(0);
        setEnd(false);
        while(this.creaturePosition!=this.environment.getEndingPos() && mouvementI<this.creature.getMovements().size() && i<maxNbTick)
        {
            List<Position2D> positions = EnvironmentUtil.doMovement(this.environment.getBoard(), this.creaturePosition, this.creature.getMovements().get(mouvementI));
            for(Position2D pos : positions)
            {
                updateCreaturePosition(pos);
                computeScore(i);
                unit.sleep(time);
                if(isDebug())
                    display();
                i++;
            }
            mouvementI++;
        }
        setEnd(true);



        reinitializeEnvironment();
    }

    public void startNoDisplayLimited(int maxNbTick) throws InterruptedException{
        updateCreaturePosition(this.environment.getStartingPos());
        int i = 0;
        int mouvementI = 0;
        computeScore(i);
        while(this.creaturePosition!=this.environment.getEndingPos() && mouvementI<this.creature.getMovements().size() && i<maxNbTick)
        {
            List<Position2D> positions = EnvironmentUtil.doMovement(this.environment.getBoard(), this.creaturePosition, this.creature.getMovements().get(mouvementI));
            for(Position2D pos : positions)
            {
                updateCreaturePosition(pos);
                computeScore(i);
                i++;
            }
            mouvementI++;
        }



        reinitializeEnvironment();
    }

    public void start() throws InterruptedException {
        updateCreaturePosition(this.environment.getStartingPos());
        display();
        int i = 0;
        int mouvementI = 0;
        computeScore(i);
        while(this.creaturePosition!=this.environment.getEndingPos() && mouvementI<this.creature.getMovements().size())
        {
            List<Position2D> positions = EnvironmentUtil.doMovement(this.environment.getBoard(), this.creaturePosition, this.creature.getMovements().get(mouvementI));
            for(Position2D pos : positions)
            {
                updateCreaturePosition(pos);
                Thread.sleep(1000);
                computeScore(i);
                display();
                i++;
            }
            mouvementI++;
        }
        reinitializeEnvironment();

    }

    public void startTrainNeat(NeuralNetwork nn, int maxTick) {
        updateCreaturePosition(this.environment.getStartingPos());
        int i = 0;
        int mouvementI = 0;
        computeScore(i);
        while(this.creaturePosition!=this.environment.getEndingPos() && mouvementI<maxTick)
        {
            nn.resetNodeOutput();
            nn.loadInput(GameNeat.gameToNNInput(this, nn.getInputNode()));
            nn.runNetwork();
            MovementConst movement = GameNeat.movementBasedOnOutput(nn.getOutputs());
            List<Position2D> positions = EnvironmentUtil.doMovement(this.environment.getBoard(), this.creaturePosition, movement);
            for(Position2D pos : positions)
            {
                updateCreaturePosition(pos);
                computeScore(i);
                i++;
            }
            mouvementI++;
        }
        reinitializeEnvironment();
    }
    public void startTrainNeatDisplay(NeuralNetwork nn, int maxTick) throws InterruptedException {
        updateCreaturePosition(this.environment.getStartingPos());
        display();
        int i = 0;
        computeScore(i);
        int movementI = 0;
        while(this.creaturePosition!=this.environment.getEndingPos() && movementI<maxTick)
        {
            nn.resetNodeOutput();
            nn.loadInput(GameNeat.gameToNNInput(this, nn.getInputNode()));
            nn.runNetwork();
            MovementConst movement = GameNeat.movementBasedOnOutput(nn.getOutputs());
            List<Position2D> positions = EnvironmentUtil.doMovement(this.environment.getBoard(), this.creaturePosition, movement);
            for(Position2D pos : positions)
            {
                updateCreaturePosition(pos);
                Thread.sleep(1000);
                computeScore(i);
                display();
                i++;
            }
            movementI++;
        }
        reinitializeEnvironment();
    }

    public void reinitializeEnvironment()
    {

        if(this.creaturePosition!=null) {
            this.environment.setBoardOccupation(this.creaturePosition, DesignConst.EMPTY);
            creaturePosition=null;
        }
        this.environment.setBoardOccupation(this.environment.getStartingPos(), DesignConst.STARTING_FLAG);
        this.environment.setBoardOccupation(this.environment.getEndingPos(), DesignConst.ENDING_FLAG);


    }

    public Environment getEnvironment() {
        return environment;
    }

    public Creature getCreature() {
        return creature;
    }

    public Position2D getCreaturePosition() {
        return creaturePosition;
    }

    public synchronized boolean isDebug() {
        return debug;
    }

    public synchronized void setDebug(boolean debug) {
        this.debug = debug;
    }
}
