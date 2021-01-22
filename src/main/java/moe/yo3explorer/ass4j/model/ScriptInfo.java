package moe.yo3explorer.ass4j.model;

public class ScriptInfo {

    private String sabbuVersion;
    private int scrollPosition;
    private int activeLine;

    public void autoSetReasonableDefaults()
    {
        wrapStyle = WrapStyle.SMART_WRAPPING_TOP_LINE;
    }

    private String title;
    private String scriptType;
    private WrapStyle wrapStyle;
    private boolean scaledBorderAndShadow;
    private moe.yo3explorer.ass4j.model.YCbCrMatrix YCbCrMatrix;
    private int playResX;
    private int playResY;
    private String originalScript;
    private String originalTiming;
    private boolean containsData;
    private String originalTranslation;
    private String originalEditing;
    private String synchPoint;
    private String scriptUpdatedBy;
    private String updateDetails;
    private Collisions collisions;
    private int playDepth;
    private double timer;
    private String videoAspectRatio;
    private double videoZoom;
    private double videoPosition;
    private String audioFile;
    private String videoFile;
    private String lastStyleStorage;

    public void setTitle(String title) {
        this.title = title;
        this.containsData = true;
    }

    public String getTitle() {
        return title;
    }

    public void setScriptType(String scriptType) {
        this.scriptType = scriptType;
        this.containsData = true;
    }

    public String getScriptType() {
        return scriptType;
    }

    public void setWrapStyle(WrapStyle wrapStyle) {
        this.wrapStyle = wrapStyle;
        this.containsData = true;
    }

    public WrapStyle getWrapStyle() {
        return wrapStyle;
    }

    public void setScaledBorderAndShadow(boolean scaledBorderAndShadow) {
        this.scaledBorderAndShadow = scaledBorderAndShadow;
        this.containsData = true;
    }

    public boolean getScaledBorderAndShadow() {
        return scaledBorderAndShadow;
    }

    public void setYCbCrMatrix(YCbCrMatrix yCbCrMatrix) {
        this.YCbCrMatrix = yCbCrMatrix;
        this.containsData = true;
    }

    public YCbCrMatrix getYCbCrMatrix() {
        return YCbCrMatrix;
    }

    public void setPlayResX(int playResX) {
        this.playResX = playResX;
        this.containsData = true;
    }

    public int getPlayResX() {
        return playResX;
    }

    public void setPlayResY(int playResY) {
        this.playResY = playResY;
        this.containsData = true;
    }

    public int getPlayResY() {
        return playResY;
    }

    public void setOriginalScript(String originalScript) {
        this.originalScript = originalScript;
        this.containsData = true;
    }

    public String getOriginalScript() {
        return originalScript;
    }

    public void setOriginalTiming(String originalTiming) {
        this.originalTiming = originalTiming;
        this.containsData = true;
    }

    public String getOriginalTiming() {
        return originalTiming;
    }

    public boolean isContainsData() {
        return containsData;
    }

    public void setOriginalTranslation(String originalTranslation) {
        this.originalTranslation = originalTranslation;
        this.containsData = true;
    }

    public String getOriginalTranslation() {
        return originalTranslation;
    }

    public void setOriginalEditing(String originalEditing) {
        this.originalEditing = originalEditing;
        this.containsData = true;
    }

    public String getOriginalEditing() {
        return originalEditing;
    }

    public void setSynchPoint(String synchPoint) {
        this.synchPoint = synchPoint;
        this.containsData = true;
    }

    public String getSynchPoint() {
        return synchPoint;
    }

    public void setScriptUpdatedBy(String scriptUpdatedBy) {
        this.scriptUpdatedBy = scriptUpdatedBy;
        this.containsData = true;
    }

    public String getScriptUpdatedBy() {
        return scriptUpdatedBy;
    }

    public void setUpdateDetails(String updateDetails) {
        this.updateDetails = updateDetails;
        this.containsData = true;
    }

    public String getUpdateDetails() {
        return updateDetails;
    }

    public void setCollisions(Collisions collisions) {
        this.collisions = collisions;
        this.containsData = true;
    }

    public Collisions getCollisions() {
        return collisions;
    }

    public void setPlayDepth(int playDepth) {
        this.playDepth = playDepth;
        this.containsData = true;
    }

    public int getPlayDepth() {
        return playDepth;
    }

    public void setTimer(double timer) {
        this.timer = timer;
        this.containsData = true;
    }

    public double getTimer() {
        return timer;
    }

    public void setVideoAspectRatio(String videoAspectRatio) {
        this.videoAspectRatio = videoAspectRatio;
        this.containsData = true;
    }

    public String getVideoAspectRatio() {
        return videoAspectRatio;
    }

    public void setVideoZoom(double videoZoom) {
        this.videoZoom = videoZoom;
        this.containsData = true;
    }

    public double getVideoZoom() {
        return videoZoom;
    }

    public void setVideoPosition(double videoPosition) {
        this.videoPosition = videoPosition;
        this.containsData = true;
    }

    public double getVideoPosition() {
        return videoPosition;
    }

    public void setAudioFile(String audioFile) {
        this.audioFile = audioFile;
        this.containsData = true;
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

    public void setLastStyleStorage(String lastStyleStorage) {
        this.lastStyleStorage = lastStyleStorage;
    }

    public String getLastStyleStorage() {
        return lastStyleStorage;
    }

    public void setSabbuVersion(String sabbuVersion) {
        this.sabbuVersion = sabbuVersion;
    }

    public String getSabbuVersion() {
        return sabbuVersion;
    }

    public void setScrollPosition(int scrollPosition) {
        this.scrollPosition = scrollPosition;
    }

    public int getScrollPosition() {
        return scrollPosition;
    }

    public void setActiveLine(int activeLine) {
        this.activeLine = activeLine;
    }

    public int getActiveLine() {
        return activeLine;
    }
}
