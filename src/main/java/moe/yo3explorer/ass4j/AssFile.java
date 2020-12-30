package moe.yo3explorer.ass4j;

import moe.yo3explorer.ass4j.model.AegisubProjectGarbage;
import moe.yo3explorer.ass4j.model.Event;
import moe.yo3explorer.ass4j.model.ScriptInfo;
import moe.yo3explorer.ass4j.model.Style;

import java.util.*;

public class AssFile {

    ArrayList<Style> styles;
    LinkedList<Event> events;
    AegisubProjectGarbage aegisubProjectGarbage;
    ScriptInfo scriptinfo;

    public List<Style> getStyles()
    {
        return Collections.unmodifiableList(styles);
    }

    public List<Event> getEvents()
    {
        return Collections.unmodifiableList(events);
    }

    public AegisubProjectGarbage getAegisubProjectGarbage() {
        return aegisubProjectGarbage;
    }

    public ScriptInfo getScriptinfo() {
        return scriptinfo;
    }
}
