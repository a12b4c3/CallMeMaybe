package t2;

public class baz {
    public void call_me_no_return() {
        System.out.printf("the baz class has been called");
    }

    public int call_me_with_return() {
        System.out.printf("the baz class has been called");
        System.out.println("im returning now");
        return 2;
    }
}
