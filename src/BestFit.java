import java.util.ArrayList;
import java.util.Arrays;

public class BestFit extends MemoryAllocationAlgorithm {
    
    public BestFit(int[] availableBlockSizes) {
        super(availableBlockSizes);
    }

    public int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots) {
        boolean fit = false;
        int address = -1;
        /* TODO: you need to add some code here
         * Hint: this should return the memory address where the process was
         * loaded into if the process fits. In case the process doesn't fit, it
         * should return -1. */
        int[] allocatedMemory = availableBlockSizes.clone();
        for(MemorySlot slot : currentlyUsedMemorySlots) {
            int temp = memoryBlockIndex(slot);
            allocatedMemory[temp] -= slot.getEnd() - slot.getStart();
        }
        for(int i = 0; i< allocatedMemory.length; i++) {
            if (pivot == -1) pivot = allocatedMemory[i];
            else if (availableBlockSizes[i] > pivot) pivot = allocatedMemory[i];
        }
        int min = this.pivot;
        for(int i = 0; i< allocatedMemory.length; i++) {
            if(p.getMemoryRequirements() <= allocatedMemory[i] && allocatedMemory[i] <= min) {
                address = i;
                min = allocatedMemory[i];
                fit = true;
            }
        }
        return address;
    }
}
