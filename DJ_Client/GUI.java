package DJ_Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.net.URL;

public class GUI implements ActionListener {
    private int likeClicks = 0;
    private int dislikeClicks = 0;
    private JLabel label = new JLabel("Number of likes:  0     ");
    private JLabel label2 = new JLabel("Number of dislikes:  0     ");
    private JLabel label3;
    private JLabel songs = new JLabel("        SONG QUEUE STAND IN      ");
    private JLabel space = new JLabel("         ");
    private JFrame frame = new JFrame();
    private JButton button = new JButton();
    private JButton button1 = new JButton();
    // private ImageIcon image;


    public GUI() {

        // image = new ImageIcon(getClass().getResource("money.png"));
        // label3 = new JLabel(image);


        // the clickable button
        button = new JButton("Like");
        button.addActionListener(this);

        button1 = new JButton("Dislike");
        button1.addActionListener(this);

        // the panel with the button and text
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new FlowLayout());
        panel.add(space);
        //panel.add(label3);
        panel.add(button);
        panel.add(button1);
        panel.add(space);
        panel.add(label);
        panel.add(label2);
        panel.add(space);
        panel.add(songs);

        // set up the frame and display it
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Caes the Music");
        frame.pack();
        frame.setVisible(true);
    }

    // process the button clicks
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button){
            likeClicks++;
            label.setText("\nNumber of likes:  " + likeClicks + "    ");
        }else{
            dislikeClicks++;
            label2.setText("\nNumber of dislikes:  " + dislikeClicks + "    ");
        }

    }

    // create one Frame
    public static void main(String[] args) {
        new GUI();
    }
}

