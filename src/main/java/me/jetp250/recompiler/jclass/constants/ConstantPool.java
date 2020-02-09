package me.jetp250.recompiler.jclass.constants;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConstantPool {
    private final List<PoolEntry> entries;

    public ConstantPool() {
        this.entries = new ArrayList<>();
    }

    private ConstantPool(DataInput input) throws IOException {
        int numEntries = input.readShort()-1;

        this.entries = new ArrayList<>(numEntries);
        for (int i = 0; i < numEntries; ++i) {
            entries.add(PoolEntry.fromBytes(input));
        }

        // TODO remove debug messages
        System.out.println("Constant pool size: " + numEntries);
        int index = 0;
        for (PoolEntry entry : entries) {
            System.out.println(" " + (index++) + ": " + entry.toString(this));
        }
        System.out.println();
        System.out.println();
    }

    public void addEntry(PoolEntry entry) {
        this.entries.add(entry);
    }

    public PoolEntry getEntry(int index) {
        return entries.get(index);
    }

    public int size() {
        return entries.size();
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }

    public void writeBytes(DataOutput output) throws IOException {
        output.writeShort(entries.size() + 1);
        for (PoolEntry entry : entries) {
            entry.writeBytes(output);
        }
    }

    public static ConstantPool fromBytes(DataInput input) throws IOException {
        return new ConstantPool(input);
    }

}
