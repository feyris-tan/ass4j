import moe.yo3explorer.ass4j.AssFile;
import moe.yo3explorer.ass4j.AssReader;
import moe.yo3explorer.ass4j.AssWriter;
import moe.yo3explorer.ass4j.model.Alignment;
import moe.yo3explorer.ass4j.model.Style;
import org.junit.Assert;
import org.junit.AssumptionViolatedException;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AssTest
{

    @Test
    public void queenMillenniaMovie() throws IOException {
        runTest("QueenMillenniaMovie.ass");
    }

    @Test
    public void stylePlayaround() throws IOException {
        runTest("StylePlayaround.ass");
    }

    @Test
    public void colorPlayaround() throws IOException {
        runTest("ColorPlayaround.ass");
    }

    @Test
    public void properties() throws IOException {
        runTest("Properties.ass");
    }

    @Test
    public void queenMillenniaTv01eng() throws IOException {
        runTest("QueenMillenniaTV01eng.ass");
    }

    @Test
    public void queenMillenniaTv01engUnformatted() throws IOException {
        runTest("QueenMillenniaTV01engUnformatted.ass");
    }

    @Test
    public void queenMillenniaTv01rus() throws IOException {
        runTest("QueenMillenniaTV01rus.ass");
    }

    @Test
    public void sandsOfTime() throws IOException {
        runTest("Sands of Time 2011.ass");
    }

    @Test
    public void matrixPc240M() throws IOException {
        runTest("MatrixPC240M.ass");
    }

    @Test
    public void matrixPc601() throws IOException {
        runTest("MatrixPC601.ass");
    }

    @Test
    public void matrixPc709() throws IOException {
        runTest("MatrixPc709.ass");
    }

    @Test
    public void matrixPcFcc() throws IOException {
        runTest("MatrixPCFCC.ass");
    }

    @Test
    public void matrixTv204m() throws IOException {
        runTest("MatrixTV204M.ass");
    }

    @Test
    public void matrixTv601() throws IOException {
        runTest("MatrixTV601.ass");
    }

    @Test
    public void matrixTv709() throws IOException {
        runTest("MatrixTV709.ass");
    }

    @Test
    public void matrixTvFcc() throws IOException {
        runTest("MatrixTVFCC.ass");
    }

    private void runTest(String name) throws IOException {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(name);
        if (resourceAsStream == null)
        {
            throw new AssumptionViolatedException("I can't distribute that file with the source code: " + name);
        }
        AssFile assFile = AssReader.parseAssFile(resourceAsStream);
        Assert.assertNotNull(assFile);
    }
}
