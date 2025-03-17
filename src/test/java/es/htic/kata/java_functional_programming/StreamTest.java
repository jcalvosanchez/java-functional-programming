package es.htic.kata.java_functional_programming;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StreamTest {

    @Test
    public void testStreamEmptyCreation() {
        Stream<String> emptyStream = Stream.empty();

        assertEqualsCollectionAndStream (new ArrayList<>(), emptyStream);
    }

    @Test
    public void testStreamCreation() {
        List<String> names = List.of("Alice", "Bob", "Charlie");
        String[] fruits = {"Apple", "Banana", "Cherry"};

        assertEqualsCollectionAndStream (names, names.stream());
        assertEqualsCollectionAndStream (names, names.parallelStream());

        assertEqualsArrayAndStream(fruits, Stream.of(fruits[0], fruits[1], fruits[2]));

        assertEqualsArrayAndStream(fruits, Arrays.stream(fruits));
        assertEqualsArrayAndStream(fruits, Stream.of(fruits));

        String sentence = "Java Streams Are Powerful";
        Stream<String> words = Pattern.compile(" ").splitAsStream(sentence);
        assertEquals(List.of("Java", "Streams", "Are", "Powerful"), words.collect(Collectors.toList()));
    }

    @Test
    public void testStreamIterate() {
        // Be careful when generatating infinite streams to avoid memory issues
        Stream<Integer> evenNumbers = Stream.iterate(0, n -> n + 2);

        //⚠ Use .limit(n) to avoid excessive memory usage
        assertEquals(List.of(0, 2, 4, 6, 8), Stream.iterate(0, n -> n + 2)
                .limit(5)
                .collect(Collectors.toList()));

        //⚠ Use .takeWhile(n) to avoid infinite loops
        assertEquals(List.of(0, 2, 4, 6, 8), Stream.iterate(0, n -> n < 10, n -> n + 2)
                .collect(Collectors.toList()));
        assertEquals(List.of(0, 2, 4, 6, 8), Stream.iterate(0, n -> n + 2)
                .takeWhile(n -> n < 10)
                .collect(Collectors.toList()));
    }

    @Test
    public void testStreamGenerate() {
        // Be careful when generatating infinite streams to avoid memory issues
        //Stream<Double> randomNumbers = Stream.generate(Math::random);

        //⚠ Use .limit(n) to avoid excessive memory usage
        List<Double> randomNumbers = Stream.generate(Math::random)
                .limit(5)
                .collect(Collectors.toList());

        assertEquals(5, randomNumbers.size());
        assertTrue(randomNumbers.stream().allMatch(n -> n >= 0.0 && n < 1.0));
    }

    @Test
    public void testFibonacciSequence() {
        assertEquals(List.of(0, 1, 1, 2, 3, 5, 8, 13, 21, 34), Stream.iterate(new int[]{0, 1}, f -> new int[]{f[1], f[0] + f[1]})
                .map(f -> f[0])
                .limit(10)
                .collect(Collectors.toList()));
    }

    @Test
    public void testStreamLazyEvaluationOnCreation() {
        // Infinite ordered stream is created but not evaluated (until a terminal operation is called)
        Stream<Integer> evenNumbers = Stream.iterate(0, n -> n + 2);
    }

    @Test
    public void testStreamLazyEvaluationOnIntermediateOperations() {
        // Counter to track evaluations
        AtomicInteger counter = new AtomicInteger();

        // Infinite stream created but NOT evaluated yet
        Stream<Integer> evenNumbers = Stream.iterate(0, n -> n + 2);

        // Intermediate operation does not trigger evaluation
        evenNumbers = evenNumbers
                .map(n -> n * 2 )
                .filter( n -> n % 3 == 0)
                .limit(5)
                .distinct()
                .peek(n -> counter.incrementAndGet());

        // No elements have been processed during intermediate operations
        assertEquals(0, counter.get());

        // Terminal operation triggers evaluation
        List<Integer> firstFive = evenNumbers.toList();

        // Now exactly 5 elements have been processed
        assertEquals(5, counter.get());

        // Validate expected results
        assertEquals(List.of(0, 12, 24, 36, 48), firstFive);
    }

    @Test
    public void testStreamTransform() {
        List<String> names = List.of("alice", "bob", "charlie");

        List<String> upperCaseNames = names.stream()
                .map(String::toUpperCase)
                .toList();

        assertEquals(List.of("ALICE", "BOB", "CHARLIE"), upperCaseNames);
    }

    @Test
    public void testStreamFlattenNestedStructures() {
        List<List<Integer>> numbers = List.of(
                List.of(1, 2, 3),
                List.of(4, 5),
                List.of(6, 7, 8, 9)
        );

        List<Integer> flattened = numbers.stream()
                .flatMap(List::stream)
                .toList();

        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9), flattened);
    }

    @Test
    public void testStreamFilter() {
        // Given: A list of numbers
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

        // When: Filtering even numbers using Stream API
        List<Integer> evenNumbers = numbers.stream()
                .filter(n -> n % 2 == 0) // Keep only even numbers
                .toList(); // Convert to List

        // Then: The result should contain only even numbers
        assertEquals(List.of(2, 4, 6, 8), evenNumbers);
    }

    @Test
    public void testStreamDistinct() {
        // Given: A list of words with duplicates
        List<String> words = List.of("apple", "banana", "apple", "orange", "banana");

        // When: Removing duplicates using Stream distinct()
        List<String> uniqueWords = words.stream()
                .distinct() // Keep only unique values
                .toList();  // Convert to List

        // Then: The result should contain only unique words
        assertEquals(List.of("apple", "banana", "orange"), uniqueWords);
    }

    @Test
    public void testStreamLimit() {
        // Given: A list of numbers
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

        // When: Taking the first three elements
        List<Integer> firstThree = numbers.stream()
                .limit(3) // Keep only the first 3 elements
                .toList();

        // Then: The result should be [1, 2, 3]
        assertEquals(List.of(1, 2, 3), firstThree);
    }

    @Test
    public void testStreamSkip() {
        // Given: A list of numbers
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

        // When: Skipping the first three elements
        List<Integer> skipThree = numbers.stream()
                .skip(3) // Skip the first 3 elements
                .toList();

        // Then: The result should be [4, 5, 6, 7, 8, 9]
        assertEquals(List.of(4, 5, 6, 7, 8, 9), skipThree);
    }

    @Test
    public void testStreamSumWithReduce() {
        // Given: A list of numbers
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);

        // When: Using reduce() to sum the numbers
        int sum = numbers.stream()
                .reduce(0, Integer::sum); // Start from 0 and sum all elements

        // Then: The result should be 15
        assertEquals(15, sum);
    }

    private void assertEqualsCollectionAndStream(Collection<String> collection, Stream<String> stream) {
        final Integer[] streamCount = {0};
        stream.forEach(name -> {
            streamCount[0]++;
            assertTrue(collection.contains(name));
        });
        assertEquals(collection.size(), streamCount[0]);
    }
    private void assertEqualsArrayAndStream(String[] array, Stream<String> stream) {
        final Integer[] streamCount = {0};
        stream.forEach(name -> {
            boolean matchFound = false;
            streamCount[0]++;
            for (String nameArray : array) {
                if (nameArray.equals(name)) {
                    matchFound = true;
                    break;
                }
            }
            assertTrue(matchFound, "El nombre " + name + " no se encontró en el array");
        });
        assertEquals(array.length, streamCount[0]);
    }
}
