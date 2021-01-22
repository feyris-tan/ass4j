package moe.yo3explorer.ass4j;

import moe.yo3explorer.ass4j.model.Timecode;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class TimecodeComparator implements Comparator<Timecode> {
    @Override
    public int compare(@NotNull Timecode o1, @NotNull Timecode o2) {
        int h = Integer.compare(o1.getHour(),o2.getHour());
        if (h != 0)
            return h;

        int m = Integer.compare(o1.getMinute(),o2.getMinute());
        if (m != 0)
            return m;

        int s = Integer.compare(o1.getSecond(),o2.getSecond());
        if (s != 0)
            return s;

        return Integer.compare(o1.getFraction(),o2.getFraction());
    }
}
