package t1;

/* base test suite
    only tests calls within the same class.
 */

public class foo {
    public String calls_one_method() {
        System.out.printf("supposed to ignore this");
        System.out.printf("call bazz next");
        int number = this.bazz();
        int number2 = this.bazz(3, "s");
        this.biff();
        System.out.println("the number is " + number);
        return "hello!";
    }

    public void calls_two_methods() {
        System.out.println("this will call two methods");
        String hello = this.calls_one_method();
        System.out.println("shouldve called two methods");
    }

    public int bazz(int i, String s) { return i; }
    public int bazz() {
        return 1;
    }
    public void biff() {
        int a = 1;
        int b = 2;
        int c = a+b;
    }


}
