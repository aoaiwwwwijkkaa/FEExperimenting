package net.aoai.feexperimenting.item.custom;

import com.sun.jdi.connect.spi.TransportService;
import net.aoai.feexperimenting.block.entity.ElectricalFreezerBlockEntity;
import net.aoai.feexperimenting.block.entity.ElectricalFurnaceBlockEntity;
import net.aoai.feexperimenting.block.entity.GeneratorBlockEntity;
import net.aoai.feexperimenting.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.EnergyStorage;

import java.util.List;

public class EnergyDetector extends Item {
    public EnergyDetector(Properties properties) {
        super(properties);
    }

    private static void display(Player user, int CurrentFE, int MaxFE) {
        user.displayClientMessage(Component.literal(CurrentFE + "FE/" + MaxFE + "FE Detected"), true);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player user = context.getPlayer();
        InteractionHand hand = context.getHand();
        ItemStack stack = user.getItemInHand(hand);

        if (!level.isClientSide()) {
            BlockPos pos = context.getClickedPos();

            BlockEntity bE = level.getBlockEntity(pos);

            EnergyStorage storage = (EnergyStorage) Capabilities.EnergyStorage.BLOCK.getCapability(level, pos, bE.getBlockState(), bE, null);

            if (storage != null) {
                int CurrentFE = storage.getEnergyStored();
                int MaxFE = storage.getMaxEnergyStored();

                display(user, CurrentFE, MaxFE);

                stack.setDamageValue(stack.getDamageValue() - 10);

                level.playSound(null, pos, ModSounds.DETECTOR_BUZZ.get(), SoundSource.PLAYERS, 1f, 1f);
            }
        }

        return super.useOn(context);
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, LevelReader level, BlockPos pos, Player player) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("feexperimenting.detector.tooltip"));

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
