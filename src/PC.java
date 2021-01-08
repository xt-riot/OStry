
public class PC {

    public static void main(String[] args) {
        /* DONE: You may change this method to perform any tests you like */
        
        Process[] processes = {
                // Process parameters are: arrivalTime, burstTime, memoryRequirements (kB)
                new Process(0, 5, 10),
                new Process(2, 2, 40),
                new Process(3, 1, 25),
                new Process(4, 3, 30)
        };
        final int[] availableBlockSizes = {15, 40, 10, 20}; // sizes in kB
        MemoryAllocationAlgorithm algorithm = new FirstFit(availableBlockSizes);
        MMU mmu = new MMU(availableBlockSizes, algorithm);        
        Scheduler scheduler = new FCFS();
        CPU cpu = new CPU(scheduler, mmu, processes);
        cpu.run();

        System.out.println("\n\n\nEnd of program.");
        for(Process proc : processes) {
            System.out.println("\u001b[34m\t--> Process (" + proc.getPCB().getPid() + ") has the following stats:");
            System.out.println("\t\t\t Turnaround time: " + proc.getTurnAroundTime());
            System.out.println("\t\t\t Response   time: " + proc.getResponseTime());
            System.out.println("\t\t\t Waiting    time: " + proc.getWaitingTime());
        }
    }

}
