package moe.yo3explorer.ass4j.model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class Color
{
    private final int[] values;

    public Color(@NotNull String readSegment) {
        if (readSegment.startsWith("&H"))
            readSegment = readSegment.substring(2);
        values = hexStringToIntArray(readSegment);
    }

    private int getA() { return values[0]; }
    private int getR() { return values[1]; }
    private int getG() { return values[2]; }
    private int getB() { return values[3]; }

    private void setA(int i)
    {
        ensureRange(i);
        values[0] = i;
    }

    private void setR(int i)
    {
        ensureRange(i);
        values[1] = i;
    }

    private void setG(int i)
    {
        ensureRange(i);
        values[2] = i;
    }

    private void setB(int i)
    {
        ensureRange(i);
        values[3] = i;
    }

    private void ensureRange(int i)
    {
        if (i < 0)
            throw new IllegalArgumentException("must be at least 0!");
        if (i > 255)
            throw new IllegalArgumentException("must be at maximum 255!");
    }

    @NotNull
    public static int[] hexStringToIntArray(@NotNull String s) {
        int len = s.length();
        int[] data = new int[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    @NotNull
    @Contract("_ -> new")
    public static String bytesToHex(@NotNull int[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public String serialize()
    {
        return "&H" + bytesToHex(values);
    }

    @Override
    public String toString() {
        return "Color{" +
                "a=" + getA() +
                ",r=" + getR() +
                ",g=" + getG() +
                ",b=" + getB() +
                ",hex=" + bytesToHex(values) +
                '}';
    }
}
