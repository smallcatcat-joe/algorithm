package tree.bst_tree;

import java.util.Iterator;
import java.util.Stack;

/**
 * Created by smallcatcat on 2019/1/14.
 */
public class BST<Key extends Comparable<Key>, Value> {
    private Node root;

    // 节点类
    private class Node {
        private Key key;
        private Value val;
        private Node left;
        private Node right;
        private int N;  // 该节点为根的子树的节点总数

        public Node(Key key, Value val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }

    // 返回二叉查找树的大小
    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (node == null) {
            return 0;
        }

        return node.N;
    }

    // 插入节点
    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    private Node put(Node node, Key key, Value val) {
        // 查找不到就创建一个子节点，并返回到上层设置父节点的左节点
        if (node == null) {
            return new Node(key, val, 1);
        }

        int cmp = key.compareTo(node.key);

        if (cmp < 0) {
            // 递归并返回该节点的左节点
            node.left = put(node.left, key, val);
        } else if (cmp > 0) {
            // 递归并返回该节点的右节点
            node.right = put(node.right, key, val);
        } else {
            node.val = val;
        }

        // 更新该节点为根节点的子树节点数量
        node.N = size(node.left) + size(node.right) + 1;

        return node;
    }

    // 获取节点的值
    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node node, Key key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);

        if (cmp < 0) {
            return get(node.left, key);
        } else if (cmp > 0) {
            return get(node.right, key);
        } else {
            return node.val;
        }
    }

    // 查找二叉树最小的key
    public Key min() {
        return min(root).key;
    }

    private Node min(Node node) {
        if (node.left == null) {
            return node;
        } else {
            return min(node.left);
        }
    }

    // 查找二叉树最大的key
    public Key max() {
        return max(root).key;
    }

    private Node max(Node node) {
        if (node.right == null) {
            return node;
        } else {
            return max(node.right);
        }
    }

    // 向下取整
    public Key floor(Key key) {
        Node node = floor(root, key);

        if (node == null) {
            return null;
        }

        return node.key;
    }

    private Node floor(Node node, Key key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);

        if (cmp < 0) {
            return floor(node.left, key);
        } else if (cmp > 0) {
            Node leftNode = floor(node.right, key);

            // 找到一个比key小的值，那就返回这个点，否则则返回当前节点
            if (leftNode != null) {
                return leftNode;
            } else {
                return node;
            }
        } else {
            return node;
        }
    }

    // 向上取整
    public Key ceiling(Key key) {
        Node node = ceiling(root, key);

        if (node == null) {
            return null;
        }

        return node.key;
    }

    private Node ceiling(Node node, Key key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);

        if (cmp > 0) {
            return ceiling(node.right, key);
        } else if (cmp < 0) {
            Node rightNode = ceiling(node.left, key);

            // 找到一个比key大的值，那就返回这个点，否则则返回当前节点
            if (rightNode != null) {
                return rightNode;
            } else {
                return node;
            }
        } else {
            return node;
        }
    }

    // 查找位于num位置的节点
    public Key select(int num) {
        return select(root, num).key;
    }

    private Node select(Node node, int num) {
        if (node == null) {
            return null;
        }

        int leftSize = size(node.left);

        if (num > leftSize) {
            return select(node.right, num - leftSize - 1);
        } else if (num < leftSize) {
            return select(node.left, num);
        } else {
            return node;
        }
    }

    // 查找key的排名
    public int rank(Key key) {
        return rank(key, root);
    }

    public int rank(Key key, Node node) {
        if (node == null) {
            return 0;
        }

        int cmp = key.compareTo(node.key);

        if (cmp > 0) {
            return  1 + size(node.left) + rank(key, node.right);
        } else if (cmp < 0) {
            return rank(key, node.left);
        } else {
            return size(node.left);
        }
    }

    // 删除key最小的节点
    public void deleteMin() {
        root = deleteMin(root);
    }

    private Node deleteMin(Node node) {
        if (node.left == null) {
            return node.right;
        }

        // 一般返回的节点为左节点，找到最小节点则返回右节点
        node.left = deleteMin(node.left);
        node.N = size(node.left) + size(node.right) + 1;

        return node;
    }

    // 删除key最大的节点
    public void deleteMax() {
        root = deleteMax(root);
    }

    private Node deleteMax(Node node) {
        if (node.right == null) {
            return node.left;
        }

        // 一般返回的节点为左节点，找到最小节点则返回右节点
        node.right = deleteMax(node.right);
        node.N = size(node.left) + size(node.right) + 1;

        return node;
    }

    // 删除指定节点
    public void delete(Key key) {
        delete(root, key);
    }

    private Node delete(Node node, Key key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);

        if (cmp > 0) {
            // 继续往右子节点递归，并且回溯的时候设置该节点的节点数数量
            node.right = delete(node.right, key);
        } else if (cmp < 0) {
            // 继续往左子节点递归，并且回溯的时候设置该节点的节点数数量
            node.left = delete(node.left, key);
        } else {
            if (node.right == null) {
                return node.left;
            }
            if (node.left == null) {
                return node.right;
            }

            // 删除右子树的最小节点，获取后将右子树最小节点替换为当前节点，然后返回
            Node rightMin = deleteMin(node.right);

            // 右子树最小节点的左右子树分别替换为被删除节点的左右子树
            rightMin.left = node.left;
            rightMin.right = node.right;

            return rightMin;
        }

        // 回溯的时候重置节点树数量
        node.N = size(node.left) + size(node.right) + 1;

        return node;
    }

    // 前序遍历打印
    public void preOtherPrint() {
        preOtherPrint(root);
    }

    private void preOtherPrint(Node node) {
        if (node == null) return;

        System.out.print(node.key);
        preOtherPrint(node.left);
        preOtherPrint(node.right);
    }

    // 中序遍历打印
    public void inOtherPrint() {
        inOtherPrint(root);
    }

    private void inOtherPrint(Node node) {
        if (node == null) return;

        inOtherPrint(node.left);
        System.out.print(node.key);
        inOtherPrint(node.right);
    }

    // 后序遍历打印
    public void postOtherPrint() {
        postOtherPrint(root);
    }

    private void postOtherPrint(Node node) {
        if (node == null) return;

        postOtherPrint(node.left);
        postOtherPrint(node.right);
        System.out.print(node.key);
    }

    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key low, Key high) {
        Queue<Key> queue = new Queue<Key>();

        keys(root, queue, low, high);

        return queue;
    }

    private void keys(Node node, Queue<Key> queue, Key low, Key high) {
        if (node == null) {
            return;
        }

        int cmplow = low.compareTo(node.key);
        int cmphigh = high.compareTo(node.key);

        // 当前节点大于最小边界，则一直往左递归知道找到空节点后回溯
        if (cmplow < 0) {
            keys(node.left, queue, low, high);
        }

        // 回溯过程中，判断当前节点如果在low到high之间则将其添加到队列当中
        if (cmplow <= 0 && cmphigh >= 0) {
            queue.enqueue(node.key);
        }

        // 当前节点小于最大边界，往右子树递归直到空节点后回溯
        if (cmphigh > 0) {
            keys(node.right, queue, low, high);
        }
    }

    // 广度优先遍历
    public void bfsPrint() {
        Queue<Node> nodeQueue = new Queue<>();

        // 初始化队列
        nodeQueue.enqueue(root);

        while (!nodeQueue.empty()) {
            // 出队列
            Node node = nodeQueue.dequeue();

            System.out.println(node.key);

            // 入队列
            if (node.left != null) {
                nodeQueue.enqueue(node.left);
            }

            if (node.right != null) {
                nodeQueue.enqueue(node.right);
            }
        }
    }

    // 深度优先遍历
    public void dfsPrint() {
        Stack<Node> nodeStack = new Stack<>();

        nodeStack.push(root);

        while (!nodeStack.empty()) {
            Node node = nodeStack.pop();

            System.out.println(node.key);

            if (node.right != null) {
                nodeStack.push(node.right);
            }

            if (node.left != null) {
                nodeStack.push(node.left);
            }
        }
    }
}
