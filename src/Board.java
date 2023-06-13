public class Board {

    public Element[][] board;
    private int width;
    private int length;

    public Board(int length, int width) {
        setZel(length, width);
        board = new Element[length][width];
        for (int i = 0; i < this.length; i++) {
            for (int j = 0; j < this.width; j++) {
                board[i][j] = new Element();
            }
        }
    }

    public boolean setZel(int l, int w) {
        boolean output;
        if (l * w < 3) {
            output = false;
        } else {
            this.length = l;
            this.width = w;
            output = true;
        }
        return output;
    }

    public int getWidth() {
        return this.width;
    }

    public int getLength() {
        return this.length;
    }

}

class Element {
    String type = "empty";
    int limit = 0;

    void setType(String type) {
        this.type = type;
    }

}

