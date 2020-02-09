package me.jetp250.recompiler.jclass.constants.types;

import me.jetp250.recompiler.jclass.constants.ConstantPool;
import me.jetp250.recompiler.jclass.constants.ConstantType;
import me.jetp250.recompiler.jclass.constants.PoolEntry;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class UTF8Entry extends PoolEntry {
    private short length;
    private byte[] data;

    public UTF8Entry(short length, byte[] data) {
        super(ConstantType.UTF8);
        this.length = length;
        this.data = data;
    }

    public UTF8Entry(DataInput input) throws IOException {
        super(ConstantType.UTF8);
        this.length = input.readShort();

        this.data = new byte[length];
        input.readFully(data, 0, length);
    }

    public String getString() {
        return new String(this.data, 0, length, StandardCharsets.UTF_8);
    }

    @Override
    protected final void writeBytes0(DataOutput output) throws IOException {
        output.writeShort(length);
        output.write(data, 0, length);
    }

    @Override
    public String toString(ConstantPool pool) {
        return "Constant_UTF8{ value = \"" + getString() + "\" }";
    }
}
