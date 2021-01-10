import moe.yo3explorer.ass4j.SubtitleFile;
import moe.yo3explorer.ass4j.formats.ass.AssWriter;
import moe.yo3explorer.ass4j.formats.vtt.VttReader;
import org.junit.Assert;
import org.junit.AssumptionViolatedException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class VttTest
{
    @Test
    public void dillermand1() throws IOException {
        runVttTest("dillermand1.vtt");
    }

    @Test
    public void convertDillermand() throws IOException {
        for (int i = 1; i < 14; i++)
        {
            String vttName = String.format("dillermand%d.vtt",i);
            String assName = String.format("dillermand%d.ass",i);
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(vttName);
            SubtitleFile subtitleFile = VttReader.parseVttFile(resourceAsStream);
            AssWriter.writeTo(subtitleFile, new File(assName));
        }
    }

    private void runVttTest(String name) throws IOException {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(name);
        if (resourceAsStream == null)
        {
            throw new AssumptionViolatedException("I can't distribute that file with the source code: " + name);
        }
        SubtitleFile subtitleFile = VttReader.parseVttFile(resourceAsStream);
        Assert.assertNotNull(subtitleFile);
    }
}
