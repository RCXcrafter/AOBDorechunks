package com.rcx.jaopcaorechunks;

import java.util.Collections;
import java.util.List;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import thelm.jaopca.api.JAOPCAApi;
import thelm.jaopca.api.forms.IForm;
import thelm.jaopca.api.forms.IFormRequest;
import thelm.jaopca.api.items.IItemInfo;
import thelm.jaopca.api.materials.IMaterial;
import thelm.jaopca.api.materials.MaterialType;
import thelm.jaopca.api.modules.IModule;
import thelm.jaopca.api.modules.IModuleData;
import thelm.jaopca.api.modules.JAOPCAModule;
import thelm.jaopca.items.ItemFormType;
import thelm.jaopca.utils.ApiImpl;
import thelm.jaopca.utils.MiscHelper;

@JAOPCAModule
public class OrechunkModule implements IModule {

	private final IForm oreChunkForm = ApiImpl.INSTANCE.newForm(this, "ore_chunks", ItemFormType.INSTANCE).setMaterialTypes(MaterialType.INGOT);
	public static IForm oreChunks;

	@Override
	public String getName() {
		return "ore_chunks";
	}

	@Override
	public List<IFormRequest> getFormRequests() {
		return Collections.singletonList(oreChunkForm.toRequest());
	}

	@Override
	public void onCommonSetup(IModuleData moduleData, FMLCommonSetupEvent event) {
		oreChunks = oreChunkForm;
		JAOPCAApi api = ApiImpl.INSTANCE;
		for(IMaterial material : oreChunkForm.getMaterials()) {
			IItemInfo chunkInfo = ItemFormType.INSTANCE.getMaterialFormInfo(oreChunkForm, material);
			ResourceLocation materialLocation = MiscHelper.INSTANCE.getTagLocation(material.getType().getFormName(), material.getName());
			api.registerSmeltingRecipe(
					new ResourceLocation("jaopcaorechunks", "ore_chunks.to_material." + material.getName()),
					chunkInfo, materialLocation, 1, 0.7F, 200);
			api.registerBlastingRecipe(
					new ResourceLocation("jaopcaorechunks", "ore_chunks.to_material_blasting." + material.getName()),
					chunkInfo, materialLocation, 1, 0.7F, 100);
			api.registerItemTag(new ResourceLocation("forge", "ores/" + material.getName()), chunkInfo.asItem());
			//api.registerItemTag(new ResourceLocation("forge", "ores"), new ResourceLocation("forge", "ores/" + material.getName()));
			//api.registerItemTag(new ResourceLocation("forge", "ore_rates/sparse"), chunkInfo.asItem());
		}
	}
}
