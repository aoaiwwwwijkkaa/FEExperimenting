package net.aoai.feexperimenting;

import net.aoai.feexperimenting.ponder.ModPonderScenes;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.foundation.PonderIndex;
import net.createmod.ponder.foundation.registration.PonderSceneRegistry;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = FEExperimenting.MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = FEExperimenting.MODID, value = Dist.CLIENT)
public class FEExperimentingClient {
    public FEExperimentingClient(ModContainer container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.

        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code

        event.enqueueWork(() -> {
            // Directly register your storyboard scenes using your plugin instance or inline call
            ModPonderScenes plugin = new ModPonderScenes();
            // Pass the active registration helper if exposed by your Ponder dependency jar,
            // or call your registration routine directly.
            // Check your IDE suggestions for PonderRegistry / PonderIndex / PonderRegistrationHelper

            PonderIndex.addPlugin(plugin);
        });
    }
}
