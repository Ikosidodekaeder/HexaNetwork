package util;

/**
 * Created by Johannes Lüke on 09.12.2017.
 */

public interface Delegate {
    public void invoke(Object... args) throws Exception;
}
