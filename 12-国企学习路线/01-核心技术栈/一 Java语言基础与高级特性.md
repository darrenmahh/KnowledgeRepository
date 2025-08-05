# 1 语法基础
## 1.1 变量

- java变量名中不能加“-”
---
- 实例变量与局部变量
---

|      | 实例变量                        | 局部变量              |
| ---- | --------------------------- | ----------------- |
| 初始值  | 0                           | 无，需要显式赋值          |
| 内存位置 | 堆                           | 栈                 |
| 修饰符  | public private final static | static(X)   final |
- static
1. 归属：不属于一个独立的对象，而是属于整个类
2. 共享：这个类的所有对象共享一个静态变量，任何一个对象修改，别的对象均能看见
3. 生命周期：类被加载到内存时创建，比任何对象的创建都要早，程序结束时销毁
4. 访问方式：类名  对象
---
- 隐式类型转换：byte->short->int->long->float->double                    char->int
- 显式类型转换：
>String是引用对象类型，非基本数据类型 
>
```java
>String s = "132";
>int i = (int)s; //这种是错的
>int i = Integer.parseInt(s);//这是对的
```
---

## 1.2 数组
- 数组length属性
```java

arr.length;    //数组的是属性
'132'.length(); //字符串的是方法
```
---
- 数组被关键字final修饰：
1. 数组长度不能改变
2. numbers不能再指向另一个数组对象
```java
final int[] numbers = {1,2,3};
```
---
- 引用类型
```java
int[] arr1 = {1,2,3};
int[] arr2 = {1,2,3};    
arr1==arr2             //结果是:false,因为是指向不同的内存地址  
```

- 比较数组:使用  boolean areContentsEqual = Arrays.equals(arr1, arr2);
> [!warning] 比较数组
> 比较二维数组的时候不能简单使用Arrays.equals,要使用Arrays.deepEquals();

- ---
## 1.3 枚举
1. java中一种特殊的类，每个枚举常量都是类的一个实例
```java
public enum Day {
    MONDAY("星期一"),     // 每个枚举常量都是 Day 类的一个实例
    TUESDAY("星期二"),
    WEDNESDAY("星期三");

    private final String chineseName; // 实例变量

    // 构造函数
    Day(String chineseName) {
        this.chineseName = chineseName;
    }

    // 方法
    public String getChineseName() {
        return chineseName;
    }
}
//使用方法
public class EnumDemo {
    public static void main(String[] args) {
        Day today = Day.MONDAY;
        System.out.println("今天是: " + today.getChineseName()); // 输出：今天是: 星期一
    }
}
//遍历
public class EnumMethodDemo {
    public static void main(String[] args) {
        for (Day day : Day.values()) { // 遍历所有 Day 枚举常量
            System.out.println(day.name() + " 的中文名是: " + day.getChineseName());
        }
    }
}
//valueOf()方法
public class EnumMethodDemo {
    public static void main(String[] args) {
        String dayName = "TUESDAY";
        Day day = Day.valueOf(dayName); // 将字符串转换为枚举常量
        System.out.println("从字符串 \"" + dayName + "\" 转换得到的枚举是: " + day.getChineseName());
    }
}
```

> [!warning] 枚举构造函数
> 可以没有，有的话就必须是private

## 1.4 异常

- 程序执行过程中，遇到的会中断程序的流程，比如：文件找不到、网络中断、数组越界、除以零，异常处理会让程序正常结束，而不是让程序直接崩溃
1. 受检异常：try catch语句由程序员决定异常
2. 非受检异常：代码直接除0
- ---
- 异常抛出
1. throw    
2. throws
3. 两者不是选其一,而是看该类型是检查性还是非检查性,前者需要在方法声明的时候加上.
- ---
- 所有“可抛出”的根源都是  Throwable  类
- Throwable类的两个直接子类：Error  Exception
- ---

|           | 检查性异常            | 非检查性异常                |
| --------- | ---------------- | --------------------- |
| 原因        | 由程序外部的、不可预测的因素引起 | 由程序员代码逻辑失误引起          |
| 发生时机      | 程序与外部环境交互时       | 程序内部，本不该发生的           |
| 编译器是否强制处理 | 预料到会发生需要给出预案     | 应该修复bug，而不是抛出错误，程序会臃肿 |

---
```java
public static int testMethod() {
    int i = 1;
    try {
        return i; // 准备返回 i (值为1)
    } finally {
        i = 100; // 在 finally 中修改 i
    }
}
// 调用 testMethod() 会返回什么？是 1 还是 100？
```

- 代码执行的时候会先把返回的值1放在一个临时的存储位置,虽然最后i是100,执行了finally中的语句,但是并没有改变返回的那个临时区的值.
- ---
- java.lang.InterruptedException
- 这个属于检查性异常,对于当前线程来说,别的线程来对这个线程做一些事,应该要检查一下并输出被打断过.
- ---
- 处理异常的原则:
1. 绝不"生吞"异常,也就是不使用空的catch
2. 使用异常链:包装异常的时候保留根本原因
3. 抛出具体的、与业务相关的异常,不要笼统抛出Exception
4. 优先使用try-with-resource，自动安全关闭资源
5. 记录详细日志，在catch中使用日志框架记录完整异常堆栈信息
- ---
- 异常对性能的开销
1. 创建异常对象代价很高，因为需要生成完整的堆栈轨迹
2. 核心原则：不要用异常控制程序的正常流程，异常是为“意外”和“错误”准备的应急机制





# 2 面向对象编程
- 

# 3 异常处理
