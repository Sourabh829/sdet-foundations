import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/*
 * GENERICS:
 * TestDataPool<T> means this class can store any one type chosen by the caller.
 *
 * Examples:
 * TestDataPool<String> names = new TestDataPool<>();
 * TestDataPool<Integer> ids = new TestDataPool<>();
 *
 * Once T is chosen, Java checks the type at compile time. A TestDataPool<String>
 * can accept "admin", but it will not accept 101.
 */
public class TestDataPool<T> {
    private final List<T> data = new ArrayList<>();

    /*
     * T is the exact item type for this pool.
     *
     * Example:
     * If this object is TestDataPool<String>, then add(T item) becomes add(String item).
     * If this object is TestDataPool<Integer>, then add(T item) becomes add(Integer item).
     */
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Test data item cannot be null");
        }
        data.add(item);
    }

    /*
     * BOUNDED WILDCARD: ? extends T
     *
     * This means "T or any child/subtype of T".
     * Use extends when the input collection PRODUCES values that this class reads.
     *
     * Example:
     * TestDataPool<Number> numbers = new TestDataPool<>();
     * List<Integer> ids = Arrays.asList(101, 102);
     * numbers.addAll(ids); // Works because Integer extends Number.
     *
     * PECS rule: Producer Extends.
     * The collection produces items for us to read and add into the pool.
     */
    public void addAll(Collection<? extends T> items) {
        if (items == null) {
            throw new IllegalArgumentException("Items collection cannot be null");
        }

        for (T item : items) {
            add(item);
        }
    }

    public T get(int index) {
        return data.get(index);
    }

    public boolean remove(T item) {
        return data.remove(item);
    }

    public boolean contains(T item) {
        return data.contains(item);
    }

    public int size() {
        return data.size();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public void clear() {
        data.clear();
    }

    public List<T> getAll() {
        return Collections.unmodifiableList(data);
    }

    /*
     * BOUNDED WILDCARD: ? super T
     *
     * This means "T or any parent/supertype of T".
     * Use super when another object can CONSUME values of type T.
     *
     * Example:
     * TestDataPool<String> names = new TestDataPool<>();
     * Predicate<Object> notNull = value -> value != null;
     * names.findFirst(notNull); // Works because Object is a supertype of String.
     *
     * PECS rule: Consumer Super.
     * The predicate consumes each T item so it can test it.
     */
    public Optional<T> findFirst(Predicate<? super T> condition) {
        if (condition == null) {
            throw new IllegalArgumentException("Condition cannot be null");
        }

        for (T item : data) {
            if (condition.test(item)) {
                return Optional.of(item);
            }
        }

        return Optional.empty();
    }

    /*
     * Another ? super T example:
     * filter passes each pool item into the predicate, so the predicate is a consumer.
     * A Predicate<Object> can safely consume String, Integer, or any other object.
     */
    public List<T> filter(Predicate<? super T> condition) {
        if (condition == null) {
            throw new IllegalArgumentException("Condition cannot be null");
        }

        List<T> matches = new ArrayList<>();
        for (T item : data) {
            if (condition.test(item)) {
                matches.add(item);
            }
        }

        return matches;
    }
}
