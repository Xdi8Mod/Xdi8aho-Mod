package org.featurehouse.mcmod.spm.platform.api.recipe;

import com.google.gson.JsonObject;
import com.mojang.logging.annotations.MethodsReturnNonnullByDefault;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract non-sealed class SimpleRecipeSerializer<T extends Recipe<?>> extends InternalRecipeSerializer<T> {
    public abstract T readJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe);
    public abstract T readPacket(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer);
    public abstract void writePacket(FriendlyByteBuf pBuffer, T pRecipe);
}
