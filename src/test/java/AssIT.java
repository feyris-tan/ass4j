import moe.yo3explorer.ass4j.SubstationAlphaStripper;
import moe.yo3explorer.ass4j.SubtitleFile;
import moe.yo3explorer.ass4j.formats.ass.AssReader;
import moe.yo3explorer.ass4j.formats.vtt.VttReader;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.AssumptionViolatedException;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class AssIT
{
    @Test
    public void queenMillenniaMovie() throws IOException {
        runAssTest("QueenMillenniaMovie.ass");
    }

    @Test
    public void stylePlayaround() throws IOException {
        runAssTest("StylePlayaround.ass");
    }

    @Test
    public void colorPlayaround() throws IOException {
        runAssTest("ColorPlayaround.ass");
    }

    @Test
    public void properties() throws IOException {
        runAssTest("Properties.ass");
    }

    @Test
    public void queenMillenniaTv01eng() throws IOException {
        runAssTest("QueenMillenniaTV01eng.ass");
    }

    @Test
    public void queenMillenniaTv01engUnformatted() throws IOException {
        runAssTest("QueenMillenniaTV01engUnformatted.ass");
    }

    @Test
    public void queenMillenniaTv01rus() throws IOException {
        runAssTest("QueenMillenniaTV01rus.ass");
    }

    @Test
    public void sandsOfTime() throws IOException {
        runAssTest("Sands of Time 2011.ass");
    }

    @Test
    public void matrixPc240M() throws IOException {
        runAssTest("MatrixPC240M.ass");
    }

    @Test
    public void matrixPc601() throws IOException {
        runAssTest("MatrixPC601.ass");
    }

    @Test
    public void matrixPc709() throws IOException {
        runAssTest("MatrixPc709.ass");
    }

    @Test
    public void matrixPcFcc() throws IOException {
        runAssTest("MatrixPCFCC.ass");
    }

    @Test
    public void matrixTv204m() throws IOException {
        runAssTest("MatrixTV204M.ass");
    }

    @Test
    public void matrixTv601() throws IOException {
        runAssTest("MatrixTV601.ass");
    }

    @Test
    public void matrixTv709() throws IOException {
        runAssTest("MatrixTV709.ass");
    }

    @Test
    public void matrixTvFcc() throws IOException {
        runAssTest("MatrixTVFCC.ass");
    }

    @NotNull
    private SubtitleFile runAssTest(String name) throws IOException {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(name);
        if (resourceAsStream == null)
        {
            throw new AssumptionViolatedException("I can't distribute that file with the source code: " + name);
        }
        SubtitleFile subtitleFile = AssReader.parseAssFile(resourceAsStream);
        Assert.assertNotNull(subtitleFile);
        return subtitleFile;
    }

    @Test
    public void cleanerTest() throws IOException {
        SubtitleFile subtitleFile = runAssTest("cleaner/19c24e62-f420-46b0-88ca-5ad6a59dfd2d.ass");
        SubstationAlphaStripper.strip(subtitleFile);

        SubtitleFile subtitleFile1 = runAssTest("cleaner/7341e49d-3382-49c5-b43e-4b4441957528.ass");
        SubstationAlphaStripper.strip(subtitleFile1);
    }
}
