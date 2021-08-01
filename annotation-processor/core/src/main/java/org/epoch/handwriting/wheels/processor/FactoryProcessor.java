package org.epoch.handwriting.wheels.processor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import com.google.auto.service.AutoService;
import org.epoch.handwriting.wheels.annotation.Factory;
import org.epoch.handwriting.wheels.exception.ProcessingException;

@AutoService(Processor.class)
@SupportedAnnotationTypes("org.epoch.handwriting.wheels.annotation.*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class FactoryProcessor extends AbstractProcessor {

    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;

    /**
     * 对需要加工厂的类做分类
     * key: parent product interface name
     * value: key:@Factory - id
     * #######value:@Factory - FactoryAnnotatedClass
     */
    private Map<String, FactoryGroupedClasses> factoryGroupedClassesMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        try {
            //遍历所有加了@Factory注解的元素
            for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Factory.class)) {
                if (annotatedElement.getKind() != ElementKind.CLASS) {
                    throw new ProcessingException(annotatedElement, "Only class can be annotated with @%s", Factory.class.getSimpleName());
                }
                TypeElement typeElement = (TypeElement) annotatedElement;
                //封装注解为注解类
                FactoryAnnotatedClass annotatedClass = new FactoryAnnotatedClass(typeElement);
                //factoryGroupedClassesMap<@Factory,Map<"Mile",FactoryAnnotatedClass>>
                FactoryGroupedClasses factoryGroupedClasses = factoryGroupedClassesMap.get(annotatedClass.getQualifiedFactoryGroupName());
                if (factoryGroupedClasses == null) {
                    String qualifiedFactoryGroupName = annotatedClass.getQualifiedFactoryGroupName();
                    factoryGroupedClasses = new FactoryGroupedClasses(qualifiedFactoryGroupName);
                    factoryGroupedClassesMap.put(qualifiedFactoryGroupName, factoryGroupedClasses);
                }
                factoryGroupedClasses.add(annotatedClass);
            }

            for (FactoryGroupedClasses factoryGroupedClasses : factoryGroupedClassesMap.values()) {
                factoryGroupedClasses.generateCode(elementUtils, filer);
            }
            factoryGroupedClassesMap.clear();


        } catch (ProcessingException e) {
            e.printStackTrace();
            error(e.getElement(), e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            error(null, e.getMessage());
        }


        return true;
    }

    public void error(Element e, String msg) {
        messager.printMessage(Diagnostic.Kind.ERROR, msg, e);
    }
}
