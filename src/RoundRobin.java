import java.util.ArrayList;

public class RoundRobin extends Scheduler {

    private int quantum;
    private ArrayList<Integer> timeLeftForProcess;
    private Process oldProcess;
    
    public RoundRobin() {
        this.quantum = 1; // default quantum
        timeLeftForProcess = new ArrayList<>();
        oldProcess = null;
        /* DONE: you _may_ need to add some code here */
    }
    
    public RoundRobin(int quantum) {
        this();
        this.quantum = quantum;
    }

    public void addProcess(Process p) {
        /* DONE: you need to add some code here */
        System.out.println("\t\t\u001b[33mSCHEDULER: Process (" + p.getPCB().getPid() + ") added to scheduler" + "\u001b[0m");
        this.processes.add(p);
        this.timeLeftForProcess.add(this.quantum);
    }
    
    public Process getNextProcess() {
        /* DONE: you need to add some code here
         * and change the return value */
        Process newProcess = null;
        int index = 0;
        for(int processTimeLeft : timeLeftForProcess) {
            ProcessState state = this.processes.get(timeLeftForProcess.indexOf(processTimeLeft)).getPCB().getState();
            if( processTimeLeft > 0 && (state == ProcessState.READY || state == ProcessState.RUNNING) ) {
                index = timeLeftForProcess.indexOf(processTimeLeft);
                newProcess = this.processes.get(index);

                break;
            }
        }
        int timeLeft = timeLeftForProcess.get(index);
        timeLeftForProcess.set(index, timeLeft-1);
        if(this.processes.get(this.processes.size()-1) == newProcess && this.timeLeftForProcess.get(index) == 0 || newProcess == null) {
            for(Process temp : this.processes) {
                timeLeftForProcess.set(this.processes.indexOf(temp), this.quantum);
            }
            if (newProcess == null) {
                newProcess = this.processes.get(0);
                timeLeftForProcess.set(0, this.quantum-1);
            }
            System.out.println("\t\t\u001b[33mSCHEDULER: Processing time for each process has been reset as this is the last process.\u001b[0m");
        }
        if(oldProcess != newProcess && oldProcess != null) {
            oldProcess.waitInBackground();
            System.out.println("\t\t\u001b[33mSCHEDULER: Process (" + oldProcess.getPCB().getPid() + ") has used the appointed processing time.\u001b[0m");
        }
        System.out.println("\t\t\u001b[33mSCHEDULER: Process (" + newProcess.getPCB().getPid() + ") has been given priority\u001b[0m");
        //newProcess.getPCB().setState(ProcessState.RUNNING, CPU.clock);
        oldProcess = newProcess;
        return newProcess;
    }

    public void removeProcess(Process p) {
        this.timeLeftForProcess.remove(this.processes.indexOf(p));
        super.removeProcess(p);
    }

}
