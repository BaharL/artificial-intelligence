import jmarc.solving.MARCSequentialSolver;
import jmarc.solving.MARCSolverMonitor;
import jmarc.solving.MARCFirstChoiceHillClimbing;
import jmarc.solving.MARCSteepestDescent;
import jmarc.solving.MARCSimulatedAnnealing;
import jmarc.modeling.MARCSolution;

import java.util.ArrayList;
import java.util.HashSet;


public class Main {

    public static void main(String[] args) {

        int dim = 8;
        int maxIdle = 1000;
        int restarts = 30;

        System.out.println("N-Queens exercise: " + dim + " queens");

        /*
         * Initial board:
         * one queen for each column, initially all in row 0.
         */
        ArrayList<Integer> initBoard = new ArrayList<>();

        for (int i = 0; i < dim; i++) {
            initBoard.add(0);
        }

        NQueens problem = new NQueens(dim);

        QueensBoard initState =
                new QueensBoard(problem, initBoard);

        problem.setInitialState(initState);

        System.out.println(
                "Initial conflicts: "
                        + problem.heuristic(initState)
        );


        /*
         * Possible moves for the queens.
         */
        HashSet<MoveQueen> store = new HashSet<>();

        for (int i = 0; i < dim; i++) {

            for (int j = -(dim - 1); j < dim; j++) {

                if (j != 0) {

                    store.add(
                            new MoveQueen(
                                    j,
                                    i,
                                    problem,
                                    "move queen " + i + " by " + j
                            )
                    );
                }
            }
        }


        /* =========================================
           1. STEEPEST DESCENT
           ========================================= */

        System.out.println("\n--- Steepest Descent ---");

        MARCSequentialSolver steepestSolver =
                new MARCSequentialSolver(problem);

        MARCSolverMonitor steepestMonitor =
                new MARCSolverMonitor("Steepest");

        steepestSolver.setMonitor(steepestMonitor);

        MARCSteepestDescent steepest =
                new MARCSteepestDescent();

        steepestSolver.scheduleAlgorithm(
                steepest,
                restarts,
                true
        );

        MARCSolution steepestSolution =
                steepestSolver.solve();

        QueensBoard steepestBoard =
                (QueensBoard)
                        steepestSolution.getStateSolution();

        System.out.println(
                "Solution: "
                        + steepestBoard.getValue()
        );

        System.out.println(
                "Conflicts: "
                        + problem.heuristic(steepestBoard)
        );


        /* =========================================
           2. FIRST CHOICE HILL CLIMBING
           ========================================= */

        System.out.println(
                "\n--- First Choice Hill Climbing ---"
        );

        MARCSequentialSolver firstSolver =
                new MARCSequentialSolver(problem);

        MARCSolverMonitor firstMonitor =
                new MARCSolverMonitor("FirstChoice");

        firstSolver.setMonitor(firstMonitor);

        MARCFirstChoiceHillClimbing firstChoice =
                new MARCFirstChoiceHillClimbing(maxIdle);

        firstSolver.scheduleAlgorithm(
                firstChoice,
                restarts,
                true
        );

        MARCSolution firstSolution =
                firstSolver.solve();

        QueensBoard firstBoard =
                (QueensBoard)
                        firstSolution.getStateSolution();

        System.out.println(
                "Solution: "
                        + firstBoard.getValue()
        );

        System.out.println(
                "Conflicts: "
                        + problem.heuristic(firstBoard)
        );


        /* =========================================
           3. SIMULATED ANNEALING
           ========================================= */

        System.out.println(
                "\n--- Simulated Annealing ---"
        );

        SimpleTemperature temperature =
                new SimpleTemperature(
                        100.0,
                        0.01
                );

        MARCSimulatedAnnealing simulatedAnnealing =
                new MARCSimulatedAnnealing(
                        temperature,
                        maxIdle
                );

        MARCSequentialSolver simulatedSolver =
                new MARCSequentialSolver(problem);

        MARCSolverMonitor simulatedMonitor =
                new MARCSolverMonitor(
                        "SimulatedAnnealing"
                );

        simulatedSolver.setMonitor(simulatedMonitor);

        simulatedSolver.scheduleAlgorithm(
                simulatedAnnealing,
                1,
                true
        );

        MARCSolution simulatedSolution =
                simulatedSolver.solve();

        QueensBoard simulatedBoard =
                (QueensBoard)
                        simulatedSolution.getStateSolution();

        System.out.println(
                "Solution: "
                        + simulatedBoard.getValue()
        );

        System.out.println(
                "Conflicts: "
                        + problem.heuristic(simulatedBoard)
        );
    }
}