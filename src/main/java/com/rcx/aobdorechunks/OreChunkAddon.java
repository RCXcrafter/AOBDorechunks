package com.rcx.aobdorechunks;

import ganymedes01.aobd.api.IAOBDAddon;
import ganymedes01.aobd.ore.Ore;
import ganymedes01.aobd.ore.OreFinder;
import ganymedes01.aobd.recipes.RecipesModule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.rcx.aobdorechunks.config.ConfigHandler;
import com.rcx.aobdorechunks.config.OreChunksConfigs;
import com.rcx.aobdorechunks.item.AOBDItemOrechunk;
import com.rcx.aobdorechunks.lib.Reference;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class OreChunkAddon implements IAOBDAddon {

	public static Map<Ore, OreChunksConfigs> chunkMap = new HashMap<Ore, OreChunksConfigs>();
	public static HashMap<String, OreInfos> dropMap = new HashMap<String, OreInfos>();

	@Override
	public void receiveOreList(Collection<Ore> ores) {

		for (Ore ore : ores) {
			if (!ore.isEnabled())
				continue;

			if (Loader.isModLoaded("FortuneOres"))
				if ("Iron".equals(ore.name()) ||
					"Gold".equals(ore.name()) ||
					"Copper".equals(ore.name()) ||
					"Tin".equals(ore.name()) ||
					"Lead".equals(ore.name()) ||
					"Silver".equals(ore.name()) ||
					"Nickel".equals(ore.name()) ||
					"Platinum".equals(ore.name()) ||
					"Aluminum".equals(ore.name()) ||
					"Aluminium".equals(ore.name()) ||
					"Cobalt".equals(ore.name()) ||
					"Ardite".equals(ore.name()) ||
					"Manganese".equals(ore.name()) ||
					"Zinc".equals(ore.name()) ||
					"Ignatius".equals(ore.name()) ||
					"ShadowIron".equals(ore.name()) ||
					"Lemurite".equals(ore.name()) ||
					"Midasium".equals(ore.name()) ||
					"Vyroxeres".equals(ore.name()) ||
					"Ceruclase".equals(ore.name()) ||
					"Alduorite".equals(ore.name()) ||
					"Kalendrite".equals(ore.name()) ||
					"Vulcanite".equals(ore.name()) ||
					"Sanguinite".equals(ore.name()) ||
					"Prometheum".equals(ore.name()) ||
					"DeepIron".equals(ore.name()) ||
					"Infuscolium".equals(ore.name()) ||
					"Oureclase".equals(ore.name()) ||
					"AstralSilver".equals(ore.name()) ||
					"Carmot".equals(ore.name()) ||
					"Mithril".equals(ore.name()) ||
					"Mythril".equals(ore.name()) ||
					"Rubracium".equals(ore.name()) ||
					"Orichalcum".equals(ore.name()) ||
					"Adamantine".equals(ore.name()) ||
					"Atlarus".equals(ore.name()) ||
					"Eximite".equals(ore.name()) ||
					"Meutoite".equals(ore.name()) ||
					"DarkIron".equals(ore.name()))
						continue;

			OreChunksConfigs config = ConfigHandler.INSTANCE.init(ore);
			chunkMap.put(ore, config);
			if (!config.isEnabled())
				continue;

			// Create the ore chunk item
			String base = "orechunk";
			AOBDItemOrechunk orechunk = new AOBDItemOrechunk(base, ore, config.getStoneType());
			orechunk.setTextureName(Reference.MOD_ID + ":" + base);
			orechunk.setUnlocalizedName(Reference.MOD_ID + "." + base + ore);
			OreFinder.registerOre(base + ore.name(), orechunk);
			OreDictionary.registerOre(AOBDOC.processingUnit + ore.name(), orechunk);

			// Add smelting recipe
			ItemStack ingot;
			ingot = RecipesModule.getOreStack("ingot", ore);
			GameRegistry.addSmelting(new ItemStack(orechunk), ingot, 1F);

			// make it drop
			OreInfos infos = new OreInfos(orechunk, config.getDropCount(), config.getMinXPDrop(), config.getMaxXPDrop());
			dropMap.put("ore" + ore.name(), infos);
		}
	}

	public void notifyColourCreation() {}

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