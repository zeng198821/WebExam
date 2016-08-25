package com.zeng.validator;

import com.zeng.Tool.ParameterInfo;
import com.zeng.Tool.reflect;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

/**
 * Created by zeng on 2016-07-19.
 */
public class ValidatorTestMain {
    public static void main(String[] args) {
        Scanners tmpScanners = new Scanners("com.zeng.org");
        tmpScanners.scan();
        HashMap<String, ParameterInfo[]> tmpserverLIst = tmpScanners.getServerListMap();
        for (String tmpserverName : tmpserverLIst.keySet()) {
            System.out.print(tmpserverName+"     ");
            ParameterInfo[] tmpParameterInfos = tmpserverLIst.get(tmpserverName);
            for (ParameterInfo tmpParameterInfo : tmpParameterInfos) {
                System.out.print(tmpParameterInfo.getParamaterName()+" ");
            }
            System.out.println("");
        }
        System.out.println("Over");
//       try {
//            List<Class<?>> classList = getClasses("com.zeng.org");
//            if(classList !=null && classList.size() > 0){
//                scanClasses(classList);
//
//            }
//            for(Class<?> tmpClass : classList){
//                System.out.println(tmpClass.getName());
//
//            }
//            //Method[] methods = ReflectionUtils.getAllDeclaredMethods(bean.getClass());
//            for (Method method : ValidatorTest.class
//                    .getClassLoader()
//                    .loadClass(("com.zeng.org.validator.ValidatorTest"))
//                    .getMethods()) {
//                // checks if MethodInfo annotation is present for the method
//                if (method.isAnnotationPresent(com.zeng.org.validator.ExtValidator.class)) {
//                    try {
//                        // iterates all the annotations available in the method
////                        for (ExtValidator anno : method.getDeclaredAnnotations()) {
////                            System.out.println("Annotation in Method '" + method + "' : " + anno);
////                        }
//                        ExtValidator methodAnno = method.getAnnotation(ExtValidator.class);
//                        String methodAnnoStr = methodAnno.value();
//                        if (methodAnnoStr != null ) {
//                            System.out.println("ValidatMethodName : " + methodAnnoStr);
//                        }
//
//                    } catch (Throwable ex) {
//                    }
//                }
//            }
//        } catch (SecurityException | ClassNotFoundException e) {
//        }
    }

    /**
     * 扫描类中是否包含对外服务函数
     *
     * @param class_para 类对象
     * @return
     */
    public static HashMap<String, ParameterInfo[]> scanClass(Class<?> class_para) {
        HashMap<String, ParameterInfo[]> tmpParameterList = new HashMap<String, ParameterInfo[]>();
        try {
            for (Method method : ValidatorTest.class
                    .getClassLoader()
                    .loadClass(class_para.getName())
                    .getMethods()) {
                // checks if MethodInfo annotation is present for the method
                if (method.isAnnotationPresent(com.zeng.validator.ExtValidator.class)) {
                    try {
                        ExtValidator methodAnno = method.getAnnotation(ExtValidator.class);
                        String methodAnnoStr = methodAnno.value();
                        if (methodAnnoStr != null) {
                            ParameterInfo[] tmpParameterInfo = getParamaters(class_para.getName(), method.getName());
                            tmpParameterList.put(methodAnnoStr.equals("medthodName") ? class_para.getName() + "." + method.getName() : methodAnnoStr, tmpParameterInfo);
                            //System.out.println("ValidatMethodName : " + methodAnnoStr);
                        }
                    } catch (Throwable ex) {
                    }
                }
            }
        } catch (SecurityException | ClassNotFoundException e) {
        }
        return tmpParameterList;
    }

    /**
     * 扫描类列表中是否包含对外服务函数
     *
     * @param classList_para
     */
    public static void scanClasses(List<Class<?>> classList_para) {
        HashMap<String, ParameterInfo[]> tmpAllParameterList = new HashMap<String, ParameterInfo[]>();
        HashMap<String, ParameterInfo[]> tmpParameterList = null;
        if (classList_para == null) {
            return;
        }
        for (Class<?> tmpClass : classList_para) {
            tmpParameterList = scanClass(tmpClass);
            if (tmpParameterList != null && tmpParameterList.size() > 0) {
                tmpAllParameterList.putAll(tmpParameterList);
            }
        }
        System.out.println("Scan Success");
    }

