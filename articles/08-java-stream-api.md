# Stream API

## What is the Java Stream API?

The **Java Stream API**, introduced in **Java 8**, is a functional-style approach to process data collections efficiently.

This API allows developers to perform **complex data transformations** like filtering, mapping, reducing, and collecting without writing explicit loops, using a **pipeline of operations** to process sequences of elements in a **declarative, efficient and potentially parallelizable** manner without modifying the underlying data source.

The result is a cleaner, more readable, more efficient, and more maintainable code.

## Why Use the Stream API?

### More Readable and Concise Code

The **declarative** and **functional programming** style, allows more concise, easier to read, more modular and composable. This results in a less boilerplate, less error prone and more maintainable code.

### More Efficient Code

Streams operate on **immutable data** and promote a **functional programming** paradigm, reducing side effects and **avoiding unnecessary modifications** to the source data.

Streams also leverage **lazy evaluation**, meaning operations are only executed when needed, improving performance by avoiding unnecessary computations.

Finally, Stream API provides a **built-in Parallel Processing** mechanism, allowing operations to run concurrently and utilize multi-core processors more effectively.

### Less error prone code

The **functional programming** paradigm reduces side effects and makes the code more predictable and easier to test and debug.

Also, by eliminating **explicit loops** and **mutations**, the Stream API reduces the risk of **common bugs** like **NullPointerException**, **IndexOutOfBoundsException** and **ConcurrentModificationException**.

## How to Create Streams in Java

### Creating Empty Streams

```java
Stream<String> emptyStream = Stream.empty();
```

### Creating Streams from Collections

```java
List<String> names = List.of("Alice", "Bob", "Charlie");
Stream<String> nameStream = names.stream();
Stream<String> parallelNameStream = names.parallelStream();
```

### Creating Streams from Arrays

```java
String[] fruits = {"Apple", "Banana", "Cherry"};
Stream<String> fruitStream = Arrays.stream(fruits);
```

### Creating Streams from Arguments

```java
Stream<String> fruitStream = Stream.of("Alice", "Bob", "Charlie");
```

### Creating Streams from Pattern.splitAsStream()

```java
String sentence = "Java Streams Are Powerful";
Stream<String> words = Pattern.compile(" ").splitAsStream(sentence);
assertEquals(List.of("Java", "Streams", "Are", "Powerful"), words.collect(Collectors.toList()));
```

### Creating Streams from Files (I/O)

```java
try (Stream<String> lines = Files.lines(Path.of("data.txt"))) {
    lines.forEach(System.out::println);
} catch (IOException e) {
    e.printStackTrace();
}
```

### Creating ordered Streams with Stream API

```java
// Be careful when creating infinite streams to avoid memory issues
Stream<Integer> evenNumbers = Stream.iterate(0, n -> n + 2);

// Continuous Background Task Execution
Stream.iterate(0, n -> n + 1)
        .forEach(n -> {
            System.out.println("Executing task #" + n);
        );

// ⚠ Use .limit(n) to avoid excessive memory usage
Stream<Integer> firstTenFibonacciSequence = Stream.iterate(new int[]{0, 1}, f -> new int[]{f[1], f[0] + f[1]})
        .map(f -> f[0])
        .limit(10);
Stream<Integer> firstFiveEvens = Stream.iterate(0, n -> n + 2).limit(5);

// ⚠ Use .takeWhile to avoid infinite loops
Stream.iterate(0, page -> page + 1)
        .map(fetchPageData)
        .takeWhile(pageData -> !pageData.isEmpty()) // Stop when no data
        .forEach(System.out::println);
```

### Creating unordered Streams with Stream API

```java
// Be careful when creating infinite streams to avoid memory issues
Stream<Double> randomNumbers = Stream.generate(Math::random);

// Real-Time Data Processing
Stream.generate(fetchLatestStockPrice)
.forEach(System.out::println); // Process indefinitely
```

## Intermediate Operations (Transformation & Filtering)

Intermediate operations in the Java Stream API are operations that transform or filter elements **without consuming the stream**.

### 1️⃣ Transformation Operations

These operations modify elements in the stream:

#### map() – Transform Each Element

The `map()` method applies a function to each element, transforming them into another form.

✔ Use case: Convert objects, apply transformations (e.g., lowercase to uppercase).

```java
@Test
public void testStreamTransform() {
    List<String> names = List.of("alice", "bob", "charlie");

    List<String> upperCaseNames = names.stream()
            .map(String::toUpperCase)
            .toList();

    assertEquals(List.of("ALICE", "BOB", "CHARLIE"), upperCaseNames);
}
```

