# Local Beam Search

Local Beam Search keeps several states at the same time.

Instead of following only one current state, the algorithm maintains a population of `k` states.

At each iteration, all valid successors of the current `k` states are generated.

The best `k` successors are then selected and used as the new population.

This allows information coming from different search regions to influence the next iteration.

## Fitness

    fitness(state) = number of non-consecutive H-H contacts

The states with higher fitness are preferred.

## Pseudocode

    LOCAL-BEAM(protein, k, maxIterations)

        population <- k RANDOM-VALID-STATES(protein)

        best <- best state in population

        for iteration <- 1 to maxIterations

            successors <- empty set

            for each state in population

                neighbours <- VALID-NEIGHBOURS(state)

                add all neighbours to successors

            if successors is empty
                break

            population <- k states in successors
                          with highest fitness

            candidate <- best state in population

            if fitness(candidate) > fitness(best)
                best <- candidate

        return best