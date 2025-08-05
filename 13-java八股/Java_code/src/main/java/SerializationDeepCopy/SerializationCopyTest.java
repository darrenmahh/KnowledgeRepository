package SerializationDeepCopy;

import java.io.*;

// 注意：所有相关的类都需要实现Serializable接口
class Address implements Serializable {
    public String city;
    public Address(String city) { this.city = city; }
}

class Student implements Serializable {
    public int age;
    public Address address;

    public Student(int age, Address address) {
        this.age = age;
        this.address = address;
    }
}

public class SerializationCopyTest {

    public static <T> T deepCopy(T original) throws IOException, ClassNotFoundException {
        // 1. 将对象写入字节流 (序列化)
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(original);

        // 2. 从字节流中读取对象 (反序列化)
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream in = new ObjectInputStream(bis);

        // 返回创建的新对象
        return (T) in.readObject();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Address originalAddress = new Address("北京");
        Student originalStudent = new Student(20, originalAddress);

        // 使用序列化工具进行深拷贝
        Student clonedStudent = deepCopy(originalStudent);

        // 修改拷贝对象的属性
        clonedStudent.age = 22;
        clonedStudent.address.city = "上海";

        System.out.println("原始学生年龄: " + originalStudent.age); // 输出: 20
        System.out.println("克隆学生年龄: " + clonedStudent.age); // 输出: 22
        System.out.println("----------- 分割线 -----------");
        System.out.println("原始学生地址: " + originalStudent.address.city); // 输出: 北京 (未受影响！)
        System.out.println("克隆学生地址: " + clonedStudent.address.city); // 输出: 上海
    }
}