package org.epoch.java.basic.spi;

/**
 * @author Marshal
 * @since 2022/11/3
 */
public class SpiServiceImpl1 implements SpiService {

    @Override
    public void process() {
        System.out.println(1);
    }
}
