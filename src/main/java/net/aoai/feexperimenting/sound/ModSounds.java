package net.aoai.feexperimenting.sound;

import net.aoai.feexperimenting.FEExperimenting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, FEExperimenting.MODID);

    public static final Supplier<SoundEvent> DETECTOR_BUZZ = regSound("detector_buzz");

    private static Supplier<SoundEvent> regSound(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(FEExperimenting.MODID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
