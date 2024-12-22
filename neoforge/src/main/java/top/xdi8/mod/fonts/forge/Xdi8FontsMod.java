package top.xdi8.mod.fonts.forge;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import io.github.qwerty770.mcmod.xdi8.api.ResourceLocationTool;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;

public class Xdi8FontsMod {
    private static final ResourceLocation FONT_CFG = ResourceLocationTool.create("xdi8_fonts",
            "modernui/fonts.json");
    private static final Logger LOGGER = LogUtils.getLogger();

    public Xdi8FontsMod() {}

    @SuppressWarnings("unused") // Soft-invoked in icyllis.modernui.forge.ModernUIForge$Client#onGetSelectedTypeface
    public static void loadFont(Set<Font> selected) {
        final ResourceManager rm = Minecraft.getInstance().getResourceManager();
        final List<ResourceLocation> requiredFonts = getRequiredFonts(rm);
        for (ResourceLocation fontId : requiredFonts) {
            try {
                Resource resource = rm.getResourceOrThrow(fontId);
                Font font = Font.createFont(Font.TRUETYPE_FONT, resource.open());
                selected.add(font);
                LOGGER.debug("Loaded xdi8 font: {}", fontId);
            } catch (IOException | FontFormatException e) {
                LOGGER.error("Can't load font {}", fontId, e);
            }
        }
    }

    private static List<ResourceLocation> getRequiredFonts(ResourceManager rm) {
        List<Resource> resources;
        try {
            resources = rm.getResourceStack(FONT_CFG);
        } catch (Exception e) {
            LOGGER.error("Unable to parse xdi8 fonts config", e);
            resources = List.of();
        }

        List<ResourceLocation> add = new ArrayList<>();
        Set<ResourceLocation> remove = new HashSet<>();
        for (Resource resource : Lists.reverse(resources)) {
            try{
                final JsonObject obj = GsonHelper.parse(new BufferedReader(
                        new InputStreamReader(resource.open(), StandardCharsets.UTF_8)));
            JsonArray arr = GsonHelper.getAsJsonArray(obj, "add", new JsonArray());
            for (JsonElement e : arr)
                add.add(ResourceLocationTool.create(GsonHelper.convertToString(e, "added")));
            arr = GsonHelper.getAsJsonArray(obj, "remove", new JsonArray());
            for (JsonElement e : arr)
                remove.add(ResourceLocationTool.create(GsonHelper.convertToString(e, "removed")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        add.removeAll(remove);

        return add.stream()
                .map(r -> ResourceLocationTool.create(r.getNamespace(), "modernui/" + r.getPath()))
                .toList();
    }

    @Mod("xdi8_fonts")  // avoid CoreMod crash
    public static final class Wrapper {}
}
