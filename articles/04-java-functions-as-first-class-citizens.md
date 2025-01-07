# Functions as First-class citizens in Java

[Functional Programming](https://www.linkedin.com/pulse/introduction-functional-programming-jer%C3%B3nimo-calvo-s%C3%A1nchez-p2s8f/) emphasizes the use of **immutable data** and **pure functions** to achieve more predictable, maintainable, and thread-safe code.

While [Java is not inherently a functional language](https://www.linkedin.com/pulse/functional-programming-java-jer%C3%B3nimo-calvo-s%C3%A1nchez-ybvdf), Java 8 (2014) introduced some major features to support these concepts. We have already seen how [Data Immutability can be achieved in Java](https://www.linkedin.com/pulse/data-immutability-java-jer%25C3%25B3nimo-calvo-s%25C3%25A1nchez-krr2f/).

Let's review in this article how Functions became First-class citizens.

## Functions as Variables

From Java 8, Functions can be assigned to variables and used like any other variable.

```java
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        Function<Integer, Integer> squareFunction = x -> x * x;
        Function<Integer, Integer> doubleFunction = x -> x * 2;

        System.out.println(squareFunction.apply(5)); // Output: 25
        System.out.println(doubleFunction.apply(5)); // Output: 10
    }
}
```

## Passing Functions as Arguments

From Java 8, Functions can be passed as arguments, making [high-order programming](https://en.wikipedia.org/wiki/Higher-order_programming) possible which is a key concept in Functional Programming.

Here's an example using the `Function` interface to pass a function as an argument:

```java
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        Function<Integer, Integer> squareFunction = x -> x * x;

        System.out.println(applyFunction(5, squareFunction)); // Output: 25
    }
    
    public static int applyFunction(int value, Function<Integer, Integer> function) {
        return function.apply(value);
    }
}
```

## Returning Functions from Functions

Another key concept became possible fron Java 8, Functions can also return Functions.

Here's an example of a function that returns another function:

```java
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        Function<Integer, Integer> squareFunction = createFunction("square");
        System.out.println(squareFunction.apply(5)); // Output: 25

        Function<Integer, Integer> doubleFunction = createFunction("double");
        System.out.println(doubleFunction.apply(5)); // Output: 10
    }
    
    public static Function<Integer, Integer> createFunction(String type) {
        if ("square".equals(type)) {
            return x -> x * x;
        } else if ("double".equals(type)) {
            return x -> x * 2;
        }
        return x -> x;
    }
}
```

## Composing Functions

Function composition allows combining smaller functions into larger operations, making the code more modular, reusable and easier to test.

- `Function.andThen`: Applies the first function, then the second.
- `Function.compose`: Applies the second function, then the first.

```java
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        Function<Integer, Integer> squareFunction = x -> x * x;
        Function<Integer, Integer> doubleFunction = x -> x * 2;

        Function<Integer, Integer> squareThenDouble = squareFunction.andThen(doubleFunction);
        System.out.println(squareThenDouble.apply(5)); // Output: (5 ^ 2) * 2 = 50

        Function<Integer, Integer> addThenMultiply = squareFunction.compose(doubleFunction);
        System.out.println(addThenMultiply.apply(5)); // Output: (5 * 2) ^ 2 = 100
    }
}
```

## Code examples

- [FunctionsAsFirsClassCitizens](../src/main/java/es/htic/kata/java_functional_programming/FunctionsAsFirstClassCitizens.java)
