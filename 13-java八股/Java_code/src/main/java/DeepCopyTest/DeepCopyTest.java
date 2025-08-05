package DeepCopyTest;

public class DeepCopyTest {
    public static void main(String[] args) throws CloneNotSupportedException {
        Address originalAddress = new Address("北京");
        Student originalStudent = new Student(20, originalAddress);

        Student clonedStudent = (Student) originalStudent.clone();

        // 再次尝试修改拷贝对象的属性
        clonedStudent.age = 22;
        clonedStudent.address.city = "上海"; // 修改引用类型内部的数据

        System.out.println("原始学生年龄: " + originalStudent.age); // 输出: 20
        System.out.println("克隆学生年龄: " + clonedStudent.age); // 输出: 22

        System.out.println("----------- 分割线 -----------");

        System.out.println("原始学生地址: " + originalStudent.address.city); // 输出: 北京 (这次没有受到影响！)
        System.out.println("克隆学生地址: " + clonedStudent.address.city); // 输出: 上海
    }
}