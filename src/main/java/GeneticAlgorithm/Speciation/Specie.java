package GeneticAlgorithm.Speciation;

import GeneticAlgorithm.FitnessComparison.FitnessComparison;
import GeneticAlgorithm.FitnessComputation.FitnessComputation;

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
            double fitness = individual.getFitness();
            individual.setAdjustedFitness(fitness/(double)size);
        }
        individuals.sort(((o1, o2) -> fitnessComparison.order(o1.getFitness(), o2.getFitness())));

    }

    /***
     *
     * @param crossOverRate
     * @param isBestSpecie
     * @return false if specie has to be killed/ true if there is still some members
     */
    public boolean killPopulation(double crossOverRate, boolean isBestSpecie){
        if(generationSinceImproved>=15 && !isBestSpecie)
        {
            return false;
        }
        int nbSurvivor =(int)Math.round(individuals.size() * (1-crossOverRate));
        if(nbSurvivor==0)
            nbSurvivor=1;
        individuals=individuals.subList(0,nbSurvivor);
        return true;
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
    public FitnessComputation getIndividualByIndex(int index){return individuals.get(index);}

    public FitnessComputation getFittest(){
        FitnessComputation returned = null;
        int size = individuals.size();
        if(size>0) {
            returned = individuals.get(0);
            for (int i = 1; i < size; i++)
            {
                if(fitnessComparison.fitness1Better(individuals.get(i).getFitness(),returned.getFitness()))
                {
                    returned = individuals.get(i);
                }
            }
        }
        return returned;
    }

    public FitnessComparison getFitnessComparison() {
        return fitnessComparison;
    }

    public FitnessComputation randomIndividual(){
        return individuals.get((int)(Math.random()*individuals.size()));
    }

    public int getGenerationSinceImproved() {
        return generationSinceImproved;
    }
}
