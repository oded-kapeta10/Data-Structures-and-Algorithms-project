public class Time23tree {
    TimeNode root;


    public Time23tree(){
        root = new TimeNode();
        root.setLeft(new TimeNode(Float.MIN_VALUE, root));
        root.setMiddle(new TimeNode(Float.MAX_VALUE,root));
    }

    public void addRunToRunner(TimeNode child){
        TimeNode y = this.root;
        while (y.getLeft() != null){
            if (y.getRight() != null) {
                if (y.getRight().getTime() < child.getTime()) {
                    y = y.getRight();
                } else if (y.getMiddle().getTime() < (child.getTime())) {
                    y = y.getMiddle();
                } else {
                    y = y.getLeft();
                }
//            if (child.getTime() < (y.getLeft().getTime())){
//                y =(Node2Keys) y.getLeft();
//            }
//            else if (child.getTime() < (y.getMiddle().getTime())) {
//                y = (Node2Keys) y.getMiddle();
//            }
//            else{
//                y = (Node2Keys) y.getRight();
//            }
            }
            else if (y.getMiddle().getTime() < (child.getTime())) {
                y = y.getMiddle();
            } else {
                y = y.getLeft();
            }
        }
        TimeNode x = y.parent;
        child = x.insertAndSplit(child);
        while (x != this.root){
            x = x.parent;
            if(child != null){
                child = x.insertAndSplit(child);
            }
            else {
                x.updateKey();
            }
        }
        if(child != null){
            TimeNode w = new TimeNode();
            w.setChildren(x,child,null);
            this.root = w;
        }
    }

    public void delete(TimeNode x){
        TimeNode y = x.getParent();
        if (x == y.getLeft()){
            y.setChildren(y.getMiddle(),y.getRight(),null);
        }
        else if (x == y.getMiddle()) {
            y.setChildren(y.getLeft(),y.getRight(),null);
        }
        else{
            y.setChildren(y.getLeft(),y.getMiddle(),null);
        }
        while (y != null){
            if (y.getMiddle() != null){
                y.updateKey();
                y = y.getParent();
            }
            else{
                if (y != this.root){
                    y = y.borrowOrMerge();
                }
                else {
                    this.root = null;
                    y.getLeft().setParent(null);
                    this.root = y.getLeft();
                    return;
                }

            }
        }
    }

    public TimeNode getRoot() {
        return root;
    }

}
