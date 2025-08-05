import java.sql.Time;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureExample {
    public static void main(String[] args) throws InterruptedException{
        System.out.println("主线程：点个汉堡（提交异步任务）");

        CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("子线程：收到订单，开始制作汉堡");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            return "老八蜜汁小汉堡";
        }).thenAccept(result -> {
            System.out.println("回调被触发");
            System.out.println("吃到了" + result);
        });

        System.out.printf("订单提交，我先走了\n");


        TimeUnit.SECONDS.sleep(3);
        System.out.println("玩好了，走了");
    }
}
