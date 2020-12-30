package moe.yo3explorer.ass4j;

import moe.yo3explorer.ass4j.model.Color;
import moe.yo3explorer.ass4j.model.Encoding;
import moe.yo3explorer.ass4j.model.Timecode;

import java.util.Arrays;
import java.util.Optional;

public class SegmentableStringContainer
{
    private String contained;

    public SegmentableStringContainer(String contained) {
        this.contained = contained;
    }

    public String readSegment()
    {
        String output = "";
        while (!contained.startsWith(","))
        {
            if (contained.equals(""))
                return output;
            char c = contained.charAt(0);
            output += c;
            contained = contained.substring(1);
        }
        contained = contained.substring(1);
        return output;
    }

    public int readSegmentAsInt()
    {
        return Integer.parseInt(readSegment());
    }

    public String getRemains()
    {
        String result =  contained;
        contained = "";
        return result;
    }

    @Override
    public String toString() {
        return "SegmentableStringContainer{" +
                "contained='" + contained + '\'' +
                '}';
    }

    public Color readSegmentAsColor() {
        return new Color(readSegment());
    }

    public boolean readSegmentAsBoolean() {
        String s = readSegment();
        switch (s)
        {
            case "-1": return true;
            case "0": return false;
            default:
                throw new AssException("Don't know what that means: " + s);
        }
    }

    public double readSegmentAsDouble() {
        return Double.parseDouble(readSegment());
    }

    public Encoding readSegmentAsEncoding() {
        int i = readSegmentAsInt();
        Optional<Encoding> first = Arrays.stream(Encoding.values()).filter(x -> x.getId() == i).findFirst();
        if (first.isEmpty())
            throw new AssException("Unknown encoding ID: " + i);
        return first.get();
    }

    public Timecode readSegmentAsTimecode()
    {
        return new Timecode(readSegment());
    }
}
