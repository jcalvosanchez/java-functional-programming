package es.htic.kata.java_functional_programming;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class FunctionalInterfacesExamplesTest {

    @Test
    public void testPredicates() {
        Predicate<String> isLongString = s -> s.length() > 5;
        Predicate<String> startsWithF = s -> s.startsWith("f") || s.startsWith("F");

        assertFalse(isLongString.test("hello"));
        assertTrue(isLongString.test("functional"));
        assertTrue(startsWithF.test("functional"));
        // Predicates can be easily chained
        assertTrue((isLongString.and(startsWithF)).test("functional"));
    }

    @Test
    public void testUsePredicatesToFilterCollections() {
        // given a list of integers from 1 to 100
        List<Integer> numbers = IntStream.rangeClosed(1, 100)
                .boxed()
                .collect(Collectors.toUnmodifiableList());
        // given a list of integers divisible by 2 and by 3
        List<Integer> numbersDivisibleByTwoAndByThree = List.of(6, 12, 18, 24, 30, 36, 42, 48, 54, 60, 66, 72, 78, 84, 90, 96);

        // Define predicates for divisibility by 2 and 3
        Predicate<Integer> isDivisibleByTwo = n -> n % 2 == 0;
        Predicate<Integer> isDivisibleByThree = n -> n % 3 == 0;

        assertEquals(numbersDivisibleByTwoAndByThree, numbers.stream()
                .filter(isDivisibleByTwo)
                .filter(isDivisibleByThree)
                .collect(Collectors.toList()));

        // Combine predicates for divisibility by both
        Predicate<Integer> isDivisibleByTwoAndThree = isDivisibleByTwo.and(isDivisibleByThree);
        assertEquals(numbersDivisibleByTwoAndByThree, numbers.stream()
                .filter(isDivisibleByTwoAndThree)
                .collect(Collectors.toList()));
    }

    @Test
    public void testFunctions() {
        Function<Integer, String> intToString = i -> "Number: " + i;
        assertEquals("Number: 42", intToString.apply(42));
    }

    @Test
    public void testConsumers() {
        Consumer<String> printMessage = s -> System.out.println("Logging: " + s);
        printMessage.accept("Example log message"); // Output: "Logging: Example log message"
    }

    @Test
    public void testSuppliers() {
        Supplier<Double> randomValue = () -> Math.random();
        // Supplier<User> getRandomUser = User.random(); // Example of a class User that would have one method random to generate a random user to be used in testing.
        System.out.println(randomValue.get());
    }

    @Test
    public void testCompareNames() {
        BiFunction<String, String, Boolean> twoUsersHaveSameName = String::equalsIgnoreCase;
        // BiFunction<String, String, Boolean> twoUsersHaveSameName = (firstName, secondName) -> firstName.equalsIgnoreCase(secondName);

        assertFalse(twoUsersHaveSameName.apply("John", "Peter"));
        assertTrue(twoUsersHaveSameName.apply("John", "joHN"));
    }

    @Test
    public void testCalculateDistance() {
        // Example: Calculating the distance between two points (x1, y1) and (x2, y2)
        BiFunction<int[], int[], Double> calculateDistance = (point1, point2) -> {
            int xDiff = point2[0] - point1[0];
            int yDiff = point2[1] - point1[1];
            return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
        };

        assertEquals(5.0, calculateDistance.apply(new int[]{0, 0}, new int[]{3, 4}), 0.01);
    }

    /**
     * BinaryOperator<T></T> is a specialization of BiFunction<T, T, T>
     */
    @Test
    public void testComposeFullNames() {
        BinaryOperator<String> fullNameUsingBinaryOperator = (firstName, lastName) -> lastName + ", " + firstName;
        BiFunction<String, String, String> fullNameUsingBiFunction = (firstName, lastName) -> lastName + ", " + firstName;

        assertEquals("Doe, John", fullNameUsingBinaryOperator.apply("John", "Doe"));
        assertEquals("Doe, John", fullNameUsingBiFunction.apply("John", "Doe"));
    }
}
