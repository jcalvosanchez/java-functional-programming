package es.htic.kata.java_functional_programming;

import java.util.function.Function;

public class FunctionsAsFirstClassCitizens {

    /**
     * Functions as variables
     * Functions as arguments
     * Functions as results
     * Functions can be composed
     */
    public static void main(String[] args) {
        Function<Integer, Integer> squareFunction = x -> x * x;
        Function<Integer, Integer> doubleFunction = x -> x * 2;

        Function<Integer, Integer> squareThenDouble = squareFunction.andThen(doubleFunction);
        System.out.println("squareFunction.andThen(doubleFunction) = " + squareThenDouble.apply(5)); // Output: (5 ^ 2) * 2 = 50

        Function<Integer, Integer> addThenMultiply = squareFunction.compose(doubleFunction);
        System.out.println("squareFunction.compose(doubleFunction) = " + addThenMultiply.apply(5)); // Output: (5 * 2) ^ 2 = 100
    }
}
