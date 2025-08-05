package Reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class UserDao {
    public void find() {
        System.out.println("在数据库中查找用户...");
    }
}

class UserService {
    private UserDao userDao;

    // Spring会通过反射来调用这个setter方法
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void findUser() {
        System.out.println("业务层开始查找用户...");
    }
}

public class MiniSpringContainer {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        // Spring读取配置文件 这里简化为字符串 根据这个来创建UserService和UserDao
        String userServiceClassName = "Reflection.UserService";
        String userDaoClassName = "Reflection.UserDao";

        UserService userService = (UserService) Class.forName(userServiceClassName).getDeclaredConstructor().newInstance();
        UserDao userDao = (UserDao) Class.forName(userDaoClassName).getDeclaredConstructor().newInstance();

        Method setterMethod = userService.getClass().getMethod("setUserDao", UserDao.class);
        setterMethod.invoke(userService, userDao);

        userService.findUser();
    }

}
