import java.util.ArrayList;

public class FirstFit extends MemoryAllocationAlgorithm {
    
    public FirstFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
    }

    public int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots) {
        //boolean fit = false;
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
        for(int i = 0; i< allocatedMemory.length; i++) {
            if(p.getMemoryRequirements() <= allocatedMemory[i]) {
                address = i;
                //fit = true;
                break;
            }
        }
        return address;
    }

}
