import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class Person {
    private String name = "老登";

    private void saySecret() {
        System.out.println(this.name + "是傻逼");
    }
}

public class PrivateAccessDemo {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Person person = new Person();

        // 1，访问私有字段
        System.out.println("--访问私有字段--");
        Field nameField = Person.class.getDeclaredField("name");

        // 关键一步：禁用访问权限审查
        nameField.setAccessible(true);
        String nameValue = (String) nameField.get(person);
        System.out.println("通过反射成功获取到私有name：" + nameValue);

        // 2，调用私有方法
        System.out.println("\n--调用私有方法--");
        Method secretMethod = Person.class.getDeclaredMethod("saySecret");
        secretMethod.setAccessible(true);
        secretMethod.invoke(person);
    }
}
