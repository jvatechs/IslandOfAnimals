package entities;

public class Plants extends Entity{
    private int currentCount;
    private static final int MAX_WEIGHT = 1;
    private static final int MAX_COUNT = 200;

    public int getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    public Plants(int currentCount) {
        this.currentCount = currentCount;
    }

    public int getMaxCount() {
        return MAX_COUNT;
    }
    public int getMaxWeight() {
        return MAX_WEIGHT;
    }

    @Override
    public String toString() {
        return "Plants{" +
                "currentCount=" + currentCount +
                '}';
    }
}
