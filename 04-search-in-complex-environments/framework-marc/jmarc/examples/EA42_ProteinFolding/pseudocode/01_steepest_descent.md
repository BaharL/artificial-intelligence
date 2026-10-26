# Steepest Ascent with Random Restarts

The algorithm starts from a random valid conformation of the protein.

At each iteration, all valid neighbours of the current state are generated.

The neighbour having the highest fitness value is selected.

If no neighbour improves the current state, the algorithm has reached a local optimum.

To reduce the probability of remaining trapped in a local optimum, the search is repeated from different random initial states.

## Fitness

The fitness of a state is the number of non-consecutive H-H contacts:

    fitness(state) = number of H-H contacts

A higher fitness value corresponds to a better folding.

## Pseudocode

    STEEPEST-ASCENT(protein, restarts)

        bestOverall <- null

        for r <- 1 to restarts

            current <- RANDOM-VALID-STATE(protein)

            loop

                neighbours <- VALID-NEIGHBOURS(current)

                if neighbours is empty
                    break

                bestNeighbour <- state in neighbours
                                 with maximum fitness

                if fitness(bestNeighbour) <= fitness(current)
                    break

                current <- bestNeighbour

            if bestOverall = null or
               fitness(current) > fitness(bestOverall)

                bestOverall <- current

        return bestOverall