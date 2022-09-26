
package avltree;

public class Main {
    public static void main(String[] args) {
        MyAVL avl = new MyAVL();
        testCaseTwo(avl);
        // AVL avl2 = new AVL();
        // testCaseTwo(avl2);
        avl.delete(14);
        System.out.println();
        avl.levelOrder(avl.root);
    }

    public static void testCaseOne(AVL avl) {
        avl.insert(5);
        avl.insert(10);
        avl.insert(15);
        // avl.insert(20);
        avl.levelOrder(avl.root);
    }

    public static void testCaseTwo(MyAVL avl) {
        avl.insert(14);
        avl.insert(17);
        avl.insert(11);
        avl.insert(7);
        avl.insert(53);
        avl.insert(4);
        avl.insert(13);
        avl.insert(12);
        avl.insert(8);
        System.out.println();
        avl.levelOrder(avl.root);
    }

    public static void testCaseTwo(AVL avl) {
        avl.insert(14);
        avl.insert(17);
        avl.insert(11);
        avl.insert(7);
        avl.insert(53);
        avl.insert(4);
        avl.insert(13);
        avl.insert(12);
        avl.insert(8);
        System.out.println();
        avl.levelOrder(avl.root);
    }

    // test thêm các test case trong slide powerpoint nữa
    // + thêm các test case trong video lúc ông đó giải thích

    public static void testCaseThree(AVL avl) {
        avl.insert(30);
        avl.insert(50);
        avl.insert(60);
        avl.insert(70);
        avl.insert(90);
        avl.insert(20);

        avl.levelOrder(avl.root);
    }

}
