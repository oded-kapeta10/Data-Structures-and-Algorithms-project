public class Race {
    Runners23tree runners;
    Node2KeysTree avg_runs;
    Node2KeysTree min_runs;

    public Race(){
        this.init();
    }


    public Runner runnerSearch(Runner root, RunnerID id){
        if (root.left == null){
            if (root.getId() == id)
                return root;
            else return null;
        }
        if (root.right != null){
            if (root.right.id.isSmaller(id) || root.right.id == id){
                return runnerSearch(root.right,id);
            }
        }
        if (root.middle.id.isSmaller(id) || root.middle.id == id){
            return runnerSearch(root.middle,id);
        }
        else{
            return runnerSearch(root.left,id);
        }
    }

    public TimeNode timeSearch(TimeNode root ,float time){
        if (root.left == null){
            if (root.getTime() == time)
                return root;
            else return null;
        }
        if (root.right != null){
            if (root.right.getTime()<= time){
                return timeSearch(root.right,time);
            }
        }
        if (root.middle.getTime() <= (time)){
            return timeSearch(root.middle,time);
        }
        else{
            return timeSearch(root.left,time);
        }
    }
    public TimeNode keys2Search(Node2Keys root ,float time,RunnerID id){
        Node2Keys right =(Node2Keys) root.right;
        Node2Keys left =(Node2Keys) root.left;
        Node2Keys middle =(Node2Keys) root.middle;


        if (root.left == null){
            if (root.getTime() == time && root.getId() == id)
                return root;
            else return null;
        }
        if (root.right != null){
            if (root.right.getTime() < time){
                return keys2Search(right,time,id);
            }
            else if (root.right.getTime() == time){
                if (right.getId() == id)    return keys2Search(right,time,id);
                if (right.getId().isSmaller(id))    return keys2Search(right,time,id);
                else {
                    return keys2Search(middle,time,id);
                }
            }
        }
        if (root.middle.getTime() < time){
            return timeSearch(root.middle,time);
        }else if (root.middle.getTime() == time){
            if (middle.getId() == id)    return keys2Search(middle,time,id);
            if (middle.getId().isSmaller(id))   return keys2Search(middle,time,id);
            else{
                return keys2Search(left,time,id);
            }
        }
        else{
            return keys2Search(left,time,id);
        }
    }

    public void init()
    {
         runners = new Runners23tree();
         avg_runs = new Node2KeysTree();
         min_runs = new Node2KeysTree();
    }
    public void addRunner(RunnerID id)
    {
        if(runnerSearch(runners.getRoot(),id) != null)  throw new ILLegalArgumentException();
        Runner child = new Runner(id);
        runners.addRunner(child);
        avg_runs.addRunToRunner(new Node2Keys(Float.MAX_VALUE, null, id));
        min_runs.addRunToRunner(new Node2Keys(Float.MAX_VALUE, null, id));

    }

    public void addRunToRunner(RunnerID id, float time)
    {
        Runner x = runnerSearch(runners.getRoot(),id);
        if( x == null || time <= 0 )  throw new ILLegalArgumentException();
        TimeNode timeNode = timeSearch(x.runs.root, time);
        if (timeNode != null)   throw new ILLegalArgumentException();
        x.runs.addRunToRunner(new TimeNode(time,null));
        x.incrementRuns();
        if (x.getRunCount() == 1){
            avg_runs.delete(keys2Search((Node2Keys) avg_runs.getRoot(), Float.MAX_VALUE,id ));
            min_runs.delete(keys2Search((Node2Keys) min_runs.getRoot(), Float.MAX_VALUE,id ));
            avg_runs.addRunToRunner(new Node2Keys(time,null,id));
            min_runs.addRunToRunner(new Node2Keys(time,null,id));
            x.setMin(time);
            x.setAvg(time);
        }
        else {
            if (time < x.getMin()) {
                min_runs.delete(keys2Search((Node2Keys) min_runs.getRoot(), x.getMin(), id));
                min_runs.addRunToRunner(new Node2Keys(time, null, id));
                x.setMin(time);
            }
                avg_runs.delete(keys2Search((Node2Keys) avg_runs.getRoot(), x.getAvg(), id));
                x.setAvg((x.getAvg() * (x.getRunCount() - 1) + time) / x.getRunCount());
                avg_runs.addRunToRunner(new Node2Keys(x.getAvg(), null, id));


        }

    }

    public void removeRunner(RunnerID id)
    {
        Runner x = runnerSearch(runners.getRoot(),id);
        if( x == null )  throw new ILLegalArgumentException();
        if (x.getRunCount() != 0 ){
            min_runs.delete( keys2Search((Node2Keys) min_runs.getRoot(),x.getMin(),id));
            avg_runs.delete(keys2Search((Node2Keys) avg_runs.getRoot(), x.getAvg(),id));
        }
        runners.delete(x);
    }


    public void removeRunFromRunner(RunnerID id, float time)
    {
        Runner x = runnerSearch(runners.getRoot(),id);
        if( x == null || time <= 0 )  throw new ILLegalArgumentException();
        TimeNode timeNode = timeSearch(x.runs.root, time);
        if (timeNode == null)   throw new ILLegalArgumentException();
        x.getRuns().delete(timeNode);
        x.decreaseRuns();
        if (x.getRunCount() == 0){
            min_runs.delete( keys2Search((Node2Keys) min_runs.getRoot(),time,id ));
            avg_runs.delete(keys2Search((Node2Keys) avg_runs.getRoot(), x.getAvg(),id));
            avg_runs.addRunToRunner(new Node2Keys(Float.MAX_VALUE, null, id));
            min_runs.addRunToRunner(new Node2Keys(Float.MAX_VALUE, null, id));
            x.setAvg(Float.MAX_VALUE);
            x.setMin(Float.MAX_VALUE);
        }
        else {
            if (x.getMin() == time){
                min_runs.delete( keys2Search((Node2Keys) min_runs.getRoot(),time,id ));
                x.setMin(x.getMinRealRun());
                min_runs.addRunToRunner(new Node2Keys(x.getMin(), null,id));
            }
            avg_runs.delete(keys2Search((Node2Keys) avg_runs.getRoot(), x.getAvg(),id));
            x.setAvg((x.getAvg()*(x.getRunCount()+1)-time)/x.getRunCount());
            avg_runs.addRunToRunner(new Node2Keys(x.getAvg(),null,id));
        }
    }


    public RunnerID getFastestRunnerAvg()
    {
        return ((Node2Keys) avg_runs.getRoot()).getId();
    }

    public RunnerID getFastestRunnerMin()
    {

        return ((Node2Keys) min_runs.getRoot()).getId();
    }

    public float getMinRun(RunnerID id)
    {
        Runner x = runnerSearch(runners.getRoot(),id);
        if( x == null )  throw new ILLegalArgumentException();
        return x.getMin();
    }
    public float getAvgRun(RunnerID id){
        Runner x = runnerSearch(runners.getRoot(),id);
        if( x == null )  throw new ILLegalArgumentException();
        return x.getAvg();
    }

    public int getRankAvg(RunnerID id)
    {
        Runner x = runnerSearch(runners.getRoot(),id);
        if( x == null )  throw new ILLegalArgumentException();
//        if (x.getMin() == Float.MAX_VALUE){
//            Node2Keys y = (Node2Keys) avg_runs.getRoot();
//            while (y.getLeft() != null){
//                if (y.getRight() != null){
//                    y = (Node2Keys) y.getRight();
//                }
//                else {
//                    y = (Node2Keys) y.getMiddle();
//                }
//            }
//            return y.Rank();
//        }
        Node2Keys timeNode =(Node2Keys) keys2Search((Node2Keys) avg_runs.getRoot(), x.getAvg(), id);
        return timeNode.Rank();
    }

    public int getRankMin(RunnerID id)
    {
        Runner x = runnerSearch(runners.getRoot(),id);
        if( x == null )  throw new ILLegalArgumentException();
//        if (x.getMin() == Float.MAX_VALUE){
//            Node2Keys y = (Node2Keys) min_runs.getRoot();
//            while (y.getLeft() != null){
//                if (y.getRight() != null){
//                    y = (Node2Keys) y.getRight();
//                }
//                else {
//                    y = (Node2Keys) y.getMiddle();
//                }
//            }
//            return y.Rank();
//
//        }
        Node2Keys timeNode =(Node2Keys) keys2Search((Node2Keys) min_runs.getRoot(), x.getMin(), id);
        return timeNode.Rank();    }
}
