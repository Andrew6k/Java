package app;

import java.io.File;
import java.lang.reflect.*;
import java.net.URL;
import java.net.URLClassLoader;

public class MyFramework {
    public static void main(String[] args) throws Exception {
        int passed = 0, failed = 0;
        MyClassLoader myLoader1 = new MyClassLoader();
        //myLoader1.loadClass("faculty.java.lab11.Student");
        File path = new File("d:\\Faculty\\java\\lab10\\build\\classes\\com\\example\\model\\Actor.class");
        myLoader1.loadClass("d:\\Faculty\\java\\lab10\\build\\classes\\com\\example\\model\\Actor.class");
        if(path.exists()) {
            URL url = path.toURI().toURL();
            myLoader1.addURL(url);
        }
        URLClassLoader urlLoader = new URLClassLoader(
                new URL[0],
        this.getClass().getClassLoader());
        urlLoader.loadClass("d:\\Faculty\\java\\lab10\\build\\classes\\com\\example\\model\\Actor.class");

        for (Method m : Class.forName(args[0]).getMethods()) {
            if (m.isAnnotationPresent(myLoader1.getClass())) {
                try {
                    m.invoke(null);
                    passed++;
                } catch (Throwable ex) {
                    System.out.printf("Test %s failed: %s %n",
                            m, ex.getCause());
                    failed++;
                }
            }
        }
        System.out.printf("Passed: %d, Failed %d%n", passed, failed);
    }
}