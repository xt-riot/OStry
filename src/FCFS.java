import java.util.ArrayList;

public class FCFS extends Scheduler {

    public FCFS() {
        /* DONE: you _may_ need to add some code here */
    }

    public void addProcess(Process p) {
        /* DONE: you need to add some code here */
        System.out.println("\t\t\u001b[33mSCHEDULER: Process (" + p.getPCB().getPid() + ") added to scheduler" + "\u001b[0m");
        this.processes.add(p);
    }
    
    public Process getNextProcess() {
        /* DONE: you need to add some code here
         * and change the return value */

        boolean running = false;
        Process process = null;
        for(Process it : this.processes) {
            if(it.getPCB().getState() == ProcessState.RUNNING) {
                running = true;
                process = it;
                break;
            }
        }

        if(!running){
            for (Process it : this.processes){
                if(it.getPCB().getState() == ProcessState.READY) {
                    process = it;
                    System.out.println("\t\t\u001b[33mSCHEDULER: Process (" + it.getPCB().getPid() + ") is about to begin"+ "\u001b[0m");
                    break;
                }
            }
        }
        return process;
    }
}
