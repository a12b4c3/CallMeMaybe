package logic;

public class Validator {
    public static void validateNotNull(Object o) {
        if (o == null) {
            throw new RuntimeException("Object is null");
        }
    }

}
