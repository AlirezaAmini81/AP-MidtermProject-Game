import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

class BoardShow extends JFrame implements ActionListener{
    static JButton[][] button;
    JFrame f; private String type;
    BorderLayout r = new BorderLayout();
    Icon star,empty,wall,limit,blue,red;
    private int limiter, counter;
    Label label, situation ;
    final private Board b;
    Piece p1,p2;
    BoardShow(Board board,int x, int y, int limiter, String type){
        this.b = board;
        label = new Label();
        label.setBounds(50,20,240,40);
        label.setFont(new Font("Serif",Font.PLAIN, 22));
        situation = new Label();
        situation.setBounds(50,(y-1) * 60 + 200,200,40);
        situation.setFont(new Font("Serif",Font.PLAIN, 20));
        situation.setBackground(Color.ORANGE);
        add(situation);
        star = new ImageIcon("star.png");
        wall = new ImageIcon("wall.png");
        limit = new ImageIcon("limit.png");
        empty = new ImageIcon("empty.jpg");
        blue = new ImageIcon("blue.jpg");
        red = new ImageIcon("red.jpg");
        CreateButton(x,y);
        this.limiter = limiter;
        setType(type);
        add(label);
        setLayout(r);
        setSize(x*60 + 200,y*60 + 320);
        setVisible(true);
        getContentPane().setBackground(Color.orange);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void CreateButton(int x, int y){
        button = new JButton[x][y];
        for(int i=0; i<x; i++){
            for(int j=0; j<y ; j++){
                button[i][j] = new JButton();
                button[i][j].setBounds(50 + i*62,100 + j*62,60,60);
                button[i][j].setIcon(empty);
                add(button[i][j]);
                button[i][j].addActionListener(this);
            }
        }
    }

    public void setLimiter(int limiter) {
        this.limiter = limiter;
    }

    public void setType(String type) {
        this.type = type;
        label.setText("Choose the " + type + "s !");
    }

    public void ChooseComponent(int i, int j, Icon IconType, String type){
        button[i][j].setIcon(IconType);
        this.b.board[i][j].setType(type);
        if(type.equals("limit")){
            while(true) {
                String l = JOptionPane.showInputDialog(f, " Enter the Value of the Limiter !");
                try {
                    int ad = Integer.parseInt(l);
                    if(ad<=0) {
                        JOptionPane.showMessageDialog(f, "invalid input !");

                    }
                    else {
                        button[i][j].setText(l);
                        button[i][j].setVerticalTextPosition(SwingConstants.TOP);
                        button[i][j].setHorizontalTextPosition(SwingConstants.CENTER);
                        this.b.board[i][j].limit = Integer.parseInt(l);
                        break;
                    }
                } catch (NumberFormatException ignored) {
                    JOptionPane.showMessageDialog(f,"invalid input !");
                }
            }
        }
        counter++;
        if(counter == limiter){
            f = new JFrame();
            JOptionPane.showMessageDialog(f,"Great !");
            counter = 0;
            if(type.equals("limit")){
                setType("playerPosition");
            }
            else{
                Main.tmp.setVisible(true);
                setVisible(false);
            }
        }
    }
    public void Move(int i, int j, Piece player) {
        if (!player.validMove(i, j)) {
            f = new JFrame();
            JOptionPane.showMessageDialog(f, "Unavailable position. Try again:!");
        }
        else {
            button[player.getX()][player.getY()].setIcon(empty);
            player.move(i, j);
            button[i][j].setIcon(player.getColor());
            button[i][j].setText(null);
            situation.setText("P1 stars: "+p1.getStars()+"     P2 stars: "+p2.getStars());
            counter ++;
            if(player.opponent.limits.size() == 0) {
                label.setText("Player" + (counter % 2 + 1) + "'s turn  no limit  ");
            }
            else{
                label.setText("Player" + (counter % 2 + 1) + "'s turn  limit = "+ player.opponent.limits.get(0));
            }
            refresh();
        }
        if (player.getStars() + player.opponent.getStars() == limiter) {
            f = new JFrame();
            if (p1.getStars() > p2.getStars()) {
                JOptionPane.showMessageDialog(f, "Game Over! The winner is player1");
            } else if(p1.getStars() < p2.getStars()) {
                JOptionPane.showMessageDialog(f, "Game Over! The winner is player2");
            }
            else{
                JOptionPane.showMessageDialog(f, "Game Over! Tie");
            }
            setVisible(false);
            System.exit(3);
        }
    }
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < button.length; i++) {
            for (int j = 0; j < button[i].length; j++) {
                if (e.getSource() == button[i][j] && type.equals("star")) {
                    ChooseComponent(i, j, star, "star");
                }
                if (e.getSource() == button[i][j] && type.equals("wall") && button[i][j].getIcon() == empty) {
                    ChooseComponent(i, j, wall, "wall");
                }
                if (e.getSource() == button[i][j] && type.equals("limit") && button[i][j].getIcon() == empty) {
                    ChooseComponent(i, j, limit, "limit");
                }
                if (e.getSource() == button[i][j] && type.equals("playerPosition") && button[i][j].getIcon() == empty) {
                    if (counter == 0) {
                        button[i][j].setIcon(blue);
                        p1 = new Piece(i, j, b);
                        p1.setColor(blue);
                        counter++;
                    } else {
                        button[i][j].setIcon(red);
                        p2 = new Piece(i, j, b);
                        p2.setColor(red);
                        p1.opponent = p2;
                        p2.opponent = p1;
                        f = new JFrame();
                        JOptionPane.showMessageDialog(f, "Great !");
                        counter = 0;
                        Main.tmp.setVisible(true);
                        setVisible(false);
                        label.setText(" Player 1 : Enter Next Position");
                    }
                }
                if (e.getSource() == button[i][j] && type.equals("play")) {
                    if (counter % 2 == 0) {
                        Move(i, j, p1);
                    } else {
                        Move(i, j, p2);
                    }
                }
            }
        }
    }

    void refresh(){
        for (int i = 0; i < button.length; i++) {
            for (int j = 0; j < button[i].length; j++) {
                if(b.board[i][j].type.equals("empty")){
                    button[i][j].setIcon(empty);
                    button[i][j].setText(null);
                }
            }
        }
    }
}