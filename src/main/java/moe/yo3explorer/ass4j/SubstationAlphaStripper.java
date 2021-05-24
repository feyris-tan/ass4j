package moe.yo3explorer.ass4j;

import moe.yo3explorer.ass4j.model.Event;
import moe.yo3explorer.ass4j.model.EventType;
import moe.yo3explorer.ass4j.model.Timecode;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class SubstationAlphaStripper {
    private SubstationAlphaStripper() {}

    public static void strip(@NotNull SubtitleFile subtitleFile)
    {
        strip(subtitleFile.events);
    }

    public static void strip(@NotNull List<Event> events)
    {
        for (Event event : events) {
            strip(event);
        }

        HashMap<String, Timecode> lastSeen = new HashMap<>();
        for (int i = 0; i < events.size() - 1; i++)
        {
            Event l = events.get(i);
            if (l.getEventType() != EventType.DIALOGUE)
            {
                events.remove(l);
                i--;
                continue;
            }
            if (lastSeen.containsKey(l.getText()))
            {
                Timecode lastOccur = lastSeen.get(l.getText());
                long diff2 = l.getStart().toMillis() - lastOccur.toMillis();
                lastSeen.remove(l.getText());
                lastSeen.put(l.getText(),l.getStart());
                if (diff2 < 1000)
                {
                    events.remove(l);
                    i--;
                    continue;
                }
            }
            else
            {
                lastSeen.put(l.getText(),l.getStart());
            }
            if (looksLikeAssDraw(l.getText()))
            {
                events.remove(l);
                i--;
                continue;
            }
        }
    }

    public static void strip(@NotNull Event event)
    {
        event.setText(strip(event.getText()));
    }

    @NotNull
    public static String strip(String input)
    {
        input = input.replace("\\N"," ");
        StringBuilder outputBuilder = new StringBuilder();
        int stack = 0;
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++)
        {
            if (chars[i] == '{')
                stack++;
            else if (chars[i] == '}')
                stack--;
            else if (stack == 0)
                outputBuilder.append(chars[i]);

            assert stack >= 0;
        }
        return outputBuilder.toString();
    }

    private static boolean looksLikeAssDraw(@NotNull String s)
    {
        String[] s1 = s.split(" ");
        for (String s2 : s1) {
            if (s2.equals("m"))
                continue;
            if (s2.equals("l"))
                continue;
            if (looksNumeric(s2))
                continue;
            return false;
        }
        return true;
    }

    private static boolean looksNumeric(@NotNull String s)
    {
        for (char c : s.toCharArray()) {
            if (c == '-')
                continue;
            if (Character.isDigit(c))
                continue;
            return false;
        }
        return true;
    }
}
