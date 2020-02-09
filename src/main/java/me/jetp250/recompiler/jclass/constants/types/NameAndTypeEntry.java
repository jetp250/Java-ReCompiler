package me.jetp250.recompiler.jclass.constants.types;

import me.jetp250.recompiler.jclass.constants.ConstantPool;
import me.jetp250.recompiler.jclass.constants.ConstantType;
import me.jetp250.recompiler.jclass.constants.PoolEntry;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class NameAndTypeEntry extends PoolEntry {
    private short nameIndex;
    private short descriptorIndex;

    public NameAndTypeEntry(short nameIndex, short descriptorIndex) {
        super(ConstantType.NAME_AND_TYPE);
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
    }

    public NameAndTypeEntry(DataInput input) throws IOException {
        this(input.readShort(), input.readShort());
    }

    @Override
    protected final void writeBytes0(DataOutput output) throws IOException {
        output.writeShort(nameIndex);
        output.writeShort(descriptorIndex);
    }

    @Override
    public String toString(ConstantPool pool) {
        UTF8Entry nameEntry = (UTF8Entry) pool.getEntry(nameIndex-1);
        UTF8Entry descriptorEntry = (UTF8Entry) pool.getEntry(descriptorIndex-1);
        return "Constant_NameAndType{ nameIndex = " + nameIndex + " -> \"" + nameEntry.getString() + "\""
                + ", descriptorIndex = " + descriptorIndex + " -> \"" +  descriptorEntry.getString() + "\" }";
    }
}
