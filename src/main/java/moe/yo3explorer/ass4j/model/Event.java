package moe.yo3explorer.ass4j.model;

public class Event {
    private EventType eventType;
    private int layer;
    private Timecode start;
    private Timecode end;
    private Style style;
    private String speaker;
    private int marginL;
    private int marginR;
    private int marginV;
    private String effect;
    private String text;

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public int getLayer() {
        return layer;
    }

    public void setStart(Timecode start) {
        this.start = start;
    }

    public Timecode getStart() {
        return start;
    }

    public void setEnd(Timecode end) {
        this.end = end;
    }

    public Timecode getEnd() {
        return end;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public Style getStyle() {
        return style;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setMarginL(int marginL) {
        this.marginL = marginL;
    }

    public int getMarginL() {
        return marginL;
    }

    public void setMarginR(int marginR) {
        this.marginR = marginR;
    }

    public int getMarginR() {
        return marginR;
    }

    public void setMarginV(int marginV) {
        this.marginV = marginV;
    }

    public int getMarginV() {
        return marginV;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getEffect() {
        return effect;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventType=" + eventType +
                ", start=" + start +
                ", speaker='" + speaker + '\'' +
                ", effect='" + effect + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
