import java.util.ArrayList;

public class WorstFit extends MemoryAllocationAlgorithm {
    
    public WorstFit(int[] availableBlockSizes) {
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
        this.pivot = -1;
        for (int i : allocatedMemory) {
            if (this.pivot == -1) this.pivot = i;
            else if (i > this.pivot) this.pivot = i;
        }
        int min = this.pivot;
        for(int i = 0; i< allocatedMemory.length; i++) {
            if(p.getMemoryRequirements() <= allocatedMemory[i] && allocatedMemory[i] >= min) {
                address = i;
                min = allocatedMemory[i];
                //fit = true;
            }
        }
        return address;
    }

}
