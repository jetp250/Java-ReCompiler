package me.jetp250.recompiler.jclass.printing;

public class PrettyPrinter {
    private final StringBuilder builder;

    private String indentation;
    private int spacesPerIndent;

    private boolean trailingIndentation = false;

    public PrettyPrinter(int spacesPerIndent) {
        this.builder = new StringBuilder();
        this.spacesPerIndent = spacesPerIndent;
        this.indentation = "";
    }

    public void startSection() {
        indentation += " ".repeat(spacesPerIndent);
    }

    public void endSection() {
        if (trailingIndentation && !indentation.isEmpty()) {
            builder.delete(builder.length()-spacesPerIndent, builder.length());
            trailingIndentation = false;
        }

        if (indentation.length() - spacesPerIndent <= 0) {
            indentation = "";
            return;
        }
        indentation = indentation.substring(0, indentation.length() - spacesPerIndent);
    }

    public void print(char ch) {
        if (ch == '\n')
            println();
        else
            builder.append(ch);
    }

    public void print(String str) {
        String[] lines = str.lines().toArray(String[]::new);
        if (lines.length == 0)
            return;

        builder.append(lines[0]);
        if (lines.length == 1) // Common
            return;

        builder.append('\n');

        for (int i = 1; i < lines.length; ++i) {
            builder.append(indentation).append(lines[i]);
        }
    }

    public void printf(String str, Object... format) {
        print(String.format(str, format));
    }

    public void println(String str) {
        print(str);
        println();
    }

    public void println() {
        builder.append('\n').append(indentation);
        trailingIndentation = true;
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}
