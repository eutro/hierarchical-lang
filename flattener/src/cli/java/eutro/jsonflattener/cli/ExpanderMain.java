package eutro.jsonflattener.cli;

import eutro.jsonflattener.JsonExpander;

import java.io.IOException;

public class ExpanderMain {
    public static void main(String[] args) throws IOException {
        FlattenerMain.runCli(JsonExpander::expand, args);
    }
}
