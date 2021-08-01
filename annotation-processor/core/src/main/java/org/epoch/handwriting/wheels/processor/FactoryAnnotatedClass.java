package org.epoch.handwriting.wheels.processor;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;

import lombok.Getter;
import org.epoch.handwriting.wheels.annotation.Factory;

@Getter
public class FactoryAnnotatedClass {
    private TypeElement typeElement; //获取被@Factory注解的原始元素
    private String qualifiedFactoryGroupName;//获取在 Factory#type()指定的类型合法全名
    private String simpleFactoryGroupName; //获取在 Factory#type() 中指定的类型的简单名字
    private String id; //获取在Factory#id()中指定的id

    /**
     * 注意 alcass是一个真正的Class对象
     *
     * @param classElement
     */
    public FactoryAnnotatedClass(TypeElement classElement) {
        try {
            //加了@Factory的代码已经编译过了
            Factory annotation = classElement.getAnnotation(Factory.class);
            id = annotation.id();
            Class aClass = annotation.type();
            qualifiedFactoryGroupName = aClass.getCanonicalName();
            simpleFactoryGroupName = aClass.getSimpleName();
            typeElement = classElement;
        } catch (MirroredTypeException e) {
            DeclaredType classTypeMirror = (DeclaredType) e.getTypeMirror();
            TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
            qualifiedFactoryGroupName = classTypeElement.getQualifiedName().toString();
            simpleFactoryGroupName = classTypeElement.getSimpleName().toString();
            typeElement = classElement;
        }

    }

}
