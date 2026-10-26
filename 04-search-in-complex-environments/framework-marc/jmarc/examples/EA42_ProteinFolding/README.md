# EA4.2 - HP 2D Protein Folding

This exercise applies iterative improvement algorithms to the HP 2D Protein Folding problem.

The input is a protein sequence composed of `H` and `P` amino acids.

The objective is to find a valid 2D conformation that maximizes the number of non-consecutive H-H contacts.

## State representation

A state represents a complete valid conformation of the protein on a 2D grid.

The first amino acid is placed at `(0,0)` and the second one at `(1,0)`.

The remaining conformation can be represented by relative moves:

- `F`: continue straight;
- `L`: turn left;
- `R`: turn right.

A conformation is valid if:

- consecutive amino acids are adjacent;
- two amino acids never occupy the same grid position;
- the chain does not cross itself.

## Objective function

The quality of a state is measured using the number of non-consecutive H-H contacts.

A contact exists when two H amino acids:

- are not consecutive in the protein sequence;
- occupy adjacent cells in the grid.

Therefore:

`fitness(state) = number of H-H contacts`

Higher fitness means a better conformation.

Equivalently, the energy can be defined as:

`energy(state) = -fitness(state)`

so minimizing energy corresponds to maximizing contacts.

## Neighbourhood

A neighbouring state is obtained by changing one relative move of the current conformation.

For example:

`F -> L`

or

`R -> F`

After the change, the coordinates of the following amino acids are reconstructed.

If the new conformation contains overlaps or is not valid, it is discarded.

## Algorithms

The following iterative improvement algorithms are considered:

1. Steepest ascent with random restarts
2. First-choice hill climbing with random restarts
3. Simulated annealing
4. Local beam search
5. Genetic algorithm

These methods try to obtain conformations with increasingly better fitness values.

Since they are local or evolutionary search methods, in a finite execution they do not necessarily guarantee that the best solution found is globally optimal.