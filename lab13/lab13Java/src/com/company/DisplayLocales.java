package com.company;

import java.util.Locale;
import java.util.ResourceBundle;

public class DisplayLocales implements Command{

        public String language ;
        public String baseName ="res.Messages";
        public Locale locale;

    public DisplayLocales(String baseName, String language, Locale locale) {
        this.baseName = baseName;
        this.language = language;
        this.locale = locale;
    }

    public void execute()
        {
            ResourceBundle messages=ResourceBundle.getBundle(baseName,locale);
            System.out.println(messages.getString("locales"));
            Locale[] available=Locale.getAvailableLocales();
            for(Locale l : available)
            {
                System.out.println(l.getDisplayName());
            }

        }


}
