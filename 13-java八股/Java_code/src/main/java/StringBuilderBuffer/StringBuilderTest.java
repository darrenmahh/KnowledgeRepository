package StringBuilderBuffer;

public class StringBuilderTest {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("hello");
        System.out.println("操作前身份证号：" + System.identityHashCode(sb));

        sb.append("789");
        System.out.println("操作后身份证号：" + System.identityHashCode(sb));
        System.out.println(sb);
    }
}
