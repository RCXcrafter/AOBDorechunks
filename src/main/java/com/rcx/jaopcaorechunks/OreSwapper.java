package com.rcx.jaopcaorechunks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import thelm.jaopca.api.items.IItemInfo;
import thelm.jaopca.api.materials.IMaterial;
import thelm.jaopca.items.ItemFormType;
import thelm.jaopca.utils.MiscHelper;

public class OreSwapper extends LootModifier {

	public static Map<IMaterial, Integer> materialDroprates = new HashMap<IMaterial, Integer>();
	public static Map<IMaterial, int[]> XPDroprates = new HashMap<IMaterial, int[]>();

	protected OreSwapper(ILootCondition[] conditionsIn) {
		super(conditionsIn);
		MinecraftForge.EVENT_BUS.addListener(this::dropXP);
	}

	private void dropXP(BreakEvent event) {
		PlayerEntity player = event.getPlayer();
		BlockState state = event.getState();
		if (player != null && state != null && state.isIn(Tags.Blocks.ORES)) {
			Item blockItem = state.getBlock().asItem();
			MiscHelper miscHelper = MiscHelper.INSTANCE;
			for (IMaterial material : OrechunkModule.oreChunks.getMaterials()) {
				ITag<Item> oreTag = miscHelper.getItemTag(miscHelper.getTagLocation("ores", material.getName()));
				if (blockItem.isIn(oreTag)) {
					int[] XP = XPDroprates.get(material);
					event.setExpToDrop(event.getExpToDrop() + event.getWorld().getRandom().nextInt(XP[1] - XP[0]) + XP[0]);
				}
			}
		}
	}

	@Override
	protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
		BlockState state = context.get(LootParameters.BLOCK_STATE);
		if (state != null && state.isIn(Tags.Blocks.ORES)) {
			Item blockItem = state.getBlock().asItem();
			MiscHelper miscHelper = MiscHelper.INSTANCE;
			for (IMaterial material : OrechunkModule.oreChunks.getMaterials()) {
				IItemInfo chunkInfo = ItemFormType.INSTANCE.getMaterialFormInfo(OrechunkModule.oreChunks, material);
				ITag<Item> oreTag = miscHelper.getItemTag(miscHelper.getTagLocation("ores", material.getName()));
				if (blockItem.isIn(oreTag)) {
					for (ItemStack stack : generatedLoot) {
						if (stack.getItem().isIn(oreTag)) {
							int amount = randomCount(stack.getCount() * materialDroprates.get(material).intValue(), EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, context.get(LootParameters.TOOL)), context.getWorld());
							generatedLoot.remove(stack);
							generatedLoot.add(new ItemStack(chunkInfo.asItem(), amount));
						}
					}
				}
			}
		}
		return generatedLoot;
	}

	public int randomCount(int baseCount, int fortuneLevel, World world) {
		if (fortuneLevel > 0) {
			int j = world.rand.nextInt(fortuneLevel + 4) - 3;

			if (j < 0)
				j = 0;

			return baseCount + j;
		} else {
			return baseCount;
		}
	}

	public static class Serializer extends GlobalLootModifierSerializer<OreSwapper> {
		@Override
		public OreSwapper read(ResourceLocation location, JsonObject object, ILootCondition[] conditions) {
			return new OreSwapper(conditions);
		}

		@Override
		public JsonObject write(OreSwapper instance) {
			return makeConditions(instance.conditions);
		}
	}
}
