package me.jetp250.recompiler.jclass.constants.types;

import me.jetp250.recompiler.jclass.constants.ConstantPool;
import me.jetp250.recompiler.jclass.constants.ConstantType;
import me.jetp250.recompiler.jclass.constants.PoolEntry;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class IntEntry extends PoolEntry {
    private int value;

    public IntEntry(int value) {
        super(ConstantType.INTEGER);
        this.value = value;
    }

    public IntEntry(DataInput input) throws IOException {
        this(input.readInt());
    }

    @Override
    protected void writeBytes0(DataOutput output) throws IOException {
        output.writeInt(value);
    }

    @Override
    public String toString(ConstantPool pool) {
        return super.toString(pool);
    }
}
