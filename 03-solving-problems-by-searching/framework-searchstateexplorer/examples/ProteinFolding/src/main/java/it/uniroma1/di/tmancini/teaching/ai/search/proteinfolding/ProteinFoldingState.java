package it.uniroma1.di.tmancini.teaching.ai.search.proteinfolding;

import it.uniroma1.di.tmancini.teaching.ai.search.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class ProteinFoldingState extends State {

    private char[][] grid;
    private int prefixIdx;
    private int x;
    private int y;
    private int psize;

    public ProteinFoldingState(ProteinFolding p) {
        super(p);

        this.prefixIdx = -1;
        this.psize = p.getProtein().length;
        this.grid = new char[(psize * 2) - 1][(psize * 2) - 1];

        this.x = -1;
        this.y = -1;
    }

    @Override
    public Collection<? extends Action> executableActions() {

        List<ProteinFoldingAction> result = new ArrayList<>();

        if (isGoal()) {
            return result;
        }

        char nextC =
                ((ProteinFolding) getProblem())
                        .getProtein()[prefixIdx + 1];

        int[][] dirs = {
                {0, 1},
                {0, -1},
                {1, 0},
                {-1, 0}
        };

        for (int[] d : dirs) {

            int nx = x + d[0];
            int ny = y + d[1];

            // Only empty cells inside the grid can be used
            if (inBounds(nx, ny) && get(nx, ny) == 'e') {

                result.add(
                        new ProteinFoldingAction(
                                this,
                                nx,
                                ny,
                                nextC));
            }
        }

        return result;
    }

    @Override
    public State resultingState(Action a) {

        ProteinFoldingAction action =
                (ProteinFoldingAction) a;

        ProteinFoldingState result =
                (ProteinFoldingState) this.clone();

        // Copy grid
        result.grid =
                new char[grid.length][grid.length];

        for (int i = 0; i < grid.length; i++) {

            System.arraycopy(
                    this.grid[i],
                    0,
                    result.grid[i],
                    0,
                    grid[i].length);
        }

        int nx = action.getX();
        int ny = action.getY();

        if (!inBounds(nx, ny)) {
            throw new IllegalArgumentException(
                    "Position outside grid");
        }

        if (result.get(nx, ny) != 'e') {
            throw new IllegalArgumentException(
                    "Position already occupied");
        }

        result.set(
                nx,
                ny,
                action.getAminoacid());

        result.x = nx;
        result.y = ny;
        result.prefixIdx++;

        return result;
    }

    @Override
    public boolean isGoal() {
        return prefixIdx == psize - 1;
    }

    public static ProteinFoldingState newInitialState(
            ProteinFolding p) {

        ProteinFoldingState state =
                new ProteinFoldingState(p);

        for (int x = -(state.psize - 1);
             x <= state.psize - 1;
             x++) {

            for (int y = -(state.psize - 1);
                 y <= state.psize - 1;
                 y++) {

                state.set(x, y, 'e');
            }
        }

        // First aminoacid starts in (0,0)
        state.set(
                0,
                0,
                p.getProtein()[0]);

        state.x = 0;
        state.y = 0;
        state.prefixIdx = 0;

        return state;
    }

    public boolean inBounds(int x, int y) {

        int max = psize - 1;

        return x >= -max
                && x <= max
                && y >= -max
                && y <= max;
    }

    public char get(int x, int y) {

        return grid[
                psize - 1 + x][
                psize - 1 + y];
    }

    public void set(int x, int y, char c) {

        grid[
                psize - 1 + x][
                psize - 1 + y] = c;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getContacts() {

        int contacts = 0;

        /*
         * Check only right and up,
         * otherwise every contact would be counted twice.
         */
        int[][] dirs = {
                {1, 0},
                {0, 1}
        };

        int min = -(psize - 1);
        int max = psize - 1;

        for (int gx = min; gx <= max; gx++) {

            for (int gy = min; gy <= max; gy++) {

                if (get(gx, gy) != 'H') {
                    continue;
                }

                for (int[] d : dirs) {

                    int nx = gx + d[0];
                    int ny = gy + d[1];

                    if (inBounds(nx, ny)
                            && get(nx, ny) == 'H') {

                        contacts++;
                    }
                }
            }
        }

        /*
         * H-H that are consecutive in the sequence
         * are not contacts.
         */
        char[] protein =
                ((ProteinFolding) getProblem())
                        .getProtein();

        int sequentialHH = 0;

        for (int i = 1; i <= prefixIdx; i++) {

            if (protein[i - 1] == 'H'
                    && protein[i] == 'H') {

                sequentialHH++;
            }
        }

        return contacts - sequentialHH;
    }

    public int getEnergy() {
        return -getContacts();
    }

    /*
     * Simple admissible heuristic.
     * A remaining P cannot create contacts,
     * therefore its minimum step cost is 3.
     * For H we optimistically consider cost 0.
     */
    @Override
    public double hValue() {

        char[] protein =
                ((ProteinFolding) getProblem())
                        .getProtein();

        int remainingP = 0;

        for (int i = prefixIdx + 1;
             i < protein.length;
             i++) {

            if (protein[i] == 'P') {
                remainingP++;
            }
        }

        return 3.0 * remainingP;
    }

    @Override
    public boolean equals(Object oo) {

        if (this == oo) {
            return true;
        }

        if (oo == null || getClass() != oo.getClass()) {
            return false;
        }

        ProteinFoldingState other =
                (ProteinFoldingState) oo;

        return prefixIdx == other.prefixIdx
                && x == other.x
                && y == other.y
                && Arrays.deepEquals(grid, other.grid);
    }

    @Override
    public int hashCode() {

        int result =
                Objects.hash(prefixIdx, x, y);

        return 31 * result
                + Arrays.deepHashCode(grid);
    }

    @Override
    public Object clone() {
        return super.clone();
    }

    @Override
    public String toStringWithPrefix(String prefix) {

        return prefix
                + "placed aminoacids = "
                + (prefixIdx + 1)
                + "\n"
                + prefix
                + "head = ("
                + x
                + ", "
                + y
                + ")\n"
                + prefix
                + "contacts = "
                + getContacts()
                + "\n"
                + prefix
                + "energy = "
                + getEnergy();
    }

    @Override
    public String toString() {
        return toStringWithPrefix("");
    }
}