package es.htic.kata.java_functional_programming;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class OptionalTest {

    public record Address(String street, String city) {}

    @Test
    public void testNullsInJava() {
        Function<Address, String> composeAddress = (address) -> address.street().concat(address.city());
        Address address = null;
        assertThrows(NullPointerException.class, () -> address.street());
        assertThrows(NullPointerException.class, () -> composeAddress.apply(null));
        assertThrows(NullPointerException.class, () -> composeAddress.apply(new Address(null, null)));
        assertThrows(NullPointerException.class, () -> composeAddress.apply(new Address("street", null)));
        assertThrows(NullPointerException.class, () -> composeAddress.apply(new Address(null, "city")));
        assertDoesNotThrow(() -> composeAddress.apply(new Address("street", "city")));
        assertEquals("streetcity", composeAddress.apply(new Address("street", "city")));
    }

    @Test
    public void testCreateOptional() {
        assertTrue(Optional.of("John Doe").isPresent());
        assertEquals("John Doe", Optional.of("John Doe").get());

        assertTrue(Optional.ofNullable(null).isEmpty());
        assertThrows(NoSuchElementException.class, () -> Optional.empty().get());

        assertTrue(Optional.empty().isEmpty());
        assertThrows(NoSuchElementException.class, () -> Optional.empty().get());
    }

    @Test
    public void testProvideDefaultValues() {
        Optional<String> emptyOptional = Optional.empty();
        Optional<String> nonEmptyOptional = Optional.of("Value Exists");

        assertEquals("Value Exists", nonEmptyOptional.orElse("Default Name"));
        assertEquals("Default Name", emptyOptional.orElse("Default Name"));
    }

    @Test
    public void testAlternativeSupplier() {
        // Use or when you have an alternative source for an Optional value.
        Function<Integer, Optional<String>> findInDefaultDataSource = (id) ->
                id == 1 ? Optional.of("Default Value") : Optional.empty();
        Supplier<Optional<String>> findInAlternativeDataSource = () -> Optional.of("Alternative Value");
        assertEquals("Default Value", findInDefaultDataSource.apply(1).or(findInAlternativeDataSource).get());
        assertEquals("Alternative Value", findInDefaultDataSource.apply(2).or(findInAlternativeDataSource).get());
    }

    @Test
    public void testWhenAbsenceShouldTriggerError() {
        Optional<String> emptyOptional = Optional.empty();
        Optional<String> nonEmptyOptional = Optional.of("Value Exists");

        // orElseThrow: Use when the absence of a value should be treated as an error.
        assertEquals("Value Exists", nonEmptyOptional.orElseThrow());
        assertThrows(NoSuchElementException.class, emptyOptional::orElseThrow);
        assertThrows(IllegalArgumentException.class,
                () -> emptyOptional.orElseThrow(() -> new IllegalArgumentException("Custom exception")));
    }

    @Test
    public void testLazyEvaluation() {
        Optional<String> emptyOptional = Optional.empty();
        Optional<String> nonEmptyOptional = Optional.of("Value Exists");

        // orElseGet: Use when computing a fallback value involves a costly operation or side effects.
        assertEquals("Value Exists", nonEmptyOptional.orElseGet(() -> "Fallback Value"));
        // Demonstrating lazy evaluation
        AtomicBoolean supplierCalled = new AtomicBoolean(false);
        String result = emptyOptional.orElseGet(() -> {
            supplierCalled.set(true);
            return "Computed Value";
        });
        assertTrue(supplierCalled.get());
        assertEquals("Computed Value", result);
    }

    @Test
    public void testAvoidNullPointerException() {
        Function<Integer, Optional<String>> findUserById = (id) -> {
            if (id == 1) {
                return Optional.of("John Doe");
            }
            return Optional.empty();
        };

        assertTrue(findUserById.apply(1).isPresent());
        assertTrue(findUserById.apply(2).isEmpty());

        assertEquals("John Doe", findUserById.apply(1).orElse("User not found"));
        assertEquals("User not found", findUserById.apply(2).orElse("User not found"));

        findUserById.apply(1).ifPresent(
                name -> System.out.println("User found: " + name)
        );
        findUserById.apply(1).ifPresentOrElse(
                name -> System.out.println("User found: " + name),
                () -> System.out.println("User not found")
        );
    }

    static Stream<Arguments> testCombineMultipleOptionalsArugments() {
        return Stream.of(
                Arguments.of("John", "Doe", "John Doe"),
                Arguments.of("John", null, "Unknown Name"),
                Arguments.of(null, "Doe", "Unknown Name"),
                Arguments.of(null, null, "Unknown Name")
        );
    }
    @ParameterizedTest
    @MethodSource("testCombineMultipleOptionalsArugments")
    public void testCombineMultipleOptionals(String firstName, String lastName, String expectedResult) {
        String fullName = Optional.ofNullable(firstName)
                .flatMap(fn -> Optional.ofNullable(lastName).map(ln -> fn + " " + ln))
                .orElse("Unknown Name");

        assertEquals(expectedResult, fullName);
    }

    @Test
    public void testTransformDataTypes() {
        Optional<String> emptyOptional = Optional.empty();
        Optional<String> nonEmptyOptional = Optional.of("Value Exists");

        assertEquals(0, emptyOptional.map(String::length).orElse(0));
        assertEquals(12, nonEmptyOptional.map(String::length).orElse(0));
    }

    @Test
    public void testTransformValues() {
        Optional<String> city = Optional.of("New York");

        assertEquals("NEW YORK", city
                .map(c -> c.toUpperCase())
                .orElse("Unknown"));

        assertEquals("NEW YORK", city
                .flatMap(c -> Optional.of(c.toUpperCase()))
                .orElse("Unknown"));
    }

    @Test
    public void testFilterConditions() {
        Predicate<String> isNewCity = name -> name.startsWith("New");

        assertEquals("New York", Optional.of("New York")
                .filter(isNewCity)
                .orElse("new city not found"));
        assertEquals("new city not found", Optional.of("London")
                .filter(isNewCity)
                .orElse("new city not found"));
    }


    @Test
    public void testExercise() {
        String defaultCity = "Salamanca";

        Function<String, Optional<String>> findCountryNameByCountryCode =
                (countryCode) -> Optional.ofNullable(
                        "es".equalsIgnoreCase(countryCode)
                            ? "Spain"
                            : null);

        Function<String, Optional<String>> findCapitalNameByCountryName =
                (countryName) -> Optional.ofNullable(
                        "Spain".equalsIgnoreCase(countryName)
                                ? "Madrid"
                                : null);

        Function<String, String> findCapitalNameByCountryCode = (countryCode) ->
                findCountryNameByCountryCode
                        .apply(countryCode)
                        .flatMap(findCapitalNameByCountryName)
                        .orElse(defaultCity);

        assertEquals("Salamanca", findCapitalNameByCountryCode.apply(null));
        assertEquals("Salamanca", findCapitalNameByCountryCode.apply(""));
        assertEquals("Salamanca", findCapitalNameByCountryCode.apply("pt"));
        assertEquals("Madrid", findCapitalNameByCountryCode.apply("es"));
        assertEquals("Madrid", findCapitalNameByCountryCode.apply("ES"));
    }
}
