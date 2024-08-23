public class SentinelMax extends RunnerID{
    @Override
    public boolean isSmaller(RunnerID other) {
        return false;
    }

    @Override
    public String toString() {
        return "∞";
    }
}
