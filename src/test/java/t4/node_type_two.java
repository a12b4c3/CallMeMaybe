package t4;

public class node_type_two implements node {
    @Override
    public void printme() {
        System.out.printf("this is printed from node_type_two");
    }

    @Override
    public int giveme() {
        return 2;
    }
}
