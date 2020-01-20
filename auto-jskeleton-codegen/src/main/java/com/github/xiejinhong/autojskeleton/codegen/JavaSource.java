package com.github.xiejinhong.autojskeleton.codegen;

import com.google.common.collect.Sets;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Value;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type JavaSource.
 *
 * @author Miracle.XJH
 * @date 2020年01月19日 12时42分14秒
 */
@Value
public class JavaSource {
    /**
     * The Fields.
     */
    private final Set<String> fields = Sets.newHashSet();
    /**
     * The Methods.
     */
    private final Set<String> methods = Sets.newHashSet();
    /**
     * The Pkg name.
     */
    private final String pkgName;
    /**
     * The Cls name.
     */
    private final String clsName;
    /**
     * The Type spec builder.
     */
    @Getter(AccessLevel.PRIVATE)
    private final TypeSpec.Builder typeSpecBuilder;

    /**
     * Gets full name.
     *
     * @return the full name
     */
    public String getFullName() {
        return pkgName + "." +clsName;
    }

    /**
     * Get type spec type spec.
     *
     * @return the type spec
     * @author Miracle.XJH
     * @date 2020年01月19日 12时42分14秒
     */
    public TypeSpec getTypeSpec(){
        return getTypeSpecBuilder().build();
    }

    /**
     * Add method.
     *
     * @param method the method
     * @author Miracle.XJH
     * @date 2020年01月19日 12时42分14秒
     */
    public void addMethod(MethodSpec method){
        String key = createMethodKey(method);
        if (this.methods.contains(key)){
            throw new RuntimeException(String.format("Repetition Method %s", key));
        }
        this.typeSpecBuilder.addMethod(method);
        this.methods.add(key);
    }

    /**
     * Add field.
     *
     * @param fieldSpec the field spec
     * @author Miracle.XJH
     * @date 2020年01月19日 12时42分14秒
     */
    public void addField(FieldSpec fieldSpec){
        String key = fieldSpec.name;
        if (this.fields.contains(key)){
            throw new RuntimeException(String.format("Repetition Field %s", key));
        }
        this.typeSpecBuilder.addField(fieldSpec);
        this.fields.add(key);
    }

    /**
     * Has field boolean.
     *
     * @param field the field
     * @return the boolean
     * @author Miracle.XJH
     * @date 2020年01月19日 12时42分14秒
     */
    public boolean hasField(String field){
        return fields.contains(field);
    }

    /**
     * Create method key string.
     *
     * @param method the method
     * @return the string
     * @author Miracle.XJH
     * @date 2020年01月19日 12时42分14秒
     */
    private String createMethodKey(MethodSpec method) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(method.name);
        if (CollectionUtils.isEmpty(method.parameters)){
            stringBuilder.append("()");
        }else {
            stringBuilder.append("(");
            stringBuilder.append(method.parameters.stream()
                    .map(parameterSpec -> parameterSpec.type)
                    .map(typeName -> typeName.toString())
                    .collect(Collectors.joining(","))
            );
            stringBuilder.append(")");
        }
        return stringBuilder.toString();
    }

    /**
     * Add type.
     *
     * @param type the type
     * @author Miracle.XJH
     * @date 2020年01月19日 12时42分14秒
     */
    public void addType(TypeSpec type) {
        this.typeSpecBuilder.addType(type);
    }
}
