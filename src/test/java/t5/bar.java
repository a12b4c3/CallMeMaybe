package t5;

public class bar {
    public static void staticMethod() {
        System.out.println("in staticMethod()");
        doesSomething();
    }

    private static void doesSomething() {
        int i = 5;
        int j = 6;
        int k = i + j;
    }
}
