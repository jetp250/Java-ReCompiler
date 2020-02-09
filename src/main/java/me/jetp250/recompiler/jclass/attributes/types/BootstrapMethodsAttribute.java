package me.jetp250.recompiler.jclass.attributes.types;

import me.jetp250.recompiler.jclass.attributes.Attribute;
import me.jetp250.recompiler.jclass.attributes.AttributeType;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class BootstrapMethodsAttribute extends Attribute {
    public static final class BootstrapMethod{
        public final int bootstrapMethodRef;
        public final int[] bootstrapArguments;

        public BootstrapMethod(int ref, int[] arguments) {
            this.bootstrapMethodRef = ref;
            this.bootstrapArguments = arguments;
        }
    }

    private static final int ATTRIBUTE_LENGTH = 0; // TODO: Insert correct value

    public final BootstrapMethod[] bootstrapMethods;

    public BootstrapMethodsAttribute(int attributeNameIndex, BootstrapMethod[] bootstrapMethods){
        super(AttributeType.BOOTSTRAP_METHODS, attributeNameIndex, ATTRIBUTE_LENGTH);
        this.bootstrapMethods = bootstrapMethods;
    }

    public BootstrapMethodsAttribute(int attributeNameIndex, DataInput dataInput) throws IOException {
        super(AttributeType.BOOTSTRAP_METHODS, attributeNameIndex, ATTRIBUTE_LENGTH);
        int lengthCheck = dataInput.readUnsignedShort();
        int methodsCount = dataInput.readUnsignedShort();
        this.bootstrapMethods = new BootstrapMethod[methodsCount];
        for (int i = 0; i < methodsCount; i++) {
            int ref = dataInput.readUnsignedShort();
            int argsCount = dataInput.readUnsignedShort();
            int[] args = new int[argsCount];

            for (int j = 0; j < argsCount; j++){
                args[j] = dataInput.readUnsignedShort();
            }

            bootstrapMethods[i] = new BootstrapMethod(ref, args);
        }

        if(lengthCheck != ATTRIBUTE_LENGTH)
            throw new IllegalStateException("The 'attribute length' parameter of BootstrapMethodsAttribute must be "
                    + ATTRIBUTE_LENGTH + ", was " + lengthCheck);
    }

    @Override
    protected void writeBytes0(DataOutput output) throws IOException {
        output.writeShort(bootstrapMethods.length);
        for (BootstrapMethod method : bootstrapMethods) {
            output.writeShort(method.bootstrapMethodRef);
            output.writeShort(method.bootstrapArguments.length);

            for (int i = 0; i < method.bootstrapArguments.length; i++) {
                output.writeShort(method.bootstrapArguments[i]);
            }
        }
    }
}
