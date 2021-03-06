package me.jetp250.recompiler.jclass.constants;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class PoolEntry {
    private final ConstantType tag;

    public PoolEntry(ConstantType tag) {
        this.tag = tag;
    }

    public final void writeBytes(DataOutput output) throws IOException {
        this.tag.writeBytes(output);
        this.writeBytes0(output);
    }

    public ConstantType getTag() {
        return tag;
    }

    protected abstract void writeBytes0(DataOutput output) throws IOException;

    public String toString(ConstantPool pool) {
        return "<not implemented>";
    }

    public static PoolEntry fromBytes(DataInput input) throws IOException {
        byte bTag = input.readByte();

        ConstantType tag = ConstantType.fromTagByte(bTag);
        if (tag == null)
            System.out.println("No tag found for bTag=" + bTag);
        else
            System.out.println("Read tag: " + tag.name());
        return tag.readEntry(input);
    }
}
