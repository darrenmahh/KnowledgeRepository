import java.time.Duration;
import java.time.Instant;
import java.util.stream.LongStream;

public class ParallelStreamExample {

    public static void main(String[] args) {
        long limit = 900_000_000L; // 两亿
        System.out.println("计算从1到" + limit + "的累加和...");

        // --- 1. 使用传统的单线程串行流 ---
        Instant start1 = Instant.now();
        long sum1 = LongStream.rangeClosed(1, limit).sum();
        Instant end1 = Instant.now();
        System.out.println("串行流计算结果: " + sum1);
        System.out.println("串行流耗时: " + Duration.between(start1, end1).toMillis() + " ms");

        System.out.println("---------------------------------");

        // --- 2. 使用并行流 ---
        Instant start2 = Instant.now();
        // 仅需一个 .parallel() 调用，即可“一键”开启并行计算
        long sum2 = LongStream.rangeClosed(1, limit).parallel().sum();
        Instant end2 = Instant.now();
        System.out.println("并行流计算结果: " + sum2);
        // 在多核CPU上，你会看到耗时显著减少
        System.out.println("并行流耗时: " + Duration.between(start2, end2).toMillis() + " ms");

        long sum3 = 0;
        Instant start3 = Instant.now();
        for (long i = 1; i <= limit; i++) {
            sum3 += i;
        }
        Instant end3 = Instant.now();
        System.out.println("---");
        System.out.println("for循环计算结果: " + sum3);
        System.out.println("for循环耗时: " + Duration.between(start3, end3).toMillis() + " ms");
    }




}
