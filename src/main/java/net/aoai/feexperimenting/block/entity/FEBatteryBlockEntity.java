package net.aoai.feexperimenting.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.EnergyStorage;

public class FEBatteryBlockEntity extends BlockEntity {
    private final EnergyStorage energy = new EnergyStorage(500, 1000, 1000);

    public FEBatteryBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEnts.FE_BATTERY_BLOCK_ENTITY_TYPE.get(), pos, blockState);
    }

    @Override
    public BlockPos getBlockPos() {
        BlockPos pos = super.getBlockPos();
        return pos != null ? pos : BlockPos.ZERO;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("energy", energy.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("energy")) {
            try {
                int stored = tag.getInt("energy");
                this.energy.receiveEnergy(stored - this.energy.getEnergyStored(), false);
            } catch (Exception e) {
                this.energy.deserializeNBT(registries, tag.get("energy"));
            }
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    public EnergyStorage getStorage() {
        return energy;
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (this.energy.getEnergyStored() > 0) {
            distributeEnergy(level, pos);
        }
    }

    private void distributeEnergy(Level level, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            BlockPos targetPos = pos.relative(direction);

            var neighborEnergy = level.getCapability(Capabilities.EnergyStorage.BLOCK, targetPos, direction.getOpposite());

            if (neighborEnergy != null && neighborEnergy.canReceive()) {
                int maxOutput = Math.min(this.energy.getEnergyStored(), 1000);

                int accepted = neighborEnergy.receiveEnergy(maxOutput, true);

                if (accepted > 0) {
                    neighborEnergy.receiveEnergy(accepted, false);
                    this.energy.extractEnergy(accepted, false);
                    setChanged();
                }
            }
        }
    }
}
