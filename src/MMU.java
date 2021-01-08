import java.util.ArrayList;

public class MMU {

    private final int[] availableBlockSizes;
    private MemoryAllocationAlgorithm algorithm;
    private ArrayList<MemorySlot> currentlyUsedMemorySlots;
    private String[] memoryPresentation;
    private ArrayList<ArrayList<Integer>> processOccupyingMemory;
    
    public MMU(int[] availableBlockSizes, MemoryAllocationAlgorithm algorithm) {
        this.availableBlockSizes = availableBlockSizes;
        this.algorithm = algorithm;
        this.currentlyUsedMemorySlots = new ArrayList<MemorySlot>();
        processOccupyingMemory = new ArrayList<>();
        this.memoryPresentation = new String[availableBlockSizes.length];
        for(int i = 0; i< availableBlockSizes.length; i++) {
            memoryPresentation[i] = "[-- free space (" + availableBlockSizes[i] + ") --]";
        }
    }

    private void removeTerminatedProcesses(Process p) {
        int temp = 0;
        int index = 0;
        for(ArrayList<Integer> processID : processOccupyingMemory) {
            if(p.getPCB().getPid() == processID.get(0)) {
                index = processOccupyingMemory.indexOf(processID);
                temp = processID.get(1);
                break;
            }
        }
        if(memoryPresentation[temp].length() == 18) {
            memoryPresentation[temp] = "[-- free space (" +availableBlockSizes[temp] + ") --]";
        } else {
            String news = "";
            int procpos = memoryPresentation[temp].indexOf(""+p.getPCB().getPid());
            news = memoryPresentation[temp].substring(0, procpos-11);
            news += memoryPresentation[temp].substring(procpos+3);
            memoryPresentation[temp] = news;
        }
        currentlyUsedMemorySlots.remove(index);
        processOccupyingMemory.remove(index);
    }

    public boolean loadProcessIntoRAM(Process p) {
        boolean fit = false;
        /* DONE: you need to add some code here
         * Hint: this should return true if the process was able to fit into memory
         * and false if not */
        if(p.getPCB().getState() == ProcessState.TERMINATED) {
            System.out.println("\t\t\t\t\u001b[32mMMU: Removing terminated process (" + p.getPCB().getPid() + ").\u001b[0m");
            removeTerminatedProcesses(p);
        }
        else {
            int address = algorithm.fitProcess(p, this.currentlyUsedMemorySlots);
            if(address != -1) {
                int blockEnd = 0;
                int blockStart = 0;
                for(int i = 0; i<=address; i++) {
                    blockStart = blockEnd;
                    blockEnd += availableBlockSizes[i];
                }
                int isSlotFree = -1;
                for( ArrayList<Integer> processID : processOccupyingMemory)
                    if(processID.get(1) == address)
                        isSlotFree = processOccupyingMemory.indexOf(processID); // returns -1 if slot is free
                int processStart = blockStart;
                if(isSlotFree != -1) {
                    String temp = memoryPresentation[address].substring(3, memoryPresentation[address].indexOf(")")+1);
                    if (currentlyUsedMemorySlots.get(isSlotFree).getBlockEnd() - currentlyUsedMemorySlots.get(isSlotFree).getEnd() < p.getMemoryRequirements()) {
                        processStart = currentlyUsedMemorySlots.get(isSlotFree).getBlockStart();
                        memoryPresentation[address] = "[-- Process(" + p.getPCB().getPid() + ") --";
                        memoryPresentation[address] += temp + " --]";
                    }
                    else {
                        processStart = currentlyUsedMemorySlots.get(isSlotFree).getEnd() + 1;
                        memoryPresentation[address] = "[--" + temp;
                        memoryPresentation[address] += " -- Process(" + p.getPCB().getPid() + ") --]";

                    }
                }
                else memoryPresentation[address] = "[-- Process(" + p.getPCB().getPid() + ") --]";
                int processEnd = processStart + p.getMemoryRequirements();
                System.out.println("\t\t\t\t\u001b[32mMMU: Found a suitable position for process (" + p.getPCB().getPid() + ") \u001b[0m");
                currentlyUsedMemorySlots.add(new MemorySlot(processStart, processEnd, blockStart, blockEnd));
                processOccupyingMemory.add(new ArrayList<>());
                processOccupyingMemory.get(processOccupyingMemory.size()-1).add(p.getPCB().getPid());
                processOccupyingMemory.get(processOccupyingMemory.size()-1).add(address);
                p.waitInBackground();
                fit = true;
            }
            else {
                System.out.println("\t\t\t\t\u001b[32mMMU: Did not find a suitable position for process (" + p.getPCB().getPid() + "). Continuing..." + "\u001b[0m");
            }
        }
        System.out.print("\t\t\t\t\u001b[32mMMU: ");
        for(String print : memoryPresentation) {
            System.out.print(print);
        }
        System.out.println("\u001b[0m");
        return fit;
    }
}
