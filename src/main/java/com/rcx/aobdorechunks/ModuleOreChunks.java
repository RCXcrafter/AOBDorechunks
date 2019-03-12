package com.rcx.aobdorechunks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Lists;
import com.rcx.aobdorechunks.item.ItemOrechunk;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import thelm.jaopca.api.EnumEntryType;
import thelm.jaopca.api.IOreEntry;
import thelm.jaopca.api.ItemEntry;
import thelm.jaopca.api.JAOPCAApi;
import thelm.jaopca.api.ModuleBase;
import thelm.jaopca.api.item.ItemProperties;
import thelm.jaopca.api.utils.Utils;

public class ModuleOreChunks extends ModuleBase {

	public static final ItemProperties ORECHUNK_PROPERTIES = new ItemProperties().setItemClass(ItemOrechunk.class);
	public static final ItemEntry ORECHUNK_ENTRY = new ItemEntry(EnumEntryType.ITEM, "oreChunk", new ModelResourceLocation("jaopcaoc:orechunk_stone#inventory")).setProperties(ORECHUNK_PROPERTIES);

	public static HashMap<String, OreInfos> dropMap = new HashMap<String, OreInfos>();
	public static List<FirstOreInfos> oreInformation = new ArrayList<FirstOreInfos>();
	String processingUnit;

	@Override
	public String getName() {
		return "orechunks";
	}

	@Override
	public List<ItemEntry> getItemRequests() {
		return Lists.<ItemEntry>newArrayList(ORECHUNK_ENTRY);
	}

	@Override
	public void registerConfigs(Configuration config) {
		for(IOreEntry entry : JAOPCAApi.ENTRY_NAME_TO_ORES_MAP.get("oreChunk")) {
			String stoneType;
			String stoneComment = "The stone type for this ore, only has a visual effect";
			if (entry.getOreName().equals("Eximite") || entry.getOreName().equals("Meutoite")) {
				stoneType = config.getString("stoneType", Utils.to_under_score(entry.getOreName()), "end", stoneComment);
			} else if (entry.getOreName().equals("Cobalt") || entry.getOreName().equals("Ardite") || entry.getOreName().equals("Ignatius") || entry.getOreName().equals("ShadowIron") || entry.getOreName().equals("Lemurite") || entry.getOreName().equals("Midasium") || entry.getOreName().equals("Vyroxeres") || entry.getOreName().equals("Ceruclase") || entry.getOreName().equals("Alduorite") || entry.getOreName().equals("Kalendrite") || entry.getOreName().equals("Vulcanite") || entry.getOreName().equals("Sanguinite")) {
				stoneType = config.getString("stoneType", Utils.to_under_score(entry.getOreName()), "nether", stoneComment);
			} else {
				stoneType = config.getString("stoneType", Utils.to_under_score(entry.getOreName()), "stone", stoneComment);
			}
			oreInformation.add(new FirstOreInfos(entry, stoneType,
			config.getInt("dropCount", Utils.to_under_score(entry.getOreName()), 1, 1, 100, "The amount of ore chunks dropped by this ore"),
			config.getInt("minXPDrop", Utils.to_under_score(entry.getOreName()), 1, 0, 1000, "The minimum amount of XP dropped by this ore"),
			config.getInt("maxXPDrop", Utils.to_under_score(entry.getOreName()), 4, 0, 1000, "The maximum amount of XP dropped by this ore")));
		}
		processingUnit = config.getString("processingUnit", "ore_chunks", "ore", "The ore dictionary prefix for ore chunks, set this to \"dust\" to prevent ore multiplication of ore chunks");
	}

	@Override
	public void init() {
		for(FirstOreInfos info : oreInformation) {
			ItemStack oreStack = Utils.getOreStack("oreChunk", info.entry, 1);
			
			//recipe stuff
			OreDictionary.registerOre(processingUnit + info.entry.getOreName(), oreStack);
			GameRegistry.addSmelting(oreStack, Utils.getOreStack("ingot", info.entry, 1), 1F);

			//make it drop
			OreInfos infos = new OreInfos(oreStack.getItem(), info.count, info.minXP, info.maxXP);
			dropMap.put("ore" + info.entry.getOreName(), infos);

			// aluminum/aluminium nonsense
			if (info.entry.getOreName().equals("Aluminum")) {
				OreDictionary.registerOre(processingUnit + "Aluminium", oreStack);
				OreDictionary.registerOre("oreChunk" + "Aluminium", oreStack);
				dropMap.put("oreAluminium", infos);
			}
			if (info.entry.getOreName().equals("Aluminium")) {
				OreDictionary.registerOre(processingUnit + "Aluminum", oreStack);
				OreDictionary.registerOre("oreChunk" + "Aluminum", oreStack);
				dropMap.put("oreAluminum", infos);
			}
		}
	}

	public class FirstOreInfos {
		public IOreEntry entry;
		public String stoneType;
		public int count;
		public int minXP;
		public int maxXP;

		public FirstOreInfos(IOreEntry oreEntry, String stone, int baseCount, int dropXPMin, int dropXPMax) {
			entry = oreEntry;
			stoneType = stone;
			count = baseCount;
			minXP = dropXPMin;
			maxXP = dropXPMax;
		}
	}

	public class OreInfos {
		public Item chunkItem;
		public int count;
		public int minXP;
		public int maxXP;

		public OreInfos(Item oreChunkItem, int baseCount, int dropXPMin, int dropXPMax) {
			chunkItem = oreChunkItem;
			count = baseCount;
			minXP = dropXPMin;
			maxXP = dropXPMax;
		}
	}
}