package app;

import com.company.Command;
import com.company.Info;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.Scanner;
public class LocaleExplore  {
    public static  void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String commandName = scanner.next();
            if (commandName.equalsIgnoreCase("exit")) {
                break;
            }
            String language=scanner.next();
            String baseName="res.Messages";
            String country=Locale.forLanguageTag(language).getCountry();

            Locale locale=new Locale(language,country);
            try {
                // The command name is actually the class name
                Class clazz = Class.forName(commandName);
                Command command = (Command) clazz.getConstructor(String.class,String.class,Locale.class).newInstance(language,baseName,locale);
                command.execute();
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                System.err.println(e);
            }
        }
    }

}
