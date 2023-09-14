package net.morgan.ssamod.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;

import net.minecraft.client.resources.language.I18n;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModInfo;

public final class ClientProxy {

	public static void initModNames(Map<String, String> map) {
		List<IModInfo> mods = ImmutableList.copyOf(ModList.get().getMods());
		for (IModInfo mod : mods) {
			String modid = mod.getModId();
			String modMenuKey = "modmenu.nameTranslation.%s".formatted(modid);
			if (I18n.exists(modMenuKey)) {
				map.put(modid, I18n.get(modMenuKey));
				continue;
			}
			String name = mod.getDisplayName();
			if (Strings.isNullOrEmpty(name)) {
				name = StringUtils.capitalize(modid);
			}
			map.put(modid, name);
		}
	}

}
