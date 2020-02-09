package me.jetp250.recompiler.jclass.attributes.types;

import me.jetp250.recompiler.jclass.attributes.Attribute;
import me.jetp250.recompiler.jclass.attributes.AttributeType;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public final class AnnotationDefaultAttribute extends Attribute {
    private static final int ATTRIBUTE_LENGTH = 0; // TODO: Insert correct value

    public AnnotationDefaultAttribute(int attributeNameIndex) {
        super(AttributeType.ANNOTATION_DEFAULT, attributeNameIndex, ATTRIBUTE_LENGTH);
    }

    public AnnotationDefaultAttribute(int attributeNameIndex, DataInput dataInput) throws IOException {
        super(AttributeType.ANNOTATION_DEFAULT, attributeNameIndex, ATTRIBUTE_LENGTH);
        int lengthCheck = dataInput.readUnsignedShort();

        if (lengthCheck != ATTRIBUTE_LENGTH)
            throw new IllegalStateException("The 'attribute length' parameter of AnnotationDefaultAttribute must be "
                    + ATTRIBUTE_LENGTH + ", was " + lengthCheck);
    }

    @Override
    protected void writeBytes0(DataOutput output) throws IOException {

    }
}
