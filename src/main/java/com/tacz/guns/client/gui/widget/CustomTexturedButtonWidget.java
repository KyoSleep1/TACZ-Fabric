package com.tacz.guns.client.gui.widget;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.util.Identifier;

public class CustomTexturedButtonWidget extends TexturedButtonWidget {

    private final int u;
    private final int v;
    private final int hoveredVOffset;

    public CustomTexturedButtonWidget(int x, int y, int width, int height, int u, int v, int hoveredVOffset,
                                      Identifier identifier, PressAction pressAction) {
        super(x, y, width, height, new ButtonTextures(identifier, identifier), pressAction);
        this.u = u;
        this.v = v;
        this.hoveredVOffset = hoveredVOffset;
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        final Identifier identifier = this.textures.get(this.isNarratable(), this.isSelected());
        int i = v;
        if (!this.isNarratable()) {
            i += hoveredVOffset * 2;
        } else if (this.isSelected()) {
            i += hoveredVOffset;
        }
        context.drawTexture(identifier, this.getX(), this.getY(), u, i, this.width, this.height);
    }
}
