package me.jetp250.recompiler.jclass.attributes;

import me.jetp250.recompiler.jclass.attributes.types.*;

import java.io.DataInput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// See https://docs.oracle.com/javase/specs/jvms/se13/html/jvms-4.html#jvms-4.7-310
public enum AttributeType {
    CONSTANT_VALUE("ConstantValue", ConstantValueAttribute::new),
    CODE("Code", CodeAttribute::new),
    EXCEPTIONS("Exceptions", ExceptionsAttribute::new),
    SOURCE_FILE("SourceFile", SourceFileAttribute::new),
    LINE_NUMBER_TABLE("LineNumberTable", LineNumberTableAttribute::new),
    LOCAL_VAR_TABLE("LocalVariableTable", LocalVariableTableAttribute::new),
    INNER_CLASSES("InnerClasses", InnerClassAttribute::new),
    SYNTHETIC("Synthetic", SyntheticAttribute::new),
    DEPRECATED("Deprecated", DeprecatedAttribute::new),
    ENCLOSING_METHOD("EnclosingMethod", EnclosingMethodAttribute::new),
    SIGNATURE("Signature", SignatureAttribute::new),
    SOURCE_DEBUG_EXTENSION("SourceDebugExtensions", SourceDebugExtensionAttribute::new),
    LOCAL_VAR_TYPE_TABLE("LocalVariableTypeTable", LocalVariableTypeTableAttribute::new),
    RUNTIME_VISIBLE_ANNOTATIONS("RuntimeVisibleAnnotations", RuntimeVisibleAnnotationsAttribute::new),
    RUNTIME_VISIBLE_PARAM_ANNOTATIONS("RuntimeVisibleParameterAnnotations", RuntimeVisibleParameterAnnotationsAttribute::new),
    RUNTIME_INVISIBLE_PARAM_ANNOTATIONS("RuntimeInvisibleParameterAnnotations", RuntimeInvisibleParameterAnnotationAttribute::new),
    ANNOTATION_DEFAULT("AnnotationDefault", AnnotationDefaultAttribute::new),
    STACK_MAP_TABLE("StackMapTable", StackMapTableAttribute::new),
    BOOTSTRAP_METHODS("BootstrapMethods", BootstrapMethodsAttribute::new),
    RUNTIME_VISIBLE_TYPE_ANNOTATIONS("RuntimeVisibleTypeAnnotations", RuntimeVisibleTypeAnnotationsAttribute::new),
    RUNTIME_INVISIBLE_TYPE_ANNOTATIONS("RuntimeInvisibleTypeAnnotations", RuntimeInvisibleTypeAnnotationsAttribute::new),
    METHOD_PARAMS("MethodParameters", MethodParametersAttribute::new),
    MODULE("Module", ModuleAttribute::new),
    MODULE_PACKAGES("ModulePackages", ModulePackagesAttribute::new),
    MODULE_MAIN_CLASS("ModuleMainClass", ModuleMainClassAttribute::new),
    NEST_HOST("NestHost", NestHostAttribute::new),
    NEST_MEMBERS("NestMembers", NestMembersAttribute::new);

    interface AttributeFactory {
        Attribute create(int attributeNameIndex, DataInput input) throws IOException;
    }

    private final String tag;
    private final AttributeFactory factory;

    AttributeType(String tag, AttributeFactory factory) {
        this.tag = tag;
        this.factory = factory;
    }

    public Attribute readAttribute(int attributeNameIndex, DataInput input) throws IOException {
        return factory.create(attributeNameIndex, input);
    }

    private static Map<String, AttributeType> TAG_TO_ENUM_MAP;

    public static AttributeType fromTag(String tag) {
        return TAG_TO_ENUM_MAP.get(tag);
    }

    static {
        TAG_TO_ENUM_MAP = new HashMap<>();
        for (AttributeType type : values()) {
            TAG_TO_ENUM_MAP.put(type.tag, type);
        }
    }

}
