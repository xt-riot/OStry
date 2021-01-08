import java.util.ArrayList;

public abstract class MemoryAllocationAlgorithm {

    protected final int[] availableBlockSizes;
    protected int[] memoryBlocksEnd;
    protected int pivot;
    
    public MemoryAllocationAlgorithm(int[] availableBlockSizes) {
        this.availableBlockSizes = availableBlockSizes;
        this.memoryBlocksEnd = new int[availableBlockSizes.length];
        this.pivot = -1;
        int end = 0;
        for (int i = 0; i < availableBlockSizes.length; i++) {
            end += availableBlockSizes[i];
            memoryBlocksEnd[i] = end;
        }
    }

    public abstract int fitProcess(Process p, ArrayList<MemorySlot> currentlyUsedMemorySlots);

    protected int memoryBlockIndex(MemorySlot slot) {
        int i;
        for(i = 0; i<memoryBlocksEnd.length; i++) {
            if(slot.getBlockEnd() == memoryBlocksEnd[i])
                break;
        }
        return i;
    }

}
