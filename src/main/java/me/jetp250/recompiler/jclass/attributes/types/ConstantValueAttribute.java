package me.jetp250.recompiler.jclass.attributes.types;

import me.jetp250.recompiler.jclass.attributes.Attribute;
import me.jetp250.recompiler.jclass.attributes.AttributeType;
import me.jetp250.recompiler.jclass.constants.ConstantPool;
import me.jetp250.recompiler.jclass.constants.PoolEntry;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class ConstantValueAttribute extends Attribute {
    private static final int ATTRIBUTE_LENGTH = 2;

    public final int valueIndex;

    public ConstantValueAttribute(int attributeNameIndex, int valueIndex) {
        super(AttributeType.CONSTANT_VALUE, attributeNameIndex, ATTRIBUTE_LENGTH);
        this.valueIndex = valueIndex;
    }

    public ConstantValueAttribute(int attributeNameIndex, DataInput dataInput) throws IOException {
        super(AttributeType.CONSTANT_VALUE, attributeNameIndex, ATTRIBUTE_LENGTH);
        int mustBeTwo = dataInput.readUnsignedShort(); // read attribute_length
        this.valueIndex = dataInput.readUnsignedShort();

        if (mustBeTwo != ATTRIBUTE_LENGTH)
            throw new IllegalStateException("The 'attribute length' parameter of ConstantValueAttribute must be "
                    + ATTRIBUTE_LENGTH + ", was " + mustBeTwo);
    }

    @Override
    protected void writeBytes0(DataOutput output) throws IOException {
        output.writeShort(valueIndex);
    }

    public PoolEntry getValueConstant(ConstantPool pool) {
        return pool.getEntry(valueIndex - 1);
    }

}
