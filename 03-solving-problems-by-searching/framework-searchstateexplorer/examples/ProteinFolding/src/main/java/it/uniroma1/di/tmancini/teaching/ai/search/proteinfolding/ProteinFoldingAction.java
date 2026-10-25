package it.uniroma1.di.tmancini.teaching.ai.search.proteinfolding;

import it.uniroma1.di.tmancini.teaching.ai.search.Action;

import java.util.Objects;

public class ProteinFoldingAction extends Action {

    private ProteinFoldingState state;
    private int x;
    private int y;
    private char c;

    public ProteinFoldingAction(
            ProteinFoldingState state,
            int x,
            int y,
            char c) {

        this.state = state;
        this.x = x;
        this.y = y;
        this.c = c;
    }

    @Override
    public double getCost() {

        /*
         * A P aminoacid cannot create H-H contacts.
         */
        if (c != 'H') {
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

            /*
             * Skip positions outside the valid protein grid.
             */
            if (!state.inBounds(nx, ny)) {
                continue;
            }

            /*
             * The previous aminoacid is sequentially connected
             * to the new one, therefore it must not count as
             * a contact.
             */
            if (nx == state.getX() && ny == state.getY()) {
                continue;
            }

            if (state.get(nx, ny) == 'H') {
                newContacts++;
            }
        }

        return 3 - newContacts;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public char getAminoacid() {
        return this.c;
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

        return this.x == other.x
                && this.y == other.y
                && this.c == other.c
                && Objects.equals(this.state, other.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, x, y, c);
    }

    @Override
    public ProteinFoldingAction clone() {
        try {
            return (ProteinFoldingAction) super.clone();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "place " + c + " at (" + x + ", " + y + ")";
    }
}
