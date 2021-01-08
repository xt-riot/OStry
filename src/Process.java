
public class Process {
    private ProcessControlBlock pcb;
    private int arrivalTime;
    private int burstTime;
    private int memoryRequirements;
    private double timeInBackground;

    public Process(int arrivalTime, int burstTime, int memoryRequirements) {
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.memoryRequirements = memoryRequirements;
        this.pcb = new ProcessControlBlock();
        this.timeInBackground = 0;
    }
    
    public ProcessControlBlock getPCB() {
        return this.pcb;
    }
   
    public void run() {
        /* DONE: you need to add some code here
         * Hint: this should run every time a process starts running */
        this.pcb.setState(ProcessState.RUNNING, CPU.clock);
        burstTime--;
        System.out.println("--Process (" + this.pcb.getPid() + ") will run for this cycle. Remaining cycles: " + burstTime);
        if(burstTime == 0) {
            this.pcb.setState(ProcessState.TERMINATED, CPU.clock + 1);
            System.out.println("--Process (" + this.pcb.getPid() + ") has finished. Completion time: " + (CPU.clock - this.arrivalTime));
        }
    }
    
    public void waitInBackground() {
        /* DONE: you need to add some code here
         * Hint: this should run every time a process stops running */
        this.timeInBackground = CPU.clock - this.arrivalTime;
        this.pcb.setState(ProcessState.READY, CPU.clock);
    }

    public double getWaitingTime() {
        /* DONE: you need to add some code here
         * and change the return value */
        if(this.pcb.getStopTimes().size() > 1) {
            for(int i = 0; i < this.pcb.getStopTimes().size(); i++) {
                this.timeInBackground += this.pcb.getStopTimes().get(i) - this.pcb.getStartTimes().get(i);
            }
        }
        return this.timeInBackground;
    }
    
    public double getResponseTime() {
        /* TODO: you need to add some code here
         * and change the return value */
        int totalTime = -1;
        totalTime = this.pcb.getStartTimes().get(0) - this.arrivalTime;
        return totalTime;
    }
    
    public double getTurnAroundTime() {
        /* DONE: you need to add some code here
         * and change the return value */
        int totalTime = -1;
        if(burstTime == 0) totalTime = this.pcb.getStopTimes().get(this.pcb.getStopTimes().size() - 1) - this.arrivalTime;
        return totalTime;
    }

    public int getBurstTime() { return this.burstTime; }
    public int getArrivalTime() { return this.arrivalTime; }

    public int getMemoryRequirements() {
        return this.memoryRequirements;
    }
}
