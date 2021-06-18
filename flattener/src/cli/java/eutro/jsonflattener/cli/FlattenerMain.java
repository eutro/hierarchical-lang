package eutro.jsonflattener.cli;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import eutro.jsonflattener.JsonFlattener;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FlattenerMain {
    public static void main(String[] args) throws IOException {
        runCli(JsonFlattener::flatten, args);
    }

    static void runCli(Function<JsonObject, JsonObject> mapper, String[] args) throws IOException {
        List<Closeable> toClose = new ArrayList<>();
        Charset charset = Charset.defaultCharset();
        Appendable out = System.out;
        Reader in = new InputStreamReader(System.in);
        GsonBuilder builder = new GsonBuilder();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            switch (arg) {
                case "-pp":
                case "-pretty-print":
                    builder.setPrettyPrinting();
                    break;
                case "-lenient":
                    builder.setLenient();
                    break;
                case "-non-executable":
                    builder.generateNonExecutableJson();
                    break;
                case "-no-html-escapes":
                    builder.disableHtmlEscaping();
                    break;
                case "-cs":
                case "-charset":
                    charset = Charset.forName(getArg(args, ++i, "UTF-8"));
                    break;
                case "-o":
                case "-out":
                case "-output": {
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(
                                    new FileOutputStream(getArg(args, ++i, "out.json")),
                                    charset
                            )
                    );
                    toClose.add(writer);
                    out = writer;
                    break;
                }
                case "-i":
                case "-in":
                case "-input": {
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(
                                    new FileInputStream(getArg(args, ++i, "in.json")),
                                    charset
                            )
                    );
                    toClose.add(reader);
                    in = reader;
                    break;
                }
                default:
                    throw new RuntimeException("Unrecognised option: " + arg);
            }
        }
        Gson gson = builder.create();
        JsonObject object = gson.fromJson(in, JsonObject.class);
        gson.toJson(mapper.apply(object), out);
        for (Closeable closeable : toClose) {
            closeable.close();
        }
    }

    private static String getArg(String[] args, int i, String def) {
        if (args.length > i) {
            return args[i];
        } else {
            return def;
        }
    }

}
