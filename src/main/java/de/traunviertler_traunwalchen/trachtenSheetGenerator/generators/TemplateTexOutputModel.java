package de.traunviertler_traunwalchen.trachtenSheetGenerator.generators;

import freemarker.core.CommonMarkupOutputFormat;
import freemarker.core.CommonTemplateMarkupOutputModel;

public class TemplateTexOutputModel extends CommonTemplateMarkupOutputModel<TemplateTexOutputModel> {
    protected TemplateTexOutputModel(String plainTextContent, String markupContent) {
        super(plainTextContent, markupContent);
    }

    @Override
    public CommonMarkupOutputFormat<TemplateTexOutputModel> getOutputFormat() {
        return TexOutputFormat.INSTANCE;
    }
}
