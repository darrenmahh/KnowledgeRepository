package CloneTest;

class MyClass implements Cloneable {
    int value;
    String name;

    public MyClass(int value, String name) {
        this.value = value;
        this.name = name;
    }

    // 重写 clone() 方法
    @Override
    public Object clone() throws CloneNotSupportedException {
        // 调用 Object 类的 clone() 方法实现浅拷贝
        return super.clone();
    }

    @Override
    public String toString() {
        return "MyClass [value=" + value + ", name=" + name + "]";
    }
}

public class CloneExample {
    public static void main(String[] args) {
        try {
            MyClass original = new MyClass(10, "原始对象");
            System.out.println("原始对象: " + original);

            MyClass cloned = (MyClass) original.clone();
            System.out.println("克隆对象: " + cloned);

            // 修改克隆对象，原始对象不受影响（对于基本类型）
            cloned.value = 20;
            cloned.name = "克隆后的对象";

            System.out.println("修改后原始对象: " + original);
            System.out.println("修改后克隆对象: " + cloned);

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            System.out.println("对象不支持克隆。");
        }
    }
}