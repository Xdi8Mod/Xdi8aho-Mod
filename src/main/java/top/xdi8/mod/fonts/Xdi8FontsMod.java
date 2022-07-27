package top.xdi8.mod.fonts;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Xdi8FontsMod {
    private static final ResourceLocation FONT_CFG = new ResourceLocation("xdi8_fonts",
            "modernui/fonts.json");
    private static final Logger LOGGER = LogUtils.getLogger();

    public Xdi8FontsMod() {

    }

    @SuppressWarnings("unused") // Soft-invoked in icyllis.modernui.forge.ModernUIForge$Client#onGetSelectedTypeface
    public static void loadFont(Set<Font> selected) {
        final ResourceManager rm = Minecraft.getInstance().getResourceManager();
        final List<ResourceLocation> requiredFonts = getRequiredFonts(rm);
        for (ResourceLocation fontId : requiredFonts) {
            try(Resource resource = rm.getResource(fontId)) {
                Font font = Font.createFont(Font.TRUETYPE_FONT, resource.getInputStream());
                selected.add(font);
                LOGGER.debug("Loaded xdi8 font: {}", fontId);
            } catch (IOException | FontFormatException e) {
                LOGGER.error("Can't load font " + fontId, e);
            }
        }
    }

    private static List<ResourceLocation> getRequiredFonts(ResourceManager rm) {
        List<Resource> resources;
        try {
            resources = rm.getResources(FONT_CFG);
        } catch (IOException e) {
            LOGGER.error("Unable to parse xdi8 fonts config", e);
            resources = List.of();
        }

        List<ResourceLocation> add = new ArrayList<>();
        Set<ResourceLocation> remove = new HashSet<>();
        for (Resource resource : Lists.reverse(resources)) {
            final JsonObject obj = GsonHelper.parse(new BufferedReader(new InputStreamReader(
                    resource.getInputStream(), StandardCharsets.UTF_8)));
            JsonArray arr;
            arr = GsonHelper.getAsJsonArray(obj, "add", new JsonArray());
            for (JsonElement e : arr)
                add.add(new ResourceLocation(GsonHelper.convertToString(e, "added")));
            arr = GsonHelper.getAsJsonArray(obj, "remove", new JsonArray());
            for (JsonElement e : arr)
                remove.add(new ResourceLocation(GsonHelper.convertToString(e, "removed")));
        }
        add.removeAll(remove);

        return add.stream()
                .map(r -> new ResourceLocation(r.getNamespace(), "modernui/" + r.getPath()))
                .toList();
    }

    @Mod("xdi8_fonts")  // avoid CoreMod crash
    public static final class Wrapper {}
}
