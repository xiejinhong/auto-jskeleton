package com.github.xiejinhong.autojskeleton.codegen;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * The type ProcessorPlugin.
 *
 * @author Miracle.XJH
 * @date 2020年01月19日 11时07分09秒
 */
public interface ProcessorPlugin {

    /**
     * Init.
     *
     * @param typeCollector the type collector
     * @param processingEnv the processing env
     * @author Miracle.XJH
     * @date 2020年01月19日 14时34分10秒
     */
    void init(TypeCollector typeCollector, ProcessingEnvironment processingEnv);

    /**
     * Supports class [ ].
     *
     * @param <A> the type parameter
     * @return the class [ ]
     * @author Miracle.XJH
     * @date 2020年01月19日 11时07分36秒
     */
    default <A extends Annotation> Class<A>[] supports() {
        return new Class[0];
    }

    /**
     * Ignore class [ ].
     *
     * @param <A> the type parameter
     * @return the class [ ]
     * @author Miracle.XJH
     * @date 2020年01月19日 11时08分02秒
     */
    default <A extends Annotation> Class<A>[] ignore() {
        return new Class[0];
    }

    /**
     * Process.
     *
     * @param annotations   the annotations
     * @param roundEnv      the round env
     * @param processingEnv the processing env
     * @author Miracle.XJH
     * @date 2020年01月19日 11时13分42秒
     */
    void process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv, ProcessingEnvironment processingEnv);

    /**
     * Write.
     *
     * @param filer         the filer
     * @param processingEnv the processing env
     * @author Miracle.XJH
     * @date 2020年01月19日 11时13分42秒
     */
    void write(Filer filer, ProcessingEnvironment processingEnv);

    /**
     * Info.
     *
     * @param processingEnv the processing env
     * @param message       the message
     * @author Miracle.XJH
     * @date 2020年01月19日 17时19分30秒
     */
    default void info(ProcessingEnvironment processingEnv, String message) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);
    }

    /**
     * Gets pkg name.
     * 获取类包名
     *
     * @param typeElement the type element
     * @return the pkg name
     */
    default String getPkgName(TypeElement typeElement) {
        return typeElement.getEnclosingElement().toString();
    }

    /**
     * Error.
     *
     * @param processingEnv the processing env
     * @param message       the message
     * @author Miracle.XJH
     * @date 2020年01月19日 17时20分44秒
     */
    default void error(ProcessingEnvironment processingEnv, String message) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message);
    }

    /**
     * Warn.
     *
     * @param processingEnv the processing env
     * @param message       the message
     * @author Miracle.XJH
     * @date 2020年01月19日 17时21分00秒
     */
    default void warn(ProcessingEnvironment processingEnv, String message) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING, message);
    }
}
