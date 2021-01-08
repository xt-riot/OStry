import java.util.ArrayList;

public class SJF extends Scheduler {
    private ArrayList<Process> old;

    public SJF() {
        old = new ArrayList<>();
        old.addAll(this.processes);
    }

    public void addProcess(Process p) {
        /* DONE: you need to add some code here */
        //System.out.println("GOT IN");
        if(this.processes.size() != 0) {
            for(Process oldProcesses : this.processes) {
                if(p.getBurstTime() < oldProcesses.getBurstTime()) {
                    old.add(this.processes.indexOf(oldProcesses), p);
                }
                else old.add(p);
            }


        }
        else old.add(p);
        this.processes.removeAll(this.processes);
        this.processes.addAll(old);
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
