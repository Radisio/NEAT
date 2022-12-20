package GeneticAlgorithm.Selection;

import GeneticAlgorithm.FitnessComputation.FitnessComputation;
import GeneticAlgorithm.Speciation.Specie;

import java.util.ArrayList;
import java.util.List;

public class TournamentSelection implements Selection{
    private int tournamentSize;

    public TournamentSelection(int tournamentSize) {
        this.tournamentSize = tournamentSize;
    }

    @Override
    public List<FitnessComputation> select(Specie pop, int returnedSize) {
        List<FitnessComputation> returnedList =new ArrayList<>();
        for(int i = 0; i<returnedSize;i++)
        {
            Specie tournament = new Specie(-1, pop.getFitnessComparison() );
            for(int j =0;j<tournamentSize;j++)
            {
                int randomId = (int)(Math.random() * pop.getIndividuals().size());
                tournament.addIndividual(pop.getIndividualByIndex(randomId ));
            }
            returnedList.add(tournament.getFittest());
        }
        return returnedList;
    }
}
