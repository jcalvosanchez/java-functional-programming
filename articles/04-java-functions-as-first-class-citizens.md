# Functions as First-class citizens in Java

![Static Badge](https://img.shields.io/badge/date-2025-orange)

![Static Badge](https://img.shields.io/badge/software--architecture-purple)
![Static Badge](https://img.shields.io/badge/functional--programming-purple)

![Cover Image](cover_img/04-java-functions-as-first-class-citizens.png)

[Functional Programming](01-introduction-to-functional-programming.md) emphasizes the use of **[immutable data](03-data-immutability-in-java.md)** and **pure functions** to achieve more predictable, maintainable, and thread-safe code.

While [Java is not inherently a functional language](02-functional-programming-in-java.md), Java 8 (2014) introduced some major features to support these concepts. Let's review in this article how Functions became First-class citizens.

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
