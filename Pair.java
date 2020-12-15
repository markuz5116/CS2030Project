package cs2030.simulator;

/**
 * Pair where it stores a two generic items. The 'Pair' class supports
 * operators that includes: (i) Static method that returns a Pair. (ii)
 * Retrieve the first item. (iii) Retrieve the second item. (iv) Retrieve an
 * empty Pair.
 * Pair contains the two generic Objects.
 * @param <T> Generic first item.
 * @param <U> Generic second item.
 */
public class Pair<T, U> {
    private final T first;
    private final U second;

    /**
     * Constructs a Pair containing a generic Pair of two different generic
     * types Object.
     * @param first Generic item stored in the first of the Pair.
     * @param second Generic item stored in the second of the Pair.
     */
    private Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Retrieve an empty Pair.
     * @param <T> First generic type of the empty Pair.
     * @param <U> Second generic type of the empty Pair.
     * @return Empty Pair that is type casted to pair in the specified Pair.
     */
    public static <T, U> Pair<T, U> empty() {
        return new Pair<T, U>(null, null);
    }

    /**
     * Retrieve a Pair of the first and second parameters.
     * @param first First generic Object of the Pair being constructed.
     * @param second Second generic Object of the Pair being constructed.
     * @param <T> The type of the first Object.
     * @param <U> The type of the second Object.
     * @return Pair containing the first and second parameters.
     */
    public static <T, U> Pair<T, U> of(T first, U second) {
        return new Pair<T, U>(first, second);
    }

    /**
     * Retrieve the first Object.
     * @return first.
     */
    public T first() {
        return first;
    }

    /**
     * Retrieve the second Object.
     * @return second.
     */
    public U second() {
        return second;
    }
}
