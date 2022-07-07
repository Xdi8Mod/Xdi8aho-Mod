package org.featurehouse.mcmod.spm.util.platform.api.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import org.featurehouse.mcmod.spm.util.platform.impl.InternalRecipeSerializer;

public abstract class SimpleRecipeSerializer<T extends Recipe<?>> extends InternalRecipeSerializer<T> {
    public abstract T readJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe);
    public abstract T readPacket(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer);
    public abstract void writePacket(FriendlyByteBuf pBuffer, T pRecipe);
}
