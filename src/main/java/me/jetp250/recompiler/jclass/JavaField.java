package me.jetp250.recompiler.jclass;

import me.jetp250.recompiler.jclass.constants.ConstantPool;
import me.jetp250.recompiler.jclass.constants.PoolEntry;
import me.jetp250.recompiler.jclass.constants.types.UTF8Entry;

import java.io.DataInput;
import java.io.IOException;

public class JavaField {
    public static final class AccessFlags {
        public static final int
                PUBLIC = 0x0001,
                PRIVATE = 0x0002,
                PROTECTED = 0x0004,
                STATIC = 0x0008,
                FINAL = 0x0010,
                VOLATILE = 0x0040,
                TRANSIENT = 0x0080,
                SYNTHETIC = 0x1000,
                ENUM = 0x4000;

        private AccessFlags() {}
    }

    public int accessFlags;
    public String name;
    public String descriptor;

    private Attribute[] attributes;

    public JavaField() {

    }

    public static JavaField fromBytes(DataInput input, ConstantPool pool) throws IOException {
        JavaField field = new JavaField();
        field.accessFlags = input.readUnsignedShort();

        int nameIndex = input.readUnsignedShort();
        PoolEntry nameEntry = pool.getEntry(nameIndex - 1);
        if (nameEntry instanceof UTF8Entry) {
            field.name = ((UTF8Entry)nameEntry).getString();
        } else {
            throw new IllegalStateException("Invalid name index: did not point to a (UTF8) string! Index = " + nameIndex);
        }

        System.out.printf("Read field: \"%s\"\n", field.name);

        int descriptorIndex = input.readUnsignedShort();
        PoolEntry descriptorEntry = pool.getEntry(descriptorIndex - 1);
        if (descriptorEntry instanceof UTF8Entry) {
            field.descriptor = ((UTF8Entry)descriptorEntry).getString();
        } else {
            throw new IllegalStateException("Invalid descriptor index: did not point to a (UTF8) string! Index = " + descriptorIndex);
        }

        int numAttributes = input.readUnsignedShort();
        field.attributes = new Attribute[numAttributes];
        for (int i = 0; i < numAttributes; ++i) {
            field.attributes[i] = Attribute.fromBytes(input, pool);
        }

        return field;
    }

}
