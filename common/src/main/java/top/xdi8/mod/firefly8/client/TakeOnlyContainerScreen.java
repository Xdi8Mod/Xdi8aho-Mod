package top.xdi8.mod.firefly8.client;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.qwerty770.mcmod.xdi8.api.ResourceLocationTool;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.CoreShaders;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.screen.TakeOnlyChestMenu;

@Environment(EnvType.CLIENT)
public class TakeOnlyContainerScreen extends AbstractContainerScreen<TakeOnlyChestMenu> {
    public TakeOnlyContainerScreen(TakeOnlyChestMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageHeight = 114 + 6 * 18;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(@NotNull GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(CoreShaders.POSITION_TEX);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, BACKGROUND);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(RenderType::guiTextured, BACKGROUND, i, j, 0, 0, this.imageWidth, 6 * 18 + 17, 256, 256);
        guiGraphics.blit(RenderType::guiTextured, BACKGROUND, i, j + 6 * 18 + 17, 0, 126, this.imageWidth, 96, 256, 256);
    }

    private static final ResourceLocation BACKGROUND = ResourceLocationTool.create("textures/gui/container/generic_54.png");
}
