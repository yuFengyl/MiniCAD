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

    public static void setList(ArrayList<MyShape> shapes){
        shapeList = shapes;
    }

    public static MyShape findMatchedShape(int x, int y){
        double MAXLENGTH = 99999999;
        double MINLENGTH = 80;
        MyShape tempShape = null;
        double shortestLength = MAXLENGTH;
        for (MyShape iter:shapeList){
            int tempX = iter.getCentralX();
            int tempY = iter.getCentralY();
            double tempLength = computeLength(iter, (double)tempX-x, (double) tempY-y);
            if (tempLength < shortestLength){
                tempShape = iter;
                shortestLength = tempLength;
            }
        }
        if (shortestLength>MINLENGTH)
            return null;
        else
            return tempShape;
    }

    private static double computeLength(MyShape s, double x, double y){
        double tempLength = Math.pow(x, 2) + Math.pow(y, 2);
        tempLength = Math.sqrt(tempLength);
        if (s.getClass().getName().equals("Text"))
            return tempLength/2;
        else
            return tempLength;
    }
}
