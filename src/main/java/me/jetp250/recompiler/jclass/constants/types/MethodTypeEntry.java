package me.jetp250.recompiler.jclass.constants.types;

import me.jetp250.recompiler.jclass.constants.ConstantPool;
import me.jetp250.recompiler.jclass.constants.ConstantType;
import me.jetp250.recompiler.jclass.constants.PoolEntry;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class MethodTypeEntry extends PoolEntry {
    private short descriptorIndex;

    public MethodTypeEntry(short descriptorIndex) {
        super(ConstantType.METHOD_TYPE);
        this.descriptorIndex = descriptorIndex;
    }

    public MethodTypeEntry(DataInput input) throws IOException {
        this(input.readShort());
    }

    @Override
    protected final void writeBytes0(DataOutput output) throws IOException {
        output.writeShort(descriptorIndex);
    }

    @Override
    public String toString(ConstantPool pool) {
        return super.toString(pool);
    }
}
