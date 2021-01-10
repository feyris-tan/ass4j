package moe.yo3explorer.ass4j.formats.ass;

import moe.yo3explorer.ass4j.SubtitleException;
import moe.yo3explorer.ass4j.SubtitleFile;
import moe.yo3explorer.ass4j.model.*;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;

public final class AssReader
{
    private AssReader() {}

    @NotNull
    public static SubtitleFile parseAssFile(@NotNull BufferedReader reader) throws IOException {
        SubtitleFile subtitleFile = new SubtitleFile();
        while (true)
        {
            String s = reader.readLine();
            if (s == null)
                break;
            if (s.startsWith("\uFEFF"))
                s = s.substring(1);
            if (s.startsWith(";"))
                continue;
            if (s.equals("[Script Info]"))
            {
                parseScriptInfo(subtitleFile,reader);
                continue;
            }
            if (s.equals("[Aegisub Project Garbage]"))
            {
                parseAegisubProjectGarbage(subtitleFile,reader);
                continue;
            }
            if (s.equals(""))
                continue;
            if (s.equals("[V4+ Styles]"))
            {
                parseStyles(subtitleFile,reader);
                continue;
            }
            if (s.equals("[Events]"))
            {
                parseEvents(subtitleFile,reader);
                continue;
            }

            throw new SubtitleException(String.format("I do not understand this line: " + s));
        }
        return subtitleFile;
    }

    private static void parseEvents(@NotNull SubtitleFile subtitleFile, @NotNull BufferedReader reader) throws IOException {
        while (true)
        {
            String s = reader.readLine();
            if (s == null)
                return;
            //Format: Layer, Start, End, Style, Actor, MarginL, MarginR, MarginV, Effect, Text
            if (s.startsWith("Format:"))
                continue;
            if (s.startsWith("Dialogue:"))
            {
                AssSegmentableStringContainer container = new AssSegmentableStringContainer(getValue(s),TimecodeFormat.ADVANCED_SUBSTATION);
                Event childEvent = new Event();
                childEvent.setEventType(EventType.DIALOGUE);
                parseEvent(container,childEvent, subtitleFile.getStyles());
                subtitleFile.getEvents().add(childEvent);
                continue;
            }
            if (s.startsWith("Comment:"))
            {
                AssSegmentableStringContainer container = new AssSegmentableStringContainer(getValue(s),TimecodeFormat.ADVANCED_SUBSTATION);
                Event childEvent = new Event();
                childEvent.setEventType(EventType.COMMENT);
                parseEvent(container,childEvent, subtitleFile.getStyles());
                subtitleFile.getEvents().add(childEvent);
                continue;
            }
            if (s.equals(""))
                continue;
            if (s.length() < 3)
                continue;
            throw new RuntimeException("Don't know what this line means: " + s);
        }
    }

    private static void parseEvent(@NotNull AssSegmentableStringContainer input, @NotNull Event output, List<Style> styles)
    {
        //Format: Layer, Start, End, Style, Name, MarginL, MarginR, MarginV, Effect, Text
        output.setLayer(input.readSegmentAsInt());
        output.setStart(input.readSegmentAsTimecode());
        output.setEnd(input.readSegmentAsTimecode());
        output.setStyle(findStyleByName(input.readSegment(),styles));
        output.setSpeaker(input.readSegment());
        output.setMarginL(input.readSegmentAsInt());
        output.setMarginR(input.readSegmentAsInt());
        output.setMarginV(input.readSegmentAsInt());
        output.setEffect(input.readSegment());
        output.setText(input.getRemains());
    }

    private static Style findStyleByName(String name, @NotNull Iterable<Style> styles)
    {
        Style defaultStyle = null;
        Style firstStyle = null;
        for (Style style : styles) {
            if (style.getName().equals(name))
            {
                return style;
            }
            if (firstStyle == null)
                firstStyle = style;
            if (style.getName().equals("Default"))
                defaultStyle = style;
        }
        if (defaultStyle != null)
            return defaultStyle;
        return firstStyle;
    }

