package me.jetp250.recompiler.jclass;

import me.jetp250.recompiler.JavaVersion;
import me.jetp250.recompiler.jclass.constants.ConstantPool;
import me.jetp250.recompiler.jclass.constants.PoolEntry;
import me.jetp250.recompiler.jclass.constants.types.ClassEntry;
import me.jetp250.recompiler.jclass.printing.ClassHeaderStringifier;

import java.io.DataInput;
import java.io.IOException;

// Middleman between on-disk representation and a programmer-friendly state
public class JavaClassHeader {
    public static final class AccessFlags {
        public static final int
                PUBLIC = 0x0001,
                FINAL = 0x0010,
                SUPER = 0x0020,
                INTERFACE = 0x0200,
                ABSTRACT = 0x0400,
                SYNTHETIC = 0x1000,
                ANNOTATION = 0x2000,
                ENUM = 0x4000,
                MODULE = 0x8000;

        private AccessFlags() {}
    }

    public enum ClassType {
        ENUM("enum"), CLASS("class"), INTERFACE("interface");

        public final String tokenName;
        ClassType(String tokenName) {
            this.tokenName = tokenName;
        }
    }

    public final JavaVersion classVersion;
    public final ConstantPool constantPool;
    public final int accessFlags;

    public final String className;
    public final String superclassName;

    public final String[] interfaces;

    private JavaClassHeader(DataInput input) throws IOException {
        checkMagicConstant(input.readInt());
        this.classVersion = JavaVersion.fromBytes(input);
        this.constantPool = ConstantPool.fromBytes(input);
        this.accessFlags = input.readShort();
        this.className = getClassName(input, constantPool);
        this.superclassName = getClassName(input, constantPool);
        this.interfaces = getInterfaces(input, constantPool);

        checkVersion(this.classVersion);
    }

    public ClassType getClassType() {
        if ((accessFlags & AccessFlags.INTERFACE) != 0)
            return ClassType.INTERFACE;
        if ((accessFlags & AccessFlags.ENUM) != 0)
            return ClassType.ENUM;
        return ClassType.CLASS;
    }

    @Override
    public String toString() {
        return ClassHeaderStringifier.toString(this);
    }

    public static JavaClassHeader fromBytes(DataInput input) throws IOException {
        return new JavaClassHeader(input);
    }

    private static String[] getInterfaces(DataInput input, ConstantPool pool) throws IOException {
        String[] interfaces = new String[input.readShort()];
        for (int i = 0; i < interfaces.length; ++i) {
            interfaces[i] = getClassName(input, pool);
        }
        return interfaces;
    }

    private static String getClassName(DataInput input, ConstantPool pool) throws IOException {
        short poolIndex = input.readShort();
        PoolEntry entry = pool.getEntry(poolIndex - 1);
        if (entry instanceof ClassEntry) {
            return ((ClassEntry)entry).getClassName(pool);
        }
        throw new IllegalArgumentException("Expected class entry, got " + entry.getTag().name() + "! Pool index: " + poolIndex);
    }

    private static void checkMagicConstant(int header) {
        if (header != 0xCAFEBABE) {
            String hex = String.format("0x%08X", header);
            throw new IllegalStateException("Invalid class header: \"" + hex + "\"");
        }
    }

    private void checkVersion(JavaVersion version) {
        if (!JavaVersion.JAVA_13.isSupported(version)) {
            throw new IllegalStateException("Unsupported class version: " +  version.asInt());
        }
    }

}
