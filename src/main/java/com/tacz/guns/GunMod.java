package com.tacz.guns;

import com.tacz.guns.api.resource.ResourceManager;
import com.tacz.guns.config.ClientConfig;
import com.tacz.guns.config.CommonConfig;
import com.tacz.guns.config.ServerConfig;
import com.tacz.guns.init.*;
import com.tacz.guns.item.tag.Tags;
import com.tacz.guns.resource.DedicatedServerReloadManager;
import com.tacz.guns.util.EnvironmentUtil;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GunMod implements ModInitializer {
	public static final String OLD_MOD_ID = "tacz-fabric";
	public static final String MOD_ID = "tacz";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID.toUpperCase());
	/**
	 * 默认模型包文件夹
	 */
	public static final String DEFAULT_GUN_PACK_NAME = "tacz_default_gun";

	@Override
	public void onInitialize() {
		Tags.Blocks.init();
		Tags.Items.init();

		NeoForgeConfigRegistry.INSTANCE.register(MOD_ID,
				ModConfig.Type.COMMON, CommonConfig.init());
		NeoForgeConfigRegistry.INSTANCE.register(MOD_ID,
				ModConfig.Type.SERVER, ServerConfig.init());
		NeoForgeConfigRegistry.INSTANCE.register(MOD_ID,
				ModConfig.Type.CLIENT, ClientConfig.init());

		ModEvents.init();
		ModBlocks.init();
		ModCreativeTabs.init();
		ModItemComponents.init();
		ModItems.init();
		ModEntities.init();
		ModRecipe.init();
		ModContainer.init();
		ModSounds.init();
		ModParticles.init();
		ModDamageTypes.init();
		CommonRegistry.init();
		CommandRegistry.init();
		CompatRegistry.init();

		registerDefaultExtraGunPack();

		if (EnvironmentUtil.isServer()) {
			DedicatedServerReloadManager.loadGunPack();
		}
	}

	private static void registerDefaultExtraGunPack() {
		String jarDefaultPackPath = String.format("/assets/%s/custom/%s", GunMod.MOD_ID, DEFAULT_GUN_PACK_NAME);
		ResourceManager.registerExtraGunPack(GunMod.class, jarDefaultPackPath);
	}
}