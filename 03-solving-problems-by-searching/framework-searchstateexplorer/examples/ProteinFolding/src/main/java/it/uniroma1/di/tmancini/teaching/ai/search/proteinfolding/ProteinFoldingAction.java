package it.uniroma1.di.tmancini.teaching.ai.search.proteinfolding;

import it.uniroma1.di.tmancini.teaching.ai.search.Action;

import java.util.Objects;

public class ProteinFoldingAction extends Action {

    private ProteinFoldingState state;
    private int x;
    private int y;
    private char c;

    public ProteinFoldingAction(ProteinFoldingState state, int x, int y, char c) {
        this.state = state;
        this.x = x;
        this.y = y;
        this.c = c;
    }

    @Override
    public double getCost() {

        // P cannot create H-H contacts
        if (c == 'P') {
            return 3;
        }

        int newContacts = 0;

        int[][] dirs = {
                {0, 1},
                {0, -1},
                {1, 0},
                {-1, 0}
        };

        for (int[] d : dirs) {

            int nx = x + d[0];
            int ny = y + d[1];

            if (!state.inBounds(nx, ny)) {
                continue;
            }

            // Previous aminoacid is sequential, so it is not a contact
            if (nx == state.getX() && ny == state.getY()) {
                continue;
            }

            if (state.get(nx, ny) == 'H') {
                newContacts++;
            }
        }

        /*
         * More contacts -> lower cost.
         * Maximum possible new contacts is 3.
         */
        return 3 - newContacts;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getAminoacid() {
        return c;
    }

    @Override
    public boolean equals(Object oo) {

        if (this == oo) {
            return true;
        }

        if (oo == null || getClass() != oo.getClass()) {
            return false;
        }

        ProteinFoldingAction other = (ProteinFoldingAction) oo;

        return x == other.x
                && y == other.y
                && c == other.c
                && Objects.equals(state, other.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, x, y, c);
    }

    @Override
    public ProteinFoldingAction clone() {
        return new ProteinFoldingAction(state, x, y, c);
    }

    @Override
    public String toString() {
        return "place " + c + " at (" + x + ", " + y + ")";
    }
}