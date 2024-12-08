package top.xdi8.mod.firefly8.core.totem;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import io.github.qwerty770.mcmod.xdi8.api.ResourceLocationTool;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.xdi8.mod.firefly8.item.symbol.Xdi8TotemItem;

import java.util.ArrayList;
import java.util.List;

public interface TotemAbilityPredicate {
    boolean matches(@NotNull ItemStack stack);

    default JsonElement toJson() { return JsonNull.INSTANCE; }

    static TotemAbilityPredicate fromJson(@Nullable JsonElement element) {
        if (element != null && !element.isJsonNull()) {
            JsonObject obj = GsonHelper.convertToJsonObject(element, "totem_root");
            final ItemPredicate itemPredicate = ItemPredicate.fromJson(obj);

            JsonArray arr = GsonHelper.getAsJsonArray(obj, "totems", new JsonArray());
            List<TotemAbility> list = new ArrayList<>();
            for (JsonElement e : arr) {
                final String s = GsonHelper.convertToString(e, "totem");
                final TotemAbility totemAbility = TotemAbilities.byId(ResourceLocationTool.create(s)).orElseThrow(() ->
                        new IllegalArgumentException("Invalid totem id: " + s));
                list.add(totemAbility);
            }
            return new Impl(itemPredicate, list);
        }
        return any();
    }

    final class Impl implements TotemAbilityPredicate {
        private final ItemPredicate itemPredicate;
        private final List<TotemAbility> totemAbilities;

        public Impl(ItemPredicate itemPredicate, List<TotemAbility> totemAbilities) {
            this.itemPredicate = itemPredicate;
            this.totemAbilities = totemAbilities;
        }

        public boolean matches(@NotNull ItemStack stack) {
            if (!itemPredicate.matches(stack)) return false;
            if (totemAbilities.isEmpty()) return true;
            final TotemAbility ability = Xdi8TotemItem.getAbility(stack);
            return totemAbilities.contains(ability);
        }

        @Override
        public JsonObject toJson() {
            JsonObject obj = new JsonObject();
            final JsonElement json = itemPredicate.serializeToJson();
            if (json.isJsonObject()) obj = json.getAsJsonObject();
            if (!totemAbilities.isEmpty()) {
                JsonArray arr = new JsonArray();
                for (TotemAbility ability : totemAbilities) {
                    arr.add(ability.getId().toString());
                }
                obj.add("totems", arr);
            }
            return obj;
        }
    }

    @Contract(pure = true)
    static @NotNull TotemAbilityPredicate any() {
        return itemStack -> true;
    }
}
