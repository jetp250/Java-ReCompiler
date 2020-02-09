package me.jetp250.recompiler.jclass.attributes;

import me.jetp250.recompiler.jclass.constants.ConstantPool;
import me.jetp250.recompiler.jclass.constants.PoolEntry;
import me.jetp250.recompiler.jclass.constants.types.UTF8Entry;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class Attribute {
    private final AttributeType type;
    private final int attributeNameIndex;
    private final int attributeLength;

    public Attribute(AttributeType type, int attributeNameIndex, int attributeLength) {
        this.type = type;
        this.attributeNameIndex = attributeNameIndex;
        // 'attribute length' is the length of the subsequent data, explained here:
        // https://docs.oracle.com/javase/specs/jvms/se13/html/jvms-4.html#jvms-4.7.2
        // TODO do verification? (each subclass knows what the length should be)
        this.attributeLength = attributeLength;
    }

    public final AttributeType getType() {
        return type;
    }

    public final void writeBytes(DataOutput output, ConstantPool pool) throws IOException {
        output.writeShort(attributeNameIndex);
        output.writeInt(attributeLength);
        writeBytes0(output);
    }

    protected abstract void writeBytes0(DataOutput output) throws IOException;

    public static Attribute fromBytes(DataInput input, ConstantPool pool) throws IOException {
        int attributeNameIndex = input.readUnsignedShort();

        AttributeType type = getAttributeType(attributeNameIndex, pool);
        return type.readAttribute(attributeNameIndex, input);
    }

    private static AttributeType getAttributeType(int attributeNameIndex, ConstantPool pool) {
        PoolEntry entry = pool.getEntry(attributeNameIndex-1);
        if (entry instanceof UTF8Entry) {
            String tag = ((UTF8Entry)entry).getString();

            AttributeType type = AttributeType.fromTag(tag);
            if (type == null) { // Do the null check here because 'tag' is available for better error reporting
                throw new IllegalStateException("Unknown attribute type: \"" + tag + "\"");
            }

            return type;
        }
        throw new IllegalStateException("Invalid attribute name index: did not point to a (UTF8) string! Index = " + attributeNameIndex);
    }

}
