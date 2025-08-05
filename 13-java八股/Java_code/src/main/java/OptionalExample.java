import java.util.Optional;

public class OptionalExample {
    // 模拟数据库查询 返回值不是0就是1
    public static String findUserById_old(int id) {
        return (id == 1) ? "Alice" : null;
    }

    public static Optional<String> findUserById_New(int id) {
        return Optional.ofNullable(findUserById_old(id));
    }

    public static void main(String[] args) {

        // 传统方式
        String userNameOld = findUserById_old(2);
        if (userNameOld != null) {
            System.out.println("传统方法找到用户： " + userNameOld.toUpperCase());
        } else {
            System.out.println("传统方式：用户不存在，使用默认名Guest");
        }

        // Optional方式
        Optional<String> optionalUser = findUserById_New(2);
        String userNameNew = optionalUser.orElse("Guest");
        System.out.println("Optional方式，当前用户是：" + userNameNew);

        // 只有findUserById_New(1)返回值存在时才会去执行ifPresent内的内容
        findUserById_New(1).ifPresent(
                name -> {
                    System.out.println("Optional方式找到用户：" + name.toUpperCase());
                }
        );
    }
}
