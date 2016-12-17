package com.rcx.aobdorechunks;

import java.util.ArrayList;

import com.rcx.aobdorechunks.OreChunkAddon.OreInfos;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.oredict.OreDictionary;

public class OreSwapper {

	@SubscribeEvent
	public void SwapOres(HarvestDropsEvent event) {
		if (event.isSilkTouching)
			return;
		if (event.drops.isEmpty())
			return;

		OreInfos oreInfos = null;
		String orename = null;
		boolean alreadyDropped = false;
		ArrayList<ItemStack> oldDrops = new ArrayList<ItemStack>();
		ArrayList<ItemStack> modifiedDrops = new ArrayList<ItemStack>();

		for (ItemStack drop : event.drops) {
			int[] oreids = OreDictionary.getOreIDs(drop);
			for (int i = 0; i < oreids.length; i++) {
				orename = OreDictionary.getOreName(oreids[i]);
				int count = drop.stackSize;
				if (!(orename.startsWith("ore") || orename.startsWith("dust")))
					continue;

				if (orename.startsWith("dust")) {
					orename = orename.replace("dust", "ore");
					count = 1;

					if (alreadyDropped)
						continue;
				}
				if (!OreChunkAddon.dropMap.containsKey(orename))
					continue;

				oreInfos = OreChunkAddon.dropMap.get(orename);
				count = randomCount(count * oreInfos.count, event.fortuneLevel, event.world);

				ItemStack chunkStack = new ItemStack(oreInfos.chunkItem);
				for (int c = 0; c < count; ++c) {
					modifiedDrops.add(chunkStack);
					alreadyDropped = true;
				}
				break;
			}
			if (!oreInfos.equals(null))
				oldDrops.add(drop);
		}

		if (oreInfos.equals(null))
			return;

		event.drops.removeAll(oldDrops);
		event.dropChance = 1.0f;
		event.drops.addAll(modifiedDrops);

		if (oreInfos.maxXP <= 0)
			return;

		int countOrbs = event.world.rand.nextInt(oreInfos.maxXP - oreInfos.minXP);
		countOrbs += oreInfos.minXP;

		for (int i = 0; i < countOrbs; ++i) {
			event.world.spawnEntityInWorld(new EntityXPOrb(event.world, event.x + 0.5D, event.y + 0.5D, event.z + 0.5D, 1));
		}
	}

	private int randomCount(int baseCount, int fortuneLevel, World world) {
		if (fortuneLevel > 0) {
			int j = world.rand.nextInt(fortuneLevel + 3) - 3;

			if (j < 0)
				j = 0;

			return baseCount + j;
		} else {
			return baseCount;
		}
	}
}
