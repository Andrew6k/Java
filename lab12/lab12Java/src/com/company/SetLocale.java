package com.company;

import javax.swing.text.html.HTML;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

public class SetLocale implements Command {

    String language;
    String baseName;
    Locale locale;

    public SetLocale(String baseName, String language, Locale locale) {
        this.baseName = baseName;
        this.language = language;
        this.locale = locale;
    }



    @Override
    public void execute() {
        Locale.setDefault(Locale.forLanguageTag(language));
        baseName="res.Messages";
        ResourceBundle message=ResourceBundle.getBundle(baseName,locale);
        String pattern =message.getString("locale.set");
        Object[] argument={locale.getDisplayName()};
        String setareDefaultLocale=new MessageFormat(pattern).format(argument);
        System.out.println(setareDefaultLocale);

    }
}
