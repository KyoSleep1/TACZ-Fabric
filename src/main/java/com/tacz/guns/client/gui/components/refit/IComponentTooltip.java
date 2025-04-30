package com.tacz.guns.client.gui.components.refit;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.List;
import java.util.function.Consumer;

public interface IComponentTooltip {
    /**
     * 获取物品的文本提示
     */
    static List<Text> getTooltipFromItem(ItemStack stack) {
        final ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return List.of();
        final GameOptions options = MinecraftClient.getInstance().options;
        return stack.getTooltip(Item.TooltipContext.create(player.clientWorld), player,
                options.advancedItemTooltips ? TooltipType.Default.ADVANCED : TooltipType.Default.BASIC);
    }

    /**
     * 添加此接口，会调用此渲染文本提示
     *
     * @param consumer 需要渲染的文本提示
     */
    void renderTooltip(Consumer<List<Text>> consumer);
}