    /**
     *
     * @param className_para
     * @param methondName_para
     * @return
     */
    public static ParameterInfo[] getParamaters(String className_para, String methondName_para) {
        ParameterInfo[] tmprtn = null;
        Class<?> clazz = null;
        int tmpParameterListLength = 0;
        try {
            clazz = Class.forName(className_para);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(reflect.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (clazz == null) {
            return tmprtn;
        }
        Method[] methods = clazz.getMethods();
        Method m = null;
        for (Method method : methods) {
            if (methondName_para.equals(method.getName())) {
                m = method;
                break;
            }
        }
        if (m == null) {
            return tmprtn;
        }
        Class[] ParameterList = m.getParameterTypes();

        if (ParameterList == null || ParameterList.length == 0) {
            return tmprtn;
        }
        tmprtn = new ParameterInfo[ParameterList.length];
        try {
            ClassPool pool = ClassPool.getDefault();
            CtClass cc = pool.getOrNull(clazz.getName());
            if (cc == null) {
                pool.insertClassPath(new ClassClassPath(clazz));
                cc = pool.get(clazz.getName());
            }
            CtMethod cm = cc.getDeclaredMethod(methondName_para);
            // 使用javaassist的反射方法获取方法的参数名
            MethodInfo methodInfo = cm.getMethodInfo();
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
            if (attr == null) {
                return tmprtn;
            }
            tmpParameterListLength = cm.getParameterTypes().length;
            int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
            for (int i = 0; i < tmpParameterListLength; i++) {
                ParameterInfo tmpinfo = new ParameterInfo();
                tmpinfo.setIndex(i);
                tmpinfo.setParamaterName(attr.variableName(i + pos));
                tmpinfo.setArameterClass(ParameterList[i]);
                tmprtn[i] = tmpinfo;
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return tmprtn;
    }

    /**
     * 打印类名称
     */
    public static void printClassName() {
        String tmpPackageStr = "com.zeng.org.service";
        List<Class<?>> classList = getClasses(tmpPackageStr);
        for (Class<?> tmpClass : classList) {
            System.out.println("ClassName:" + tmpClass.getName());
        }
    }

    /**
     * 从包package中获取所有的Class
     *
     * @param packageName
     * @return
     */
    public static List<Class<?>> getClasses(String packageName) {

        //第一个class类的集合
        List<Class<?>> classes = new ArrayList<Class<?>>();
        //是否循环迭代
        boolean recursive = true;
        //获取包的名字 并进行替换
        String packageDirName = packageName.replace('.', '/');
        //定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            //循环迭代下去
            while (dirs.hasMoreElements()) {
                //获取下一个元素
                URL url = dirs.nextElement();
                //得到协议的名称
                String protocol = url.getProtocol();
                if (null != protocol) //如果是以文件的形式保存在服务器上
                {
                    switch (protocol) {
                        case "file":
                            //获取包的物理路径
                            String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                            //以文件的方式扫描整个包下的文件 并添加到集合中
                            findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                            break;
                        case "jar":
                            //如果是jar包文件
                            //定义一个JarFile
                            JarFile jar;
                            try {
                                //获取jar
                                jar = ((JarURLConnection) url.openConnection()).getJarFile();
                                //从此jar包 得到一个枚举类
                                Enumeration<JarEntry> entries = jar.entries();
                                //同样的进行循环迭代
                                while (entries.hasMoreElements()) {
                                    //获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                                    JarEntry entry = entries.nextElement();
                                    String name = entry.getName();
                                    //如果是以/开头的
                                    if (name.charAt(0) == '/') {
                                        //获取后面的字符串
                                        name = name.substring(1);
                                    }
                                    //如果前半部分和定义的包名相同
                                    if (name.startsWith(packageDirName)) {
                                        int idx = name.lastIndexOf('/');
                                        //如果以"/"结尾 是一个包
                                        if (idx != -1) {
                                            //获取包名 把"/"替换成"."
                                            packageName = name.substring(0, idx).replace('/', '.');
                                        }
                                        //如果可以迭代下去 并且是一个包
                                        if ((idx != -1) || recursive) {
                                            //如果是一个.class文件 而且不是目录
                                            if (name.endsWith(".class") && !entry.isDirectory()) {
                                                //去掉后面的".class" 获取真正的类名
                                                String className = name.substring(packageName.length() + 1, name.length() - 6);
                                                try {
                                                    //添加到classes
                                                    classes.add(Class.forName(packageName + '.' + className));
                                                } catch (ClassNotFoundException e) {
                                                }
                                            }
                                        }
                                    }
                                }
                            } catch (IOException e) {
                            }
                            break;
                    }
                }
            }
        } catch (IOException e) {
        }

        return classes;
    }

    /**
     * 以文件的形式来获取包下的所有Class
     *
     * @param packageName
     * @param packagePath
     * @param recursive
     * @param classes
     */
    public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, List<Class<?>> classes) {
        //获取此包的目录 建立一个File
        File dir = new File(packagePath);
        //如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        //如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(new FileFilter() {
            //自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            public boolean accept(File file) {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });
        //循环所有文件
        for (File file : dirfiles) {
            //如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(),
                        file.getAbsolutePath(),
                        recursive,
                        classes);
            } else {
                //如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    //添加到集合中去
                    classes.add(Class.forName(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
