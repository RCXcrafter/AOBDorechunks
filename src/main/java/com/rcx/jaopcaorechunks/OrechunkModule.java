package com.rcx.jaopcaorechunks;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import thelm.jaopca.api.JAOPCAApi;
import thelm.jaopca.api.config.IDynamicSpecConfig;
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

	public List<String> materialTags = Arrays.asList("forge:ores/");
	public List<String> otherTags = Arrays.asList(/*"forge:ore_rates/sparse"*/);

	@Override
	public String getName() {
		return "ore_chunks";
	}

	@Override
	public List<IFormRequest> getFormRequests() {
		return Collections.singletonList(oreChunkForm.toRequest());
	}

	public void defineModuleConfig(IModuleData moduleData, IDynamicSpecConfig config) {
		config.getDefinedStringList("orechunks.materialtags", materialTags, "A list of tags the ore chunks will be added to, the material name will be added to the end of the tag");
		config.getDefinedStringList("orechunks.othertags", otherTags, "A list of tags the ore chunks will be added to");
	}

	public void defineMaterialConfig(IModuleData moduleData, Map<IMaterial, IDynamicSpecConfig> configs) {
		for (IMaterial material : configs.keySet()) {
			OreSwapper.materialDroprates.put(material, configs.get(material).getDefinedInt("orechunks.droprate", 1, "The amount of ore chunks that drop from this ore"));
		}
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
			for (String tag : materialTags)
				api.registerItemTag(new ResourceLocation(tag + material.getName()), chunkInfo.asItem());
			for (String tag : otherTags)
				api.registerItemTag(new ResourceLocation(tag), chunkInfo.asItem());
		}
	}
}
