package Reflection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)  //注解必须在运行时可见反射才能找到
@Target(ElementType.METHOD) // 关键：只能用在方法上
@interface MyTest {};

class Calculator {
    @MyTest
    public void testAddition() {
        if ((1 + 1) == 2) {
            System.out.println("测试Add方法...成功！");
        } else {
            System.out.println("测试Add方法...失败！");
        }
    }

    public void normalMethod() {
        System.out.println("这是一个普通方法，不执行。");
    }

    @MyTest
    public void testSubtraction() {
        if ((3 - 1) == 2) {
            System.out.println("测试Subtract方法...成功！");
        } else {
            System.out.println("测试Subtract方法...失败！");
        }
    }
}

public class AnnotationTestRunner {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> calculatorClass = Class.forName("Reflection.Calculator");
        Object calculatorInstance = calculatorClass.getDeclaredConstructor().newInstance();

        for (Method method : calculatorClass.getMethods()) {
            if (method.isAnnotationPresent(MyTest.class)) {
                System.out.println("发现测试方法" + method.getName() + "，开始执行...");
                method.invoke(calculatorInstance);
            }
        }
    }
}
