package me.jetp250.recompiler.jclass.constants.types;

import me.jetp250.recompiler.jclass.constants.ConstantPool;
import me.jetp250.recompiler.jclass.constants.ConstantType;
import me.jetp250.recompiler.jclass.constants.PoolEntry;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class MethodHandleEntry extends PoolEntry {
    private short referenceKind;
    private short referenceIndex;

    public MethodHandleEntry(short referenceKind, short referenceIndex) {
        super(ConstantType.METHOD_HANDLE);
        this.referenceKind = referenceKind;
        this.referenceIndex = referenceIndex;
    }

    public MethodHandleEntry(DataInput input) throws IOException {
        this(input.readByte(), input.readByte());
    }

    @Override
    protected final void writeBytes0(DataOutput output) throws IOException {
        output.writeShort(referenceKind);
        output.writeShort(referenceIndex);
    }

    @Override
    public String toString(ConstantPool pool) {
        return super.toString(pool);
    }
}
