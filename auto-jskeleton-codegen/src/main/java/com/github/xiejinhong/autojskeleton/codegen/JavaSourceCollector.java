package com.github.xiejinhong.autojskeleton.codegen;

import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Map;

/**
 * The type JavaSourceCollector.
 *
 * @author Miracle.XJH
 * @date 2020年01月19日 11时37分26秒
 */
public class JavaSourceCollector {
    /**
     * The Java sources.
     */
    private final Map<String, JavaSource> javaSources = Maps.newHashMap();

    /**
     * Get all java source collection.
     *
     * @return the collection
     * @author Miracle.XJH
     * @date 2020年01月19日 11时37分32秒
     */
    public Collection<JavaSource> getAllJavaSource(){
        return this.javaSources.values();
    }

    /**
     * Get by name java source.
     *
     * @param name the name
     * @return the java source
     * @author Miracle.XJH
     * @date 2020年01月19日 11时37分32秒
     */
    public JavaSource getByName(String name){
        return javaSources.get(name);
    }

    /**
     * Register.
     *
     * @param name       the name
     * @param javaSource the java source
     * @author Miracle.XJH
     * @date 2020年01月19日 11时37分32秒
     */
    public void register(String name, JavaSource javaSource){
        this.javaSources.put(name, javaSource);
    }

    /**
     * Register.
     *
     * @param javaSource the java source
     * @author Miracle.XJH
     * @date 2020年01月19日 11时37分32秒
     */
    public void register(JavaSource javaSource){
        register(javaSource.getFullName(), javaSource);
    }
}
