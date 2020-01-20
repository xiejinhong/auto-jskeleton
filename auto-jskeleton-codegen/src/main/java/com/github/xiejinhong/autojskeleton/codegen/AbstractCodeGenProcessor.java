package com.github.xiejinhong.autojskeleton.codegen;

import org.apache.commons.collections4.CollectionUtils;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type AbstractCodeGenProcessor.
 *
 * @author Miracle.XJH
 * @date 2020年01月19日 11时28分35秒
 */
public abstract class AbstractCodeGenProcessor extends AbstractProcessor {
    /**
     * Gets plugins.
     *
     * @return the plugins
     * @author Miracle.XJH
     * @date 2020年01月19日 11时25分33秒
     */
    protected abstract ProcessorPlugin[] getPlugins();

    /**
     * Gets supported annotation types.
     *
     * @return the supported annotation types
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Arrays.stream(getPlugins()).map(ProcessorPlugin::supports)
                .flatMap(Arrays::stream)
                .map(Class::getName)
                .collect(Collectors.toSet());
    }

    /**
     * Gets supported source version.
     *
     * @return the supported source version
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * Process boolean.
     *
     * @param annotations the annotations
     * @param roundEnv    the round env
     * @return the boolean
     * @author Miracle.XJH
     * @date 2020年01月19日 11时04分19秒
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        this.processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "");
        if (CollectionUtils.isEmpty(annotations)) {
            return true;
        }
        for (ProcessorPlugin plugin : this.getPlugins()) {
            try {
                plugin.process(annotations, roundEnv, this.processingEnv);
                plugin.write(this.processingEnv.getFiler(), this.processingEnv);
            } catch (Exception e) {
                this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "处理失败：" + e.getMessage());
            }
        }
        return false;
    }
}
