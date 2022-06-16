package averager;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

class Average {
  private double sum = 0;
  private long count = 0;
  public Average() {}
  public Average(double sum, long count) {
    this.sum = sum;
    this.count = count;
  }

  public Average merge(Average other) {
    return new Average(this.sum + other.sum, this.count + other.count);
  }

  public Optional<Double> get() {
    if (count > 0) {
      return Optional.of(sum / count);
    } else {
      return Optional.empty();
    }
  }
}
public class Averager {
  public static void main(String[] args) {
    long start = System.nanoTime();
//    ThreadLocalRandom.current().doubles(3_000_000_000L, -1.0, 1.0)
    Stream.iterate(0.0, x -> ThreadLocalRandom.current().nextDouble(-1.0, 1.0))
        .parallel()
        .unordered()
        .limit(3_000_000_000L)
//        .mapToObj(v -> new Average(v, 1))
        .map(v -> new Average(v, 1))
        .reduce(new Average(), Average::merge)
        .get()
        .ifPresent(a -> System.out.println("Average is " + a));
    long time = System.nanoTime() - start;
    System.out.printf("time was %8.5f\n", (time / 1_000_000_000.0));
  }
}
