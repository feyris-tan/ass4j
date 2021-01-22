package moe.yo3explorer.ass4j;

import moe.yo3explorer.ass4j.model.Event;

import java.util.Comparator;

public class EventByTimecodeComparator implements Comparator<Event> {
    private static TimecodeComparator timecodeComparer;
    @Override
    public int compare(Event o1, Event o2) {
        if (timecodeComparer == null)
            timecodeComparer = new TimecodeComparator();

        return timecodeComparer.compare(o1.getStart(),o2.getStart());
    }
}
