package me.jetp250.recompiler.jclass.constants.types;

import me.jetp250.recompiler.jclass.constants.ConstantPool;
import me.jetp250.recompiler.jclass.constants.ConstantType;
import me.jetp250.recompiler.jclass.constants.PoolEntry;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class LongEntry extends PoolEntry {
    private int highBytes, lowBytes;

    public LongEntry(int highBytes, int lowBytes) {
        super(ConstantType.LONG);
        this.highBytes = highBytes;
        this.lowBytes = lowBytes;
    }

    public LongEntry(DataInput input) throws IOException {
        this(input.readInt(), input.readInt());
    }

    @Override
    protected void writeBytes0(DataOutput output) throws IOException {
        output.writeInt(highBytes);
        output.writeInt(lowBytes);
    }

    @Override
    public String toString(ConstantPool pool) {
        return super.toString(pool);
    }
}

