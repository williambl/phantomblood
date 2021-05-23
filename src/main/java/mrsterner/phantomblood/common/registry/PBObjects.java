package mrsterner.phantomblood.common.registry;

import moriyashiine.bewitchment.common.registry.BWMaterials;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.entity.*;
import net.minecraft.tag.Tag;
import net.minecraft.util.Rarity;
import mrsterner.phantomblood.PhantomBlood;
import mrsterner.phantomblood.common.item.*;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

import static mrsterner.phantomblood.PhantomBlood.PHANTOMBLOOD_GROUP;


public class PBObjects {
    private static final Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
    private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

    //Armor
    public static final Item STONE_MASK_ITEM = create("stonemaskitem", new StonemaskItem(ArmorMaterials.DIAMOND, EquipmentSlot.HEAD, gen()));
    public static final Item BLOODY_STONE_MASK_ITEM = create("bloodstonemaskitem", new BloodStonemaskItem(ArmorMaterials.DIAMOND, EquipmentSlot.HEAD, gen()));
    public static final Item VAMPIRE_HAT = create("vampire_hat", new VampireArmorItem(BWMaterials.BESMIRCHED_ARMOR, EquipmentSlot.HEAD));
    public static final Item VAMPIRE_JACKET = create("vampire_coat", new VampireArmorItem(BWMaterials.BESMIRCHED_ARMOR, EquipmentSlot.CHEST));
    public static final Item VAMPIRE_PANTS = create("vampire_pants", new VampireArmorItem(BWMaterials.BESMIRCHED_ARMOR, EquipmentSlot.LEGS));
    public static final Item VAMPIRE_BOOTS = create("vampire_shoes", new VampireArmorItem(BWMaterials.BESMIRCHED_ARMOR, EquipmentSlot.FEET));
    //Item
    public static final Item STONE_OF_AJA = create("stone_of_aja", new Item(gen()));

    public static final Item TEST_ITEM = create("test_item", new TestItem(gen().maxCount(1)));
    //Food
    public static final Item BLOODY_MEAL = create("bloody_meal", new Item(gen().food(new FoodComponent.Builder().hunger(1).saturationModifier(1).alwaysEdible().meat().build())));
    //Tags
    public static final Tag<Item> VAMPIRE_FOODS = TagRegistry.item(new Identifier(PhantomBlood.MODID, "vampire_foods"));


    //Register
    private static <T extends Block> T create(String name, T block, boolean createItem) {
        BLOCKS.put(block, new Identifier(PhantomBlood.MODID, name));
        if (createItem) {
            ITEMS.put(new BlockItem(block, gen()), BLOCKS.get(block));
        }
        return block;
    }

    private static <T extends Item> T create(String name, T item) {
        ITEMS.put(item, new Identifier(PhantomBlood.MODID, name));
        return item;
    }

    private static Item.Settings gen() {
        return new Item.Settings().group(PHANTOMBLOOD_GROUP);
    }

    public static void init() {
        BLOCKS.keySet().forEach(block -> Registry.register(Registry.BLOCK, BLOCKS.get(block), block));
        ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));
    }

}
