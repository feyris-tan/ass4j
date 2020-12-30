package moe.yo3explorer.ass4j.model;

public enum Encoding {
    ANSI(0),
    DEFAULT(1),
    SYMBOL(2),
    MAC(77),
    SHIFT_JIS(128),
    HANGEUL(129),
    JOHAB(130),
    GB2312(134),
    CHINESE_BIG5(136),
    GREEK(161),
    TURKISH(162),
    VIETNAMESE(163),
    HEBREW(177),
    ARABIC(178),
    BALTIC(186),
    RUSSIAN(204),
    THAI(222),
    EAST_EUROPEAN(238),
    OEM(255)
    ;

    private final int id;

    Encoding(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
