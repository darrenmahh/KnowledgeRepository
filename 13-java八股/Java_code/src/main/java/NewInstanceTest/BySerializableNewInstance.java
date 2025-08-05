package NewInstanceTest;

import java.io.*;
import java.sql.SQLOutput;

class NewObject implements Serializable {
    private static final long serialVersionUID = 1L;
    public String message = "I am a new object.";

    public NewObject() {
        System.out.println("MyObject的构造方法被调用了");
    }
}

public class BySerializableNewInstance {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // 1,创建一个原始对象
        NewObject originalObj = new NewObject();

        // 2,将其序列化到内存的字节数组中
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(originalObj);

        byte[] objectData = bos.toByteArray();
        System.out.println("----开始反序列化----");

        ByteArrayInputStream bis = new ByteArrayInputStream(objectData);
        ObjectInputStream in = new ObjectInputStream(bis);
        NewObject newObj = (NewObject) in.readObject();

        System.out.println("新创建的对象是" + newObj.message);
        System.out.println("他们是同一个数据吗" + (originalObj == newObj));
    }

}
