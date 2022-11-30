import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// 用于构建画的 frame
public class DPanel extends JPanel {
    public DPanel() {
        super();
        setFocusable(true);
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (NewShapeControl.getCurrentState() == NewShapeControl.State.isSelected){
                    if (e.getKeyChar() == 'L' || e.getKeyChar() == 'l')
                        NewShapeControl.prolongSelected();
                    else if (e.getKeyChar() == 'S' || e.getKeyChar() == 's')
                        NewShapeControl.shortenSelected();
                    else if (e.getKeyChar() == 'w' || e.getKeyChar() == 'W')
                        NewShapeControl.widenSelected();
                    else if (e.getKeyChar() == 't' || e.getKeyChar() == 'T')
                        NewShapeControl.thinSelected();
                    else if (e.getKeyChar() == 'D' || e.getKeyChar() == 'd'){
                        NewShapeControl.deleteSelected();
                        NewShapeControl.setCurrentState(null);
                    }
                    repaint();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(MouseEvent e) {
                if (NewShapeControl.getCurrentState() == NewShapeControl.State.select) {
                    MyShape selectedShape = AllShapes.findMatchedShape(e.getX(), e.getY());
                    if (selectedShape == null) return;
                    else {
                        NewShapeControl.setCurrentState(NewShapeControl.State.isSelected);
                        NewShapeControl.setSelectedShape(selectedShape);
                        AllShapes.remove(selectedShape);
                        repaint();
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (NewShapeControl.getCurrentState() == null) return;
                if (NewShapeControl.getCurrentState() == NewShapeControl.State.isSelected){

                }
                else if (NewShapeControl.getCurrentState() != NewShapeControl.State.select){
                    NewShapeControl.createNewShape();
                    if (NewShapeControl.getCurrentState() == NewShapeControl.State.drawText){
                        NewShapeControl.setCurrentShapeText();
                    }
                    NewShapeControl.setCurrentShapeStartPoint(e.getX(), e.getY());
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (NewShapeControl.getCurrentState() == null) return;
                if (NewShapeControl.getCurrentState() == NewShapeControl.State.isSelected){
                    NewShapeControl.moveSelectedShapePosition(e.getX(), e.getY());
                }
                else if (NewShapeControl.getCurrentState() != NewShapeControl.State.select){
                    int x = e.getX();
                    int y = e.getY();
                    NewShapeControl.setCurrentShapeOffset(x, y);
                    AllShapes.add(NewShapeControl.getCurrentShape());
                    NewShapeControl.dropCurrentShape();
                }
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (NewShapeControl.getCurrentState() == null) return;
                if (NewShapeControl.getCurrentState() == NewShapeControl.State.isSelected){
                    NewShapeControl.moveSelectedShapePosition(e.getX(), e.getY());
                }
                else if (NewShapeControl.getCurrentState() != NewShapeControl.State.select){
                    NewShapeControl.setCurrentShapeOffset(e.getX(), e.getY());
                }
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });

    }

    public void rePaintAfterOpen(){
        getGraphics().clearRect(0, 0, getWidth(), getHeight());
        Graphics g = getGraphics();
        ArrayList<MyShape> shapeList = AllShapes.getList();
        for (MyShape iter : shapeList){
            iter.draw((Graphics2D) g);
        }
    }

    // 这个函数会在 Panel 上画东西
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        ArrayList<MyShape> list = AllShapes.getList();
        if (!list.isEmpty()){
            for (MyShape i:list)
                i.draw(g2);
        }
        if (NewShapeControl.getCurrentShape() != null)
            NewShapeControl.getCurrentShape().draw(g2);
        if (NewShapeControl.getSelectedShape() != null)
            NewShapeControl.getSelectedShape().draw(g2);
    }
}
