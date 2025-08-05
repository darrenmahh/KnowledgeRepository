package Reflection;

class Wutao {}

public class ReflectionDefinitionDemo {
    public static void main(String[] args) {
        Class<?> class1 = Wutao.class;

        Wutao machong = new Wutao();
        Class<?> class2 = machong.getClass();

        try {
            Class<?> class3 = Class.forName("Reflection.Wutao");

            System.out.println("三种方式获取的是同一个Class对象吗？" + (class1 == class2 && class2 == class3));
            System.out.println("获取到的类名是:" + class1.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}














