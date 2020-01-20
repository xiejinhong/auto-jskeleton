package com.github.xiejinhong.autojskeleton.codegen;

import com.squareup.javapoet.JavaFile;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

/**
 * The type AbstractProcessorPlugin.
 *
 * @author Miracle.XJH
 * @date 2020年01月19日 10时58分17秒
 */
@Getter
@Setter(AccessLevel.PRIVATE)
public abstract class AbstractProcessorPlugin implements ProcessorPlugin {
    /**
     * The Java source collector.
     */
    private final JavaSourceCollector javaSourceCollector = new JavaSourceCollector();
    /**
     * The Type collector.
     */
    private TypeCollector typeCollector;

    /**
     * Init.
     *
     * @param typeCollector the type collector
     * @param processingEnv the processing env
     * @author Miracle.XJH
     * @date 2020年01月19日 14时34分59秒
     */
    @Override
    public void init(TypeCollector typeCollector, ProcessingEnvironment processingEnv) {
        this.typeCollector = typeCollector;
    }

    /**
     * Process.
     *
     * @param typeElement   the type element
     * @param annotation    the annotation
     * @param processingEnv the processing env
     * @author Miracle.XJH
     * @date 2020年01月19日 11时04分19秒
     */
    protected abstract void process(TypeElement typeElement, Annotation annotation, ProcessingEnvironment processingEnv);

    /**
     * Process.
     *
     * @param annotations   the annotations
     * @param roundEnv      the round env
     * @param processingEnv the processing env
     * @author Miracle.XJH
     * @date 2020年01月19日 14时22分50秒
     */
    @Override
    public void process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv, ProcessingEnvironment processingEnv) {
        roundEnv.getRootElements().stream()
                .filter(o -> o instanceof TypeElement)
                .filter(o -> !isIgnore((TypeElement) o))
                .forEach(o -> {
                    Optional<Annotation> support = support(o);
                    support.ifPresent(annotationClass -> process((TypeElement) o, annotationClass, processingEnv));
                });
    }

    /**
     * Write.
     *
     * @param filer         the filer
     * @param processingEnv the processing env
     * @author Miracle.XJH
     * @date 2020年01月19日 14时31分35秒
     */
    @Override
    public void write(Filer filer, ProcessingEnvironment processingEnv) {
        javaSourceCollector.getAllJavaSource()
                .forEach(javaSource -> createJavaFile(filer, javaSource));
    }

    /**
     * Create java file.
     *
     * @param filer      the filer
     * @param javaSource the java source
     * @author Miracle.XJH
     * @date 2020年01月19日 14时32分27秒
     */
    private void createJavaFile(Filer filer, JavaSource javaSource) {
        try {
            JavaFile javaFile = JavaFile.builder(javaSource.getPkgName(), javaSource.getTypeSpec())
                    .addFileComment(" This codes are generated automatically. Do not modify!")
                    .build();
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Is ignore boolean.
     *
     * @param typeElement the type element
     * @return the boolean
     * @author Miracle.XJH
     * @date 2020年01月19日 14时22分50秒
     */
    private boolean isIgnore(TypeElement typeElement) {
        Class<Annotation>[] ignore = ignore();
        Optional<Class<Annotation>> any = Arrays.stream(ignore)
                .filter(annotationClass -> typeElement.getAnnotation(annotationClass) != null)
                .findAny();
        return any.isPresent();
    }

    /**
     * Support optional.
     *
     * @param element the element
     * @return the boolean
     * @author Miracle.XJH
     * @date 2020年01月19日 14时25分29秒
     */
    private Optional<Annotation> support(Element element) {
        return Arrays.stream(supports())
                .filter(annotationClass -> element.getAnnotation(annotationClass) != null)
                .map(element::getAnnotation)
                .findFirst();
    }
}
