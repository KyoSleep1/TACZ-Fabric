package com.tacz.guns;

import com.tacz.guns.item.tag.Tags;
import com.tacz.guns.item.tag.data.BlockTagProvider;
import com.tacz.guns.item.tag.data.ItemTagLangProvider;
import com.tacz.guns.item.tag.data.ItemTagProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class GunModDataGen implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        BlockTagProvider blocks = pack.addProvider(BlockTagProvider::new);
        pack.addProvider((output, registries) -> new ItemTagProvider(output, registries, blocks));
        pack.addProvider(GunModDataGen::itemTagLang);
    }

    private static ItemTagLangProvider itemTagLang(FabricDataOutput output,
                                                   CompletableFuture<RegistryWrapper.WrapperLookup> future) {
        return new ItemTagLangProvider(Tags.Items.class, output, future);
    }
}