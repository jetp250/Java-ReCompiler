package me.jetp250.recompiler.jclass.printing;

import me.jetp250.recompiler.jclass.JavaClass;
import me.jetp250.recompiler.jclass.JavaClassHeader;
import me.jetp250.recompiler.jclass.JavaField;
import me.jetp250.recompiler.jclass.JavaMethod;

// TODO rework conversion to string?
// Stuffing everything into one class works but it's not exactly intuitive
// A much better system is possible, but this could be discussed (also not super high priority!)

public class ClassHeaderStringifier {

    public static String toString(JavaClassHeader header) {
        PrettyPrinter printer = new PrettyPrinter(3);

        // Class signature
        StringBuilder builder = new StringBuilder();
        appendClassSignature(builder, header);
        printer.print(builder.toString());

        printer.startSection();
        printer.println("{");

        // Print variables, methods..
        printFields(printer, header);

        printMethods(printer, header);

        printer.endSection();
        printer.println("}");
        return printer.toString();
    }

    private static void printMethods(PrettyPrinter printer, JavaClassHeader header) {
        for (JavaMethod method : header.methods) {
            printMethod(printer, method);
        }
    }

    private static void printMethod(PrettyPrinter printer, JavaMethod method) {
        StringBuilder builder = new StringBuilder();
        appendMethodModifiers(builder, method);

        builder.append((method.descriptor)).append(' ');
        builder.append(method.name).append("(??) {??}");

        printer.print(builder.toString());
        printer.println();
    }

    private static void appendMethodModifiers(StringBuilder builder, JavaMethod method) {
        int modifiers = method.accessFlags;
        if ((modifiers & JavaMethod.AccessFlags.PUBLIC) != 0)
            builder.append("public ");
        else if ((modifiers & JavaMethod.AccessFlags.PROTECTED) != 0)
            builder.append("protected ");
        else if ((modifiers & JavaMethod.AccessFlags.PRIVATE) != 0)
            builder.append("private ");

        if ((modifiers & JavaMethod.AccessFlags.STATIC) != 0)
            builder.append("static ");
        if ((modifiers & JavaMethod.AccessFlags.FINAL) != 0)
            builder.append("final ");
        if ((modifiers & JavaMethod.AccessFlags.SYNCHRONIZED) != 0)
            builder.append("synchronized ");
        if ((modifiers & JavaMethod.AccessFlags.ABSTRACT) != 0)
            builder.append("abstract ");
        if ((modifiers & JavaMethod.AccessFlags.NATIVE) != 0)
            builder.append("native ");

        if ((modifiers & JavaMethod.AccessFlags.SYNTHETIC) != 0)
            builder.append("/*synthetic*/ ");
        if ((modifiers & JavaMethod.AccessFlags.BRIDGE) != 0)
            builder.append("/*bridge*/ ");
    }

    private static void printFields(PrettyPrinter printer, JavaClassHeader header) {
        for (JavaField field : header.fields) {
            printField(printer, field);
        }
    }

    private static void printField(PrettyPrinter printer, JavaField field) {
        StringBuilder builder = new StringBuilder();
        appendFieldModifiers(builder, field);

        builder.append(NameConverter.convertInternal(field.descriptor)).append(' ');
        builder.append(field.name).append(';');

        printer.println(builder.toString());
        printer.println();
    }

    private static void appendFieldModifiers(StringBuilder builder, JavaField field) {
        int modifiers = field.accessFlags;
        if ((modifiers & JavaField.AccessFlags.PUBLIC) != 0)
            builder.append("public ");
        else if ((modifiers & JavaField.AccessFlags.PROTECTED) != 0)
            builder.append("protected ");
        else if ((modifiers & JavaField.AccessFlags.PRIVATE) != 0)
            builder.append("private ");

        if ((modifiers & JavaField.AccessFlags.STATIC) != 0)
            builder.append("static ");
        if ((modifiers & JavaField.AccessFlags.FINAL) != 0)
            builder.append("final ");
        if ((modifiers & JavaField.AccessFlags.VOLATILE) != 0)
            builder.append("volatile ");
        if ((modifiers & JavaField.AccessFlags.TRANSIENT) != 0)
            builder.append("transient ");
    }

    private static void appendClassSignature(StringBuilder builder, JavaClassHeader header) {
        appendClassModifiers(builder, header);

        builder.append(header.getClassType().tokenName).append(' ');
        builder.append(header.className).append(' ');

        if (header.superclassName != null) {
            // TODO Don't append if superclass is java.lang.Object & fix superclass name parsing
            builder.append("extends ").append(header.superclassName.replace('/', '.').replace('$', '.')).append(' ');
        }
        if (header.interfaces.length != 0) {
            builder.append("implements ");
            for (int i = 0; i < header.interfaces.length; ++i) {
                // TODO Fix interface name parsing
                builder.append(header.interfaces[i].replace('/', '.').replace('$','.'));
                if (i != header.interfaces.length-1)
                    builder.append(',');
                builder.append(' ');
            }
        }
    }

    private static void appendClassModifiers(StringBuilder builder, JavaClassHeader header) {
        int modifiers = header.accessFlags;
        if ((modifiers & JavaClassHeader.AccessFlags.PUBLIC) != 0)
            builder.append("public ");
        if ((modifiers & JavaClassHeader.AccessFlags.ABSTRACT) != 0)
            builder.append("abstract ");
        if ((modifiers & JavaClassHeader.AccessFlags.FINAL) != 0)
            builder.append("final ");
    }

}