    private static void parseStyles(@NotNull SubtitleFile subtitleFile, @NotNull BufferedReader reader) throws IOException {
        while (true)
        {
            String line = reader.readLine();
            if (line.startsWith("Format:"))
                continue;
            if (line.startsWith("Style: "))
            {
                AssSegmentableStringContainer ssc = new AssSegmentableStringContainer(getValue(line),TimecodeFormat.ADVANCED_SUBSTATION);
                Style childStyle = new Style();
                //Format: Name, Fontname, Fontsize, PrimaryColour, SecondaryColour, OutlineColour, BackColour,
                childStyle.setName(ssc.readSegment());
                childStyle.setFontName(ssc.readSegment());
                childStyle.setFontSize(ssc.readSegmentAsDouble());
                childStyle.setPrimaryColor(ssc.readSegmentAsColor());
                childStyle.setSecondaryColor(ssc.readSegmentAsColor());
                childStyle.setOutlineColor(ssc.readSegmentAsColor());
                childStyle.setBackColor(ssc.readSegmentAsColor());
                //Bold, Italic, Underline, StrikeOut, ScaleX, ScaleY, Spacing, Angle, BorderStyle, Outline,
                childStyle.setBold(ssc.readSegmentAsBoolean());
                childStyle.setItalic(ssc.readSegmentAsBoolean());
                childStyle.setUnderline(ssc.readSegmentAsBoolean());
                childStyle.setStrikeout(ssc.readSegmentAsBoolean());
                childStyle.setScaleX(ssc.readSegmentAsDouble());
                childStyle.setScaleY(ssc.readSegmentAsDouble());
                childStyle.setSpacing(ssc.readSegmentAsDouble());
                childStyle.setAngle(ssc.readSegmentAsDouble());
                childStyle.setBorderStyle(ssc.readSegmentAsInt());
                childStyle.setOutline(ssc.readSegmentAsDouble());
                //Shadow, Alignment, MarginL, MarginR, MarginV, Encoding
                childStyle.setShadow(ssc.readSegmentAsDouble());
                childStyle.setAlignment(Alignment.values()[ssc.readSegmentAsInt() - 1]);
                childStyle.setMarginL(ssc.readSegmentAsInt());
                childStyle.setMarginR(ssc.readSegmentAsInt());
                childStyle.setMarginV(ssc.readSegmentAsInt());
                childStyle.setEncoding(ssc.readSegmentAsEncoding());
                subtitleFile.getStyles().add(childStyle);
                continue;
            }
            if (line.equals(""))
                return;
            throw new SubtitleException("I don't understand this line: " + line);
        }
    }

    private static void parseAegisubProjectGarbage(@NotNull SubtitleFile subtitleFile, @NotNull BufferedReader reader) throws IOException {
        while (true)
        {
            String s = reader.readLine();
            if (s.equals(""))
                return;
            if (s.startsWith("Active Line:"))
            {
                subtitleFile.getAegisubProjectGarbage().setActiveLine(getValueAsInt(s));
                continue;
            }
            if (s.startsWith("Last Style Storage:"))
            {
                subtitleFile.getAegisubProjectGarbage().setLastStyleStorage(getValue(s));
                continue;
            }
            if (s.startsWith("Audio File:"))
            {
                subtitleFile.getAegisubProjectGarbage().setAudioFile(getValue(s));
                continue;
            }
            if (s.startsWith("Video File:"))
            {
                subtitleFile.getAegisubProjectGarbage().setVideoFile(getValue(s));
                continue;
            }
            if (s.startsWith("Video AR Value:"))
            {
                subtitleFile.getAegisubProjectGarbage().setVideoArValue(getValueAsDouble(s));
                continue;
            }
            if (s.startsWith("Scroll Position:"))
            {
                subtitleFile.getAegisubProjectGarbage().setScrollPosition(getValueAsInt(s));
                continue;
            }
            if (s.startsWith("Video Position:"))
            {
                subtitleFile.getAegisubProjectGarbage().setVideoPosition(getValueAsInt(s));
                continue;
            }
            throw new SubtitleException(String.format("I do not understand this line: " + s));
        }
    }

