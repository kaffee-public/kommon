package kaffee.patterns;

import java.io.Serializable;

/**
 * .
 * @author ahorvath
 * @param <L>
 * @param <R>
 */
public class Pair<L extends Serializable, R extends Serializable> {

    private L left;
    private R right;

    public void Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L left() {
        return left;
    }

    public R right() {
        return right;
    }
}
