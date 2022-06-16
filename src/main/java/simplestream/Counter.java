package simplestream;

import java.sql.SQLOutput;
import java.util.List;
import java.util.stream.IntStream;

public class Counter {
  public static void main(String[] args) {
//    IntStream.range(0, 1000)
//        .peek(x -> System.out.println("> " + x))
//        .map(x -> 2 * x)
//        .peek(x -> System.out.println(">> " + x))
//        .filter(x -> x % 3 == 0)
//        .forEach(System.out::println);

//    long res = IntStream.range(0, 1000)
//        .peek(System.out::println)
//        .filter(x -> x % 3 == 0)
//        .count();
//    System.out.println("count is " + res);

//    int[] count = { 0 };
//    long res = IntStream.range(0, 1_000_000_000)
//        .parallel()
//        .peek(x -> count[0]++)  // NONONONO!! Horrible side-effect
//                                // also thread-safety issue
//        .filter(x -> false)
//        .count();
//    System.out.println("count is " + count[0]);
//    System.out.println("res is " + res);

    int count = IntStream.range(0, 1_000_000_000)
        .parallel()
        .map(x -> 1)
        .reduce(0, (a, b) -> a + b);
    System.out.println("count is " + count);
  }
}
