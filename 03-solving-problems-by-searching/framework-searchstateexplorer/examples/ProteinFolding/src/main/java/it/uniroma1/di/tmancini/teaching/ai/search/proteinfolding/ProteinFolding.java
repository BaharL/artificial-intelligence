package it.uniroma1.di.tmancini.teaching.ai.search.proteinfolding;

import it.uniroma1.di.tmancini.teaching.ai.search.*;
import picocli.CommandLine;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

public class ProteinFolding
        extends Problem
        implements Callable<Integer> {

    @CommandLine.Option(
            names = {"--size"},
            required = true,
            description = "The size of the protein.")
    private int size;

    @CommandLine.Option(
            names = {"--algos", "--algorithms"},
            required = true,
            split = ",",
            description = "Algorithms: MINCOST, BFG, A*")
    private String[] algos;

    @CommandLine.Option(
            names = {"--protein"},
            required = true,
            description = "Protein sequence containing H and P.")
    private String protein;

    @CommandLine.Option(
            names = {"-v", "--verbosity"},
            defaultValue = "0",
            description = "Verbosity level from 0 to 3.")
    private int vlevel;

    public ProteinFolding() {
        super("ProteinFolding");
    }

    private void checkInput() {

        List<String> algorithms =
                Arrays.asList(
                        "mincost",
                        "bfg",
                        "a*");

        for (String algo : algos) {

            String current =
                    algo.trim().toLowerCase();

            if (!algorithms.contains(current)) {

                throw new IllegalArgumentException(
                        "[ERROR] Unknown algorithm: "
                                + algo);
            }
        }

        if (protein == null || protein.isEmpty()) {

            throw new IllegalArgumentException(
                    "[ERROR] Protein cannot be empty.");
        }

        protein = protein.toUpperCase();

        for (char c : protein.toCharArray()) {

            if (c != 'H' && c != 'P') {

                throw new IllegalArgumentException(
                        "[ERROR] Protein can contain only H and P.");
            }
        }

        if (size != protein.length()) {

            throw new IllegalArgumentException(
                    "[ERROR] Size must be equal to protein length.");
        }

        if (vlevel < 0 || vlevel > 3) {

            throw new IllegalArgumentException(
                    "[ERROR] Verbosity must be between 0 and 3.");
        }
    }

    @Override
    public Integer call() {

        try {

            checkInput();

            ProteinFoldingState initialState =
                    ProteinFoldingState
                            .newInitialState(this);

            System.out.println(
                    "Initial state:\n"
                            + initialState);

            for (String algo : algos) {

                String algorithm =
                        algo.trim().toLowerCase();

                SearchStateExplorer explorer;

                switch (algorithm) {

                    case "mincost":
                        explorer =
                                new MinCostExplorer(this);
                        break;

                    case "bfg":
                        explorer =
                                new BestFirstGreedyExplorer(this);
                        break;

                    case "a*":
                        explorer =
                                new AstarExplorer(this);
                        break;

                    default:
                        throw new IllegalStateException(
                                "Unknown algorithm");
                }

                explorer.setVerbosity(
                        SearchStateExplorer
                                .VERBOSITY
                                .values()[vlevel]);

                System.out.println(
                        "\n========================");

                System.out.println(
                        "Algorithm "
                                + explorer
                                + " started");

                /*
                 * Continue also after finding a goal,
                 * so the best complete folding is kept.
                 */
                List<Action> result =
                        explorer.run(initialState, true);

                System.out.println(
                        "Algorithm "
                                + explorer
                                + " terminated.");

                explorer.outputStats();

                printSolution(
                        initialState,
                        result);
            }

            return 0;

        } catch (IllegalArgumentException e) {

            System.err.println(e.getMessage());
            return 1;

        } catch (IllegalStateException e) {

            System.err.println(e.getMessage());
            return 2;
        }
    }

    private void printSolution(
            ProteinFoldingState initialState,
            List<Action> actions) {

        if (actions == null) {

            System.out.println("No solution found.");
            return;
        }

        ProteinFoldingState state =
                initialState;

        double totalCost = 0;

        System.out.println("\nSolution:");

        int i = 0;

        for (Action action : actions) {

            System.out.println(
                    "[" + i + "] "
                            + action
                            + " cost="
                            + action.getCost());

            totalCost += action.getCost();

            state =
                    (ProteinFoldingState)
                            state.resultingState(action);

            i++;
        }

        System.out.println(
                "\nFinal state:\n"
                        + state);

        System.out.println(
                "Total cost = "
                        + totalCost);
    }

    public char[] getProtein() {
        return protein.toCharArray();
    }

    public int getSize() {
        return size;
    }

    private static void printOutputHeader() {

        System.out.println(
                "\n============================================"
                        + "\nProtein Folding - SearchStateExplorer"
                        + "\n============================================");
    }

    public static void main(String[] args) {

        printOutputHeader();

        int exitCode =
                new CommandLine(
                        new ProteinFolding())
                        .execute(args);

        System.out.printf(
                "%nExecution completed. Exiting %s errors.%n",
                exitCode > 0
                        ? "with"
                        : "without");

        System.exit(exitCode);
    }
}