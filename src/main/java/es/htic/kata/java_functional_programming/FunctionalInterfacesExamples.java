package es.htic.kata.java_functional_programming;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FunctionalInterfacesExamples {

    public static void main(String[] args) {
        customFunctionalInterfaceExample();
        unaryOperatorExample();
    }

    /**
     * Example of a Custom Functional Interface
     */
    @FunctionalInterface
    interface Greeting {
        void sayHello(String name);
    }

    private static void customFunctionalInterfaceExample() {
        System.out.println("customFunctionalInterfaceExample");
        Greeting greet = name -> System.out.println("Hello, " + name);
        greet.sayHello("Alice");  // Outputs: Hello, Alice
    }

    private static void unaryOperatorExample() {
        UnaryOperator<Double> applyDiscount = price -> price * 0.9;

        List<Double> discountedPrices = Arrays.asList(100.0, 200.0, 300.0).stream()
                .map(applyDiscount)
                .collect(Collectors.toList());

        discountedPrices.forEach(System.out::println); // Outputs: 90.0 180.0 270.0
    }
}
