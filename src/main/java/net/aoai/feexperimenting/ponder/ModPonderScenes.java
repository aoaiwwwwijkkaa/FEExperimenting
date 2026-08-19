package net.aoai.feexperimenting.ponder;

import net.aoai.feexperimenting.FEExperimenting;
import net.aoai.feexperimenting.block.ModBlocks;
import net.aoai.feexperimenting.item.ModItems;
import net.createmod.catnip.math.Pointing;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

public class ModPonderScenes implements PonderPlugin {
    @Override
    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        ModPonderTags.reg(helper);
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        helper.forComponents(ModBlocks.GENERATOR.getId())
                .addStoryBoard(ResourceLocation.fromNamespaceAndPath(FEExperimenting.MODID, "generator_ponder"), ModPonderScenes::generatorScript);
        helper.forComponents(ModItems.ENERGY_DETECTOR.getId())
                .addStoryBoard(ResourceLocation.fromNamespaceAndPath(FEExperimenting.MODID, "energy_d_ponder"), ModPonderScenes::energyDetectorScript);
        helper.forComponents(ModBlocks.FE_BATTERY.getId())
                .addStoryBoard(ResourceLocation.fromNamespaceAndPath(FEExperimenting.MODID, "fe_battery_ponder"), ModPonderScenes::feBatteryScript);
    }

    public static void feBatteryScript(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("fe_battery_ponder", "Storing and extracting FE");

        scene.setNextUpEnabled(false);

        scene.configureBasePlate(0,0,5);
        scene.showBasePlate();
        scene.idle(10);

        scene.world().showSection(util.select().layersFrom(1), Direction.DOWN);
        scene.idle(100);

        scene.world().setBlock(new BlockPos(3,1,2), ModBlocks.ELECTRICAL_FURNACE.get().defaultBlockState(), true);

        scene.idle(80);
    }

    public static void energyDetectorScript(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("energy_d_ponder", "Seeing current FE");

        scene.setNextUpEnabled(false);

        scene.configureBasePlate(0,0,5);
        scene.showBasePlate();
        scene.idle(10);

        scene.world().showSection(util.select().layersFrom(1), Direction.DOWN);
        scene.idle(15);
        scene.rotateCameraY(45);

        scene.idle(20);

        scene.overlay().showControls(
                util.select().position(1, 1, 2).getCenter(),
                Pointing.DOWN,
                20
        ).rightClick().withItem(ModItems.ENERGY_DETECTOR.toStack());

        scene.idle(60);


        scene.overlay().showControls(
                util.select().position(2, 1, 2).getCenter(),
                Pointing.DOWN,
                20
        ).rightClick().withItem(ModItems.ENERGY_DETECTOR.toStack()).whileSneaking();
        scene.idle(80);
    }

    public static void generatorScript(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("generator_ponder", "Generating FE");

        scene.setNextUpEnabled(false);

        scene.configureBasePlate(0,0,3);
        scene.showBasePlate();
        scene.idle(10);

        scene.world().showSection(util.select().layersFrom(1), Direction.DOWN);

        scene.idle(20);

        scene.overlay().showText(80)
                .text("This is the generator.")
                .attachKeyFrame()
                .pointAt(util.select().position(1,1,1).getCenter());

        scene.idle(90);

        scene.overlay().showText(100)
                .text("If it is blocked from above, it wont work.")
                .pointAt(util.select().position(1,1,1).getCenter());

        scene.idle(120);

        scene.rotateCameraY(-90);

        scene.idle(20);

        scene.overlay().showText(120)
                .text("This is the power source.")
                .attachKeyFrame()
                .pointAt(util.select().position(BlockPos.containing(util.select().position(new BlockPos(1, 1, 2)).getCenter())).getCenter());
        scene.idle(130);

        scene.rotateCameraY(90);

        scene.idle(80);
    }

    @Override
    public String getModId() {
        return FEExperimenting.MODID;
    }
}
