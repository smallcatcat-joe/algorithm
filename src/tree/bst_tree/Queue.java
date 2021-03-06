package tree.bst_tree;

import java.util.Iterator;

/**
 * Created by smallcatcat on 2019/1/17.
 */
// 实现Iterable接口的Queue类
public class Queue<Item> implements Iterable<Item> {
    private QueueNode first = null;
    private int queueLen = 0;

    private class QueueNode {
        private Item val;
        private QueueNode next;

        public QueueNode(Item val) {
            this.val = val;
            this.next = null;
        }

        public QueueNode getNext() {
            return this.next;
        }

        public void setNext(QueueNode queueNode) {
            this.next = queueNode;
        }

        public Item getVal() {
            return this.val;
        }
    }

    public boolean empty() {
        return queueLen == 0;
    }

    // 添加项
    public void enqueue(Item item) {
        QueueNode queueNode = new QueueNode(item);

        if (first == null) {
            first = queueNode;
            queueLen++;

            return;
        }

        QueueNode last = findLast(first);

        last.setNext(queueNode);
        queueLen++;
    }

    public Item dequeue() {
        Item shiftNode = first.getVal();

        first = first.getNext();
        queueLen = queueLen == 0 ? 0 : queueLen - 1;

        return shiftNode;
    }

    public void print() {
        print(first);
    }

    private void print(QueueNode node) {
        System.out.println(node.getVal());

        if (node.getNext() != null) {
            print(node.getNext());
        }
    }

    private QueueNode findLast(QueueNode queueNode) {
        if (queueNode.next == null) {
            return queueNode;
        } else {
            return findLast(queueNode.getNext());
        }
    }

    // 迭代器
    private class LinkListIterator implements Iterator<Item> {
        QueueNode node = first;
        int i = 0;

        @Override
        public boolean hasNext() {
            return i != queueLen;
        }

        @Override
        public Item next() {
            Item item = node.getVal();

            node = node.getNext();
            i++;

            return item;
        }
    }

    @Override
    public Iterator<Item> iterator() {
        return new LinkListIterator();
    }
}
