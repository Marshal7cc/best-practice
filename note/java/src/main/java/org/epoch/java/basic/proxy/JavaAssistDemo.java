package org.epoch.java.basic.proxy;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

/**
 * @author Marshal
 * @since 2022/11/1
 */
public class JavaAssistDemo {
    public static void main(String[] args) throws Exception {
        JavaAssistSubject subject = new JavaAssistSubject();

        // 获取ClassPool
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.get("org.epoch.java.basic.proxy.JavaAssistSubject");
        // 获取sayHelloFinal方法
        CtMethod ctMethod = ctClass.getDeclaredMethod("action");
        // 方法前后进行增强
        ctMethod.insertBefore("{ System.out.println(\"start\");}");
        ctMethod.insertAfter("{ System.out.println(\"end\"); }");
        // CtClass对应的字节码加载到JVM里
        Class c = ctClass.toClass();
        //反射生成增强后的类
        JavaAssistSubject demo = (JavaAssistSubject) c.newInstance();
        demo.action();
    }
}

