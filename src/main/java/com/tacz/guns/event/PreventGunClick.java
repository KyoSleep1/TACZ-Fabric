package com.tacz.guns.event;

import com.tacz.guns.api.item.IGun;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class PreventGunClick {

    public static ActionResult onLeftClickBlock(PlayerEntity player, World world, Hand hand, BlockPos pos,
                                                Direction direction) {
        // 只要主手有枪，那么禁止交互
        ItemStack itemInHand = player.getStackInHand(Hand.MAIN_HAND);
        if (itemInHand.getItem() instanceof IGun) {
            return ActionResult.FAIL;
        }
        return ActionResult.PASS;
    }
}
