package libpianotranscription.midi;

public class PedalEvent {
    private final float onsetTime;
    private final float offsetTime;

    public PedalEvent(float onsetTime, float offsetTime) {
        this.onsetTime = onsetTime;
        this.offsetTime = offsetTime;
    }

    public float getOffsetTime() {
        return offsetTime;
    }

    public float getOnsetTime() {
        return onsetTime;
    }
}
