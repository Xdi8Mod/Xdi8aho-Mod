package top.xdi8.mod.firefly8.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.qwerty770.mcmod.xdi8.api.ResourceLocationTool;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.screen.Xdi8TableMenu;

import java.util.Objects;

public class Xdi8TableScreen extends AbstractContainerScreen<Xdi8TableMenu> {
    public Xdi8TableScreen(Xdi8TableMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        imageHeight = 166;
        imageWidth = 176;
        inventoryLabelY = imageHeight - 94;
    }
    // Button Size: (34, 16)

    @Override
    protected void renderBg(@NotNull PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, THE_BG);

        this.blit(pPoseStack, leftPos, topPos, 0, 0, imageWidth, imageHeight);
        if (isOnButton(pMouseX, pMouseY)) {
            this.blit(pPoseStack, leftPos + 129, topPos + 36,
                    176, 16,
                    34, 16);
        } else {
            this.blit(pPoseStack, leftPos + 129, topPos + 36,
                    176, 0,
                    34, 16);
        }
        FormattedCharSequence cs = Component.translatable("gui.ok").getVisualOrderText();
        final int width1 = (font.width(cs)) >> 1;
        final int start = leftPos + 129 + 17 - width1;
        font.draw(pPoseStack, cs, start, topPos + 40, 0xffffff);

    }

    @Override
    public void render(@NotNull PoseStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        //RenderSystem.disableBlend();
        this.renderTooltip(matrices, mouseX, mouseY);
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

    private static final ResourceLocation THE_BG = ResourceLocationTool.create("firefly8",
            "textures/menu/xdi8_table.png");

    private boolean isOnButton(double mouseX, double mouseY) {
        final int startX = leftPos + 129, startY = topPos + 36;
        final int endX = startX + 34, endY = startY + 16;
        return startX <= mouseX && mouseX <= endX &&
               startY <= mouseY && startY <= endY;
    }
}
