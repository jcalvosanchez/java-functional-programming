package es.htic.kata.java_functional_programming;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DataImmutabilityTest {

    @Test
    public void testFinalPrimitiveTypeIsImmutable() {
        final int primitiveNumber = 5;
        //Compiler does not allow to assign a new value
        //primitiveNumber++; //Cannot assign a value to final variable 'number'
        //primitiveNumber = 6; //Cannot assign a value to final variable 'number'
    }

    @Test
    public void testFinalIntegerIsImmutable() {
        final Integer number = new Integer(5);
        //Compiler does not allow to assign a new value
        //number++; //Cannot assign a value to final variable 'number'
        //number = new Integer(6); //Cannot assign a value to final variable 'number'
    }

    @Test
    public void testFinalCollectionIsMutableBeforeJava8() {
        //given a final collection
        final Collection<Integer> list = new ArrayList<>();
        assertEquals(0, list.size());
        //when a new element is added
        list.add(new Integer(1));
        //then collection size is increased
        assertEquals(1, list.size());
    }

    @Test
    public void testFinalCollectionIsMutableSinceJava8() {
        //given a final collection
        final Collection<Integer> integersFrom1To100 = IntStream.rangeClosed(1, 100) // Generate numbers from 1 to 100
                .boxed()                                            // Convert int to Integer
                .collect(Collectors.toList());                      // Collect to a List
        assertEquals(100, integersFrom1To100.size());

        //when a new element is added
        integersFrom1To100.add(101);

        //then collection size is increased
        assertEquals(101, integersFrom1To100.size());
    }

    /**
     * From Java 9 onwards
     */
    @Test
    public void testCollectionOfIsImmutableFromJava9() {
        assertThrows(UnsupportedOperationException.class, () -> List.of(1,2,3).add(4));
        assertThrows(UnsupportedOperationException.class, () -> Set.of(1,2,3).add(4));
    }

    /**
     * From Java 10 onwards
     */
    @Test
    public void testCollectionOfIsImmutableFromJava10() {
        //given a final collection
        Collection<Integer> integersFrom1To100 = IntStream.rangeClosed(1, 100)  // Generate numbers from 1 to 100
                .boxed()                                                        // Convert int to Integer
                .collect(Collectors.toUnmodifiableList());                      // Collect to a Immutable List

        //when a new element is added
        assertThrows(UnsupportedOperationException.class, () -> integersFrom1To100.add(101));
    }

    /**
     * From Java 10 onwards
     */
    @Test
    public void testImmutableView() {
        // Original mutable list
        List<Integer> originalList = new ArrayList<>(List.of(1, 2, 3));

        // Create an immutable view
        List<Integer> immutableView = Collections.unmodifiableList(originalList);

        // Modifications via the view are not allowed
        assertThrows(UnsupportedOperationException.class, () -> immutableView.add(4));

        // However, changes to the original list are reflected in the view
        originalList.add(4);
        assertEquals(List.of(1, 2, 3, 4), immutableView);
    }

}
