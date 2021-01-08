import java.util.ArrayList;

public class NextFit extends MemoryAllocationAlgorithm {
    private int lastAddress;
    
    public NextFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
        lastAddress = 0;
    }

    public int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots) {
        boolean fit = false;
        int address = -1;
        /* DONE: you need to add some code here
         * Hint: this should return the memory address where the process was
         * loaded into if the process fits. In case the process doesn't fit, it
         * should return -1. */

        int[] allocatedMemory = availableBlockSizes.clone();

        for(MemorySlot slot : currentlyUsedMemorySlots) {
            int temp = memoryBlockIndex(slot);
            allocatedMemory[temp] -= slot.getEnd() - slot.getStart();
        }
        for(int i = lastAddress; i< allocatedMemory.length; i++) {
            if(p.getMemoryRequirements() <= allocatedMemory[i]) {
                address = i;
                if(i == allocatedMemory.length-1) lastAddress = 0;
                else lastAddress = i;
                //System.out.println("// " + lastAddress);
                //fit = true;
                break;
            }
            if(i == allocatedMemory.length-1 && !fit) {
                i = 0;
                fit = true;
            }
        }
        return address;
    }

}
