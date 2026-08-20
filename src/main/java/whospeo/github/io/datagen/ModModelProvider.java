package whospeo.github.io.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.renderer.item.RangeSelectItemModel;
import net.minecraft.client.renderer.item.properties.numeric.Damage;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {

    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.itemModelOutput.accept(
                Items.DIAMOND_PICKAXE,
                ItemModelUtils.rangeSelect(
                        new Damage(true),
                        1.0f,
                        ItemModelUtils.plainModel(ModelLocationUtils.getModelLocation(Items.DIAMOND_PICKAXE)),
                        new RangeSelectItemModel.Entry[] {
                                ItemModelUtils.override(ItemModelUtils.plainModel(Identifier.fromNamespaceAndPath("rusting-tools", "item/diamond_pickaxe_worn_1")), 0.20f),
                                ItemModelUtils.override(ItemModelUtils.plainModel(Identifier.fromNamespaceAndPath("rusting-tools", "item/diamond_pickaxe_worn_2")), 0.35f),
                                ItemModelUtils.override(ItemModelUtils.plainModel(Identifier.fromNamespaceAndPath("rusting-tools", "item/diamond_pickaxe_worn_3")), 0.50f),
                                ItemModelUtils.override(ItemModelUtils.plainModel(Identifier.fromNamespaceAndPath("rusting-tools", "item/diamond_pickaxe_worn_4")), 0.65f),
                                ItemModelUtils.override(ItemModelUtils.plainModel(Identifier.fromNamespaceAndPath("rusting-tools", "item/diamond_pickaxe_worn_5")), 0.80f),
                                ItemModelUtils.override(ItemModelUtils.plainModel(Identifier.fromNamespaceAndPath("rusting-tools", "item/diamond_pickaxe_worn_6")), 0.90f),
                                ItemModelUtils.override(ItemModelUtils.plainModel(Identifier.fromNamespaceAndPath("rusting-tools", "item/diamond_pickaxe_worn_7")), 0.95f),
                        }
                )
        );
    }
}
