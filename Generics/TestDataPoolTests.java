import java.util.Arrays;
import java.util.List;

public class TestDataPoolTests {
    public static void main(String[] args) {
        testStoresAndRetrievesStringData();
        testWorksWithIntegerData();
        testRemoveUpdatesPool();
        testFilterReturnsMatchingItems();
        testGetAllReturnsReadOnlyList();

        System.out.println("All 5 TestDataPool test cases passed.");
    }

    private static void testStoresAndRetrievesStringData() {
        // Generics example: T is String for this object, so only String values are accepted.
        TestDataPool<String> users = new TestDataPool<>();

        users.add("admin");
        users.add("tester");

        assertEquals(2, users.size(), "String pool size should be 2");
        assertEquals("admin", users.get(0), "First user should be admin");
        assertTrue(users.contains("tester"), "Pool should contain tester");
    }

    private static void testWorksWithIntegerData() {
        /*
         * ? extends example through addAll():
         * Here T is Number, and the source list contains Integer values.
         * This works because Integer is a subtype of Number.
         */
        TestDataPool<Number> ids = new TestDataPool<>();

        ids.addAll(Arrays.asList(101, 102, 103));

        assertEquals(3, ids.size(), "Integer pool size should be 3");
        assertEquals(Integer.valueOf(102), ids.get(1), "Second id should be 102");
    }

    private static void testRemoveUpdatesPool() {
        TestDataPool<String> browsers = new TestDataPool<>();
        browsers.addAll(Arrays.asList("Chrome", "Firefox", "Edge"));

        boolean removed = browsers.remove("Firefox");

        assertTrue(removed, "Firefox should be removed");
        assertEquals(2, browsers.size(), "Pool size should reduce after remove");
        assertFalse(browsers.contains("Firefox"), "Pool should not contain removed item");
    }

    private static void testFilterReturnsMatchingItems() {
        TestDataPool<String> testNames = new TestDataPool<>();
        testNames.addAll(Arrays.asList("login_valid_user", "login_locked_user", "profile_update"));

        /*
         * ? super example through filter():
         * The predicate consumes each String test name.
         * A Predicate<String> is common, but Predicate<Object> would also be allowed
         * because Object is a supertype of String.
         */
        List<String> loginTests = testNames.filter(name -> name.startsWith("login"));

        assertEquals(2, loginTests.size(), "Two login tests should match");
        assertTrue(loginTests.contains("login_valid_user"), "Filtered list should include valid login test");
        assertTrue(loginTests.contains("login_locked_user"), "Filtered list should include locked login test");
    }

    private static void testGetAllReturnsReadOnlyList() {
        TestDataPool<String> roles = new TestDataPool<>();
        roles.addAll(Arrays.asList("viewer", "editor"));

        /*
         * PECS reminder:
         * - Producer Extends: addAll(Collection<? extends T>) reads from a source.
         * - Consumer Super: filter/findFirst(Predicate<? super T>) sends T into a consumer.
         */
        try {
            roles.getAll().add("admin");
            throw new AssertionError("getAll should return a read-only list");
        } catch (UnsupportedOperationException expected) {
            assertEquals(2, roles.size(), "Original pool should stay unchanged");
        }
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (!expected.equals(actual)) {
            throw new AssertionError(message + ". Expected: " + expected + ", Actual: " + actual);
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void assertFalse(boolean condition, String message) {
        assertTrue(!condition, message);
    }
}
