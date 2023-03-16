package github.javaxbx.extension;

/**
 * 用于给不能修改的常量提供一个可修改的方式
 * @param <T>
 */
public class Holder<T> {

    private volatile T value;

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }
}
