package moe.yo3explorer.ass4j.formats.srt;

import moe.yo3explorer.ass4j.SubtitleFile;
import moe.yo3explorer.ass4j.model.Event;
import moe.yo3explorer.ass4j.model.EventType;
import moe.yo3explorer.ass4j.model.Style;
import moe.yo3explorer.ass4j.model.TimecodeFormat;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.List;

public class SrtWriter {
    private SrtWriter() {}

    public static void writeTo(@NotNull SubtitleFile input, @NotNull PrintWriter output)
    {
        List<Event> events = input.getEvents();
        int order = 0;
        for (Event event : events) {
            if (event.getEventType() != EventType.DIALOGUE)
                continue;

            output.println(++order);
            event.getStart().setFormat(TimecodeFormat.WEBVTT);
            event.getEnd().setFormat(TimecodeFormat.WEBVTT);
            output.println(String.format("%s --> %s",event.getStart().toString(),event.getEnd().toString()));
            String[] split = event.getText().split("\\\\N");
            for (String s : split) {
                output.println(s);
            }
            output.println("");
        }
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
}
