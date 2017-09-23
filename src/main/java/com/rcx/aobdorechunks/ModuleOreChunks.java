package com.rcx.aobdorechunks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Lists;

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
import thelm.jaopca.api.utils.Utils;

public class ModuleOreChunks extends ModuleBase {

	public static final ItemEntry ORECHUNK_ENTRY = new ItemEntry(EnumEntryType.ITEM, "oreChunk", new ModelResourceLocation("jaopcaoc:orechunk_stone#inventory"));


	//public static Map<Ore, OreChunksConfigs> chunkMap = new HashMap<Ore, OreChunksConfigs>();
	public static HashMap<String, OreInfos> dropMap = new HashMap<String, OreInfos>();
	List<FirstOreInfos> oreInformation = new ArrayList<FirstOreInfos>();
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
			//save it for later
			String stoneType;
			if (entry.getOreName().equals("Cobalt") || entry.getOreName().equals("Ardite")){
				stoneType = config.get(Utils.to_under_score(entry.getOreName()), "stoneType", "nether").setRequiresMcRestart(true).getString();
			} else{
				stoneType = config.get(Utils.to_under_score(entry.getOreName()), "stoneType", "stone").setRequiresMcRestart(true).getString();
			}
			oreInformation.add(new FirstOreInfos(entry, stoneType,
			config.get(Utils.to_under_score(entry.getOreName()), "dropCount", 1).setRequiresMcRestart(true).getInt(),
			config.get(Utils.to_under_score(entry.getOreName()), "minXPDrop", 1).setRequiresMcRestart(true).getInt(),
			config.get(Utils.to_under_score(entry.getOreName()), "maxXPDrop", 4).setRequiresMcRestart(true).getInt()));
		}
		processingUnit = config.get("ore_chunks", "processingUnit", "ore").setRequiresMcRestart(true).getString();
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

			//AOBDOC.proxy.registerModel(oreStack.getItem(), info.stoneType);

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
		IOreEntry entry;
		String stoneType;
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