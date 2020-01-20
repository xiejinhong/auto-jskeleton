package com.github.xiejinhong.autojskeleton.codegen;

import com.github.xiejinhong.autojskeleton.codegen.mapper.GenMapperPlugin;
import com.google.auto.service.AutoService;

import javax.annotation.processing.Processor;

/**
 * The type CrudCodeGenProcessor.
 *
 * @author Miracle.XJH
 * @date 2020年01月19日 14时43分23秒
 */
@AutoService(Processor.class)
public class CrudCodeGenProcessor extends AbstractCodeGenProcessor {
    /**
     * Get plugins processor plugin [ ].
     *
     * @return the processor plugin [ ]
     * @author Miracle.XJH
     * @date 2020年01月19日 14时43分23秒
     */
    @Override
    protected ProcessorPlugin[] getPlugins() {
        return new ProcessorPlugin[]{
                new GenMapperPlugin()
        };
    }
}
