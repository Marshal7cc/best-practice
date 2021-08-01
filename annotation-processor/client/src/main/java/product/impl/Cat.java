package product.impl;

import org.epoch.handwriting.wheels.annotation.Factory;
import product.Animal;

/**
 * @author Marshal
 * @date 2021/8/1
 */
@Factory(id = "cat", type = Animal.class)
public class Cat implements Animal {
}