#### flatMap() – Flatten Nested Structures

`flatMap()` is used when each element produces multiple elements, flattening them into a single stream.

✔ Use case: Work with nested collections (e.g., lists inside a list).

```java
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
```

### 2️⃣ Filtering Operations

These operations remove elements based on conditions.

### filter() – Keep Only Matching Elements

The `filter()` method keeps elements that match a condition.

✔ Use case: Remove unwanted elements based on conditions.

```java
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
```

### distinct() – Remove Duplicates

The `distinct()` method removes duplicate elements, using equals().

✔ Use case: Remove duplicate elements.

```java
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
```

### limit() & skip() – Select a Portion of the Stream

`limit(n)`: Keeps only the first n elements.

`skip(n)`: Skips the first n elements.

✔ Use case: Pagination, selecting a subset of data.

```java
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
```

```java
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

```

## Terminal Operations (Collecting, Reducing & Consuming Results)

Terminal operations **consume** the stream and **produce a result**. Unlike intermediate operations, these **end** the pipeline and do not return another stream.

### 1️⃣ Collecting Results with `collect()`

The `collect()` method converts Stream to Collection (lists, sets, or maps).

```java
List<String> names = List.of("Alice", "Bob", "Charlie");

List<String> collectedList = names.stream()
    .collect(Collectors.toList());

Set<String> collectedSet = names.stream()
    .collect(Collectors.toSet());
```

### 2️⃣ Reducing Elements with `reduce()`

The `reduce()` method Computes aggregates by combining elements into a single value (sum, product, concatenation, etc.).

```java
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
```

### 3️⃣ Consuming Results

- `forEach()` performs an action on each element
- `count()`, `min()`, `max()` – Retrieve Statistics

```java
List<String> names = List.of("Alice", "Bob", "Charlie");

names.stream()
        .forEach(System.out::println);
long count = names.stream().count();
Optional<String> longestName = names.stream()
        .max(Comparator.comparing(String::length));
```

## Lazy evaluation

Streams **delay execution** until a **terminal operation** (like `collect()`, `forEach()`, `count()`) is called. This **lazy behavior** optimizes performance by processing only the necessary elements, avoiding unnecessary computations.

### Stream Creation is Lazy

Creating a stream does not process elements immediately:

```java
@Test
public void testStreamLazyEvaluationOnCreation() {
    // Infinite ordered stream is declared but NOT evaluated
    Stream<Integer> evenNumbers = Stream.iterate(0, n -> n + 2);

    // No computation happens at this point
}
```

### Intermediate Operations are Lazy

Intermediate operations are also **deferred** until a terminal operation is triggered:

```java
@Test
public void testStreamLazyEvaluationOnIntermediateOperations() {
    // Counter to track evaluations
    AtomicInteger counter = new AtomicInteger();

    // Infinite stream created but NOT evaluated yet
    Stream<Integer> evenNumbers = Stream.iterate(0, n -> n + 2)
            .map(n -> {
                counter.incrementAndGet(); // Increment counter when element is processed
                return n * 2; // Transformation operation
            })
            .limit(10)
            .distinct();

    // No elements have been processed yet
    assertEquals(0, counter.get());

    // Terminal operation triggers evaluation
    List<Integer> firstFive = evenNumbers.limit(5).toList();

    // Now exactly 5 elements have been processed
    assertEquals(5, counter.get());

    // Validate expected results
    assertEquals(List.of(0, 4, 8, 12, 16), firstFive);
}
```

## Parallel Streams

The Java Stream API supports **parallel processing** to speed up computations by leveraging multiple CPU cores. 

### How Parallel Streams Work

1. **Splitting the Data**: The stream divides elements into multiple chunks.
2. **Processing in Parallel**: Chunks are processed concurrently using the Fork/Join framework.
3. **Merging Results**: Results from different threads are combined.

### When to use it 

The benefits overcome the complexity added and the cost in time and resources when:

- ✅ Large datasets (thousands/millions of elements)
- ✅ CPU-intensive tasks
- ✅ Independent operations

### How to Create a parallel Stream

```java
List<Integer> numbers = List.of(1, 2, 3, 4, 5);

int sum = numbers.stream()
    .parallel()  // Convert to parallel stream
    .reduce(0, Integer::sum);

System.out.println(sum); // 15

// Create a parallel stream directly from a collection
Stream<Integer> parallelStream = numbers.parallelStream();
```















