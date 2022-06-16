package collector;

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

  public void merge(Average other) {
    this.sum += other.sum;
    this.count += other.count;
  }

  public void include(double d) {
    this.sum += d;
    this.count++;
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
    ThreadLocalRandom.current().doubles(3_000_000_000L, -1.0, 1.0)
//    Stream.iterate(0.0, x -> ThreadLocalRandom.current().nextDouble(-1.0, 1.0))
        .parallel()
//        .unordered()
//        .limit(3_000_000_000L)
//        .mapToObj(v -> new Average(v, 1))
        .boxed()
        .collect(() -> new Average(),
            (a, d) -> a.include(d),
            (a1, a2) -> a1.merge(a2))
        .get()
        .ifPresent(a -> System.out.println("Average is " + a));
    long time = System.nanoTime() - start;
    System.out.printf("time was %8.5f\n", (time / 1_000_000_000.0));

    /*
    Streams in parallel normally use the ForkJoinCommonPool, and
    that runs up as many threads as needed to keep the CPUs busy
    But, if you start a parallel Stream from code running in a different
    ForkJoinPool, then that stream runs in that ForkJoinPool, so,
    you can use this to control the number of CPUs being used.

    BUT!!! If you have other important uses for your CPUS, then
    as discussed, perhaps you shouldn't be doing this in the first place :)
     */
  }
}
