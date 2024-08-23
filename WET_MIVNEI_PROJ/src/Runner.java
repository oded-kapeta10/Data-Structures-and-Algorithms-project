public class Runner {
    Runner left;
    Runner middle;
    Runner right;
    Runner parent;
    RunnerID id;

    float avg;
    float min;

    int runCount;

    Time23tree runs;
    public Runner(RunnerID x){
        id = x;
        parent = null;
        left = null;
        middle = null;
        right = null;
        runs = new Time23tree();
        runCount= 0;
        avg = Float.MAX_VALUE;
        min = Float.MAX_VALUE;
    }
    public Runner(RunnerID x, Runner parent){
        id = x;
        this.parent = parent;
        left = null;
        middle = null;
        right = null;
        runs = new Time23tree();
        runCount = 0;
        avg = Float.MAX_VALUE;
        min = Float.MAX_VALUE;
    }

    public void updateKey(){
        this.id = this.left.getId();
    }

    public void setChildren(Runner l,Runner m,Runner r){
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

    public Runner insertAndSplit(Runner child){
        Runner l = this.left;
        Runner m = this.middle;
        Runner r = this.right;
        if (r == null){
            if(!(l.getId().isSmaller(child.getId()))){
                this.setChildren(child,l,m);
            }
            else if (!(m.getId().isSmaller(child.getId()))) {
                this.setChildren(l,child,m);
            }
            else {
                this.setChildren(l,m,child);
            }
            return null;
        }
        Runner y = new Runner(null);
        if (!(l.getId().isSmaller(child.getId()))){
            this.setChildren(child,l,null);
            y.setChildren(m,r,null);
        }
        else if (!(m.getId().isSmaller(child.getId()))) {
            this.setChildren(l,child,null);
            y.setChildren(m,r,null);
        }
        else if (!(r.getId().isSmaller(child.getId()))) {
            this.setChildren(l,m,null);
            y.setChildren(child,r,null);
        }
        else {
            this.setChildren(l,m,null);
            y.setChildren(r,child,null);
        }
        return y;
    }
    public Runner borrowOrMerge(){
        Runner z = this.parent;
        if (this == z.getLeft()){
            Runner x = z.getMiddle();
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
            Runner x = z.getLeft();
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
            Runner x = z.getMiddle();
            if (x.getRight() != null ){
                this.setChildren(x.getRight(),this.left,null);
                x.setChildren(x.getLeft(),x.getMiddle(),null);
            }
            else {
                x.setChildren(x.getLeft(),x.getMiddle(),this.left);
                z.setChildren(z,z.getLeft(),null);
            }
            return z;
        }

    }


    public void incrementRuns(){
        this.runCount++;
    }
    public void decreaseRuns(){
        this.runCount--;
    }

    public int getRunCount() {
        return runCount;
    }

    public void setLeft(Runner left) {
        this.left = left;
    }

    public void setMiddle(Runner middle) {
        this.middle = middle;
    }

    public void setRight(Runner right) {
        this.right = right;
    }

    public void setParent(Runner parent) {
        this.parent = parent;
    }

    public RunnerID getId() {
        return id;
    }

    public Runner getLeft() {
        return left;
    }

    public Runner getRight() {
        return right;
    }

    public Runner getMiddle() {
        return middle;
    }

    public Runner getParent() {
        return parent;
    }

    public float getAvg() {
        return avg;
    }

    public float getMin() {
        return min;
    }
    public float getMinRealRun(){
         TimeNode y = this.getRuns().root;
         while (y.getLeft()!= null){
             y = y.getLeft();
         }
         return y.getParent().getMiddle().getTime();


    }

    public void setAvg(float avg) {
        this.avg = avg;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public Time23tree getRuns() {
        return runs;
    }

    public void setRuns(Time23tree runs) {
        this.runs = runs;
    }
}
