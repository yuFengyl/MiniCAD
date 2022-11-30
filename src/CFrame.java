import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

// 用于构建整个程序的 frame
public class CFrame extends JFrame {
    private DPanel drawPanel;
    private JPanel buttonPanel;
    private JPanel buttonColorPanel;

    Color[] allColor = {Color.BLACK, Color.BLUE, Color.GRAY, Color.GREEN, Color.ORANGE, Color.PINK, Color.RED, Color.YELLOW, Color.CYAN};
    public CFrame(){
        super();
        setTitle("miniCAD");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0, 0));

        // set the drawing board
        drawPanel  = new DPanel();
        drawPanel.setBackground(Color.WHITE);
        drawPanel.requestFocusInWindow();
        add(drawPanel, BorderLayout.CENTER);

        buttonColorPanel = new JPanel();
        buttonColorPanel.setLayout(new GridLayout(3, 3));
        setColorButton();

        // set the button
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(7, 1));
        setOtherButton("Rectangle", NewShapeControl.State.drawRectangle);
        setOtherButton("Circle", NewShapeControl.State.drawCircle);
        setOtherButton("Line", NewShapeControl.State.drawLine);
        setOtherButton("Text", NewShapeControl.State.drawText);
        setOtherButton("Select", NewShapeControl.State.select);
        setOtherButton("Cancel Select", null);
        buttonPanel.add(buttonColorPanel, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.EAST);

        JMenuBar menu = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open Project"){{
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String loadFileName = buildFileDialog("Open", FileDialog.LOAD);
                    try {
                        FileInputStream loadFileStream = new FileInputStream(loadFileName);
                        ObjectInputStream objectStream = new ObjectInputStream(loadFileStream);
                        AllShapes.setList((ArrayList<MyShape>) objectStream.readObject());
                        drawPanel.rePaintAfterOpen();
                    } catch (FileNotFoundException ex) {
                        JDialog remindDialog = new JDialog();
                        remindDialog.setTitle("Open");
                        JPanel t = new JPanel();
                        t.setLayout(new BorderLayout());
                        JButton ok = new JButton("OK");
                        ok.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                remindDialog.dispose();
                            }
                        });
                        t.add(ok, BorderLayout.SOUTH);
                        JLabel remindText = new JLabel("FILE NOT FOUND!", SwingConstants.CENTER);
                        t.add(remindText, BorderLayout.CENTER);
                        remindDialog.add(t);
                        remindDialog.setSize(200,120);
                        remindDialog.setVisible(true);
                    } catch (IOException ex) {
                        JDialog remindDialog = new JDialog();
                        remindDialog.setTitle("Open");
                        JPanel t = new JPanel();
                        t.setLayout(new BorderLayout());
                        JButton ok = new JButton("OK");
                        ok.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                remindDialog.dispose();
                            }
                        });
                        t.add(ok, BorderLayout.SOUTH);
                        t.add(new JLabel("WRONG FILE LOADED!", SwingConstants.CENTER), BorderLayout.CENTER);
                        remindDialog.add(t);
                        remindDialog.setSize(200,120);
                        remindDialog.setVisible(true);
                    } catch (ClassNotFoundException ex) {
                    }
                    drawPanel.requestFocusInWindow();
                }
            });
        }};
        JMenuItem saveItem = new JMenuItem("Save Project"){{
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (NewShapeControl.getSelectedShape() != null)
                        NewShapeControl.cancelSelectedShape();
                    String saveFileName = buildFileDialog("Save", FileDialog.SAVE);
                    try {
                        FileOutputStream saveFileStream = new FileOutputStream(saveFileName);
                        ObjectOutputStream objectStream = new ObjectOutputStream(saveFileStream);
                        objectStream.writeObject(AllShapes.getList());
                        saveFileStream.flush();
                    } catch (FileNotFoundException ex) {
                    } catch (IOException ex) {
                    }
                    drawPanel.requestFocusInWindow();
                }
            });
        }};

        JMenu helpMenu = new JMenu("Help");

        JMenuItem helpItem = new JMenuItem("Help"){{
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JDialog remindDialog = new JDialog();
                    remindDialog.setTitle("Help");
                    JPanel t = new JPanel();
                    t.setLayout(new BorderLayout());
                    JButton ok = new JButton("OK");
                    ok.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            remindDialog.dispose();
                        }
                    });
                    t.add(ok, BorderLayout.SOUTH);
                    String line1 = "    w/t 键表示 变粗/变细";
                    String line2 = "    l/s 键表示 变长/变短";
                    String line3 = "    d   键表示 删除图形";
                    String line4 = "    更多信息可以参考报告";
                    String showText = "<html><body>" + line1 + "<br>" + line2 + "<br>" + line3 + "<br>" + line4 + "<body></html>";
                    t.add(new JLabel(showText, SwingConstants.CENTER), BorderLayout.CENTER);
                    remindDialog.add(t);
                    remindDialog.setSize(300, 180);
                    remindDialog.setVisible(true);
                }
            });
        }};
        JMenuItem aboutItem = new JMenuItem("About"){{
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JDialog remindDialog = new JDialog();
                    remindDialog.setTitle("Help");
                    JPanel t = new JPanel();
                    t.setLayout(new BorderLayout());
                    JButton ok = new JButton("OK");
                    ok.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            remindDialog.dispose();
                        }
                    });
                    t.add(ok, BorderLayout.SOUTH);
                    String line1 = "miniCAD 2022.1.1 (Ultimate Edition)";
                    String line2 = "Released on November 29, 2022";
                    String line3 = "Author: 赵小迪";
                    String showText = "<html><body>" + line1 + "<br>" + line2 + "<br>" + line3 + "<body></html>";
                    t.add(new JLabel(showText, SwingConstants.CENTER), BorderLayout.CENTER);
                    remindDialog.add(t);
                    remindDialog.setSize(300, 180);
                    remindDialog.setVisible(true);
                }
            });
        }};
        helpMenu.add(helpItem);
        helpMenu.add(aboutItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        menu.add(fileMenu);
        menu.add(helpMenu);
        setJMenuBar(menu);
        setVisible(true);
    }

    private String buildFileDialog(String name, int mode){
        FileDialog dialog = new FileDialog(this, name, mode);
        dialog.setFile("project.miniCAD");
        dialog.setVisible(true);
        return dialog.getDirectory() + dialog.getFile();
    }

    private void setColorButton(){
        int i;
        for (i=0; i<allColor.length; i++){
            JButton btn = new JButton();
            btn.setBackground(allColor[i]);
            Color thisColor = allColor[i];
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (NewShapeControl.getCurrentState() == NewShapeControl.State.isSelected){
                        NewShapeControl.setSelectedColor(thisColor);
                    }
                    else if (NewShapeControl.getCurrentState() != NewShapeControl.State.select){
                        NewShapeControl.setCurrentColor(thisColor);
                    }
                    repaint();
                    drawPanel.requestFocusInWindow();
                }
            });
            buttonColorPanel.add(btn);
        }
    }

    private void setOtherButton(String name, NewShapeControl.State state){
        JButton btn = new JButton(name);
        btn.addActionListener(new ActionListener() {
            // Only set state when button was pressed, draw the shape when the mouse was moved
            @Override
            public void actionPerformed(ActionEvent e) {
                NewShapeControl.setCurrentState(state);
                // this is needed as keyboard
                drawPanel.requestFocusInWindow();
                if (name.equals("Text")){
                    textDialogBox();
                }
                if (name.equals("Cancel Select")){
                    NewShapeControl.cancelSelectedShape();
                    repaint();
                }
            }
        });
        buttonPanel.add(btn);
    }

    private void textDialogBox(){
        JDialog textDialog = new JDialog();
        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancel");
        JTextField inputText = new JTextField();

        JPanel b = new JPanel();
        b.setLayout(new GridLayout(1, 2, 5, 5));
        JPanel t = new JPanel();
        t.setLayout(new GridLayout(1, 2, 5, 5));
        b.add(ok);
        b.add(cancel);
        t.add(new JLabel("Input Your Text"));
        t.add(inputText);

        textDialog.setTitle("Input Text");
        textDialog.setSize(400, 120);
        textDialog.setLayout(new BorderLayout());
        textDialog.add(t, BorderLayout.CENTER);
        textDialog.add(b, BorderLayout.SOUTH);
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewShapeControl.setBufferText(inputText.getText());
                textDialog.dispose();
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textDialog.dispose();
            }
        });
        textDialog.setVisible(true);
    }
}
