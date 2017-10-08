package com.rcx.aobdorechunks;

import java.util.ArrayList;

import com.rcx.aobdorechunks.ModuleOreChunks.OreInfos;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

public class OreSwapper {

	@SubscribeEvent
	public void SwapOres(HarvestDropsEvent event) {
		if (event.isSilkTouching())
			return;
		if (event.getDrops().isEmpty())
			return;

		OreInfos oreInfos = null;
		String orename = null;
		ArrayList<ItemStack> oldDrops = new ArrayList<ItemStack>();
		ArrayList<ItemStack> modifiedDrops = new ArrayList<ItemStack>();
		int dropCount = 0;
		boolean stopDropping = false;

		for (ItemStack drop : event.getDrops()) {
			if (drop.equals(null))
				continue;
			int[] oreids = OreDictionary.getOreIDs(drop);
			for (int i = 0; i < oreids.length; i++) {
				orename = OreDictionary.getOreName(oreids[i]);
				int count = drop.stackSize;
				boolean alreadyMultiplied = false;

				if (!(orename.startsWith("ore") || orename.startsWith("dust")))
					continue;

				if (orename.startsWith("dust")) {
					orename = orename.replace("dust", "ore");

					if (stopDropping)
						continue;

					alreadyMultiplied = true;
				}
				if (!ModuleOreChunks.dropMap.containsKey(orename))
					continue;

				if (orename.equals("oreDraconium")){
					count = 1;
					alreadyMultiplied = false;
					stopDropping = true;
				}

				oreInfos = ModuleOreChunks.dropMap.get(orename);
				if (!alreadyMultiplied)
					count = randomCount(count * oreInfos.count, event.getFortuneLevel(), event.getWorld());

				ItemStack chunkStack = new ItemStack(oreInfos.chunkItem);
				for (int c = 0; c < count; ++c) {
					modifiedDrops.add(chunkStack);
					dropCount += 1;
				}
				break;
			}
			if (!(oreInfos == null))
				oldDrops.add(drop);
		}

		if (oreInfos == null)
			return;

		event.getDrops().removeAll(oldDrops);
		event.setDropChance(1.0f);
		event.getDrops().addAll(modifiedDrops);

		if (oreInfos.maxXP <= 0 || dropCount <= 0)
			return;

		int countOrbs = event.getWorld().rand.nextInt(oreInfos.maxXP - oreInfos.minXP);
		countOrbs += oreInfos.minXP;

		for (int i = 0; i < countOrbs * dropCount; ++i) {
			event.getWorld().spawnEntityInWorld(new EntityXPOrb(event.getWorld(), event.getPos().getX() + 0.5D, event.getPos().getY() + 0.5D, event.getPos().getZ() + 0.5D, 1));
		}
	}

	int randomCount(int baseCount, int fortuneLevel, World world) {
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
