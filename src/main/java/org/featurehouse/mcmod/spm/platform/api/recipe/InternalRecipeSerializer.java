package org.featurehouse.mcmod.spm.platform.api.recipe;

import com.google.gson.JsonObject;
import com.mojang.logging.annotations.MethodsReturnNonnullByDefault;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@ApiStatus.Internal
abstract sealed class InternalRecipeSerializer<T extends Recipe<?>> extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>>
        implements RecipeSerializer<T>
        permits SimpleRecipeSerializer {
    protected abstract T readJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe);
    protected abstract T readPacket(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer);
    protected abstract void writePacket(FriendlyByteBuf pBuffer, T pRecipe);


    @Override
    public T fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
        return readJson(pRecipeId, pSerializedRecipe);
    }

    @Nullable
    @Override
    public T fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
        return readPacket(pRecipeId, pBuffer);
    }

    @Override
    public void toNetwork(FriendlyByteBuf pBuffer, T pRecipe) {
        writePacket(pBuffer, pRecipe);
    }
}
