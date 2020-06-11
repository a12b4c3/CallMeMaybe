package logic;

import java.util.Set;

public class Validator {
    public static void validateNotNull(Object o) {
        if (o == null) {
            throw new RuntimeException("Object is null");
        }
    }
}
