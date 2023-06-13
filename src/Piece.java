import java.util.ArrayList;
import javax.swing.*;

public class Piece {
    private int x;
    private int y;
    private Icon color;
    private final Board b;
    public Piece opponent;
    private int stars = 0;
    ArrayList limits = new ArrayList<Integer>();

    public Piece(int x, int y, Board board) {
        this.x = x;
        this.y = y;
        this.b = board;
        this.b.board[x][y].setType("player1");
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getStars() {
        return this.stars;
    }

    Boolean validMove(int x2, int y2) {
        if (this.b.board[x2][y2].type.equals("player1")) return false;
        int x1 = this.x, y1 = this.y;
        int di = x2 - x1, dj = y2 - y1;
        if (di * dj != 0 || di + dj == 0) return false;
        if (this.limits.size() != 0 && Math.abs(di + dj) >  Integer.parseInt((String) this.limits.get(0)))
            return false;
        int i, j;
        if (di == 0) i = 0;
        else i = Math.abs(di) / di;
        if (dj == 0) j = 0;
        else j = Math.abs(dj) / dj;
        for (; x1 != x2 + i || y1 != y2 + j; x1 += i, y1 += j) {
            if (this.b.board[x1][y1].type.equals("wall"))
                return false;
        }
        if (this.limits.size() != 0)
            this.limits.remove(0);
        return true;
    }

    void starAndLimitCollector(int x2, int y2) {
        int x1 = this.x, y1 = this.y;
        int di = x2 - x1, dj = y2 - y1;
        int i, j;
        if (di == 0) i = 0;
        else i = Math.abs(di) / di;
        if (dj == 0) j = 0;
        else j = Math.abs(dj) / dj;
        for (; x1 != x2 + i || y1 != y2 + j; x1 += i, y1 += j) {
            if (this.b.board[x1][y1].type.equals("star")) {
                this.b.board[x1][y1].setType("empty");
                setStars(getStars() + 1);
            }
            if (this.b.board[x1][y1].type.equals("limit")) {
                this.opponent.limits.add(BoardShow.button[x1][y1].getText());
                this.b.board[x1][y1].setType("empty");
            }
        }
    }

    void move(int i,int j) {
        starAndLimitCollector(i, j);
        this.b.board[this.x][this.y].setType("empty");
        this.x = i;
        this.y = j;
        this.b.board[this.x][this.y].setType("player1");

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setColor(Icon color) {
        this.color = color;
    }

    public Icon getColor() {
        return color;
    }
}
