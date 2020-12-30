package moe.yo3explorer.ass4j.model;

public enum EventType {
    DIALOGUE("Dialogue"),
    COMMENT("Comment");

    private final String dialogue;

    EventType(String dialogue) {

        this.dialogue = dialogue;
    }

    public String getDialogue() {
        return dialogue;
    }
}
