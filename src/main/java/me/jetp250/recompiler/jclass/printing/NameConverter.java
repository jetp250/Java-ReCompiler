package me.jetp250.recompiler.jclass.printing;

// TODO rework this. Plan was good, implementation did not end up that great..
// Something like this is needed though, just, a better way to implement it is needed
// Probably boils down to me not still entirely understanding the internal format -jetp

// See Oracle's guide for descriptors: https://docs.oracle.com/javase/specs/jvms/se13/html/jvms-4.html#jvms-4.3
public final class NameConverter {
    private NameConverter() {}

    public static String convertInternal(String internal) {
        if (internal.isEmpty())
            return "<invalid>";

        int length = internal.length();

        int arrays = 0;
        int index = 0;
        while(index < length && internal.charAt(index) == '[') {
            arrays++;
        }

        String fieldType = readFieldType(internal, index);
        for (int i = 0; i < arrays; ++i)
            fieldType += "[]";

        return fieldType;
    }

    private static String readFieldType(String string, int offset) {
        if (string.length() <= offset)
            return "<invalid>";

        char type = string.charAt(offset);
        // See https://docs.oracle.com/javase/specs/jvms/se13/html/jvms-4.html#jvms-4.3.2-200

        switch(type) {
            case 'B':
                return "byte";
            case 'C':
                return "char";
            case 'D':
                return "double";
            case 'F':
                return "float";
            case 'I':
                return "int";
            case 'J':
                return "long";
            case 'S':
                return "short";
            case 'Z':
                return "boolean";
            case 'L': {
                int endIndex = string.indexOf(';', offset);
                if (endIndex == -1)
                    return "<invalid>";
                return fixClassName(string.substring(offset + 1, endIndex));
            }
            default:
                return "<invalid>";
        }
    }

    private static String fixClassName(String className) {
        if (className.startsWith("java/lang/"))
            return className.substring("java/lang/".length());

        className = className.replace('/', '.');
        className = className.replace('$', '.');
        return className;
    }
}
