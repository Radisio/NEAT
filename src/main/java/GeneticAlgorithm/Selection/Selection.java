package GeneticAlgorithm.Selection;

import GeneticAlgorithm.FitnessComputation.FitnessComputation;
import GeneticAlgorithm.Speciation.Specie;

import java.util.List;

public interface Selection {

    List<FitnessComputation> select(Specie pop, int returnedSize);
}
