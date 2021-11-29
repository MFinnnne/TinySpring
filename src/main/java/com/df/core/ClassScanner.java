package com.df.core;

import com.df.anno.GetMapping;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author MFine
 * @version 1.0
 * @date 2021/11/17 21:11
 **/
public class ClassScanner {

    static void scan(String pathName) {
        final String scan = pathName.replaceAll("\\.", "/");
        try {
            final Enumeration<URL> enumeration = Thread.currentThread().getContextClassLoader().getResources(scan);
            while (enumeration.hasMoreElements()) {
                final URL url = enumeration.nextElement();
                if (url.getProtocol().equals("file")) {
                    final ArrayList<File> files = new ArrayList<>();
                    listFiles((new File(url.getFile())), files);
                    final List<Class<?>> classes = loadClasses(files, scan);
                    initIoc(classes);
                } else if (url.getProtocol().equals("jar")) {

                }
            }
        } catch (IOException | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private static List<Class<?>> loadClasses(List<File> classes, String scan) throws ClassNotFoundException {
        final ArrayList<Class<?>> clazzes = new ArrayList<>();
        for (File file : classes) {
            final String fPath = file.getAbsolutePath().replaceAll("\\\\", "/");
            String pkgName = fPath.substring(fPath.lastIndexOf(scan));
            pkgName = pkgName.replace(".class", "").replace("/", ".");
            clazzes.add(Class.forName(pkgName));
        }
        return clazzes;
    }

    private static void listFiles(File dir, List<File> fileList) {
        if (dir.isDirectory()) {
            for (File f : dir.listFiles()) {
                listFiles(f, fileList);
            }
        } else {
            if (dir.getName().endsWith(".class")) {
                fileList.add(dir);
            }
        }
    }


    static void initIoc(List<Class<?>> classes) throws IllegalAccessException {
        for (Class<?> clazz : classes) {
            if (clazz.isAnnotation()) {
                continue;
            }
            final Annotation[] annotations = clazz.getAnnotations();
            for (Annotation annotation : annotations) {

                if (Constant.COMPONENT.equals(annotation.annotationType().getName())) {
                    final Object instance = newInstance(clazz);
                    if (instance != null) {
                        IOC.getBeanMap().put(clazz.getName(), instance);
                        injectInstance(clazz);
                    }
                }
                if (Constant.REST_CONTROLLER.equals(annotation.annotationType().getName())) {
                    final Object newInstance = newInstance(clazz);
                    if (newInstance != null) {
                        IOC.getBeanMap().put(clazz.getName(), newInstance);
                    }
                    //url->method
                    requestMapping(clazz, newInstance);
                    injectInstance(clazz);
                }
            }
        }
    }

    private static void requestMapping(Class<?> clazz, Object newInstance) {
        final Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            final Annotation[] as = method.getAnnotations();
            for (Annotation a : as) {
                if (Constant.GET_MAPPING.equals(a.annotationType().getName())) {
                    final GetMapping annotation1 = method.getAnnotation(GetMapping.class);
                    IOC.getRequestMethodMap().put(annotation1.value(), method);
                    IOC.getRequestInstanceMap().put(annotation1.value(), newInstance);
                }
            }
        }
    }

    private static void injectInstance(Class<?> clazz) throws IllegalAccessException {
        final Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            final Annotation[] fieldAnnotations = field.getAnnotations();
            for (Annotation fieldAnnotation : fieldAnnotations) {
                if (Constant.AUTOWIRED.equals(fieldAnnotation.annotationType().getName())) {
                    final Object obj = IOC.getBeanMap().get(field.getType().getName());
                    if (obj == null) {
                        final Object o = newInstance(field.getType());
                        if (o != null) {
                            field.setAccessible(true);
                            IOC.getBeanMap().put(field.getType().getName(), o);
                            final Object whose = IOC.getBeanMap().get(clazz.getName());
                            field.set(whose, o);
                            if (o.getClass().getDeclaredFields().length > 0) {
                                injectInstance(o.getClass());
                            }
                        } else {
                            throw new RuntimeException(field.getType().getName() + " 未注入IOC");
                        }
                    }

                }
            }
        }
    }

    private static Object newInstance(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
