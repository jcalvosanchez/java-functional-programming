package es.htic.kata.java_functional_programming;

import java.util.List;
import java.util.function.Consumer;

public class LambdaExpressionExamples {

    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);

        System.out.println("result using buildAnonymousClass");
        numbers.forEach(buildAnonymousClass());
        System.out.println("result using buildAnonymousClassWithStatements");
        numbers.forEach(buildAnonymousClassWithStatements());
        System.out.println("result using buildLambdaExpression");
        numbers.forEach(buildLambdaExpression());
        System.out.println("result using buildLambdaExpressionWithMethodReference");
        numbers.forEach(buildLambdaExpressionWithMethodReference());
    }

    /**
     * How to declare an anonymous class in Java.
     */
    private static Consumer<Integer> buildAnonymousClass() {
        return new Consumer<Integer>() {
            @Override
            public void accept(Integer n) {
                System.out.println(n);
            }
        };
    }

    /**
     * How to declare an anonymous class in Java.
     */
    private static Consumer<Integer> buildAnonymousClassWithStatements() {
        return new Consumer<Integer>() {
            @Override
            public void accept(Integer n) {
                System.out.print("Integer is... ");
                System.out.println(n);
            }
        };
    }

    /**
     * How to declare an anonymous class in Java using Lambda expression
     */
    private static Consumer<Integer> buildLambdaExpression() {
        return n -> System.out.println(n);
    }

    /**
     * How to declare an anonymous class in Java using Lambda expression with method reference
     */
    private static Consumer<Integer> buildLambdaExpressionWithMethodReference() {
        return System.out::println;
    }
}
