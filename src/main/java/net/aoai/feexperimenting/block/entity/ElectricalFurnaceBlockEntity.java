package net.aoai.feexperimenting.block.entity;

import net.aoai.feexperimenting.block.custom.ElectricalFurnaceBlock;
import net.aoai.feexperimenting.recipe.ElectricalFurnaceRecipe;
import net.aoai.feexperimenting.recipe.ElectricalFurnaceRecipeInput;
import net.aoai.feexperimenting.recipe.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Random;

public class ElectricalFurnaceBlockEntity extends BlockEntity {
    private static final Random random = new Random();
    private final EnergyStorage energy = new EnergyStorage(100, 20, 1000);

    protected final ContainerData data;
    private int prog = 0;
    private int maxprog = 64;

    public ElectricalFurnaceBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEnts.ELECTRICAL_FURNACE_BLOCK_ENTITY_TYPE.get(), pos, blockState);
        data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i) {
                    case 0 -> ElectricalFurnaceBlockEntity.this.prog;
                    case 1 -> ElectricalFurnaceBlockEntity.this.maxprog;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {
                switch (i) {
                    case 0: ElectricalFurnaceBlockEntity.this.prog = value;
                    case 1: ElectricalFurnaceBlockEntity.this.maxprog = value;
                }
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
    }

    public final ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(),getBlockState(),getBlockState(),3);
            }
        }
    };

    public void clearContents() {
        inventory.setStackInSlot(0,ItemStack.EMPTY);
    }

    public void drops() {
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());

        for (int i = 0; i < inventory.getSlots(); i++) {
            inv.setItem(i, inventory.getStackInSlot(i));
        }

        Containers.dropContents(this.level,this.worldPosition,inv);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inv", inventory.serializeNBT(registries));
        tag.putInt("electrical_furnace.prog", prog);
        tag.putInt("electrical_furnace.maxprog", maxprog);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inv"));
        prog = tag.getInt("electrical_furnace.prog");
        maxprog = tag.getInt("electrical_furnace.maxprog");
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    private boolean hasRecipe() {
        Optional<RecipeHolder<ElectricalFurnaceRecipe>> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) {
            return false;
        }
        
        return true;
    }

    private Optional<RecipeHolder<ElectricalFurnaceRecipe>> getCurrentRecipe() {
        return this.level.getRecipeManager()
                .getRecipeFor(ModRecipes.ELECTRICAL_FURNACE_TYPE.get(), new ElectricalFurnaceRecipeInput(inventory.getStackInSlot(0)),level);
    }

    private boolean hasPower() {return this.energy.getEnergyStored() > 0;}

    private void reset() {
        prog = 0;
        maxprog = 128;
    }

    private void increaseProgress() {
        prog++;
    }

    private boolean hasFinished() {
        return this.prog > this.maxprog;
    }


    public void tick(Level lvl, BlockPos pos, BlockState state) {
        if (hasRecipe() && hasPower()) {
            increaseProgress();
            setChanged(lvl,pos,state);

            energy.extractEnergy(6,false);

            if (hasFinished()) {
                lvl.playSound(
                        null,
                        pos,
                        SoundEvents.LAVA_EXTINGUISH,
                        SoundSource.BLOCKS,
                        1f,1f
                );

                craft();
                reset();
            }
        } else {
            reset();
        }
    }

    private void craft() {
        Optional<RecipeHolder<ElectricalFurnaceRecipe>> recipe = getCurrentRecipe();
        ItemStack output = recipe.get().value().output();

        inventory.extractItem(0,1,false);
        inventory.setStackInSlot(0, new ItemStack(output.getItem(),
                inventory.getStackInSlot(0).getCount() + output.getCount()));
    }

    public EnergyStorage getStorage() {
        return energy;
    }
}
