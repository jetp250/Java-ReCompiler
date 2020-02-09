package me.jetp250.recompiler.jclass.attributes;

import me.jetp250.recompiler.jclass.constants.ConstantPool;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

public class AttributeMap {
    private final Map<AttributeType, Attribute> attributes;

    public AttributeMap() {
        this.attributes = new EnumMap<>(AttributeType.class);
    }

    public Attribute getAttribute(AttributeType type) {
        return attributes.get(type);
    }

    public void addAttribute(Attribute attribute) {
        attributes.put(attribute.getType(), attribute);
    }

    public void writeBytes(DataOutput output, ConstantPool pool) throws IOException {
        // TODO
    }

    public static AttributeMap fromBytes(DataInput input, ConstantPool pool) throws IOException {
        AttributeMap map = new AttributeMap();

        int numAttributes = input.readUnsignedShort();
        for (int i = 0; i < numAttributes; ++i) {
            Attribute attribute = Attribute.fromBytes(input, pool);
            map.addAttribute(attribute);
        }
        return map;
    }

}
