package me.jetp250.recompiler.jclass.constants.types;

import me.jetp250.recompiler.jclass.constants.ConstantPool;
import me.jetp250.recompiler.jclass.constants.ConstantType;
import me.jetp250.recompiler.jclass.constants.PoolEntry;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class FloatEntry extends PoolEntry {
    private int bytes;

    public FloatEntry(int bytes) {
        super(ConstantType.FLOAT);
        this.bytes = bytes;
    }

    public FloatEntry(DataInput input) throws IOException {
        this(input.readInt());
    }

    @Override
    protected final void writeBytes0(DataOutput output) throws IOException {
        output.writeInt(bytes);
    }

    @Override
    public String toString(ConstantPool pool) {
        return super.toString(pool);
    }
}
