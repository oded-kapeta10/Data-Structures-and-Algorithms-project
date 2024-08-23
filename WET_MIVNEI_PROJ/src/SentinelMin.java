public class SentinelMin extends RunnerID {
    @Override
    public boolean isSmaller(RunnerID other) {
        return true;
    }

    @Override
    public String toString() {
        return "-∞";
    }
}
