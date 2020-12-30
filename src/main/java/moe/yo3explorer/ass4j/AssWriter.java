package moe.yo3explorer.ass4j;

import moe.yo3explorer.ass4j.model.Event;
import moe.yo3explorer.ass4j.model.Style;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public final class AssWriter {
    private AssWriter() {}

    public static void writeTo(@NotNull AssFile input, @NotNull PrintWriter output)
    {
        output.println("[Script Info]");
        output.println(String.format("Title: %s",input.scriptinfo.getTitle()));
        output.println("ScriptType: v4.00+");
        output.println(String.format("WrapStyle: %d",input.scriptinfo.getWrapStyle().ordinal()));
        output.println("");
        output.println("[V4+ Styles]");
        output.println("Format: Name, Fontname, Fontsize, PrimaryColour, SecondaryColour, OutlineColour, BackColour, Bold, Italic, Underline, StrikeOut, ScaleX, ScaleY, Spacing, Angle, BorderStyle, Outline, Shadow, Alignment, MarginL, MarginR, MarginV, Encoding");
        for (Style style : input.styles) {
            output.println(String.format("Style: %s,%s,%.0f,%s,%s,%s,%s,%d,%d,%d,%d,%.0f,%.0f,%.0f,%.0f,%d,%.0f,%.0f,%d,%d,%d,%d,%d",
                    style.getName(),style.getFontName(),style.getFontSize(),
                    style.getPrimaryColor().serialize(),style.getSecondaryColor().serialize(),
                    style.getOutlineColor().serialize(),style.getBackColor().serialize(),writeBoolean(style.getBold()),writeBoolean(style.getItalic()),writeBoolean(style.getUnderline()),writeBoolean(style.getStrikeout()),style.getScaleX(),style.getScaleY(),style.getSpacing(),style.getAngle(),style.getBorderStyle(),style.getOutline(),style.getShadow(),style.getAlignment().ordinal() + 1,style.getMarginL(),style.getMarginR(),style.getMarginV(),style.getEncoding().getId()));
        }
        output.println("");
        output.println("[Events]");
        output.println("Format: Layer, Start, End, Style, Name, MarginL, MarginR, MarginV, Effect, Text");
        for (Event event : input.events) {
            output.println(String.format("Dialogue: %d,%s,%s,%s,%s,%04d,%04d,%04d,%s,%s",
                    event.getLayer(),event.getStart(),event.getEnd(),event.getStyle().getName(),event.getSpeaker(),event.getMarginL(),event.getMarginR(),event.getMarginV(),event.getEffect(),event.getText()));
        }
        output.println("");
        output.flush();
    }

    public static void writeTo(AssFile input, Writer writer)
    {
        writeTo(input,new PrintWriter(writer));
    }

    public static void writeTo(AssFile input, OutputStream stream)
    {
        writeTo(input,new OutputStreamWriter(stream));
    }

    public static String writeToString(AssFile input)
    {
        StringWriter stringWriter = new StringWriter();
        writeTo(input,stringWriter);
        return stringWriter.toString();
    }

    @NotNull
    public static byte[] writeToByteArray(AssFile input)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        writeTo(input,baos);
        return baos.toByteArray();
    }

    private static int writeBoolean(boolean value)
    {
        return value ? -1 : 0;
    }
}
