package com.company;

import java.text.DateFormatSymbols;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

public class Info implements Command {
    public String tag;
    public String baseName;
    public Locale locale;

    public Info(String tag, String baseName, Locale locale) {
        this.tag = tag;
        this.baseName = baseName;
        this.locale = locale;
    }
    public void execute()
    {
        ResourceBundle message=ResourceBundle.getBundle(baseName,this.locale);
        String pattern=message.getString("info");
        Object[] argument={locale.getCountry()};
        String infoAbout=new MessageFormat(pattern).format(argument);
        Calendar calendar =Calendar.getInstance(locale);
        System.out.println(calendar.getCalendarType());
       /* Currency currency=Currency.getInstance(locale);
        System.out.println(currency.getCurrencyCode());*/
        DateFormatSymbols formatulDatei=DateFormatSymbols.getInstance(locale);
        System.out.println("Days:" + Arrays.toString( formatulDatei.getWeekdays()));
        System.out.println("Months:"+Arrays.toString(formatulDatei.getMonths()));
        LocalDateTime today= LocalDateTime.now();
        DateTimeFormatter formatter=DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(locale);
        System.out.println("Date:" +today.format(formatter));

    }
}
