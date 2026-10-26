# First-Choice Hill Climbing with Random Restarts

First-choice hill climbing is similar to steepest ascent, but it does not need to evaluate all the neighbours before making a move.

The neighbours are examined in random order.

As soon as a neighbour that improves the current state is found, the algorithm moves to that state.

If no improving neighbour exists, the current state is a local optimum.

Random restarts allow the algorithm to start again from another region of the search space.

## Fitness

    fitness(state) = number of non-consecutive H-H contacts

A higher value is better.

## Pseudocode

    FIRST-CHOICE(protein, restarts)

        bestOverall <- null

        for r <- 1 to restarts

            current <- RANDOM-VALID-STATE(protein)

            loop

                neighbours <- VALID-NEIGHBOURS(current)

                SHUFFLE(neighbours)

                found <- false

                for each next in neighbours

                    if fitness(next) > fitness(current)

                        current <- next
                        found <- true
                        break

                if found = false
                    break

            if bestOverall = null or
               fitness(current) > fitness(bestOverall)

                bestOverall <- current

        return bestOverall