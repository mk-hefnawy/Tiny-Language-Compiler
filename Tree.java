public  class Tree {

    Node root;

    public Tree(){
        root = null;
    }
    static void printPreorder(Node node)
    {
        if (node == null)
            return;

        System.out.print(node.circle.searchKey + "->");

        printPreorder(node.leftChild);
        printPreorder(node.midChild);

        printPreorder(node.rightChild);
        printPreorder(node.neighbour);
    }

}

