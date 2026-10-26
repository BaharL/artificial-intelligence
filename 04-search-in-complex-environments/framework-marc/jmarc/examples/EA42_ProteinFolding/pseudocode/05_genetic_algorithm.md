# Genetic Algorithm

The Genetic Algorithm maintains a population of candidate protein conformations.

A conformation is represented by a sequence of relative moves.

For example:

    F L R F L R

where:

- `F` means continue forward;
- `L` means turn left;
- `R` means turn right.

These moves can be interpreted as genes.

At every generation, better conformations have a higher probability of being selected as parents.

New individuals are produced using crossover and mutation.

## Fitness

The fitness of a conformation is:

    fitness(state) = number of non-consecutive H-H contacts

A higher fitness value corresponds to a better folding.

## Selection

Two parent states are selected from the population, favouring states with higher fitness.

## Crossover

A crossover point is chosen.

The first part of one parent is combined with the second part of the other parent.

Example:

    Parent 1: F L R | F F L
    Parent 2: R F L | L R F

    Child:    F L R | L R F

## Mutation

With a small probability, one gene is changed randomly.

For example:

    F L R F L

can become:

    F L L F L

After crossover or mutation, the resulting conformation must still be valid.

Invalid conformations are discarded or replaced by another valid state.

## Pseudocode

    GENETIC-ALGORITHM(protein, populationSize,
                      mutationProbability, generations)

        population <- populationSize
                      RANDOM-VALID-STATES(protein)

        best <- best state in population

        for g <- 1 to generations

            newPopulation <- empty set

            while size(newPopulation) < populationSize

                parent1 <- SELECT(population,
                                  favouring higher fitness)

                parent2 <- SELECT(population,
                                  favouring higher fitness)

                crossoverPoint <- random position

                childMoves <-
                    first part of parent1
                    +
                    second part of parent2

                with probability mutationProbability

                    position <- random gene position

                    replace the gene with another move
                    from {F, L, R}

                child <- BUILD-CONFORMATION(childMoves)

                if child is valid
                    add child to newPopulation
                else
                    add RANDOM-VALID-STATE(protein)

            population <- newPopulation

            candidate <- best state in population

            if fitness(candidate) > fitness(best)
                best <- candidate

        return best