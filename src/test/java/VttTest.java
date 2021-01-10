import moe.yo3explorer.ass4j.SubtitleFile;
import moe.yo3explorer.ass4j.formats.vtt.VttReader;
import org.junit.Assert;
import org.junit.AssumptionViolatedException;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class VttTest
{
    @Test
    public void dillermand1() throws IOException {
        runVttTest("dillermand1.vtt");
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
