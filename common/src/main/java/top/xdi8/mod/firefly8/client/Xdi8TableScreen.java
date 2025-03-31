package top.xdi8.mod.firefly8.client;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.qwerty770.mcmod.xdi8.registries.ResourceLocationTool;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.CoreShaders;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.screen.Xdi8TableMenu;

import java.util.Objects;

@Environment(EnvType.CLIENT)
public class Xdi8TableScreen extends AbstractContainerScreen<Xdi8TableMenu> {
    public Xdi8TableScreen(Xdi8TableMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        imageHeight = 166;
        imageWidth = 176;
        inventoryLabelY = imageHeight - 94;
    }
    // Button Size: (34, 16)

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(CoreShaders.POSITION_TEX);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, BACKGROUND);

        guiGraphics.blit(RenderType::guiTextured, BACKGROUND, leftPos, topPos, 0, 0, imageWidth, imageHeight, 256, 256);
        if (isOnButton(mouseX, mouseY)) {
            guiGraphics.blit(RenderType::guiTextured, BACKGROUND, leftPos + 129, topPos + 36, 176, 16, 34, 16, 256, 256);
        } else {
            guiGraphics.blit(RenderType::guiTextured, BACKGROUND, leftPos + 129, topPos + 36, 176, 0, 34, 16, 256, 256);
        }
        FormattedCharSequence cs = Component.translatable("gui.ok").getVisualOrderText();
        final int width1 = (font.width(cs)) >> 1;
        final int start = leftPos + 129 + 17 - width1;
        guiGraphics.drawString(font, cs, start, topPos + 40, 0xffffff);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (isOnButton(pMouseX, pMouseY)) {
            this.menu.clickMenuButton(Objects.requireNonNull(Objects.requireNonNull(this.minecraft).player), 0);
            Objects.requireNonNull(this.minecraft.gameMode)
                    .handleInventoryButtonClick(menu.containerId, 0);
            return true;
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    private static final ResourceLocation BACKGROUND = ResourceLocationTool.create("firefly8",
            "textures/menu/xdi8_table.png");

    private boolean isOnButton(double mouseX, double mouseY) {
        final int startX = leftPos + 129, startY = topPos + 36;
        final int endX = startX + 34, endY = startY + 16;
        return startX <= mouseX && mouseX <= endX &&
               startY <= mouseY && startY <= endY;
    }
}
