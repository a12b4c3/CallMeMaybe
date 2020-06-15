package t5;

public class foo {
    int ifield = 5;
    String sfield = "hello";
    int fillmein;

    public foo() {
        this.fillmein = 5;
    }

    private void local_method() {
        this.returnsNumber(ifield);

        int hey = this.returnsNumber(ifield);

        for (int i = 0; i < ifield; i++) {
            for (int j = 4; j < ifield; j++) {
                System.out.println("how does it handle nested loops?");
                bar.staticMethod();
            }
            if (i == 3) {
                returnsNumber(ifield);
            }
        }
        while(this.ifield <5) {
            System.out.println("incrementing ifield");
            this.ifield++;
        }
    }

    private int returnsNumber(int k) {
        System.out.println("in returnsNumber");
        return k;
    }
}
