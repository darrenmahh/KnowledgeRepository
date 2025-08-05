package NewInstanceTest;

class MyObject {
    public MyObject() {
        System.out.println("MyObject 的无参数构造方法被调用了！");
    }
    public void sayHello() {
        System.out.println("Hello, I was created by reflection.");
    }
}

public class ReflectionDemo {
    public static void main(String[] args) {
        try {
            // 1. 根据类的全名（字符串）获取Class对象
            Class<?> clazz = Class.forName("NewInstanceTest.MyObject");

            // 2. 通过Class对象，调用其无参数构造方法创建实例 (JDK 9+ 推荐方式)
            // Constructor.newInstance() 比 Class.newInstance() 更强大，可以调用私有或带参构造
            Object obj = clazz.getDeclaredConstructor().newInstance();

            // 3. 调用对象的方法
            ((MyObject) obj).sayHello();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
