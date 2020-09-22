package autoFunctions;

import util.CodeSeg;


public class AutoThread implements Runnable{
    private boolean done = false;
    CodeSeg cs;

    public void init(CodeSeg cs){
        this.cs = cs;
    }
    public synchronized void stop() {
        this.done = true;
    }

    private synchronized boolean isExecuting() {
        return !this.done;
    }
    @Override
    public void run() {
        while (isExecuting()){
            cs.run();
        }
    }
}
