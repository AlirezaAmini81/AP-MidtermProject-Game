import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.JOptionPane;


public class ScreenShow extends JFrame implements ActionListener{
    JButton next, play ;int s = 0;JFrame f;BoardShow boardshow;
    JTextField t1,t2;Label l1;
    Star star;
    Wall wall;
    Limit limit;
    Board b;
    public ScreenShow(){
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        t1 = new JTextField(null);
        t1.setBounds(100,160,100,40);
        t2 = new JTextField();
        t2.setBounds(100,210,100,40);
        next = new JButton("Next");
        next.setBounds(300,300,60,30);
        l1 = new Label("Enter the length and width of the board:");
        l1.setBackground(Color.yellow);
        l1.setBounds(100,40,320,60);
        l1.setFont(new Font("Serif",Font.PLAIN, 20));
        play = new JButton("Let's Play !");
        play.setBounds(130,100,300,200);
        play.setBackground(Color.green);
        play.setFont(new Font("Serif",Font.PLAIN, 40));
        add(t1);add(t2);add(l1);add(next);play.addActionListener(this);
        next.addActionListener(this);
        setSize(600,500);
        setLayout(null);
        setVisible(true);
        getContentPane().setBackground(Color.cyan);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void actionPerformed(ActionEvent e){

        if(e.getSource() == play){
            boardshow.setType("play");
            boardshow.setVisible(true);
            boardshow.situation.setBackground(Color.LIGHT_GRAY);
            boardshow.label.setText("Player 1's turn no limit");
            boardshow.situation.setText("P1 stars : " + boardshow.p1.getStars() + "   P2 stars : "+ boardshow.p2.getStars());
            this.setVisible(false);
            boardshow.setLimiter(star.getCount());
        }
        if(e.getSource() == next && s == 0){
            try {
                int length = Integer.parseInt(t1.getText());
                int width = Integer.parseInt(t2.getText());
                t1.setText(null);
                b = new Board(length, width);
                if(!b.setZel(length, width)){
                    f = new JFrame();
                    JOptionPane.showMessageDialog(f,"This size is unavailable. Try again:");
                }
                else{
                    t2.setVisible(false);
                    l1.setText("Enter the number of stars:");
                    s = 1;
                }
            } catch (NumberFormatException ignored) {
            }
        }
        if(e.getSource() == next && s == 1){
            try {
                int number = Integer.parseInt(t1.getText());
                t1.setText(null);
                star = new Star(b);
                if (!star.setCount(number)) {
                    f = new JFrame();
                    JOptionPane.showMessageDialog(f, "This number is unavailable. Try again:");
                } else {
                    this.setVisible(false);
                    boardshow = new BoardShow(b, b.getLength(), b.getWidth(), star.getCount(), "star");
                    s = 2;
                    l1.setText("Enter the number of walls:");
                }
            } catch (NumberFormatException ignored) {
            }
        }
        if(e.getSource() == next && s == 2){
            try {
                int number = Integer.parseInt(t1.getText());
                t1.setText(null);
                wall = new Wall(b);
                if(!wall.setCount(number, star.getCount())){
                    JOptionPane.showMessageDialog(f,"This number is unavailable. Try again:");
                }
                else if(number==0){
                    l1.setText("Enter the number of limiters:");
                    s=3;
                }
                else{
                    this.setVisible(false);
                    boardshow.setType("wall");
                    boardshow.setLimiter(wall.getCount());
                    boardshow.setVisible(true);
                    l1.setText("Enter the number of limiters:");
                    s = 3;
                }
            } catch (NumberFormatException ignored) {
            }
        }
        if(e.getSource() == next && s == 3){
            try {
                int number = Integer.parseInt(t1.getText());
                limit = new Limit(b);
                if (!limit.setCount(number, wall.getCount(), star.getCount())) {
                    JOptionPane.showMessageDialog(f, "This number is unavailable. Try again:");
                } else {
                    if(number != 0) {
                        this.setVisible(false);
                        boardshow.setType("limit");
                        boardshow.setLimiter(limit.getCount());
                    }
                    else boardshow.setType("playerPosition");
                    this.setVisible(false);
                    boardshow.setVisible(true);
                    t1.setVisible(false);
                    l1.setVisible(false);
                    next.setVisible(false);
                    add(play);
                }
            }catch (NumberFormatException ignored){
            }
        }

    }
}
