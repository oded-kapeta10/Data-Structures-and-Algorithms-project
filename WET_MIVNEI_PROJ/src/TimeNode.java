public class TimeNode {
    float time;
    TimeNode parent;
    TimeNode left;
    TimeNode middle;
    TimeNode right;

    public  TimeNode(){
        this.time = Float.MAX_VALUE;
        parent = null;
        left = null;
        middle = null;
        right = null;
    }
    public  TimeNode(float time, TimeNode parent){
        this.time = time;
        this.parent = parent;
        left = null;
        middle = null;
        right = null;
    }

    public void updateKey(){
        this.time = this.left.getTime();
    }

    public void setChildren(TimeNode l,TimeNode m,TimeNode r){
        this.left = l;
        this.middle = m;
        this.right = r;
        l.setParent(this);
        if (m != null){
            m.setParent(this);
        }
        if (r != null){
            r.setParent(this);
        }
        this.updateKey();
    }

    public TimeNode insertAndSplit(TimeNode child){
        TimeNode l = this.left;
        TimeNode m = this.middle;
        TimeNode r = this.right;
        if (r == null){
            if(child.getTime() < (l.getTime())){
                this.setChildren(child,l,m);
            }
            else if (child.getTime() < (m.getTime())) {
                this.setChildren(l,child,m);
            }
            else {
                this.setChildren(l,m,child);
            }
            return null;
        }
        TimeNode y = new TimeNode();
        if (child.getTime() < (l.getTime())){
            this.setChildren(child,l,null);
            y.setChildren(m,r,null);
        }
        else if (child.getTime() < (m.getTime())) {
            this.setChildren(l,child,null);
            y.setChildren(m,r,null);
        }
        else if (child.getTime() < (r.getTime())) {
            this.setChildren(l,m,null);
            y.setChildren(child,r,null);
        }
        else {
            this.setChildren(l,m,null);
            y.setChildren(r,child,null);
        }
        return y;
    }

    public TimeNode borrowOrMerge(){
        TimeNode z = this.parent;
        if (this == z.getLeft()){
            TimeNode x = z.getMiddle();
            if (x.getRight() != null ){
                this.setChildren(this.left,x.getLeft(),null);
                x.setChildren(x.getMiddle(),x.getRight(),null);
            }
            else {
                x.setChildren(this.left,x.getLeft(),x.getMiddle());
                z.setChildren(x,z.getRight(),null);
            }
            return z;
        }
        if (this == z.getMiddle()){
            TimeNode x = z.getLeft();
            if (x.getRight() != null ){
                this.setChildren(x.getRight(),this.left,null);
                x.setChildren(x.getLeft(),x.getMiddle(),null);
            }
            else {
                x.setChildren(x.getLeft(),x.getMiddle(),this.left);
                z.setChildren(x,z.getRight(),null);
            }
            return z;
        }
        else{
            TimeNode x = z.getMiddle();
            if (x.getRight() != null ){
                this.setChildren(x.getRight(),this.left,null);
                x.setChildren(x.getLeft(),x.getMiddle(),null);
            }
            else {
                x.setChildren(x.getLeft(),x.getMiddle(),this.left);
                z.setChildren(z.getLeft(),x,null);
            }
            return z;
        }

    }



    public void setParent(TimeNode parent) {
        this.parent = parent;
    }

    public void setLeft(TimeNode left) {
        this.left = left;
    }

    public void setMiddle(TimeNode middle) {
        this.middle = middle;
    }

    public void setRight(TimeNode right) {
        this.right = right;
    }

    public TimeNode getParent() {
        return parent;
    }

    public TimeNode getLeft() {
        return left;
    }

    public TimeNode getMiddle() {
        return middle;
    }

    public TimeNode getRight() {
        return right;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }


}
