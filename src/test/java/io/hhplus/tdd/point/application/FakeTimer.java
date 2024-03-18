package io.hhplus.tdd.point.application;

public class FakeTimer implements Timer{
    private long settingTime;
    public FakeTimer() {
    }
    public void setTime(long time) {
        this.settingTime = time;
    }
    @Override
    public long getCurrentTime() {
        return this.settingTime;
    }
}
