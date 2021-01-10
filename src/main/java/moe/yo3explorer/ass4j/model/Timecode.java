package moe.yo3explorer.ass4j.model;

import moe.yo3explorer.ass4j.SubtitleException;
import org.jetbrains.annotations.NotNull;

public class Timecode
{
    public Timecode(@NotNull String timecode, @NotNull TimecodeFormat format)
    {
        switch (format)
        {
            case ADVANCED_SUBSTATION:
                String[] split = timecode.split(":");
                hour = Integer.parseInt(split[0]);
                minute = Integer.parseInt(split[1]);
                split = split[2].split("\\.");
                second = Integer.parseInt(split[0]);
                fraction = Integer.parseInt(split[1]);
                break;
            case WEBVTT:
                String[] split2 = timecode.split(":");
                hour = Integer.parseInt(split2[0]);
                minute = Integer.parseInt(split2[1]);
                split2 = split2[2].split("\\.");
                second = Integer.parseInt(split2[0]);
                fraction = Integer.parseInt(split2[1]);
                fraction /= 10;
                break;
            default:
                throw new SubtitleException(String.format("Unknown timecode format: " + format));
        }
        this.format = format;
    }

    public Timecode(int hour, int minute, int second, int fraction, TimecodeFormat format) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.fraction = fraction;
        this.format = format;
    }

    private int hour;
    private int minute;
    private int second;
    private int fraction;
    private TimecodeFormat format;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        ensureRange(0,59,minute);
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        ensureRange(0,59,second);
        this.second = second;
    }

    public int getFraction() {
        return fraction;
    }

    public void setFraction(int fraction) {
        ensureRange(0,99,fraction);
        this.fraction = fraction;
    }

    public void shiftByOneSecond()
    {
        second++;
        if (second == 60)
        {
            second = 0;
            minute++;
        }
        if (minute == 60)
        {
            minute = 0;
            hour++;
        }
    }

    public void unshiftByOneSecond()
    {
        second--;
        if (second == -1)
        {
            second = 59;
            minute--;
        }
        if (minute == -1)
        {
            minute = 59;
            hour--;
        }
        if (hour == -1)
        {
            fraction = 0;
            second = 0;
            minute = 0;
            hour = 0;
        }
    }

    private void ensureRange(int min, int max, int actual)
    {
        if (actual > max)
            throw new IllegalArgumentException(String.format("The value should be at least %d and at maximum %d. You specified %d"));
        if (actual < min)
            throw new IllegalArgumentException(String.format("The value should be at least %d and at maximum %d. You specified %d"));
    }

    public long toMillis()
    {
        long result = 0;
        result += (fraction * 10);
        result += (second * 1000);
        result += (minute * 60 * 1000);
        result += (hour * 60 * 60 * 1000);
        return result;
    }

    public TimecodeFormat getFormat() {
        return format;
    }

    public void setFormat(TimecodeFormat format) {
        this.format = format;
    }

    @Override
    public String toString() {
        switch (format)
        {
            case ADVANCED_SUBSTATION:
                return String.format("%d:%02d:%02d.%02d",hour,minute,second,fraction);
            case WEBVTT:
                return String.format("%02d:%02d:%02d,%03d",hour,minute,second,fraction * 10);
            default:
                throw new SubtitleException("Unknown timecode format: " + format.toString());
        }
    }
}
