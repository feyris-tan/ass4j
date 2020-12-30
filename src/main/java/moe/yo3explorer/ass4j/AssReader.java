package moe.yo3explorer.ass4j;

import moe.yo3explorer.ass4j.model.*;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.StreamSupport;

public final class AssReader
{
    private AssReader() {}

    @NotNull
    public static AssFile parseAssFile(@NotNull BufferedReader reader) throws IOException {
        AssFile assFile = new AssFile();
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
                parseScriptInfo(assFile,reader);
                continue;
            }
            if (s.equals("[Aegisub Project Garbage]"))
            {
                parseAegisubProjectGarbage(assFile,reader);
                continue;
            }
            if (s.equals(""))
                continue;
            if (s.equals("[V4+ Styles]"))
            {
                parseStyles(assFile,reader);
                continue;
            }
            if (s.equals("[Events]"))
            {
                parseEvents(assFile,reader);
                continue;
            }

            throw new AssException(String.format("I do not understand this line: " + s));
        }
        return assFile;
    }

    private static void parseEvents(@NotNull AssFile assFile, BufferedReader reader) throws IOException {
        if (assFile.events == null)
        {
            assFile.events = new LinkedList<Event>();
        }
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
                SegmentableStringContainer container = new SegmentableStringContainer(getValue(s));
                Event childEvent = new Event();
                childEvent.setEventType(EventType.DIALOGUE);
                parseEvent(container,childEvent,assFile.styles);
                assFile.events.add(childEvent);
                continue;
            }
            if (s.startsWith("Comment:"))
            {
                SegmentableStringContainer container = new SegmentableStringContainer(getValue(s));
                Event childEvent = new Event();
                childEvent.setEventType(EventType.COMMENT);
                parseEvent(container,childEvent, assFile.styles);
                assFile.events.add(childEvent);
                continue;
            }
            if (s.equals(""))
                continue;
            if (s.length() < 3)
                continue;
            throw new RuntimeException("Don't know what this line means: " + s);
        }
    }

    private static void parseEvent(@NotNull SegmentableStringContainer input, @NotNull Event output, ArrayList<Style> styles)
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

    private static void parseStyles(@NotNull AssFile assFile, BufferedReader reader) throws IOException {
        if (assFile.styles == null)
        {
            assFile.styles = new ArrayList<Style>();
        }
        while (true)
        {
            String line = reader.readLine();
            if (line.startsWith("Format:"))
                continue;
            if (line.startsWith("Style: "))
            {
                SegmentableStringContainer ssc = new SegmentableStringContainer(getValue(line));
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
                assFile.styles.add(childStyle);
                continue;
            }
            if (line.equals(""))
                return;
            throw new AssException("I don't understand this line: " + line);
        }
    }

    private static void parseAegisubProjectGarbage(@NotNull AssFile assFile, BufferedReader reader) throws IOException {
        if (assFile.aegisubProjectGarbage == null)
        {
            assFile.aegisubProjectGarbage = new AegisubProjectGarbage();
        }
        while (true)
        {
            String s = reader.readLine();
            if (s.equals(""))
                return;
            if (s.startsWith("Active Line:"))
            {
                assFile.aegisubProjectGarbage.setActiveLine(getValueAsInt(s));
                continue;
            }
            if (s.startsWith("Last Style Storage:"))
            {
                assFile.aegisubProjectGarbage.setLastStyleStorage(getValue(s));
                continue;
            }
            if (s.startsWith("Audio File:"))
            {
                assFile.aegisubProjectGarbage.setAudioFile(getValue(s));
                continue;
            }
            if (s.startsWith("Video File:"))
            {
                assFile.aegisubProjectGarbage.setVideoFile(getValue(s));
                continue;
            }
            if (s.startsWith("Video AR Value:"))
            {
                assFile.aegisubProjectGarbage.setVideoArValue(getValueAsDouble(s));
                continue;
            }
            if (s.startsWith("Scroll Position:"))
            {
                assFile.aegisubProjectGarbage.setScrollPosition(getValueAsInt(s));
                continue;
            }
            if (s.startsWith("Video Position:"))
            {
                assFile.aegisubProjectGarbage.setVideoPosition(getValueAsInt(s));
                continue;
            }
            throw new AssException(String.format("I do not understand this line: " + s));
        }
    }

    private static void parseScriptInfo(@NotNull AssFile result, @NotNull BufferedReader reader) throws IOException {
        if (result.scriptinfo == null)
        {
            result.scriptinfo = new ScriptInfo();
        }
        while (true) {
            String s = reader.readLine();
            if (s.startsWith(";"))
                continue;
            if (s.startsWith("Title:")) {
                result.scriptinfo.setTitle(getValue(s));
                continue;
            }
            if (s.startsWith("ScriptType:"))
            {
                result.scriptinfo.setScriptType(getValue(s));
                continue;
            }
            if (s.startsWith("WrapStyle:"))
            {
                result.scriptinfo.setWrapStyle(WrapStyle.values()[getValueAsInt(s)]);
                continue;
            }
            if (s.startsWith("ScaledBorderAndShadow:"))
            {
                result.scriptinfo.setScaledBorderAndShadow(getValueAsBoolean(s));
                continue;
            }
            if (s.startsWith("YCbCr Matrix:"))
            {
                String matrixName = getValue(s);
                Optional<YCbCrMatrix> yCbCrMatrix = Arrays.stream(YCbCrMatrix.values()).filter(x -> x.getName().equals(matrixName)).findFirst();
                if (!yCbCrMatrix.isPresent())
                    throw new AssException("Unknown YCbCr Matrix: " + matrixName);
                result.scriptinfo.setYCbCrMatrix(yCbCrMatrix.get());
                continue;
            }
            if (s.startsWith("PlayResX:"))
            {
                result.scriptinfo.setPlayResX(getValueAsInt(s));
                continue;
            }
            if (s.startsWith("PlayResY:"))
            {
                result.scriptinfo.setPlayResY(getValueAsInt(s));
                continue;
            }
            if (s.startsWith("Original Script:"))
            {
                result.scriptinfo.setOriginalScript(getValue(s));
                continue;
            }
            if (s.startsWith("Original Timing:"))
            {
                result.scriptinfo.setOriginalTiming(getValue(s));
                continue;
            }
            if (s.equals(""))
            {
                if (result.scriptinfo.isContainsData())             /* Workaround for MEDUSA */
                    break;
                else
                    continue;
            }
            if (s.startsWith("Original Translation:"))
            {
                result.scriptinfo.setOriginalTranslation(getValue(s));
                continue;
            }
            if (s.startsWith("Original Editing:"))
            {
                result.scriptinfo.setOriginalEditing(getValue(s));
                continue;
            }
            if (s.startsWith("Synch Point:"))
            {
                result.scriptinfo.setSynchPoint(getValue(s));
                continue;
            }
            if (s.startsWith("Script Updated By:"))
            {
                result.scriptinfo.setScriptUpdatedBy(getValue(s));
                continue;
            }
            if (s.startsWith("Update Details:"))
            {
                result.scriptinfo.setUpdateDetails(getValue(s));
                continue;
            }
            if (s.startsWith("Collisions:"))
            {
                String collisionsName = getValue(s);
                Optional<Collisions> first = Arrays.stream(Collisions.values()).filter(x -> x.getNormal().equals(collisionsName)).findFirst();
                if (first.isEmpty())
                    throw new AssException("Unknown collision method: " + collisionsName);
                result.scriptinfo.setCollisions(first.get());
                continue;
            }
            if (s.startsWith("PlayDepth:"))
            {
                result.scriptinfo.setPlayDepth(getValueAsInt(s));
                continue;
            }
            if (s.startsWith("Timer:"))
            {
                result.scriptinfo.setTimer(getValueAsDouble(s));
                continue;
            }
            if (s.startsWith("Video Aspect Ratio:"))
            {
                result.scriptinfo.setVideoAspectRatio(getValue(s));
                continue;
            }
            if (s.startsWith("Video Zoom:"))
            {
                result.scriptinfo.setVideoZoom(getValueAsDouble(s));
                continue;
            }
            if (s.startsWith("Video Position:"))
            {
                result.scriptinfo.setVideoPosition(getValueAsDouble(s));
                continue;
            }
            if (s.startsWith("Audio File:"))
            {
                result.scriptinfo.setAudioFile(getValue(s));
                continue;
            }
            if (s.startsWith("Video File:"))
            {
                result.scriptinfo.setVideoFile(getValue(s));
                continue;
            }
            if (s.startsWith("Last Style Storage:"))
            {
                result.scriptinfo.setLastStyleStorage(getValue(s));
                continue;
            }
            throw new AssException(String.format("I do not understand this line: " + s));
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
    public static AssFile parseAssFile(Reader reader) throws IOException {
        BufferedReader br = new BufferedReader(reader);
        AssFile result = parseAssFile(br);
        br.close();
        return result;
    }

    @NotNull
    public static AssFile parseAssFile(InputStream inputStream) throws IOException {
        InputStreamReader isr = new InputStreamReader(inputStream);
        AssFile result = parseAssFile(isr);
        isr.close();
        return result;
    }

    @NotNull
    public static AssFile parseAssFile(byte[] buffer) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
        AssFile result = parseAssFile(bais);
        bais.close();
        return result;
    }

    @NotNull
    public static AssFile parseAssFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        AssFile result = parseAssFile(fis);
        fis.close();
        return result;
    }
}
