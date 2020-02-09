package me.jetp250.recompiler.jclass.constants.types;

import me.jetp250.recompiler.jclass.constants.ConstantPool;
import me.jetp250.recompiler.jclass.constants.ConstantType;
import me.jetp250.recompiler.jclass.constants.PoolEntry;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class InterfaceMethodEntry extends PoolEntry {
    private short classIndex;
    private short nameAndTypeIndex;

    public InterfaceMethodEntry(short classIndex, short nameAndTypeIndex) {
        super(ConstantType.INTERFACE_METHOD_REF);
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public InterfaceMethodEntry(DataInput input) throws IOException {
        this(input.readShort(), input.readShort());
    }

    @Override
    protected void writeBytes0(DataOutput output) throws IOException {
        output.writeShort(classIndex);
        output.write(nameAndTypeIndex);
    }

    @Override
    public String toString(ConstantPool pool) {
        return super.toString(pool);
    }
}
