public class Stat {
    private int current;
    private int max;

    public Stat(int current, int max) {
        this.current = current;
        this.max = max;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = Math.min(current, max);
        if (this.current < 0) this.current = 0;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void add(int amount) {
        this.current = Math.min(this.current + amount, max);
    }

    public void subtract(int amount) {
        this.current = Math.max(this.current - amount, 0);
    }

    public boolean isMax() {
        return current >= max;
    }

    public boolean isEmpty() {
        return current <= 0;
    }
}