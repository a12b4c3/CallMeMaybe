package t4;

public class node_type_one implements node {

    @Override
    public void printme() {
        System.out.printf("this is printed from node type one");
    }

    @Override
    public int giveme() {
        return 1;
    }
}
