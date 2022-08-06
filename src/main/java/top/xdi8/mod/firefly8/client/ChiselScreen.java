package top.xdi8.mod.firefly8.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import top.xdi8.mod.firefly8.recipe.SymbolStoneProductionRecipe;
import top.xdi8.mod.firefly8.screen.ChiselMenu;

import java.util.List;
import java.util.Objects;

@Deprecated
public final class ChiselScreen extends AbstractContainerScreen<ChiselMenu> {
    private float scrollOffs;
    private boolean scrolling;
    private int startIndex;
    public ChiselScreen(ChiselMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        --this.titleLabelY;
    }
    // Scroll offset: -22

    @Override
    public void render(@NotNull PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        this.scrolling = false;
        int x = this.leftPos + 8;
        int y = this.topPos + 14;
        int endIndex = startIndex + 24;
        for (int i = startIndex; i < endIndex; i++) {
            int relIndex = i - startIndex;
            double dx = pMouseX - (double) (x + relIndex % 8 * 16);
            double dy = pMouseY - (double) (y + relIndex / 8 * 18);
            if (0 <= dx && dx < 16 &&
            0 <= dy && dy < 18 &&
            this.menu.clickMenuButton(Objects.requireNonNull(getMinecraft().player), i)) {
                getMinecraft().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
                Objects.requireNonNull(getMinecraft().gameMode).handleInventoryButtonClick(menu.containerId, i);
                return true;
            }
        }
        x = leftPos + 154;
        y = topPos + 9;
        if (x <= pMouseX && pMouseX < (x + 12) &&
        y <= pMouseY && pMouseY < (y + 54))
            scrolling = true;

        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if (scrolling && isScrollBarActive()) {
            int y1 = topPos + 14;
            int y2 = y1 + 54;
            scrollOffs = Mth.clamp(((float) pMouseY - (float) y1 - 7.5F) * 0.04F, 0, 1);
            startIndex = (int) ((double)(scrollOffs * (float)getOffscreenRows()) + 0.5D) << 3;
            return true;
        } else return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
    }

    @Override
    public boolean mouseScrolled(double pMouseX, double pMouseY, double pDelta) {
        if (isScrollBarActive()) {
            int i = getOffscreenRows();
            float f = (float)pDelta / (float)i;
            this.scrollOffs = Mth.clamp(this.scrollOffs - f, 0, 1);
            startIndex = (int)((double)(this.scrollOffs * (float)i) + 0.5D) << 3;
        }

        return true;
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
        this.blit(pPoseStack, leftPos + 154, topPos + 15 + scrolls, 176 + (this.isScrollBarActive() ? 0 : 12), 0, 12, 15);
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

    private int getOffscreenRows() {
        return (this.menu.getRecipes().size() + 8 - 1) / 8 - 3;
    }

    private boolean isScrollBarActive() {
        return this.menu.getRecipes().size() > 24;
    }

    private static final ResourceLocation THE_BG = new ResourceLocation("firefly8",
            "textures/menu/chisel.png");
}
