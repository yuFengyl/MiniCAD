import java.awt.*;
import java.io.Serializable;

public abstract class MyShape implements Serializable {

    // the shape
    protected int x, y, dx, dy; // the
    protected Color shapeColor = Color.black;
    protected float shapeWidth = 2.0f;

    // only for text
    protected String inputText = "";
    public void setInputText(String text){
        inputText = text;
    }
    public String getInputText(){
        return inputText;
    }



    public void setColor(Color c){
        shapeColor = c;
    }
    // abstract method
    public abstract void draw(Graphics2D g);
    public void increaseWidthInSelection(){
        shapeWidth += 3.0f;
    }
    public void decreaseWidthInSelection(){
        shapeWidth -= 3.0f;
    }
    public void moveToCentralPosition(int x0, int y0){
        x = x0 - dx/2;
        y = y0 - dy/2;
    }
    public void setStartPosition(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void setOffset(int x2, int y2){
        dx = x2 - x;
        dy = y2 - y;
    }
    protected void setDrawingState(Graphics2D g){
        g.setColor(shapeColor);
        g.setStroke(new BasicStroke(shapeWidth));
    }
    public int getCentralX(){
        return x+dx/2;
    }
    public int getCentralY(){
        return y+dy/2;
    }
    private int calculateX(int k){
        if (dx < 0)
            return -k;
        else if (dx == 0)
            return 0;
        else
            return k;
    }
    private int calculateY(int k){
        if (dy < 0)
            return -k;
        else if (dy == 0)
            return 0;
        else
            return k;
    }
    public void prolong(){
        int k = Math.abs(dy/dx);
        if (k!=0){
            dx+=calculateX(1);
            dy+=calculateY(k);
        }
        else{
            k = Math.abs(dx/dy);
            dx+=calculateX(k);
            dy+=calculateY(1);
        }
    }
    public void shorten(){
        int k = Math.abs(dy/dx);
        if (k!=0){
            dx-=calculateX(1);
            dy-=calculateY(k);
        }
        else{
            k = Math.abs(dx/dy);
            dx-=calculateX(k);
            dy-=calculateY(1);
        }
    }
    public void widen(){
        shapeWidth += 0.1f;
    }
    public void thin(){
        if (shapeWidth <= 4.0f)
            return;
        shapeWidth -= 0.1f;
    }
}

class Line extends MyShape{
    @Override
    public void draw(Graphics2D g) {
        super.setDrawingState(g);
        g.drawLine(x, y, x+dx, y+dy);
    }
}

class Circle extends MyShape{

    @Override
    public void draw(Graphics2D g) {
        super.setDrawingState(g);
        g.drawRoundRect(x, y, dx, dy, dx, dy);
    }
}

class Rectangle extends MyShape{
    @Override
    public void draw(Graphics2D g) {
        super.setDrawingState(g);
        g.drawRoundRect(x, y, dx, dy, 0, 0);
    }
}

class Text extends MyShape{
    @Override
    public void draw(Graphics2D g) {
        super.setDrawingState(g);
        int fontSize = (int)(2.4 * Math.max(Math.abs(dx), Math.abs(dy)) / inputText.length());
        g.setFont(new Font(null, 0, fontSize));
        g.drawString(inputText, x, y+dy);
    }
}