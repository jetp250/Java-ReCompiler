package me.jetp250.recompiler;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public enum JavaVersion {
    JAVA_6(50),
    JAVA_7(51),
    JAVA_8(52),
    JAVA_9(53),
    JAVA_10(54),
    JAVA_11(55),
    JAVA_12(56),
    JAVA_13(57);

    private final short versionNumber;

    JavaVersion(int versionNum) {
        this.versionNumber = (short)versionNum;
    }

    public void writeBytes(DataOutput output) throws IOException {
        output.writeShort(0x0); // Minor version
        output.writeShort(versionNumber);
    }

    public boolean isSupported(JavaVersion other) {
        // TODO do proper compatibility checks
        // See this table: https://docs.oracle.com/javase/specs/jvms/se13/html/jvms-4.html#jvms-4.1-200-B.2
        return other == this;
    }

    public int asInt() {
        return versionNumber;
    }

    @Override
    public String toString() {
        return name();
    }

    private static final Map<Integer, JavaVersion> INT_TO_ENUM_MAP;

    public static JavaVersion fromInt(int tag) {
        return INT_TO_ENUM_MAP.get(tag);
    }

    public static JavaVersion fromBytes(DataInput input) throws IOException {
        short minorVer = input.readShort();
        short majorVer = input.readShort();
        return fromInt(majorVer);
    }

    static {
        INT_TO_ENUM_MAP = new HashMap<>();
        for (JavaVersion version : values())
            INT_TO_ENUM_MAP.put((int)version.versionNumber, version);
    }
}
