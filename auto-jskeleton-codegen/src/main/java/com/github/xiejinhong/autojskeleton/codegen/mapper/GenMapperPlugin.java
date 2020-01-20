package com.github.xiejinhong.autojskeleton.codegen.mapper;

import com.github.xiejinhong.autojskeleton.annotation.GenMapper;
import com.github.xiejinhong.autojskeleton.codegen.AbstractProcessorPlugin;
import com.github.xiejinhong.autojskeleton.codegen.JavaSource;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;

/**
 * The type MapperProcessorPlugin.
 *
 * @author Miracle.XJH
 * @date 2020年01月19日 14时35分20秒
 */
public class GenMapperPlugin extends AbstractProcessorPlugin {
    /**
     * Process.
     *
     * @param typeElement   the type element
     * @param annotation    the annotation
     * @param processingEnv the processing env
     * @author Miracle.XJH
     * @date 2020年01月19日 14时35分52秒
     */
    @Override
    protected void process(TypeElement typeElement, Annotation annotation, ProcessingEnvironment processingEnv) {
        Name qualifiedName = typeElement.getQualifiedName();
        info(processingEnv, "待处理类：" + qualifiedName);
        String pkgName = getPkgName(typeElement);
        info(processingEnv, "包名：" + pkgName);
        Name simpleName = typeElement.getSimpleName();
        String mapperClsName = simpleName + "Mapper";
        TypeSpec.Builder builder = TypeSpec.classBuilder(mapperClsName)
                .addModifiers(Modifier.PUBLIC)
                .addField(FieldSpec.builder(TypeName.INT, "userId", Modifier.PRIVATE).build());
        JavaSource mapperSource = new JavaSource(pkgName + ".mapper", mapperClsName, builder);
        getJavaSourceCollector().register(mapperSource);
    }

    /**
     * Supports class [ ].
     *
     * @param <A> the type parameter
     * @return the class [ ]
     * @author Miracle.XJH
     * @date 2020年01月19日 14时35分52秒
     */
    @Override
    public <A extends Annotation> Class<A>[] supports() {
        return new Class[]{GenMapper.class};
    }

    /**
     * Ignore class [ ].
     *
     * @param <A> the type parameter
     * @return the class [ ]
     * @author Miracle.XJH
     * @date 2020年01月19日 14时35分52秒
     */
    @Override
    public <A extends Annotation> Class<A>[] ignore() {
        return new Class[0];
    }
}
