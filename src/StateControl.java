import java.util.logging.Logger;

// the shape and state
public class StateControl {
    // state
    enum State{
        select, drawLine, drawRectangle, drawCircle, drawText
    }
    private static State currentState;
    public static void setCurrentState(State state) {
        currentState = state;
    }
    public static State getCurrentState(){return currentState;}

    // current shape
    private static MyShape currentShape = null;

    public static void createNewShape(){
        if (currentState == State.drawLine) currentShape = new Line();
        else if (currentState == State.drawCircle) currentShape = new Circle();
        else if (currentState == State.drawRectangle) currentShape = new Rectangle();
        else Logger.getGlobal().info("wrong type on StateControl createNewShape");
    }

    public static void setStartPoint(int x, int y){
        currentShape.setStartPosition(x, y);
    }
    public static void setOffset(int x, int y){
        currentShape.setOffset(x, y);
    }
    public static MyShape getCurrentShape(){
        return currentShape;
    }
}
