package DeepCopyTest;

class Student implements Cloneable {
    public int age;
    public Address address;

    public Student(int age, Address address) {
        this.age = age;
        this.address = address;
    }

    // 重写clone方法，实现深拷贝
    @Override
    protected Object clone() throws CloneNotSupportedException {
        // 1. 先进行一次浅拷贝，得到一个基本复制的clonedStudent
        Student clonedStudent = (Student) super.clone();

        // 2. 对引用类型的成员变量，单独进行一次拷贝
        clonedStudent.address = (Address) this.address.clone();

        return clonedStudent;
    }

    // 新增浅拷贝方法
    protected Student shallowClone() throws CloneNotSupportedException {
        return (Student) super.clone();
    }
}
