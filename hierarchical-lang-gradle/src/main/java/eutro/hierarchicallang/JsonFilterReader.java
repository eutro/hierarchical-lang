package eutro.hierarchicallang;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.*;

/**
 * A FilterReader that reads Json and transforms it somehow, before writing it back out.
 */
public class JsonFilterReader extends FilterReader {
    private static final Gson GSON = new Gson();

    /**
     * The Gson object used for reading.
     */
    public Gson inGson = GSON;

    /**
     * The Gson object used for writing.
     */
    public Gson outGson = GSON;

    /**
     * The transformer used to transform the read Json.
     */
    public JsonTransformer transformer = object -> object;

    private boolean initialized = false;
    private final Reader oldIn;

    /**
     * Creates a new filtered reader.
     *
     * @param in a Reader object providing the underlying stream.
     * @throws NullPointerException if {@code in} is {@code null}
     */
    public JsonFilterReader(Reader in) {
        super(new PipedReader());
        oldIn = in;
    }

    private void maybeInit() {
        if (initialized) return;
        initialized = true;
        try (PipedWriter writer = new PipedWriter((PipedReader) this.in)) {
            JsonObject object = inGson.fromJson(oldIn, JsonObject.class);
            JsonObject transformed = transformer.transform(object);
            outGson.toJson(transformed, writer);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public int read() throws IOException {
        maybeInit();
        return super.read();
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        maybeInit();
        return super.read(cbuf, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
        maybeInit();
        return super.skip(n);
    }

    @Override
    public boolean ready() throws IOException {
        maybeInit();
        return super.ready();
    }

    @Override
    public boolean markSupported() {
        maybeInit();
        return super.markSupported();
    }

    @Override
    public void mark(int readAheadLimit) throws IOException {
        maybeInit();
        super.mark(readAheadLimit);
    }

    @Override
    public void reset() throws IOException {
        maybeInit();
        super.reset();
    }

    @Override
    public void close() throws IOException {
        maybeInit();
        oldIn.close();
        super.close();
    }
}
