package client;

import java.lang.reflect.Method;

/**
 * @author Marshal
 * @date 2021/8/1
 */
public class Client {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Class.forName("client.product.AnimalFactory");
        Object o = clazz.newInstance();
        Method method = clazz.getDeclaredMethod("create", String.class);

        Object dog = method.invoke(o, "dog");

//        AnimalFactory animalFactory = new AnimalFactory();
//        animalFactory.create("dog");

        System.out.println(222);
    }
}
