package me.jetp250.recompiler.jclass.constants.types;

import me.jetp250.recompiler.jclass.constants.ConstantPool;
import me.jetp250.recompiler.jclass.constants.ConstantType;
import me.jetp250.recompiler.jclass.constants.PoolEntry;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class ClassEntry extends PoolEntry {
    private short nameIndex;

    public ClassEntry(short nameIndex) {
        super(ConstantType.CLASS);
        this.nameIndex = nameIndex;
    }

    public ClassEntry(DataInput input) throws IOException {
        this(input.readShort());
    }

    public String getClassName(ConstantPool pool) {
        PoolEntry nameEntry = pool.getEntry(nameIndex-1);
        if (nameEntry instanceof UTF8Entry) {
            return ((UTF8Entry)nameEntry).getString();
        }
        throw new IllegalStateException("Invalid class entry: did not point to an UTF8 name! nameIndex = " + nameIndex);
    }

    @Override
    protected final void writeBytes0(DataOutput output) throws IOException {
        output.writeInt(nameIndex);
    }

    @Override
    public String toString(ConstantPool pool) {
        PoolEntry nameEntry = pool.getEntry(nameIndex - 1);
        return "Constant_CLASS{ nameIndex = " + nameIndex + " -> \"" + ((UTF8Entry)nameEntry).getString() + "\" }";
    }
}
