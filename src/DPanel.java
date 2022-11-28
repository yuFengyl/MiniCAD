import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

// 用于构建画的 frame
public class DPanel extends JPanel {
    public DPanel() {
        super();
        addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(MouseEvent e) {
                if (StateControl.getCurrentState() == StateControl.State.select){
                    int x = e.getX();
                    int y = e.getY();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (StateControl.getCurrentState() != StateControl.State.select){
                    StateControl.createNewShape();
                    StateControl.setStartPoint(e.getX(), e.getY());
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (StateControl.getCurrentState() != StateControl.State.select){
                    int x = e.getX();
                    int y = e.getY();
                    StateControl.setOffset(x, y);
                    AllShapes.add(StateControl.getCurrentShape());
                    repaint();
                }
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
                if (StateControl.getCurrentState() != StateControl.State.select){
                    StateControl.setOffset(e.getX(), e.getY());
                    repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
    }

    // 这个函数会在 Panel 上画东西
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        ArrayList<MyShape> list = AllShapes.getList();
        for (MyShape i:list)
            i.draw(g2);
        if (StateControl.getCurrentShape() != null)
            StateControl.getCurrentShape().draw(g2);
    }
}
