public class Star {
    private int count;
    final private Board b;

    public Star(Board b) {
        this.b = b;
    }

    public boolean setCount(int n) {
        boolean output;
        if (n <= 0 || n > b.getWidth() * b.getLength() - 2) {
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
