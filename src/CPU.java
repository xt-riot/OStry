public class CPU {

    public static int clock = 0; // this should be incremented on every CPU cycle

    private Scheduler scheduler;
    private MMU mmu;
    private Process[] processes;
    private int currentProcess;

    public CPU(Scheduler scheduler, MMU mmu, Process[] processes) {
        this.scheduler = scheduler;
        this.mmu = mmu;
        this.processes = processes;
    }

    public void run() {
        /* TODO: you need to add some code here
         * Hint: you need to run tick() in a loop, until there is nothing else to do... */
        currentProcess = 0;

        while (currentProcess != this.processes.length) {
            System.out.println("\u001b[36mCPU clock: " + clock + "\u001b[0m");
            for (Process process : this.processes) {
                if (process.getArrivalTime() == clock) {
                    System.out.println("\u001b[31mCPU: Process (" + process.getPCB().getPid() + ") arrived\u001b[0m");
                    scheduler.addProcess(process);
                    if (mmu.loadProcessIntoRAM(process)) {
                        System.out.println("\u001b[31mCPU: Process (" + process.getPCB().getPid() + ") added in memory at CPU tick: " + clock + ". Changing state to READY..." + "\u001b[0m");
                    }
                }
                else if (process.getArrivalTime() < clock && process.getPCB().getState() == ProcessState.NEW) {
                    System.out.println("\u001b[31mCPU: Retrying to load process (" + process.getPCB().getPid() + ") into memory." + "\u001b[0m");
                    if (mmu.loadProcessIntoRAM(process)) {
                        System.out.println("\u001b[31mCPU: Process (" + process.getPCB().getPid() + ") added in memory. Changing state to READY..." + "\u001b[0m");
                    }
                }
                else if (process.getArrivalTime() > clock) break;
            }

            if(currentProcess != this.processes.length) {
                this.tick();
                clock++;
            }
        }

    }

    public void tick() {
        /* DONE: you need to add some code here
         * Hint: this method should run once for every CPU cycle */

        Process nextProcess = scheduler.getNextProcess();
        nextProcess.run();
        if(nextProcess.getPCB().getState() == ProcessState.TERMINATED){
            scheduler.removeProcess(nextProcess);
            mmu.loadProcessIntoRAM(nextProcess);
            currentProcess++;
        }
    }
}
