package me.jetp250.recompiler.jclass.constants;

import me.jetp250.recompiler.jclass.constants.types.*;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public enum ConstantType {
    CLASS(7, ClassEntry::new),
    FIELD_REF(9, FieldRefEntry::new),
    METHOD_REF(10, MethodRefEntry::new),
    INTERFACE_METHOD_REF(11, InterfaceMethodEntry::new),
    STRING(8, StringEntry::new),
    INTEGER(3, IntEntry::new),
    FLOAT(4, FloatEntry::new),
    LONG(5, LongEntry::new),
    DOUBLE(6, DoubleEntry::new),
    NAME_AND_TYPE(12, NameAndTypeEntry::new),
    UTF8(1, UTF8Entry::new),
    METHOD_HANDLE(15, MethodHandleEntry::new),
    METHOD_TYPE(16, MethodTypeEntry::new),
    DYNAMIC(17, DynamicEntry::new),
    INVOKE_DYNAMIC(18, InvokeDynamicEntry::new),
    MODULE(19, ModuleEntry::new),
    PACKAGE(20, PackageEntry::new);

    interface PoolEntryFactory {
        PoolEntry create(DataInput input) throws IOException;
    }

    private final byte tag;
    private final PoolEntryFactory factory;

    ConstantType(int tag, PoolEntryFactory factory) {
        this.tag = (byte) tag;
        this.factory = factory;
    }

    public PoolEntry readEntry(DataInput input) throws IOException {
        return factory.create(input);
    }

    public void writeBytes(DataOutput output) throws IOException {
        output.writeByte(tag);
    }

    private static Map<Integer, ConstantType> ID_TO_ENUM_MAP;

    public static ConstantType fromTagByte(byte tag) {
        return ID_TO_ENUM_MAP.get((int)tag);
    }

    static {
        ID_TO_ENUM_MAP = new HashMap<>();
        for (ConstantType type : ConstantType.values()) {
            ID_TO_ENUM_MAP.put((int)type.tag, type);
        }
    }
}
