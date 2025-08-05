package SuperTest;

// 父类：动物
class Animal {
    String name;

    // 父类的无参数构造方法
    public Animal() {
        System.out.println("1. 父类Animal的无参数构造方法被调用了。");
    }

    // 父类的有参数构造方法
    public Animal(String name) {
        this.name = name;
        System.out.println("2. 父类Animal的有参数构造方法被调用了，名字是：" + this.name);
    }
}

// 子类：狗
class Dog extends Animal {
    int age;

    public Dog() {
        // 这里编译器会自动隐式地插入 super();
        System.out.println("3. 子类Dog的无参数构造方法被调用了。");
    }

    public Dog(String name, int age) {
        // 显式调用父类的有参数构造方法，必须在第一行
        super(name);
        this.age = age;
        System.out.println("4. 子类Dog的有参数构造方法被调用了，年龄是：" + this.age);
    }
}

public class SuperTest {
    public static void main(String[] args) {
        System.out.println("--- 创建无参数的Dog对象 ---");
        Dog dog1 = new Dog(); // 输出 1 -> 3

        System.out.println("\n--- 创建有参数的Dog对象 ---");
        Dog dog2 = new Dog("旺财", 2); // 输出 2 -> 4
    }
}