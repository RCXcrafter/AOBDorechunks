package com.rcx.jaopcaorechunks;

import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(JAOPCAOreChunks.MODID)
public class JAOPCAOreChunks {

	public static final String MODID = "jaopcaorechunks";
	private static final DeferredRegister<GlobalLootModifierSerializer<?>> LootModifiers = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, MODID);

	public static final RegistryObject<OreSwapper.Serializer> ORE_CHUNK_REPLACER = LootModifiers.register("ore_chunk_replacer", OreSwapper.Serializer::new);

	public JAOPCAOreChunks() {
		LootModifiers.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}
