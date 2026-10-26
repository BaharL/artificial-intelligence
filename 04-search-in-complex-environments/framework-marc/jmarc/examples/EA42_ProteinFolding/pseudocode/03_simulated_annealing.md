# Simulated Annealing

Simulated Annealing is a local search algorithm that can sometimes accept a worse state.

This behaviour helps the algorithm escape from local optima.

At the beginning, when the temperature is high, worse moves can be accepted with relatively high probability.

During the execution the temperature decreases, so the algorithm becomes progressively more selective.

## Fitness

    fitness(state) = number of non-consecutive H-H contacts

A higher fitness value is better.

## Acceptance probability

For a candidate state:

    delta = fitness(next) - fitness(current)

If:

    delta >= 0

the new state is accepted.

If:

    delta < 0

the worse state can still be accepted with probability:

    p = exp(delta / T)

where `T` is the current temperature.

As the temperature decreases, the probability of accepting worse moves also decreases.

## Pseudocode

    SIMULATED-ANNEALING(protein, initialTemperature, alpha, maxSteps)

        current <- RANDOM-VALID-STATE(protein)

        best <- current

        T <- initialTemperature

        for step <- 1 to maxSteps

            if T is very small
                break

            next <- RANDOM-VALID-NEIGHBOUR(current)

            delta <- fitness(next) - fitness(current)

            if delta >= 0

                current <- next

            else

                p <- exp(delta / T)

                if RANDOM(0,1) < p
                    current <- next

            if fitness(current) > fitness(best)
                best <- current

            T <- alpha * T

        return best