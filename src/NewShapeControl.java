import java.awt.*;
import java.util.logging.Logger;

// the shape and state
public class NewShapeControl {
    // control the state of next
    enum State{
        select, drawLine, drawRectangle, drawCircle, drawText, isSelected
    }

    private static State currentState;
    public static void setCurrentState(State state) {
        currentState = state;
    }
    public static State getCurrentState(){return currentState;}


    // string in the buffer, wait for creating in the new text
    private static String bufferText = "";
    public static void setBufferText(String text){
        bufferText = text;
    }
    public static String getBufferText(){
        return bufferText;
    }
    public static void setCurrentShapeText(){
        currentShape.setInputText(bufferText);
    }


    // the color that will be used
    private static Color currentColor = Color.BLACK;

    public static void setCurrentColor(Color color){
        currentColor = color;
    }

    public static Color getCurrentColor(){
        return currentColor;
    }


    // current shape
    private static MyShape currentShape = null;

    public static void dropCurrentShape(){
        currentShape = null;
    }

    public static void createNewShape(){
        if (currentState == null) return;
        if (currentState == State.drawLine) currentShape = new Line();
        else if (currentState == State.drawCircle) currentShape = new Circle();
        else if (currentState == State.drawRectangle) currentShape = new Rectangle();
        else if (currentState == State.drawText) currentShape = new Text();
        else Logger.getGlobal().info("wrong type on NewShapeControl createNewShape");
        currentShape.setColor(currentColor);
    }
    public static void setCurrentShapeStartPoint(int x, int y){
        currentShape.setStartPosition(x, y);
    }
    public static void setCurrentShapeOffset(int x, int y){
        if (currentState == null) return;
        currentShape.setOffset(x, y);
    }
    public static MyShape getCurrentShape(){
        return currentShape;
    }


    // about the selected shape
    private static MyShape selectedShape = null;

    public static void setSelectedShape(MyShape shape){
        // TODO: 可能需要在其它的情况下也要把笔画的粗细复原
        // as we have removed the shape in the list, we need to add back
        if (selectedShape != null) {
            selectedShape.decreaseWidthInSelection();
            AllShapes.add(selectedShape);
        }
        selectedShape = shape;
        selectedShape.increaseWidthInSelection();
    }
    public static void cancelSelectedShape(){
        if (selectedShape != null) {
            selectedShape.decreaseWidthInSelection();
            AllShapes.add(selectedShape);
        }
        selectedShape = null;
    }

    public static MyShape getSelectedShape(){
        return selectedShape;
    }
    public static void moveSelectedShapePosition(int x, int y){
        selectedShape.moveToCentralPosition(x, y);
    }
    public static void setSelectedColor(Color c){
        selectedShape.setColor(c);
    }
    public static void prolongSelected(){
        selectedShape.prolong();
    }
    public static void shortenSelected(){
        selectedShape.shorten();
    }
    public static void widenSelected(){
        selectedShape.widen();
    }
    public static void thinSelected(){
        selectedShape.thin();
    }
    public static void deleteSelected(){
        selectedShape = null;
    }
}
