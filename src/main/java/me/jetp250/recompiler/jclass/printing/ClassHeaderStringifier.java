package me.jetp250.recompiler.jclass.printing;

import me.jetp250.recompiler.jclass.JavaClassHeader;

public class ClassHeaderStringifier {

    public static String toString(JavaClassHeader header) {
        PrettyPrinter printer = new PrettyPrinter(3);

        // Class signature
        StringBuilder builder = new StringBuilder();
        if ((header.accessFlags & JavaClassHeader.AccessFlags.PUBLIC) != 0)
            builder.append("public ");
        if ((header.accessFlags & JavaClassHeader.AccessFlags.FINAL) != 0)
            builder.append("final ");

        builder.append(header.getClassType().tokenName).append(' ');
        builder.append(header.className).append(' ');

        if (header.superclassName != null) {
            builder.append("extends ").append(header.superclassName.replace('/', '.')).append(' ');
        }
        if (header.interfaces.length != 0) {
            builder.append("implements ");
            for (int i = 0; i < header.interfaces.length; ++i) {
                builder.append(header.interfaces[i]);
                if (i != header.interfaces.length-1)
                    builder.append(',');
                builder.append(' ');
            }
        }
        builder.append('{');
        printer.println(builder.toString());
        printer.startSection();
        printer.println();

        // Print variables, methods..
        printer.println("Test");

        printer.println();
        printer.endSection();
        printer.print("}");
        printer.println();
        return printer.toString();
    }

}
