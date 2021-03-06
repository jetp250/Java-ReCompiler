package me.jetp250.recompiler.jclass.constants.types;

import me.jetp250.recompiler.jclass.constants.ConstantPool;
import me.jetp250.recompiler.jclass.constants.ConstantType;
import me.jetp250.recompiler.jclass.constants.PoolEntry;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class PackageEntry extends PoolEntry {
    private short nameIndex;

    public PackageEntry(short nameIndex) {
        super(ConstantType.PACKAGE);
        this.nameIndex = nameIndex;
    }

    public PackageEntry(DataInput input) throws IOException {
        this(input.readShort());
    }

    @Override
    protected final void writeBytes0(DataOutput output) throws IOException {
        output.writeShort(nameIndex);
    }

    @Override
    public String toString(ConstantPool pool) {
        return super.toString(pool);
    }
}
