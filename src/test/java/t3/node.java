package t3;

/* looping control flow test suite
    this tests if the app can handle and draw looping control flows
 */
public class node {
    public void loop_five_times() {
        for (int i = 0; i < 5; i++) {
            if (i == 2) {
                System.out.printf("another line here");
            } else if (i == 3) {
                System.out.printf("another line there");
            } else {
                this.loop_this();
            }
        }
    }

    public void loop_this() {
        System.out.printf("im looping");
    }

    public void calls_something_that_loops() {
        System.out.printf("start of function");
        this.loop_five_times();
    }

    public void calls_something_else_that_loops() {
        System.out.printf("start of function");
        this.calls_something_that_loops();
    }
}
