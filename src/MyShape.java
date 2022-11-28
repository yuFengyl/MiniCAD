import java.awt.*;
import java.io.Serializable;

public abstract class MyShape implements Serializable {

    // the shape
    protected int x, y, dx, dy; // the
    protected Color shapeColor = Color.black;
    protected float shapeWidth = 2.0f;
    protected boolean isSelected = false;



    public void setColor(Color c){
        shapeColor = c;
    }
    // abstract method
    public abstract void draw(Graphics2D g);
    public void moveTo(int dx, int dy){
        x += dx;
        y += dy;
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