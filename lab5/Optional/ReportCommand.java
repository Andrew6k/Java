package com.company;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
public class ReportCommand {
    Catalog catalog;
    public static void report(Catalog catalog) throws TemplateException, IOException {
        Configuration cfg = new Configuration(new Version("2.3.23"));

        cfg.setClassForTemplateLoading(Main.class, "/");
        cfg.setDefaultEncoding("UTF-8");

        Template template = cfg.getTemplate("test.ftl");
        Map<String, Object> templateData = new HashMap<>();
        templateData.put("items", catalog.getItems());

        try (StringWriter out = new StringWriter()) {

            template.process(templateData, out);
            System.out.println(out.getBuffer().toString());

            out.flush();
        }
    }
}
