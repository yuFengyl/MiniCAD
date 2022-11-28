import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// 用于构建整个程序的 frame
public class CFrame extends JFrame {
    private DPanel drawPanel;
    private JPanel buttonPanel;
    private JPanel buttonColorPanel;
    private ArrayList<JButton> buttonList;
    public CFrame(){
        super();
        setTitle("miniCAD");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0, 0));
        setVisible(true);

        // set the drawing board
        drawPanel  = new DPanel();
        drawPanel.setBackground(Color.WHITE);
        drawPanel.setFocusable(true);
        add(drawPanel, BorderLayout.CENTER);

        buttonColorPanel = new JPanel();
        buttonColorPanel.setLayout(new GridLayout(3, 3));
        setColorButton();

        // set the button
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1));
        setOtherButton("Rectangle", StateControl.State.drawRectangle);
        setOtherButton("Circle", StateControl.State.drawCircle);
        setOtherButton("Line", StateControl.State.drawLine);
        setOtherButton("Text", StateControl.State.drawText);
        setOtherButton("Select", StateControl.State.select);
        buttonPanel.add(buttonColorPanel, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.EAST);

    }

    private void setColorButton(){
        JButton btn = new JButton();
        btn.setBackground(Color.black);
        buttonColorPanel.add(btn);
        btn = new JButton();
        btn.setBackground(Color.BLUE);
        buttonColorPanel.add(btn);
        btn = new JButton();
        btn.setBackground(Color.GRAY);
        buttonColorPanel.add(btn);
        btn = new JButton();
        btn.setBackground(Color.GREEN);
        buttonColorPanel.add(btn);
        btn = new JButton();
        btn.setBackground(Color.ORANGE);
        buttonColorPanel.add(btn);
        btn = new JButton();
        btn.setBackground(Color.PINK);
        buttonColorPanel.add(btn);
        btn = new JButton();
        btn.setBackground(Color.RED);
        buttonColorPanel.add(btn);
        btn = new JButton();
        btn.setBackground(Color.YELLOW);
        buttonColorPanel.add(btn);
        btn = new JButton();
        btn.setBackground(Color.CYAN);
        buttonColorPanel.add(btn);
    }

    private void setOtherButton(String name, StateControl.State state){
        JButton btn = new JButton(name);
        btn.addActionListener(new ActionListener() {
            // Only set state when button was pressed, draw the shape when the mouse was moved
            @Override
            public void actionPerformed(ActionEvent e) {
                StateControl.setCurrentState(state);
                drawPanel.setFocusable(true);
            }
        });
        buttonPanel.add(btn);
    }
}
