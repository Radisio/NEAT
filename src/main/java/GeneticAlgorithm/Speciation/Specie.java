package GeneticAlgorithm.Speciation;

import FitnessComparison.FitnessComparison;
import FitnessComputation.FitnessComputation;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Specie {
    private int id;
    private FitnessComparison fitnessComparison;
    private List<FitnessComputation> individuals;
    private double averageFitness;
    private double offSpringAllowed;
    private int generationSinceImproved;
    private double bestAverageFitness;
    public Specie(int id,List<FitnessComputation> individuals, FitnessComparison fitnessComparison) {
        this.id = id;
        this.individuals = individuals;
        this.generationSinceImproved=0;
        this.bestAverageFitness = -1;
        this.fitnessComparison = fitnessComparison;
    }

    public Specie(int id, FitnessComparison fitnessComparison){
        this.id = id;
        this.fitnessComparison = fitnessComparison;
        this.individuals = new ArrayList<>();
        this.generationSinceImproved = 0;
        this.bestAverageFitness = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<? extends FitnessComputation> getIndividuals() {
        return individuals;
    }

    public void setIndividuals(List<FitnessComputation> individuals) {
        this.individuals = individuals;
    }

    public double getAverageFitness() {
        return averageFitness;
    }

    public void setAverageFitness(double averageFitness) {
        this.averageFitness = averageFitness;
    }

    public void computeAverageAdjustedFitness(){
        double totalFitness = 0.0;
        for(FitnessComputation individual : individuals)
        {
            totalFitness+=individual.getAdjustedFitness();
        }
        averageFitness = totalFitness/individuals.size();
        if(fitnessComparison.fitness1Better(averageFitness, bestAverageFitness))
        {
            bestAverageFitness = averageFitness;
            generationSinceImproved = 0;
        }
        else
            generationSinceImproved++;
    }
    public void computeAdjustedFitness(){
        int size = individuals.size();
        for(FitnessComputation individual : individuals){
            individual.setAdjustedFitness(individual.getFitness()/size);
        }
    }

    public double getOffSpringAllowed() {
        return offSpringAllowed;
    }

    public void computeOffSpringAllowed(double globalAvg){
        this.offSpringAllowed = (this.averageFitness/globalAvg )*individuals.size();
    }
    /*public void setOffSpringAllowed(double offSpringAllowed) {
        this.offSpringAllowed = offSpringAllowed;
    }*/

    public void addIndividual(FitnessComputation fc){
        individuals.add(fc);
    }
}
