public class Node2KeysTree extends Time23tree {

    public Node2KeysTree() {
        root = new Node2Keys();
        root.setLeft(new Node2Keys(Float.MIN_VALUE, (Node2Keys) root, new SentinelMin()));
        root.setMiddle(new Node2Keys(Float.MAX_VALUE, (Node2Keys) root, new SentinelMax()));
    }



    public void addRunToRunner(Node2Keys child) {
        Node2Keys y = (Node2Keys) this.root;
        while (y.getLeft() != null) {
            if (y.getRight() != null) {
                if (y.getRight().getTime() < child.getTime()) {
                    y = (Node2Keys) y.getRight();
                    continue;
                }
                if (child.getTime() == y.getRight().getTime()) {
                    if (((Node2Keys) y.getRight()).getId().isSmaller(child.getId())) {
                        y = (Node2Keys) y.getRight();
                        continue;
                    }
                }
                if (y.getMiddle().getTime() < (child.getTime())) {
                    y = (Node2Keys) y.getMiddle();
                    continue;
                }
                if (child.getTime() == y.getMiddle().getTime()) {
                    if (((Node2Keys) y.getMiddle()).getId().isSmaller(child.getId())) {
                        y = (Node2Keys) y.getMiddle();
                        continue;
                    }
                }

                    y = (Node2Keys) y.getLeft();


            } else{
                if (y.getMiddle().getTime() < (child.getTime())) {
                    y = (Node2Keys) y.getMiddle();
                    continue;
                }
                if (child.getTime() == y.getMiddle().getTime()) {
                    if (((Node2Keys) y.getMiddle()).getId().isSmaller(child.getId())) {
                        y = (Node2Keys) y.getMiddle();
                        continue;
                    }
                }

                    y = (Node2Keys) y.getLeft();

            }
        }
            Node2Keys x = (Node2Keys) y.parent;
            child = (Node2Keys) x.insertAndSplit(child);
            while (x != this.root) {
                x = (Node2Keys) x.parent;
                if (child != null) {
                    child = (Node2Keys) x.insertAndSplit(child);
                } else {
                    x.updateKey();
                }
            }
            x.updateKey();
            if (child != null) {
                Node2Keys w = new Node2Keys();
                w.setChildren(x, child, null);
                this.root = w;
            }
        }
    }

