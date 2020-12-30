package moe.yo3explorer.ass4j.model;

public enum YCbCrMatrix
{
    NONE("None"),
    TV601("TV.601"),
    PC601("PC.601"),
    TV709("TV.709"),
    PC709("PC.709"),
    TVFCC("TV.FCC"),
    PCFCC("PC.FCC"),
    TV240M("TV.240M"),
    PC240M("PC.240M")
    ;

    private final String name;

    YCbCrMatrix(String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }
}
