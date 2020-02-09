package me.jetp250.recompiler.jclass.attributes.types;

import me.jetp250.recompiler.jclass.attributes.Attribute;
import me.jetp250.recompiler.jclass.attributes.AttributeType;
import me.jetp250.recompiler.jclass.constants.ConstantPool;
import me.jetp250.recompiler.jclass.constants.PoolEntry;
import me.jetp250.recompiler.jclass.constants.types.UTF8Entry;

import java.io.DataInput;
import java.io.IOException;

public final class ConstantValueAttribute extends Attribute {
    public final int nameIndex;
    public final int valueIndex;

    public ConstantValueAttribute(int nameIndex, int valueIndex) {
        super(AttributeType.CONSTANT_VALUE);
        this.nameIndex = nameIndex;
        this.valueIndex = valueIndex;
    }

    public ConstantValueAttribute(DataInput dataInput) throws IOException {
        super(AttributeType.CONSTANT_VALUE);
        this.nameIndex = dataInput.readUnsignedShort();
        int mustBeTwo = dataInput.readUnsignedShort();
        this.valueIndex = dataInput.readUnsignedShort();

        if (mustBeTwo != 2)
            throw new IllegalStateException("The 'attribute length' parameter of ConstantValueAttribute must be 2, was " + mustBeTwo);
    }

    public String resolveName(ConstantPool pool) {
        PoolEntry entry = pool.getEntry(nameIndex - 1);
        return (entry instanceof UTF8Entry) ? ((UTF8Entry)entry).getString() : null;
    }

    public PoolEntry getValueConstant(ConstantPool pool) {
        return pool.getEntry(valueIndex - 1);
    }

}
