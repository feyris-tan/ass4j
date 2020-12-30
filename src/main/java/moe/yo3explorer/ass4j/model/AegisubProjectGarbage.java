package moe.yo3explorer.ass4j.model;

public class AegisubProjectGarbage {
    private int activeLine;
    private String lastStyleStorage;
    private String audioFile;
    private String videoFile;
    private double videoArValue;
    private int scrollPosition;
    private int videoPosition;

    public void setActiveLine(int activeLine) {
        this.activeLine = activeLine;
    }

    public int getActiveLine() {
        return activeLine;
    }

    public void setLastStyleStorage(String lastStyleStorage) {
        this.lastStyleStorage = lastStyleStorage;
    }

    public String getLastStyleStorage() {
        return lastStyleStorage;
    }

    public void setAudioFile(String audioFile) {
        this.audioFile = audioFile;
    }

    public String getAudioFile() {
        return audioFile;
    }

    public void setVideoFile(String videoFile) {
        this.videoFile = videoFile;
    }

    public String getVideoFile() {
        return videoFile;
    }

    public void setVideoArValue(double videoArValue) {
        this.videoArValue = videoArValue;
    }

    public double getVideoArValue() {
        return videoArValue;
    }

    public void setScrollPosition(int scrollPosition) {
        this.scrollPosition = scrollPosition;
    }

    public int getScrollPosition() {
        return scrollPosition;
    }

    public void setVideoPosition(int videoPosition) {
        this.videoPosition = videoPosition;
    }

    public int getVideoPosition() {
        return videoPosition;
    }
}
