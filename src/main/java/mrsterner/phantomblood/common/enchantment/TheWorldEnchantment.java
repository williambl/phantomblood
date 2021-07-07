package mrsterner.phantomblood.common.enchantment;

import mrsterner.phantomblood.PhantomBlood;
import mrsterner.phantomblood.common.item.ArrowHeadItem;
import net.minecraft.enchantment.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class TheWorldEnchantment extends Enchantment {
    public TheWorldEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot[] slotTypes) {
        super(weight, type, slotTypes);
    }
    @Override
    protected boolean canAccept(Enchantment other) {
        return super.canAccept(other)
                && other != PhantomBlood.CRAZY_DIAMOND_ENCHANTMENT
                && other != PhantomBlood.THE_SUN_ENCHANTMENT
                && other != PhantomBlood.STAR_PLATINUM_ENCHANTMENT
                && other != PhantomBlood.KILLER_QUEEN_ENCHANTMENT
                && other != PhantomBlood.DARK_BLUE_MOON_ENCHANTMENT
                && other != PhantomBlood.WEATHER_REPORT_ENCHANTMENT;
    }

    @Override
    public int getMinPower(int level) {
        return 20;
    }

    @Override
    public int getMaxPower(int level) { return 50; }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return super.isAcceptableItem(stack);
    }
    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }


}