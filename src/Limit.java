public class Limit {

    final private Board b;
    private int count;

    public Limit(Board b) {
        this.b = b;
    }

    public boolean setCount(int n, int WallCount, int StarCount) {
        boolean output;
        if (n < 0 || n > b.getWidth() * b.getLength() - 2 - WallCount - StarCount) {
            output = false;
        } else {
            this.count = n;
            output = true;
        }
        return output;
    }

    public int getCount() {
        return this.count;
    }

}