package moe.yo3explorer.ass4j.formats.vtt;

import moe.yo3explorer.ass4j.SubtitleFile;
import moe.yo3explorer.ass4j.model.*;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.InputMismatchException;

public class VttReader
{
    private VttReader() {}

    @NotNull
    public static SubtitleFile parseVttFile(@NotNull BufferedReader reader) throws IOException {

        SubtitleFile subtitleFile = new SubtitleFile();
        Style style = Style.createReasonableDefault();
        subtitleFile.getStyles().add(style);

        if (!reader.readLine().equals("WEBVTT"))
        {
            throw new InputMismatchException("invalid VTT file!");
        }

        while (true)
        {
            String inline = reader.readLine();
            if (inline == null)
                break;
            if (inline.equals(""))
                continue;
            if (Character.isDigit(inline.charAt(0)) && inline.contains("-->"))
            {
                subtitleFile.getEvents().add(parseEvent(inline,reader,style));
            }
            else
            {
                throw new RuntimeException(String.format("I do not understand this line: " + inline));
            }
        }

        return subtitleFile;
    }

    @NotNull
    private static Event parseEvent(@NotNull String timestamps, @NotNull BufferedReader reader, Style style) throws IOException {
        String[] timestampSplit = timestamps.split(" ");
        Timecode start = new Timecode(timestampSplit[0], TimecodeFormat.WEBVTT);
        Timecode end = new Timecode(timestampSplit[2], TimecodeFormat.WEBVTT);

        String outLine = null;
        while (true)
        {
            String line = reader.readLine();
            if (line.equals(""))
                break;
            line = line.trim();
            if (outLine == null)
                outLine = line;
            else
                outLine += "\\N" + line;
        }

        Event result = new Event();
        result.setEventType(EventType.DIALOGUE);
        result.setLayer(0);
        result.setStart(start);
        result.setEnd(end);
        result.setStyle(style);
        result.setSpeaker("Undefined");
        result.setText(outLine);
        return result;
    }

    @NotNull
    public static SubtitleFile parseVttFile(Reader reader) throws IOException {
        BufferedReader br = new BufferedReader(reader);
        SubtitleFile result = parseVttFile(br);
        br.close();
        return result;
    }

    @NotNull
    public static SubtitleFile parseVttFile(InputStream inputStream) throws IOException {
        InputStreamReader isr = new InputStreamReader(inputStream);
        SubtitleFile result = parseVttFile(isr);
        isr.close();
        return result;
    }

    @NotNull
    public static SubtitleFile parseVttFile(byte[] buffer) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
        SubtitleFile result = parseVttFile(bais);
        bais.close();
        return result;
    }

    @NotNull
    public static SubtitleFile parseVttFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        SubtitleFile result = parseVttFile(fis);
        fis.close();
        return result;
    }

}
