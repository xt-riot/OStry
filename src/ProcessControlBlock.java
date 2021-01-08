import java.util.ArrayList;

public class ProcessControlBlock {
    
    private final int pid;
    private ProcessState state;
    // the following two ArrayLists should record when the process starts/stops
    // for statistical purposes
    private ArrayList<Integer> startTimes; // when the process starts running
    private ArrayList<Integer> stopTimes;  // when the process stops running
    
    private static int pidTotal= 0;
    
    public ProcessControlBlock() {
        this.state = ProcessState.NEW;
        this.startTimes = new ArrayList<Integer>();
        this.stopTimes = new ArrayList<Integer>();
        /* DONE: you need to add some code here
         * Hint: every process should get a unique PID */
        this.pid = pidTotal; // change this line
        pidTotal++;

    }

    public ProcessState getState() {
        return this.state;
    }
    
    public void setState(ProcessState state, int currentClockTime) {
        /* DONE: you need to add some code here
         * Hint: update this.state, but also include currentClockTime
         * in startTimes/stopTimes */
        System.out.println(" state " + state.toString() + " // " + currentClockTime);
        if(state == ProcessState.RUNNING && this.state != ProcessState.RUNNING) {
            startTimes.add(currentClockTime);
            System.out.println(" ADDED START TIME: " + currentClockTime);
        }
        else if (state != this.state && this.state == ProcessState.RUNNING) {
            stopTimes.add(currentClockTime);
            System.out.println(" ADDED STOP TIME: " + currentClockTime);
        }
        this.state = state;
        
    }
    
    public int getPid() { 
        return this.pid;
    }
    
    public ArrayList<Integer> getStartTimes() {
        return startTimes;
    }
    
    public ArrayList<Integer> getStopTimes() {
        return stopTimes;
    }
    
}
