package me.jetp250.recompiler.jclass;

import me.jetp250.recompiler.jclass.constants.ConstantPool;
import me.jetp250.recompiler.jclass.constants.PoolEntry;
import me.jetp250.recompiler.jclass.constants.types.UTF8Entry;

import java.io.DataInput;
import java.io.IOException;

public class JavaMethod {
    public static final class AccessFlags {
        public static final int
                PUBLIC = 0x0001,
                PRIVATE = 0x0002,
                PROTECTED = 0x0004,
                STATIC = 0x0008,
                FINAL = 0x0010,
                SYNCHRONIZED = 0x0020,
                BRIDGE = 0x0040,
                VARARGS = 0x0080,
                NATIVE = 0x0100,
                ABSTRACT = 0x0400,
                STRICT = 0x0800,
                SYNTHETIC = 0x1000;

        private AccessFlags() {}
    }

    public int accessFlags;
    public String name;
    public String descriptor;

    public Attribute[] attributes;

    public JavaMethod() {

    }

    public static JavaMethod fromBytes(DataInput input, ConstantPool pool) throws IOException {
        JavaMethod method = new JavaMethod();
        method.accessFlags = input.readUnsignedShort();

        int nameIndex = input.readUnsignedShort();
        PoolEntry nameEntry = pool.getEntry(nameIndex - 1);
        if (nameEntry instanceof UTF8Entry) {
            method.name = ((UTF8Entry)nameEntry).getString();
        } else {
            throw new IllegalStateException("Invalid method name index: did not point to a (UTF8) string! Index = " + nameEntry);
        }

        int descriptorIndex = input.readUnsignedShort();
        PoolEntry descriptorEntry = pool.getEntry(descriptorIndex - 1);
        if (descriptorEntry instanceof UTF8Entry) {
            method.descriptor = ((UTF8Entry)descriptorEntry).getString();
        } else {
            throw new IllegalStateException("Invalid method descriptor index: did not point to a (UTF8) string! Index = " + descriptorIndex);
        }

        int numAttributes = input.readUnsignedShort();
        method.attributes = new Attribute[numAttributes];
        for (int i = 0; i < numAttributes; ++i)
            method.attributes[i] = Attribute.fromBytes(input, pool);

        return method;
    }

}
