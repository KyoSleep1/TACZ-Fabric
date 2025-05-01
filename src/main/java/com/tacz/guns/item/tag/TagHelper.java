package com.tacz.guns.item.tag;

import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Util;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TagHelper {
    public static <V> Optional<RegistryEntry.Reference<V>> getReverseTag(Registry<V> registry, @NotNull V value) {
        return registry.getEntry(registry.getKey(value).get());
    }

    public static <V> Optional<V> getRandomElement(Registry<V> registry, TagKey<V> tag, Random random) {
        return Util.getRandomOrEmpty(getContents(registry, tag), random);
    }

    public static <V> List<V> getContents(Registry<V> registry, TagKey<V> tag) {
        return registry.getEntryList(tag).map(holders -> holders.stream().map(RegistryEntry::value)
                        .toList())
                .orElse(Collections.emptyList());
    }
}