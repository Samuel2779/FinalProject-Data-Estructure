package sample;

public class Node<T> {
    private T element;
    private Node<T> left;
    private Node<T> right;
    private int height;

   public Node(T c, Node<T> g, Node<T> d){
        super();
        left= g;
        element = c;
        right= d;
        height = 1 + Math.max(height(g), height(d));
    }

    public  int height(Node<T> a){
        if(a== null)
            return -1;
        return 1+Math.max(height(a.getLeft()),height(a.getRight()));
    }

    public Node(T element) {
        this.element = element;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
