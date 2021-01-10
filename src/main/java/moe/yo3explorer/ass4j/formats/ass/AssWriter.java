package moe.yo3explorer.ass4j.formats.ass;

import moe.yo3explorer.ass4j.SubtitleFile;
import moe.yo3explorer.ass4j.model.Event;
import moe.yo3explorer.ass4j.model.Style;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public final class AssWriter {
    private AssWriter() {}

    public static void writeTo(@NotNull SubtitleFile input, @NotNull PrintWriter output)
    {
        output.println("[Script Info]");
        output.println(String.format("Title: %s",input.getScriptinfo().getTitle()));
        output.println("ScriptType: v4.00+");
        output.println(String.format("WrapStyle: %d",input.getScriptinfo().getWrapStyle().ordinal()));
        output.println("");
        output.println("[V4+ Styles]");
        output.println("Format: Name, Fontname, Fontsize, PrimaryColour, SecondaryColour, OutlineColour, BackColour, Bold, Italic, Underline, StrikeOut, ScaleX, ScaleY, Spacing, Angle, BorderStyle, Outline, Shadow, Alignment, MarginL, MarginR, MarginV, Encoding");
        for (Style style : input.getStyles()) {
            output.println(String.format("Style: %s,%s,%.0f,%s,%s,%s,%s,%d,%d,%d,%d,%.0f,%.0f,%.0f,%.0f,%d,%.0f,%.0f,%d,%d,%d,%d,%d",
                    style.getName(),style.getFontName(),style.getFontSize(),
                    style.getPrimaryColor().serialize(),style.getSecondaryColor().serialize(),
                    style.getOutlineColor().serialize(),style.getBackColor().serialize(),writeBoolean(style.getBold()),writeBoolean(style.getItalic()),writeBoolean(style.getUnderline()),writeBoolean(style.getStrikeout()),style.getScaleX(),style.getScaleY(),style.getSpacing(),style.getAngle(),style.getBorderStyle(),style.getOutline(),style.getShadow(),style.getAlignment().ordinal() + 1,style.getMarginL(),style.getMarginR(),style.getMarginV(),style.getEncoding().getId()));
        }
        output.println("");
        output.println("[Events]");
        output.println("Format: Layer, Start, End, Style, Name, MarginL, MarginR, MarginV, Effect, Text");
        for (Event event : input.getEvents()) {
            output.println(String.format("Dialogue: %d,%s,%s,%s,%s,%04d,%04d,%04d,%s,%s",
                    event.getLayer(),event.getStart(),event.getEnd(),event.getStyle().getName(),event.getSpeaker(),event.getMarginL(),event.getMarginR(),event.getMarginV(),event.getEffect(),event.getText()));
        }
        output.println("");
        output.flush();
    }

    public static void writeTo(SubtitleFile input, Writer writer)
    {
        writeTo(input,new PrintWriter(writer));
    }

    public static void writeTo(SubtitleFile input, OutputStream stream)
    {
        writeTo(input,new OutputStreamWriter(stream));
    }

    public static void writeTo(SubtitleFile input, File output) throws IOException {
        FileOutputStream fos = new FileOutputStream(output,false);
        writeTo(input,fos);
        fos.flush();
        fos.close();
    }

    public static String writeToString(SubtitleFile input)
    {
        StringWriter stringWriter = new StringWriter();
        writeTo(input,stringWriter);
        return stringWriter.toString();
    }

    @NotNull
    public static byte[] writeToByteArray(SubtitleFile input)
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
