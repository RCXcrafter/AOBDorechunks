package com.rcx.aobdorechunks.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ganymedes01.aobd.lib.Reference;
import ganymedes01.aobd.ore.Ore;
import com.rcx.aobdorechunks.AOBDOC;
import com.rcx.aobdorechunks.OreChunkAddon;

import java.awt.Color;
import java.io.File;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {

	public static ConfigHandler INSTANCE = new ConfigHandler();
	public Configuration configFile;
	public Set<String> usedCategories = new HashSet<String>();

	public void preInit(File file) {
		configFile = new Configuration(file, true);
	}

	public void init() {
		usedCategories.clear();
		usedCategories.add("AOBDOC");
		AOBDOC.processingUnit = configFile.get("The oredict prefix of the ore chunks", Configuration.CATEGORY_GENERAL, "ore").setRequiresMcRestart(true).setRequiresWorldRestart(true).getString();
		for (Entry<Ore, OreChunksConfigs> entry : OreChunkAddon.chunkMap.entrySet())
			init(entry.getKey().name(), entry.getValue());
	}

	public OreChunksConfigs init(Ore ore) {
		OreChunksConfigs config = new OreChunksConfigs(ore.name().toLowerCase());
		init(ore.name(), config);

		return config;
	}

	public void init(String name, OreChunksConfigs config) {
		config.setEnabled(getBoolean(name, "Enabled", true, true));
		config.setDropCount(getInt(name, "Amount of chunks to drop", config.getDropCount()));
		config.setMinXPDrop(getInt(name, "Minimum XP drop", config.getMinXPDrop()));
		config.setMaxXPDrop(getInt(name, "Maximum XP drop", config.getMaxXPDrop()));
		config.setStoneType(getString(name, "Stone type", config.getStoneType()));
		usedCategories.add(name);

		if (configFile.hasChanged())
			configFile.save();
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if (MOD_ID.equals(eventArgs.getModID())) {
			configFile.load();
			init();
		}
	}

	private boolean getBoolean(String category, String name, boolean requiresRestart, boolean def) {
		return configFile.get(category, name, def).setRequiresMcRestart(requiresRestart).getBoolean(def);
	}

	private int getInt(String category, String name, int def) {
		return configFile.get(category, name, def).getInt(def);
	}

	private String getString(String category, String name, String def) {
		return configFile.get(category, name, def).getString();
	}
}