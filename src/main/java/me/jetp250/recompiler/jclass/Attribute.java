package me.jetp250.recompiler.jclass;

import me.jetp250.recompiler.jclass.constants.ConstantPool;
import me.jetp250.recompiler.jclass.constants.PoolEntry;
import me.jetp250.recompiler.jclass.constants.types.UTF8Entry;

import java.io.DataInput;
import java.io.IOException;

// TEMPORARY: Actual implementation of attributes is in the 'attributes' package.
public class Attribute {
    public final String name;
    public final byte[] data;

    public Attribute(String name, byte[] data) {
        this.name = name;
        this.data = data;
    }

    public static Attribute fromBytes(DataInput input, ConstantPool pool) throws IOException  {
        String name;

        int nameIndex = input.readUnsignedShort();
        PoolEntry nameEntry = pool.getEntry(nameIndex - 1);
        if (nameEntry instanceof UTF8Entry) {
            name = ((UTF8Entry)nameEntry).getString();
        } else {
            throw new IllegalStateException("Invalid attribute name entry: did not point to a (UTF8) string! Index = " + nameIndex);
        }

        int dataLength = input.readInt();
        byte[] data = new byte[dataLength];
        input.readFully(data, 0, dataLength);

        System.out.printf("Read attribute: \"%s\"\n", name);

        return new Attribute(name, data);
    }
}
