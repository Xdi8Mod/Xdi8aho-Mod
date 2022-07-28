package top.xdi8.mod.firefly8.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.screen.Xdi8TableMenu;

public class Xdi8TableScreen extends AbstractContainerScreen<Xdi8TableMenu> {
    public Xdi8TableScreen(Xdi8TableMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        imageHeight = 166;
        imageHeight = 181;
        inventoryLabelY = imageHeight - 94;
    }

    @Override
    protected void renderBg(@NotNull PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, THE_BG);
        int x = (width - imageWidth) / 2;
        int y = (height = imageHeight) / 2;

        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);
        //TODO
    }

    @Override
    public void render(@NotNull PoseStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        //RenderSystem.disableBlend();
        this.renderTooltip(matrices, mouseX, mouseY);
    }

    private static final ResourceLocation THE_BG = new ResourceLocation("firefly8",
            "textures/menu/xdi8_table.png");
}
