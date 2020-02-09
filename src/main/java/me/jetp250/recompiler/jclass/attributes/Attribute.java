package me.jetp250.recompiler.jclass.attributes;

import me.jetp250.recompiler.jclass.constants.ConstantPool;
import me.jetp250.recompiler.jclass.constants.PoolEntry;
import me.jetp250.recompiler.jclass.constants.types.UTF8Entry;

import java.io.DataInput;
import java.io.IOException;

public abstract class Attribute {
    private final AttributeType type;

    public Attribute(AttributeType type) {
        this.type = type;
    }

    public final AttributeType getType() {
        return type;
    }

    public static Attribute fromBytes(DataInput input, ConstantPool pool) throws IOException {
        AttributeType type = getAttributeType(input, pool);
        return type.readAttribute(input);
    }

    private static AttributeType getAttributeType(DataInput input, ConstantPool pool) throws IOException {
        int poolIndex = input.readUnsignedShort();
        PoolEntry entry = pool.getEntry(poolIndex-1);
        if (entry instanceof UTF8Entry) {
            String tag = ((UTF8Entry)entry).getString();

            AttributeType type = AttributeType.fromTag(tag);
            if (type == null) { // Do the null check here because 'tag' is available for better error reporting
                throw new IllegalStateException("Unknown attribute type: \"" + tag + "\"");
            }

            return type;
        }
        throw new IllegalStateException("Invalid attribute name index: did not point to a (UTF8) string! Index = " + poolIndex);
    }

}
