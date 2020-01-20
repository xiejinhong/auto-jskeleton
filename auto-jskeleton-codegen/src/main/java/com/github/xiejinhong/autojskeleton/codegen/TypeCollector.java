package com.github.xiejinhong.autojskeleton.codegen;

import com.google.common.collect.Maps;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Map;

/**
 * The type TypeCollector.
 *
 * @author Miracle.XJH
 * @date 2020年01月19日 14时33分46秒
 */
public final class TypeCollector {
    /**
     * The Type elements.
     */
    private final Map<String, TypeElement> typeElements = Maps.newHashMap();

    /**
     * Sync form.
     *
     * @param roundEnv the round env
     * @author Miracle.XJH
     * @date 2020年01月19日 14时33分46秒
     */
    public void syncForm(RoundEnvironment roundEnv){
        for (Element element :  roundEnv.getRootElements()){
            if (element instanceof TypeElement){
                String name = ((TypeElement) element).getQualifiedName().toString();
                this.typeElements.put(name, (TypeElement) element);
            }
        }
    }

    /**
     * Get by name type element.
     *
     * @param name the name
     * @return the type element
     * @author Miracle.XJH
     * @date 2020年01月19日 14时33分46秒
     */
    public TypeElement getByName(String name){
        return this.typeElements.get(name);
    }
}
