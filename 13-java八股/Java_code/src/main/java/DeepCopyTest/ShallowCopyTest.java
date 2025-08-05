package DeepCopyTest;

public class ShallowCopyTest {
    public static void main(String[] args) throws CloneNotSupportedException {
        // 1. 创建原始对象
        Address originalAddress = new Address("北京");
        Student originalStudent = new Student(20, originalAddress);

        // 2. 进行浅拷贝
        Student clonedStudent = originalStudent.shallowClone();

        // 3. 修改拷贝对象的属性
        clonedStudent.age = 22; // 修改基本类型
        clonedStudent.address.city = "上海"; // 修改引用类型内部的数据

        // 4. 观察结果
        System.out.println("原始学生年龄: " + originalStudent.age); // 输出: 20 (未受影响)
        System.out.println("克隆学生年龄: " + clonedStudent.age); // 输出: 22

        System.out.println("----------- 分割线 -----------");

        System.out.println("原始学生地址: " + originalStudent.address.city); // 输出: 上海 (受到影响!)
        System.out.println("克隆学生地址: " + clonedStudent.address.city); // 输出: 上海
    }
}