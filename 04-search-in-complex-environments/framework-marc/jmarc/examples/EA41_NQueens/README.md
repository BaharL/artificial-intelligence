# EA4.1 - N-Queens

This exercise solves the N-Queens problem using the MARC framework and local search algorithms.

The goal is to place `n` queens on an `n x n` chessboard without two queens attacking each other.

In this implementation I use `n = 8`.

## State representation

A state is represented by a list of integers.

There is one queen for each column and the value in position `i` represents the row of the queen in column `i`.

For example:

`[4, 1, 3, 6, 2, 7, 5, 0]`

represents a complete configuration of 8 queens.

## Moves

A move changes the row of one queen while keeping it in the same column.

For every queen, the possible movements are generated both upward and downward, when the resulting position is still inside the board.

For an 8-queens problem, each state can therefore have up to:

`8 * 7 = 56`

possible neighboring states.

## Heuristic

The heuristic counts the number of pairs of queens attacking each other.

Two queens are in conflict if they are on the same row or on the same diagonal.

The objective is therefore to minimize the heuristic value.

A solution is found when:

`h(state) = 0`

because this means that no queen attacks another queen.

## Algorithms

I tested three local search algorithms available in the MARC framework:

- Steepest Descent
- First Choice Hill Climbing
- Simulated Annealing

For Steepest Descent and First Choice Hill Climbing I used random restarts.

The parameters used in the current test are:

- `n = 8`
- `restarts = 30`
- `maxIdle = 1000`

For Simulated Annealing I used a simple decreasing temperature:

`T(step) = 100 * exp(-0.01 * step)`

At the beginning the temperature is higher, so worse moves can be accepted more easily.
As the execution continues, the temperature decreases.

## Example execution

In one execution I obtained:

### Steepest Descent

`Conflicts: 1.0`

The algorithm stopped in a configuration close to the solution but still containing one conflict.

### First Choice Hill Climbing

Solution:

`[4, 1, 3, 6, 2, 7, 5, 0]`

`Conflicts: 0.0`

In this execution First Choice Hill Climbing found a complete solution.

### Simulated Annealing

`Conflicts: 1.0`

The algorithm finished with one remaining conflict.

The results can change between executions because the algorithms use random states and random choices.

## Files

- `Main.java`: runs the three algorithms
- `NQueens.java`: definition of the N-Queens problem and heuristic
- `QueensBoard.java`: representation of a state
- `MoveQueen.java`: definition of a move
- `SimpleTemperature.java`: temperature function used by Simulated Annealing

The implementation is based on the N-Queens example provided with the MARC framework.