package mrsterner.phantomblood.common.stand;


import mrsterner.phantomblood.PhantomBlood;
import mrsterner.phantomblood.common.entity.KillerVirusEntity;
import mrsterner.phantomblood.common.registry.PBObjects;
import mrsterner.phantomblood.common.registry.PBSoundEvents;
import mrsterner.phantomblood.common.registry.PBStatusEffects;
import mrsterner.phantomblood.common.timestop.TimeStopUtils;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.DropperBlock;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.Locale;

public enum Stand {
    NONE(0, (server, player, handler1, buf, responseSender) -> {}),
    THE_WORLD(60000, (server, player, handler1, buf, responseSender) -> {
        server.execute(() -> {
            int energy = StandUtils.getStandEnergy(player);
            int energyForAbility = StandUtils.getStand(player).energyForAbility;
            long ticks = StandUtils.getStandLevel(player) == 0 ? 120 : 200;
            if (energy >= energyForAbility) {
                StandUtils.setStandEnergy(player, energy - energyForAbility);
                TimeStopUtils.setTimeStoppedTicks(player.world, ticks);
                TimeStopUtils.setTimeStopper(player.world, player);
                player.world.playSound(null, player.getBlockPos(), PBSoundEvents.THE_WORLD, SoundCategory.PLAYERS, 1, 1f);
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, (int) ticks));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, (int) ticks));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, (int) ticks));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, (int) ticks));
                server.getPlayerManager().sendToAll(ServerPlayNetworking.createS2CPacket(new Identifier("phantomblood:stop_time"), PacketByteBufs.create().writeUuid(player.getUuid()).writeVarLong(ticks)));
            }
        });
    }),
    STAR_PLATINUM(40000, (server, player, handler1, buf, responseSender) -> {
        server.execute(() -> {
            long ticks = StandUtils.getStandLevel(player) == 0 ? 90 : 180;
            int energy = StandUtils.getStandEnergy(player);
            int energyForAbility = StandUtils.getStand(player).energyForAbility;
             if (energy >= energyForAbility) {
                StandUtils.setStandEnergy(player, energy - energyForAbility);
                TimeStopUtils.setTimeStoppedTicks(player.world, ticks);
                TimeStopUtils.setTimeStopper(player.world, player);
                player.world.playSound(null, player.getBlockPos(), PBSoundEvents.THE_WORLD, SoundCategory.PLAYERS, 1, 1f);
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, (int) ticks, 1));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, (int) ticks, 2));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, (int) ticks));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, (int) ticks));
                server.getPlayerManager().sendToAll(ServerPlayNetworking.createS2CPacket(new Identifier("phantomblood:stop_time"), PacketByteBufs.create().writeUuid(player.getUuid()).writeVarLong(ticks)));
            }
        });
    }),
    KILLER_QUEEN(8000, (server, player, handler1, buf, responseSender) -> {}),
    CRAZY_DIAMOND(10000, (server, player, handler1, buf, responseSender) -> {
        int energy = StandUtils.getStandEnergy(player);
        int energyForAbility = StandUtils.getStand(player).energyForAbility;
        if(StandUtils.isStandActive(player) && StandUtils.getStandMode(player) == StandMode.HEALING){
            StandUtils.setStandMode(player, StandMode.ATTACKING);
        }
        else if(StandUtils.isStandActive(player) && StandUtils.getStandMode(player) == StandMode.ATTACKING && energy >= energyForAbility){
            StandUtils.setStandEnergy(player, energy - energyForAbility);
            StandUtils.setStandMode(player, StandMode.HEALING);
        }
    }),
    WEATHER_REPORT(8000, (server, player, handler1, buf, responseSender) -> {}),
    DARK_BLUE_MOON(8000, (server, player, handler1, buf, responseSender) -> {
        server.execute(() -> {
            long ticks = StandUtils.getStandLevel(player) == 0 ? 200 : 300;
            player.addStatusEffect(new StatusEffectInstance(PBStatusEffects.DARK_BLUE_MOON, (int) ticks));
        });

    }),
    THE_SUN(3000, (server, player, handler1, buf, responseSender) -> {}),
    KING_CRIMSON(3000, (server, player, handler1, buf, responseSender) -> {}),
    PURPLE_HAZE(60000, (server, player, handler1, buf, responseSender) -> {
        server.execute(() ->{
            int energy = StandUtils.getStandEnergy(player);
            int energyForAbility = StandUtils.getStand(player).energyForAbility;
            if (energy >= energyForAbility) {
                ItemStack itemStack = new ItemStack(PBObjects.KILLER_VIRUS);
                KillerVirusEntity killerVirusEntity = new KillerVirusEntity(player.world, player);
                killerVirusEntity.setItem(itemStack);
                killerVirusEntity.setPosition(player.getX() - MathHelper.cos(player.getHeadYaw()) , player.getY() + 1D, player.getZ() - MathHelper.cos(player.getHeadYaw()));

                //killerVirusEntity.setPos(player.getX()-1D, player.getY() + 1D, player.getZ());
                //killerVirusEntity.setProperties(player, player.pitch, player.yaw, 0.0F, 0.5F, 2F); //modifierZ is the power thrown, 1.5F is standard
                float f = player.getHeadYaw();
                player.world.spawnEntity(killerVirusEntity); // spawns entity
            }
        });
    }),
    TWENTY_CENTURY_BOY(30000, (server, player, handler1, buf, responseSender) -> {}),
    HIEROPHANT_GREEN(3000, (server, player, handler1, buf, responseSender) -> {}),
    ANUBIS(3000, (server, player, handler1, buf, responseSender) -> {});

    public int energyForAbility;
    public ServerPlayNetworking.PlayChannelHandler handler;
    Stand(int i, ServerPlayNetworking.PlayChannelHandler handlerIn) {
        energyForAbility = i;
        handler = handlerIn;
    }

    @Override
    public String toString() {
        return "stand.phantomblood."+name().toLowerCase(Locale.ROOT);
    }
}