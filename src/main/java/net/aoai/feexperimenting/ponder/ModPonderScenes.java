package net.aoai.feexperimenting.ponder;

import net.aoai.feexperimenting.FEExperimenting;
import net.aoai.feexperimenting.block.ModBlocks;
import net.aoai.feexperimenting.block.entity.GeneratorBlockEntity;
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
    }

    public static void energyDetectorScript(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("energy_d_ponder", "Seeing current FE");

        scene.setNextUpEnabled(false);

        scene.configureBasePlate(0,0,5);
        scene.showBasePlate();
        scene.idle(10);

        scene.world().showSection(util.select().layer(0), Direction.UP);
        scene.idle(15);
        scene.rotateCameraY(45);

        scene.world().showSection(util.select().layer(1), Direction.DOWN);

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

        scene.world().showSection(util.select().layer(0), Direction.UP);
        scene.idle(15);
        scene.world().showSection(util.select().layer(1), Direction.DOWN);

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
