public class Node2Keys extends TimeNode {
    RunnerID id;
    int size;

    public Node2Keys() {
        super();
        id = null;
        size = 0;
    }




    public Node2Keys(float time, Node2Keys parent, RunnerID id) {
        super(time, parent);
        this.id = id;
        if ( id.getClass() == SentinelMax.class || time == Float.MIN_VALUE) {
            size = 0;
        } else size = 1;
    }


    @Override
    public void updateKey() {
        if (this.left.getTime() == Float.MIN_VALUE) {
            if (this.middle != null) {
                this.time = this.middle.getTime();
                this.id = ((Node2Keys) this.middle).getId();
            }
            else{
                this.time = Float.MAX_VALUE;
                this.id = null;
            }
        } else {
            this.time = this.left.getTime();
            this.id = ((Node2Keys) this.left).getId();

        }
    }

    @Override
    public void setChildren(TimeNode l, TimeNode m, TimeNode r) {
        this.left = l;
        this.middle = m;
        this.right = r;
        l.setParent(this);
        this.size = ((Node2Keys) l).getSize();
        if (m != null) {
            m.setParent(this);
            this.size += ((Node2Keys) m).getSize();
        }
        if (r != null) {
            r.setParent(this);
            this.size += ((Node2Keys) r).getSize();

        }
        this.updateKey();
    }


    @Override
    public TimeNode insertAndSplit(TimeNode child) {
        Node2Keys l = (Node2Keys) this.left;
        Node2Keys m = (Node2Keys) this.middle;
        Node2Keys r = (Node2Keys) this.right;
        Node2Keys kid = (Node2Keys) child;
        Node2Keys y = new Node2Keys();
        if (r != null) {
            if (child.getTime() > (r.getTime())) {
                y.setChildren(r, child, null);
                this.setChildren(l, m, null);
                return y;
            }
            if (child.getTime() == r.getTime()) {
                if (r.getId().isSmaller(kid.getId())) {
                    y.setChildren(r, child, null);
                    this.setChildren(l, m, null);
                    return y;
                }
            }
            if (child.getTime() > m.getTime()) {
                y.setChildren(child, r, null);
                this.setChildren(l, m, null);
                return y;
            }
            if (child.getTime() == m.getTime()) {
                if ((m.getId().isSmaller(kid.getId()))) {
                    y.setChildren(child, r, null);
                    this.setChildren(l, m, null);
                    return y;
                }
            }
            if (child.getTime() > l.getTime()) {
                y.setChildren(m, r, null);
                this.setChildren(l, child, null);
                return y;
            }
            if (child.getTime() == l.getTime()) {
                if ((l.getId().isSmaller(kid.getId()))) {
                    y.setChildren(m, r, null);
                    this.setChildren(l, child, null);
                    return y;
                }
            }
            y.setChildren(m, r, null);
            this.setChildren(child, l, null);
            return y;
        } else {
            if (child.getTime() > m.getTime()){
                this.setChildren(l,m,child);
                return null;
            }
            if (child.getTime() == m.getTime()){
                if ((m.getId().isSmaller(kid.getId()))){
                    this.setChildren(l,m,child);
                    return null;
                }
            }
            if (child.getTime() > l.getTime()){
                this.setChildren(l,child,m);
                return null;
            }
            if (child.getTime() == l.getTime()){
                if ((l.getId().isSmaller(kid.getId()))){
                    this.setChildren(l,child,m);
                    return null;
                }
            }
            this.setChildren(child,l,m);
            return null;
        }
    }

//    @Override
//    public TimeNode insertAndSplit(TimeNode child) {
//        Node2Keys l = (Node2Keys) this.left;
//        Node2Keys m = (Node2Keys) this.middle;
//        Node2Keys r = (Node2Keys) this.right;
//        Node2Keys kid = (Node2Keys) child;
//        Node2Keys y = new Node2Keys();
//        if (r != null) {
//            if (child.getTime() > (r.getTime())) {
//                y.setChildren(r, child, null);
//                this.setChildren(l, m, null);
//                return y;
//            }
//            if (child.getTime() == r.getTime()) {
//                if (!(kid.getId().isSmaller(r.getId()))) {
//                    y.setChildren(r, child, null);
//                    this.setChildren(l, m, null);
//                    return y;
//                }
//            }
//            if (child.getTime() > m.getTime()) {
//                y.setChildren(child, r, null);
//                this.setChildren(l, m, null);
//                return y;
//            }
//            if (child.getTime() == m.getTime()) {
//                if (!(kid.getId().isSmaller(m.getId()))) {
//                    y.setChildren(child, r, null);
//                    this.setChildren(l, m, null);
//                    return y;
//                }
//            }
//            if (child.getTime() > l.getTime()) {
//                y.setChildren(m, r, null);
//                this.setChildren(l, child, null);
//                return y;
//            }
//            if (child.getTime() == l.getTime()) {
//                if (!(kid.getId().isSmaller(l.getId()))) {
//                    y.setChildren(m, r, null);
//                    this.setChildren(l, child, null);
//                    return y;
//                }
//            }
//            y.setChildren(m, r, null);
//            this.setChildren(child, l, null);
//            return y;
//        } else {
//            if (child.getTime() > m.getTime()){
//                this.setChildren(l,m,child);
//                return null;
//            }
//            if (child.getTime() == m.getTime()){
//                if (!(kid.getId().isSmaller(m.getId()))){
//                    this.setChildren(l,m,child);
//                    return null;
//                }
//            }
//            if (child.getTime() > l.getTime()){
//                this.setChildren(l,child,m);
//                return null;
//            }
//            if (child.getTime() == l.getTime()){
//                if (!(kid.getId().isSmaller(l.getId()))){
//                    this.setChildren(l,child,m);
//                }
//            }
//            this.setChildren(child,l,m);
//            return null;
//        }
//    }



    public int Rank(){
        int rank = 1;
         Node2Keys x = this;
        Node2Keys y = (Node2Keys)x.getParent();
        while (y != null){
            if (x == y.getMiddle())
                rank += ((Node2Keys)(y.getLeft())).getSize();
            else if (x == y.getRight()) {
                rank += ((Node2Keys) y.getLeft()).getSize() + ((Node2Keys) y.getMiddle()).getSize();

            }
            x = y;
            y = (Node2Keys) y.getParent();
        }
        return rank;
    }


    public RunnerID getId() {
        return id;
    }

    public int getSize() {
        return size;
    }


}
