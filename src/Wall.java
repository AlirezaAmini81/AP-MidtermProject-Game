public class Wall {
    private int count;
    final private Board b;
    public Wall(Board b) {
        this.b = b;
    }

    public boolean setCount(int n, int StarCount) {
        boolean output;
        if (n < 0 || n > b.getWidth() * b.getLength() - 2 - StarCount) {
            output = false;
        }
        else{
            this.count = n;
            output = true;
        }
        return output;
    }
    public int getCount() {
        return this.count;
    }
}