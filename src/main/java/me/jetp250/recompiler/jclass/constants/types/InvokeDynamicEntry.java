package me.jetp250.recompiler.jclass.constants.types;

import me.jetp250.recompiler.jclass.constants.ConstantPool;
import me.jetp250.recompiler.jclass.constants.ConstantType;
import me.jetp250.recompiler.jclass.constants.PoolEntry;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class InvokeDynamicEntry extends PoolEntry {
    private short bootstrapMethodAttribIndex;
    private short nameAndTypeIndex;

    public InvokeDynamicEntry(short bootstrapMethodAttribIndex, short nameAndTypeIndex) {
        super(ConstantType.INVOKE_DYNAMIC);
        this.bootstrapMethodAttribIndex = bootstrapMethodAttribIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public InvokeDynamicEntry(DataInput input) throws IOException {
        this(input.readShort(), input.readShort());
    }

    @Override
    protected final void writeBytes0(DataOutput output) throws IOException {
        output.writeShort(bootstrapMethodAttribIndex);
        output.writeShort(nameAndTypeIndex);
    }

    @Override
    public String toString(ConstantPool pool) {
        return super.toString(pool);
    }
}