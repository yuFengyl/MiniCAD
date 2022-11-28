import java.util.ArrayList;
import java.util.List;

public class AllShapes {


    private static ArrayList<MyShape> shapeList = new ArrayList<>();

    public static ArrayList<MyShape> getList() {
        return shapeList;
    }

    public static void add(MyShape e) {
        shapeList.add(e);
    }

    public static void remove(MyShape e) {
        shapeList.remove(e);
    }

}
