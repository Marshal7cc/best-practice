package product.impl;

import org.epoch.handwriting.wheels.annotation.Factory;
import product.Animal;

/**
 * @author Marshal
 * @date 2021/8/1
 */
@Factory(id = "dog", type = Animal.class)
public class Dog implements Animal {
}
