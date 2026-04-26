public class Stat {
    private int current;
    private int max;

    public Stat(int current, int max) {
        this.max = Math.max(0, max);
        this.current = Math.max(0, Math.min(current, this.max));
    }

    public int getCurrent() {
        return current;
    }

    public int getMax() {
        return max;
    }

    public void setCurrent(int current) {
        this.current = Math.max(0, Math.min(current, max));
    }

    public void setMax(int max) {
        this.max = Math.max(0, max);
        if (current > this.max) {
            current = this.max;
        }
    }

    public void add(int amount) {
        setCurrent(current + amount);
    }

    public void subtract(int amount) {
        setCurrent(current - amount);
    }

    public void restore() {
        current = max;
    }

    public boolean isEmpty() {
        return current <= 0;
    }

    @Override
    public String toString() {
        return "[" + current + "/" + max + "]";
    }
}
