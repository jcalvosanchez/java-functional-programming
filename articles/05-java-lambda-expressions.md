# Lambda Expressions in Java

In [Functional Programming](https://www.linkedin.com/pulse/introduction-functional-programming-jer%2525C3%2525B3nimo-calvo-s%2525C3%2525A1nchez-p2s8f), one of the consequences of Functions becoming [First Class Citizens](https://www.linkedin.com/pulse/functions-first-class-citizens-java-jer%2525C3%2525B3nimo-calvo-s%2525C3%2525A1nchez-zsskf), is that they are used more frequently and their life cycle is shorter.

This is why when using [Functional Programming in Java](https://www.linkedin.com/pulse/functional-programming-java-jer%2525C3%2525B3nimo-calvo-s%2525C3%2525A1nchez-ybvdf), **anonymous classes** are much more frequently used as a mechanism to declare and pass around short-live **anonymous functions**.

However, because this approach is quite verbose, **lambda expressions** and **method references** were introduced in Java8 to simplify the syntax and enhance readability.

Let's see them in more detail.

## Anonymous Classes

**Anonymous classes** are local classes without a name, typically for a one time use as a class that has a specific implementation of a subclass or interface.

```java
import java.util.List;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);

        numbers.forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer n) {
                System.out.println(n);
            }
        });
    }
}
```

## Anonymous Functions

**Anonymous functions** are functions without a name, and just as **Anonymous Classes**, they are intended to serve a short-lived purpose.

Before Java8, there was no native way to define standalone functions, so developers often used anonymous classes as a workaround to encapsulate and pass functions.

## Lambda expressions

**Lambda expressions** are a concise representation of **Anonymous Functions**, introduced in Java 8. They replace anonymous classes in many scenarios, making the code shorter and easier to read.

The basic syntax is 

```
(parameter) -> expression
(parameter) -> { statements; }
(parameter1, parameter2) -> expression
(parameter1, parameter2) -> { statements; }
```

In the following example, the lambda function `n -> System.out.println(n)` is used to print each element of the list.

```java
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Arrays.asList(1, 2, 3, 4, 5)
                .forEach(n -> System.out.println(n));
    }
}
```

## Method references

**Method references** were introduced in Java 8 as a more concise way to express a lambda expression to improve code readability, just by referring a method without explicitly invoking it using the `::` operator.

```
ClassName::methodName
instance::methodName
```

Here we can find the same example as before, but using method reference.


```java
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Arrays.asList(1, 2, 3, 4, 5)
                .forEach(System.out::println);
    }
}
```

## Code examples

- [LambdaExpressionsExamples](../src/main/java/es/htic/kata/java_functional_programming/LambdaExpressionExamples.java)
