public class Runners23tree {
    Runner root;
    public Runners23tree(){
        root = new Runner(new SentinelMin());
        root.setLeft(new Runner(new SentinelMin(), root));
        root.setMiddle(new Runner(new SentinelMax(), root));
    }

    public void addRunner(Runner child){
        Runner y = this.root;
        while (y.getLeft() != null){
            if (y.getRight() != null){
                if (y.getRight().getId().isSmaller(child.getId())) {
                    y = y.getRight();
                }
                else if(y.getMiddle().getId().isSmaller(child.getId())){
                    y = y.getMiddle();
                }
                else {
                    y = y.getLeft();
                }
            }
            else if(y.getMiddle().getId().isSmaller(child.getId())){
                y = y.getMiddle();
            }
            else {
                y = y.getLeft();
            }

//            if (!( y.getLeft().getId().isSmaller(child.getId()))){
//                y = y.getLeft();
//            }
//            else if ( !(y.getMiddle().getId().isSmaller(child.getId()))){
//                y = y.getMiddle();
//            }
//            else if(y.getRight() == null){
//                y = y.getMiddle();
//            }
//            else {
//                y = y.getRight();
//            }
        }
        Runner x = y.parent;
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
            Runner w = new Runner(null);
            w.setChildren(x,child,null);
            this.root = w;
        }
    }
    public void delete(Runner x){
        Runner y = x.getParent();
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
    public Runner getRoot() {
        return root;
    }
}
