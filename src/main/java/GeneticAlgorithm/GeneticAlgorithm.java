package GeneticAlgorithm;

import GeneticAlgorithm.Crossover.CrossOver;
import GeneticAlgorithm.FitnessComparison.FitnessComparison;
import GeneticAlgorithm.FitnessComputation.FitnessComputation;
import GeneticAlgorithm.FitnessComputation.FitnessComputationUtil;
import GeneticAlgorithm.Selection.Selection;
import GeneticAlgorithm.Speciation.Specie;
import GeneticAlgorithm.Speciation.SpecieUtil;
import NeuralNetwork.ActivationFunction.ActivationFunction;
import NeuralNetwork.NeuralNetwork;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm {
    private List<Specie> species;
    private double mutationAddNodeRate;
    private double mutationAddConnectionRate;
    private double mutationChangeUpWeight;
    private double mutationChangeSubWeight;
    private double mutationResetWeight;
    private double solution;
    private double crossOverRate; /// aka survivalRate (Easier for me to understand)
    private int popSize;
    private Selection parentSelection;
    private Selection crossoverSelection;
    private CrossOver crossOver;
    private String nameFitnessComputation;
    private int inputNode;
    private int outputNode;
    private int hiddenNode;
    private ActivationFunction hiddenActivationFunction;
    private ActivationFunction outputActivationFunction;
    private double thresholdSpecie;
    private FitnessComparison fitnessComparison;
    private int speciesTargetSize;
    private double compat_mod;
    public GeneticAlgorithm(double mutationAddNodeRate, double mutationAddConnectionRate,
                            double mutationChangeUpWeight, double mutationChangeSubWeight, double mutationResetWeight,
                            double solution,double crossOverRate, int popSize, Selection parentSelection,
                            Selection crossoverSelection, CrossOver crossOver, String nameFitnessComputation,
                            int inputNode, int outputNode, int hiddenNode, ActivationFunction hiddenActivationFunction,
                            ActivationFunction outputActivationFunction, double thresholdSpecie, FitnessComparison fitnessComparison,
                            int speciesTargetSize, double compat_mod) {
        this.species = new ArrayList<>();
        this.mutationAddNodeRate = mutationAddNodeRate;
        this.mutationAddConnectionRate = mutationAddConnectionRate;
        this.mutationChangeUpWeight = mutationChangeUpWeight;
        this.mutationChangeSubWeight = mutationChangeSubWeight;
        this.mutationResetWeight = mutationResetWeight;
        this.solution = solution;
        this.crossOverRate = crossOverRate;
        this.popSize = popSize;
        this.parentSelection = parentSelection;
        this.crossoverSelection = crossoverSelection;
        this.crossOver = crossOver;
        this.nameFitnessComputation = nameFitnessComputation;
        this.inputNode = inputNode;
        this.outputNode = outputNode;
        this.hiddenNode = hiddenNode;
        this.hiddenActivationFunction = hiddenActivationFunction;
        this.outputActivationFunction = outputActivationFunction;
        this.thresholdSpecie = thresholdSpecie;
        this.fitnessComparison = fitnessComparison;
        this.speciesTargetSize = speciesTargetSize;
        this.compat_mod = compat_mod;
    }
    private int getLastIdSpecie(){
        int returnedId = 0;
        for(int i =0;i<species.size();i++)
            if(species.get(i).getId()>returnedId)
                returnedId = species.get(i).getId();
        return returnedId;
    }
    private void adaptThreshold(){
        if(species.size()<speciesTargetSize)
            thresholdSpecie+=compat_mod;
        else if (species.size()>speciesTargetSize)
            thresholdSpecie-=compat_mod;

        if(thresholdSpecie<0.3) thresholdSpecie=0.3;
    }
    private void gen0(){
        List<FitnessComputation> firstPop = FitnessComputationUtil.generate(this.nameFitnessComputation,
                this.popSize, this.inputNode, this.outputNode, this.hiddenNode, this.hiddenActivationFunction, this.outputActivationFunction);
        while(firstPop.size()!=0)
        {
            int randomIndex = (int)(Math.random() * firstPop.size());
            FitnessComputation firstMemberOfNewSpecie = firstPop.get(randomIndex);
            firstPop.remove(randomIndex);
            List<FitnessComputation> newSpecie = new ArrayList<>();
            newSpecie.add(firstMemberOfNewSpecie);
            for(int i =0;i<firstPop.size();i++)
            {

                if(SpecieUtil.getDifferenceBetweenTwoIndividuals(firstMemberOfNewSpecie.getNeuralNetwork(), firstPop.get(i).getNeuralNetwork())<this.thresholdSpecie)
                {
                    newSpecie.add(firstPop.get(i));
                    firstPop.remove(i);
                    i--;
                }
            }
            species.add(new Specie(getLastIdSpecie()+1,newSpecie, this.fitnessComparison));
        }
    }
    private void computeOffspringOfSpecies(){
        double globalAverage = SpecieUtil.getGlobalAverage(species);
        for(Specie spe : species){
            spe.computeOffSpringAllowed(globalAverage);
        }
    }

    private void evaluatePopulation(){
        for(Specie spe: species){
            for(FitnessComputation fc : spe.getIndividuals()){
                fc.computeFitness();
            }
            spe.computeAdjustedFitness();
            spe.computeAverageAdjustedFitness();

        }
        computeOffspringOfSpecies();

    }

    private double getBestFitnessValue(){
        double bestFitness = -1;
        for(Specie spe: species)
        {
            if(fitnessComparison.fitness1Better(spe.getFittest().getFitness(),bestFitness))
            {
                bestFitness=spe.getFittest().getFitness();
            }
        }
        return bestFitness;
    }

    private FitnessComputation getFittestOverAll(){
        double bestFitness = -1;
        FitnessComputation returnedFc = null;
        for(Specie spe: species)
        {
            if(spe.getFittest().getFitness()>bestFitness)
            {
                returnedFc=spe.getFittest();
                bestFitness=returnedFc.getFitness();
            }
        }
        return returnedFc;
    }


    public FitnessComputation runAlgorithm(int maxIter){
        gen0();
        int generationCount = 1;
        evaluatePopulation();
        while(fitnessComparison.fitness1Better(this.solution, getBestFitnessValue()) && generationCount<maxIter){
            System.out.println("Génération : " + generationCount);
            System.out.println("Best score : " + getBestFitnessValue());
            System.out.println("Nb connexion : " + getFittestOverAll().getNeuralNetwork().getConnectionList().size());
            System.out.println("Nb Node : " + getFittestOverAll().getNeuralNetwork().getNodeList().size());
            System.out.println("Number of species : " + species.size());
            System.out.println("Nb pop : " + numberPopTotal() );
            System.out.println("Threshold : " + thresholdSpecie);
            System.out.println("Target species size : " + speciesTargetSize);
            adaptThreshold();
            evolvePopulation();
            evaluatePopulation();
            generationCount++;
        }
        if(generationCount>=maxIter)
            System.out.println("Max iteration reached");
        else
            System.out.println("Solution found");
        return getFittestOverAll();
    }
    private int getBestSpecieIndex(){
        int index = -1;
        double bestFitness = -1;
        for(int i =0;i<species.size();i++)
        {
            if(fitnessComparison.fitness1Better(species.get(i).getFittest().getFitness(),bestFitness))
            {
                bestFitness = species.get(i).getFittest().getFitness();
                index = i;
            }
        }
        return index;
    }
    private void killSpecies(){
        int indexBest = getBestSpecieIndex();
        for(int i =0;i<species.size();i++)
        {
            if(!species.get(i).killPopulation(this.crossOverRate, i==indexBest))
            {
                species.remove(i);
                i--;
            }
        }
    }
    private void fillSpecies(){
        for(Specie spe: species){
            fillSpecie(spe);
        }
    }
    private void mutate(List<FitnessComputation> fcList){
        Random r = new Random();
        for(FitnessComputation fit : fcList){
            if(r.nextDouble()<0.8)
            {
                int randomInnovationNumberEnnabled = fit.getNeuralNetwork().getRandomInnovationNumberEnabled();
                if(r.nextDouble()<0.9)
                {
                    if(r.nextDouble()<0.5)
                    {
                        if(randomInnovationNumberEnnabled!=-1)
                            fit.getNeuralNetwork().mutateSubWeightByIN(randomInnovationNumberEnnabled);
                    }
                    else{
                        if(randomInnovationNumberEnnabled!=-1)
                            fit.getNeuralNetwork().mutateAddWeightByIN(randomInnovationNumberEnnabled);

                    }
                }
                else{
                    if(randomInnovationNumberEnnabled!=-1)
                        fit.getNeuralNetwork().mutateNewWeightByIN(randomInnovationNumberEnnabled);
                }
            }
            else{
                if(r.nextDouble()<mutationAddNodeRate)
                {
                    int randomInnovationNumberEnnabled = fit.getNeuralNetwork().getRandomInnovationNumberEnabled();
                    if(randomInnovationNumberEnnabled!=-1) {
                        fit.getNeuralNetwork().mutationBreakConnection(randomInnovationNumberEnnabled);
                }
                else
                {
                    if(r.nextDouble()<mutationAddConnectionRate)
                    {
                        fit.getNeuralNetwork().addConnection();
                    }
                }
            }
        }
        /*
        for(FitnessComputation fit : fcList){
            if(r.nextDouble()<mutationAddNodeRate) {
                int randomInnovationNumberEnnabled = fit.getNeuralNetwork().getRandomInnovationNumberEnabled();
                if(randomInnovationNumberEnnabled!=-1) {
                    fit.getNeuralNetwork().mutationBreakConnection(randomInnovationNumberEnnabled);
                    continue;
                }
            }
            if(r.nextDouble()<mutationAddConnectionRate){
                fit.getNeuralNetwork().addConnection();
                continue;
            }
            if(r.nextDouble()<mutationChangeSubWeight){
                int randomInnovationNumberEnnabled = fit.getNeuralNetwork().getRandomInnovationNumberEnabled();
                if(randomInnovationNumberEnnabled!=-1) {
                    fit.getNeuralNetwork().mutateSubWeightByIN(randomInnovationNumberEnnabled);
                    continue;
                }
            }
            if(r.nextDouble()<mutationResetWeight){
                int randomInnovationNumberEnnabled = fit.getNeuralNetwork().getRandomInnovationNumberEnabled();
                if(randomInnovationNumberEnnabled!=-1) {
                    fit.getNeuralNetwork().mutateNewWeightByIN(randomInnovationNumberEnnabled);
                    continue;
                }
            }
            if(r.nextDouble()<mutationChangeUpWeight){
                int randomInnovationNumberEnnabled = fit.getNeuralNetwork().getRandomInnovationNumberEnabled();
                if(randomInnovationNumberEnnabled!=-1) {
                    fit.getNeuralNetwork().mutateAddWeightByIN(randomInnovationNumberEnnabled);
                }
            }*/
        }
    }

    public void fillSpecie(Specie spec){
        if(spec.getIndividuals().size()<=spec.getOffSpringAllowed()){

            int difference =(int)Math.round(spec.getOffSpringAllowed()) - spec.getIndividuals().size();
            List<FitnessComputation> newIndividuals = FitnessComputationUtil.generate(this.nameFitnessComputation,
                    difference, this.inputNode, this.outputNode, this.hiddenNode, this.hiddenActivationFunction, this.outputActivationFunction);
            for(int i =0;i<difference;i++)
            {
                List<FitnessComputation> fit=crossoverSelection.select(spec,2);
                NeuralNetwork newInd= crossOver.crossover(fit.get(0),fit.get(1));
                newIndividuals.get(i).setNeuralNetwork(newInd);
            }
            ///Mutation
            mutate(newIndividuals);
            for(FitnessComputation fc: newIndividuals)
                spec.addIndividual(fc);

        }
        else{
            ///Too many individuals, kill the weaks
            int difference = spec.getIndividuals().size()-(int)Math.round(spec.getOffSpringAllowed());
            int remain = spec.getIndividuals().size()-difference;
            if(remain>0)
                spec.getIndividuals().subList(0, remain);
            else{
                spec.getIndividuals().clear();
            }
        }
    }
    private int numberPopTotal(){
        int count = 0;
        for(Specie spe : species)
            count+=spe.getIndividuals().size();
        return count;
    }
    private void evolvePopulation(){
        killSpecies();

        ///On va créer les enfants des espèces
        fillSpecies();
        ///Delete species empty
        for(int i = 0;i<species.size();i++)
        {
            if(species.get(i).getIndividuals().size()==0)
            {
                species.remove(i);
                i--;
            }
        }
        System.out.println("Species size : " + species.size());
        int count = numberPopTotal();
        List<FitnessComputation> pop = new ArrayList<>();
        System.out.println("Count : " + count);
        if(count<this.popSize)
        {
            pop =fillPop(count);
        }
        ///Respeciate every individual
        List<FitnessComputation> presidents = new ArrayList<>();
        ///Elect president and fill population
        for(Specie spe: species) {
            FitnessComputation pres = spe.randomIndividual();
            spe.getIndividuals().remove(pres);
            presidents.add(pres);
            pop.addAll(spe.getIndividuals());
            spe.setIndividuals(new ArrayList<>());
        }

        for(int i =0;i<presidents.size();i++)
        {
            for(int j=0;j<pop.size();j++)
            {
                if(SpecieUtil.getDifferenceBetweenTwoIndividuals(presidents.get(i).getNeuralNetwork(), pop.get(j).getNeuralNetwork())<this.thresholdSpecie)
                {
                    species.get(i).addIndividual(pop.get(j));
                    pop.remove(j);
                    j--;
                }
            }
            species.get(i).addIndividual(presidents.get(i));
        }
        while(pop.size()!=0)
        {
            int randomIndex = (int)(Math.random() * pop.size());
            FitnessComputation firstMemberOfNewSpecie = pop.get(randomIndex);
            pop.remove(randomIndex);
            List<FitnessComputation> newSpecie = new ArrayList<>();
            newSpecie.add(firstMemberOfNewSpecie);
            for(int i =0;i<pop.size();i++)
            {
                if(SpecieUtil.getDifferenceBetweenTwoIndividuals(firstMemberOfNewSpecie.getNeuralNetwork(), pop.get(i).getNeuralNetwork())<this.thresholdSpecie)
                {
                    newSpecie.add(pop.get(i));
                    pop.remove(i);
                    i--;
                }
            }
            species.add(new Specie(getLastIdSpecie()+1,newSpecie, this.fitnessComparison));
        }

        ///Delete species empty
        for(int i = 0;i<species.size();i++)
        {
            if(species.get(i).getIndividuals().size()==0)
            {
                species.remove(i);
                i--;
            }
        }

    }

    private List<NeuralNetwork> getNeuralNetworkFromRandomSpecies(int count){
        List<NeuralNetwork> returnedList = new ArrayList<>();
        for(int i =0;i<count;i++)
        {
            int specieIndex = (int) (Math.random() * species.size());
            System.out.println("Species indexed size : " + species.get(specieIndex).getIndividuals().size());
            FitnessComputation fc1 = crossoverSelection.select(species.get(specieIndex),1).get(0);
            specieIndex = (int) (Math.random() * species.size());
            FitnessComputation fc2  = crossoverSelection.select(species.get(specieIndex),1).get(0);
            returnedList.add(crossOver.crossover(fc1,fc2));
        }
        return returnedList;
    }

    private List<FitnessComputation> fillPop(int count){
        int difference = this.popSize - count;
        List<FitnessComputation> newIndividuals = FitnessComputationUtil.generate(this.nameFitnessComputation,
                difference, this.inputNode, this.outputNode, this.hiddenNode, this.hiddenActivationFunction, this.outputActivationFunction);
        List<NeuralNetwork> newNeuralNetworks = getNeuralNetworkFromRandomSpecies(difference);
        for(int i =0;i<difference;i++)
            newIndividuals.get(i).setNeuralNetwork(newNeuralNetworks.get(i));
        mutate(newIndividuals);
        //speciateNewIndividuals(newIndividuals);
        return newIndividuals;
    }

    private void speciateNewIndividuals(List<FitnessComputation> fc){
        boolean added = false;
        for(int i =0;i<fc.size();i++)
        {
            added=false;
            for(Specie spe : species)
            {
                if(SpecieUtil.getDifferenceBetweenTwoIndividuals(spe.randomIndividual().getNeuralNetwork(), fc.get(i).getNeuralNetwork())<this.thresholdSpecie)
                {
                    spe.addIndividual(fc.get(i));
                    added=true;
                    break;
                }
            }
            if(!added) {
                List<FitnessComputation> newSpecie = new ArrayList<>();
                newSpecie.add(fc.get(i));
                species.add(new Specie(getLastIdSpecie(), newSpecie, this.fitnessComparison));
            }
        }
    }


}
