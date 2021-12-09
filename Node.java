public class Node {

    Node leftChild;
    Node rightChild;
    Node midChild;
    Node neighbour;
    String name;
    Circle circle;

    int shape;

    public Node(String name, int shape){

        leftChild = null;
        rightChild = null;
        midChild = null;
        neighbour = null;
        this.shape = shape;
        this.name = name;
        this.circle = new Circle(name);
    }
}