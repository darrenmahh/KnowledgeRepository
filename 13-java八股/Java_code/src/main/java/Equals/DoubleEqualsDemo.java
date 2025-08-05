package Equals;

public class DoubleEqualsDemo {
    public static void main(String[] args) {
        int a = 100;
        int b = 100;

        System.out.println("基本类型比较(a==b)：" + (a == b));


        String str1 = new String("java");
        String str2 = new String("java1");
        String str3 = str1;
        System.out.println("str1 == str2" + (str1 == str2));
        System.out.println("str1 == str3" + (str1 == str3));


        System.out.println(str1.hashCode());
        System.out.println(str2.hashCode());
        System.out.println(str3.hashCode());
    }
}
