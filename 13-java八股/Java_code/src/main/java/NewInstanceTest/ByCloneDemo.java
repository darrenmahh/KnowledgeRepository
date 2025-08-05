package NewInstanceTest;

class Sheep implements Cloneable {
    public Sheep() {
        System.out.println("羊的构造方法被调用了");
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        System.out.println("正在克隆一只新羊");
        return super.clone();
    };
}

public class ByCloneDemo {
    public static void main(String[] args) throws CloneNotSupportedException {
        Sheep originalSheep = new Sheep();
        System.out.println("----开始克隆----");

        Sheep clonedSheep = (Sheep) originalSheep.clone();

        System.out.println("原始羊" + originalSheep);
        System.out.println("克隆羊" + clonedSheep);
        System.out.println("他们是同一个对象吗？" + (originalSheep == clonedSheep));


    }
}
