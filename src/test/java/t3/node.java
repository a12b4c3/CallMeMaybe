package t3;

/* looping control flow test suite
    this tests if the app can handle and draw looping control flows
 */
public class node {
    public void loop_five_times() {
        for (int i=0; i<5; i++) {
            System.out.printf("im looping");
        }
    }

    public void calls_something_that_loops() {
        System.out.printf("start of function");
        loop_five_times();
    }

    public void calls_something_else_that_loops() {
        System.out.printf("start of function");
        calls_something_that_loops();
    }
}
