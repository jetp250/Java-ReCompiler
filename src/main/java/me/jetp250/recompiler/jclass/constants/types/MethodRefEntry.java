package me.jetp250.recompiler.jclass.constants.types;

import me.jetp250.recompiler.jclass.constants.ConstantPool;
import me.jetp250.recompiler.jclass.constants.ConstantType;
import me.jetp250.recompiler.jclass.constants.PoolEntry;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class MethodRefEntry extends PoolEntry {
    private short classIndex;
    private short nameAndTypeIndex;

    public MethodRefEntry(short classIndex, short nameAndTypeIndex) {
        super(ConstantType.METHOD_REF);
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public MethodRefEntry(DataInput input) throws IOException {
        this(input.readShort(), input.readShort());
    }

    @Override
    protected void writeBytes0(DataOutput output) throws IOException {
        output.writeShort(classIndex);
        output.write(nameAndTypeIndex);
    }

    @Override
    public String toString(ConstantPool pool) {
        ClassEntry classEntry = (ClassEntry) pool.getEntry(classIndex-1);
        NameAndTypeEntry nameAndTypeEntry = (NameAndTypeEntry) pool.getEntry(nameAndTypeIndex-1);
        return "Constant_MethodRef{ classIndex = " + classIndex + " -> " + classEntry.toString(pool) + ", "
                + "nameAndTypeIndex = " + nameAndTypeIndex + " -> " + nameAndTypeEntry.toString(pool) + " }";
    }
}