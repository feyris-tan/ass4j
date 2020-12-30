package moe.yo3explorer.ass4j.model;

public class Style {
    private String name;
    private String fontName;
    private double fontSize;
    private Color primaryColor;
    private Color secondaryColor;
    private Color outlineColor;
    private Color backColor;
    private boolean bold;
    private boolean italic;
    private boolean underline;
    private boolean strikeout;
    private double scaleX;
    private double scaleY;
    private double spacing;
    private double angle;
    private int borderStyle;
    private double outline;
    private double shadow;
    private Alignment alignment;
    private int marginL;
    private int marginR;
    private int marginV;
    private Encoding encoding;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontSize(double fontSize) {
        this.fontSize = fontSize;
    }

    public double getFontSize() {
        return fontSize;
    }

    public void setPrimaryColor(Color primaryColor) {
        this.primaryColor = primaryColor;
    }

    public Color getPrimaryColor() {
        return primaryColor;
    }

    public void setSecondaryColor(Color secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public Color getSecondaryColor() {
        return secondaryColor;
    }

    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
    }

    public Color getOutlineColor() {
        return outlineColor;
    }

    public void setBackColor(Color backColor) {
        this.backColor = backColor;
    }

    public Color getBackColor() {
        return backColor;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public boolean getBold() {
        return bold;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public boolean getItalic() {
        return italic;
    }

    public void setUnderline(boolean underline) {
        this.underline = underline;
    }

    public boolean getUnderline() {
        return underline;
    }

    public void setStrikeout(boolean strikeout) {
        this.strikeout = strikeout;
    }

    public boolean getStrikeout() {
        return strikeout;
    }

    public void setScaleX(double scaleX) {
        this.scaleX = scaleX;
    }

    public double getScaleX() {
        return scaleX;
    }

    public void setScaleY(double scaleY) {
        this.scaleY = scaleY;
    }

    public double getScaleY() {
        return scaleY;
    }

    public void setSpacing(double spacing) {
        this.spacing = spacing;
    }

    public double getSpacing() {
        return spacing;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }

    public void setBorderStyle(int borderStyle) {
        this.borderStyle = borderStyle;
    }

    public int getBorderStyle() {
        return borderStyle;
    }

    public void setOutline(double outline) {
        this.outline = outline;
    }

    public double getOutline() {
        return outline;
    }

    public void setShadow(double shadow) {
        this.shadow = shadow;
    }

    public double getShadow() {
        return shadow;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public Alignment getAlignment() {
        return alignment;
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

    public void setEncoding(Encoding encoding) {
        this.encoding = encoding;
    }

    public Encoding getEncoding() {
        return encoding;
    }
}
