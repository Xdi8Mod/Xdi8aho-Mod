package top.xdi8.mod.firefly8.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.recipe.SymbolStoneProductionRecipe;
import top.xdi8.mod.firefly8.screen.ChiselMenu;

import java.util.List;

public final class ChiselScreen extends AbstractContainerScreen<ChiselMenu> {
    private float scrollOffs;
    private boolean scrolling;
    private int startIndex;
    public ChiselScreen(ChiselMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        --this.titleLabelY;
    }

    @Override
    public void render(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(@NotNull PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        this.renderBackground(pPoseStack);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, THE_BG);
        this.blit(pPoseStack, leftPos, topPos, 0, 0,
                imageWidth, imageHeight);
        int scrolls = (int)(41 * scrollOffs);
        this.blit(pPoseStack, leftPos + 119, topPos + 15 + scrolls, 176 + (this.isScrollBarActive() ? 0 : 12), 0, 12, 15);
        int x = leftPos + 8;
        int y = topPos + 14;
        int startIndex = this.startIndex + 24;
        this.renderButtons(pPoseStack, pMouseX, pMouseY, x, y, startIndex);
        this.renderRecipes(x, y, startIndex);
    }

    @Override
    protected void renderTooltip(@NotNull PoseStack pPoseStack, int pX, int pY) {
        super.renderTooltip(pPoseStack, pX, pY);
        final int startX = leftPos + 8;
        final int startY = topPos + 14;
        final int startIndex = this.startIndex + 24;
        final List<SymbolStoneProductionRecipe> recipes = this.menu.getRecipes();

        for (int i = this.startIndex; i < startIndex && i < menu.getRecipes().size(); i++) {
            int relativeIndex = i - startIndex;
            int relativeMajX = startX + relativeIndex % 8 * 16;
            int relativeMajY = startY + relativeIndex / 8 * 18 + 2;

            if (relativeMajX <= pX && pX < startX + 16 &&
                relativeMajY <= pY && pY < startY + 18) {
                this.renderTooltip(pPoseStack, recipes.get(i).getResultItem(), pX, pY);
            }
        }
    }

    private void renderButtons(PoseStack poseStack, int mouseX, int mouseY, int x, int y, int lastVisibleIndex) {
        for (int i = startIndex; i < lastVisibleIndex && i < menu.getRecipes().size(); i++) {
            int relativeIndex = i - startIndex;
            int relativeMajX = x + relativeIndex % 8 * 16;
            int l = relativeMajX / 8;
            int i1 = y + l * 18 + 2;
            int height = imageHeight;
            if (relativeMajX <= mouseX && mouseX < relativeIndex + 16 &&
            mouseY < i1 + 18) {
                height += 36;
            }

            this.blit(poseStack, relativeMajX, i1 - 1,
                    0, height,
                    16, 18);
        }
    }

    private void renderRecipes(int x, int y, int off) {
        final List<SymbolStoneProductionRecipe> recipes = menu.getRecipes();

        for (int i = startIndex; i < off && i < recipes.size(); i++) {
            int relativeIndex = i - startIndex;
            int relativeMajX = x + relativeIndex % 8 * 16;
            int l = relativeMajX / 8;
            int i1 = y + l * 18 + 2;
            this.getMinecraft().getItemRenderer()
                    .renderAndDecorateItem(recipes.get(i).getResultItem(), relativeMajX, i1);
        }
    }

    private boolean isScrollBarActive() {
        return this.menu.getRecipes().size() > 24;
    }

    private static final ResourceLocation THE_BG = new ResourceLocation("firefly8",
            "textures/menu/chisel.png");
}
