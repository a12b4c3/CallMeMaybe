package t2;

public class bar {
    public void call_me_no_return() {
        System.out.printf("the bar class has been called");
    }

    public int call_me_with_return() {
        System.out.printf("the bar class has been called");
        System.out.println("im returning now");
        return 1;
    }
}
