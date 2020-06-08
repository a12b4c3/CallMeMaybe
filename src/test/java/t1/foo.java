package t1;

/* base test suite
    only tests calls within the same class.
 */

public class foo {
    public void calls_one_method() {
        System.out.printf("supposed to ignore this");
        System.out.printf("call bazz next");
        int number = this.bazz();
        System.out.println("the number is " + number);
    }

    public void calls_two_methods() {
        System.out.println("this will call two methods");
        calls_one_method();
        System.out.println("shouldve called two methods");
    }

    public int bazz() {
        return 1;
    }
}
