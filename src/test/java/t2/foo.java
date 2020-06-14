package t2;

/* multi-class test suite
    this tests if the parser will recursively parse multiple project files to get more than one call
 */

public class foo extends moo {
    ziff z = new ziff();

    public void calls_one_other_class_no_return() {
        bar b = new bar();
        int a;
        a = 0;
        a += 5;
        System.out.println("im going to bar class");
        b.call_me_no_return();
        System.out.println("ive returned to foo class");
    }

    public int calls_one_other_class_no_return(int c) {
        bar b = new bar();
        int a = 0;
        a += 5;
        c = a + 5;
        System.out.println("im going to bar class");
        b.call_me_no_return();
        System.out.println("ive returned to foo class");
        return c;
    }

    public void calls_one_other_class_returns() {
        bar b = new bar();
        System.out.println("im going to bar class");
        int num = b.call_me_with_return();
        System.out.println("ive returned to foo class with a number: " + num);
    }

    public void calls_two_other_classes_mixed_returns() {
        bar b = new bar();
        System.out.println("im going to bar class");
        int num = b.call_me_with_return();
        baz z = new baz();
        System.out.println("im going to baz class");
        int num2 = z.call_me_with_return();
        System.out.println("i got two numbers bar: " + num + " and baz: " + num2);
    }

    public void calls_other_classes_with_existing_object() {
        bar b = new bar();
        b.call_me_with_return();
        System.out.println("ziff object is never created within this scope");
        System.out.println("new participant should be added in order");
        z.ziffy();
    }
}
