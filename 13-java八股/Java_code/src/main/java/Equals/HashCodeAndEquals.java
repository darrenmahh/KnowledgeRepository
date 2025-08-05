package Equals;

import java.util.HashSet;
import java.util.Objects;

class Person {
    String name;
    public Person(String name) {this.name = name;};

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name);
    }
}

public class HashCodeAndEquals {
    public static void main(String[] args) {
        HashSet<Person> personSet = new HashSet<>();
        Person p1 = new Person("张三");
        Person p2 = new Person("张三");

        System.out.println("p1.equals(p2) 的结果是: " + p1.equals(p2)); // true，符合我们的逻辑
        System.out.println("p1 的哈希码是: " + p1.hashCode()); // 一个和地址相关的整数
        System.out.println("p2 的哈希码是: " + p2.hashCode()); // 另一个和地址相关的整数，和p1的不同

        personSet.add(p1);
        personSet.add(p2); // 存入p2时，HashSet一看p2的哈希码和p1不同，就认为p2是新对象，也加了进去！

        // 我们期望Set里只有一个"张三"，但因为违反了约定，导致出现了重复元素
        System.out.println("Set里的人数: " + personSet.size()); // 期望是1，但实际输出了 2
    }

}