    private static void parseScriptInfo(@NotNull SubtitleFile result, @NotNull BufferedReader reader) throws IOException {
        while (true) {
            String s = reader.readLine();
            if (s.startsWith(";"))
                continue;
            if (s.startsWith("Title:")) {
                result.getScriptinfo().setTitle(getValue(s));
                continue;
            }
            if (s.startsWith("ScriptType:"))
            {
                result.getScriptinfo().setScriptType(getValue(s));
                continue;
            }
            if (s.startsWith("WrapStyle:"))
            {
                result.getScriptinfo().setWrapStyle(WrapStyle.values()[getValueAsInt(s)]);
                continue;
            }
            if (s.startsWith("ScaledBorderAndShadow:"))
            {
                result.getScriptinfo().setScaledBorderAndShadow(getValueAsBoolean(s));
                continue;
            }
            if (s.startsWith("YCbCr Matrix:"))
            {
                String matrixName = getValue(s);
                Optional<YCbCrMatrix> yCbCrMatrix = Arrays.stream(YCbCrMatrix.values()).filter(x -> x.getName().equals(matrixName)).findFirst();
                if (!yCbCrMatrix.isPresent())
                    throw new SubtitleException("Unknown YCbCr Matrix: " + matrixName);
                result.getScriptinfo().setYCbCrMatrix(yCbCrMatrix.get());
                continue;
            }
            if (s.startsWith("PlayResX:"))
            {
                result.getScriptinfo().setPlayResX(getValueAsInt(s));
                continue;
            }
            if (s.startsWith("PlayResY:"))
            {
                result.getScriptinfo().setPlayResY(getValueAsInt(s));
                continue;
            }
            if (s.startsWith("Original Script:"))
            {
                result.getScriptinfo().setOriginalScript(getValue(s));
                continue;
            }
            if (s.startsWith("Original Timing:"))
            {
                result.getScriptinfo().setOriginalTiming(getValue(s));
                continue;
            }
            if (s.equals(""))
            {
                if (result.getScriptinfo().isContainsData())             /* Workaround for MEDUSA */
                    break;
                else
                    continue;
            }
            if (s.startsWith("Original Translation:"))
            {
                result.getScriptinfo().setOriginalTranslation(getValue(s));
                continue;
            }
            if (s.startsWith("Original Editing:"))
            {
                result.getScriptinfo().setOriginalEditing(getValue(s));
                continue;
            }
            if (s.startsWith("Synch Point:"))
            {
                result.getScriptinfo().setSynchPoint(getValue(s));
                continue;
            }
            if (s.startsWith("Script Updated By:"))
            {
                result.getScriptinfo().setScriptUpdatedBy(getValue(s));
                continue;
            }
            if (s.startsWith("Update Details:"))
            {
                result.getScriptinfo().setUpdateDetails(getValue(s));
                continue;
            }
            if (s.startsWith("Collisions:"))
            {
                String collisionsName = getValue(s);
                Optional<Collisions> first = Arrays.stream(Collisions.values()).filter(x -> x.getNormal().equals(collisionsName)).findFirst();
                if (first.isEmpty())
                    throw new SubtitleException("Unknown collision method: " + collisionsName);
                result.getScriptinfo().setCollisions(first.get());
                continue;
            }
            if (s.startsWith("PlayDepth:"))
            {
                result.getScriptinfo().setPlayDepth(getValueAsInt(s));
                continue;
            }
            if (s.startsWith("Timer:"))
            {
                result.getScriptinfo().setTimer(getValueAsDouble(s));
                continue;
            }
            if (s.startsWith("Video Aspect Ratio:"))
            {
                result.getScriptinfo().setVideoAspectRatio(getValue(s));
                continue;
            }
            if (s.startsWith("Video Zoom:"))
            {
                result.getScriptinfo().setVideoZoom(getValueAsDouble(s));
                continue;
            }
            if (s.startsWith("Video Position:"))
            {
                result.getScriptinfo().setVideoPosition(getValueAsDouble(s));
                continue;
            }
            if (s.startsWith("Audio File:"))
            {
                result.getScriptinfo().setAudioFile(getValue(s));
                continue;
            }
            if (s.startsWith("Video File:"))
            {
                result.getScriptinfo().setVideoFile(getValue(s));
                continue;
            }
            if (s.startsWith("Last Style Storage:"))
            {
                result.getScriptinfo().setLastStyleStorage(getValue(s));
                continue;
            }
            throw new SubtitleException(String.format("I do not understand this line: " + s));
        }
    }

    @NotNull
    private static String getValue(@NotNull String line)
    {
        int offset = line.indexOf(':');
        offset++;
        String substring = line.substring(offset);
        substring = substring.trim();
        return substring;
    }

    private static int getValueAsInt(String line)
    {
        return Integer.parseInt(getValue(line));
    }

    private static double getValueAsDouble(String line)
    {
        return Double.parseDouble(getValue(line));
    }

    private static boolean getValueAsBoolean(String line)
    {
        String value = getValue(line);
        switch (value)
        {
            case "yes":
                return true;
            case "no":
                return false;
            default:
                throw new IllegalArgumentException("Don't know what that means: " + value);
        }
    }

    @NotNull
    public static SubtitleFile parseAssFile(Reader reader) throws IOException {
        BufferedReader br = new BufferedReader(reader);
        SubtitleFile result = parseAssFile(br);
        br.close();
        return result;
    }

    @NotNull
    public static SubtitleFile parseAssFile(InputStream inputStream) throws IOException {
        InputStreamReader isr = new InputStreamReader(inputStream);
        SubtitleFile result = parseAssFile(isr);
        isr.close();
        return result;
    }

    @NotNull
    public static SubtitleFile parseAssFile(byte[] buffer) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
        SubtitleFile result = parseAssFile(bais);
        bais.close();
        return result;
    }

    @NotNull
    public static SubtitleFile parseAssFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        SubtitleFile result = parseAssFile(fis);
        fis.close();
        return result;
    }
}
