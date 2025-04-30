package com.tacz.guns.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.StringHelper;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class NBTUtil {

    @Nullable
    public static GameProfile readGameProfile(NbtCompound nbt) {
        String name = null;
        UUID uuid = null;
        if (nbt.contains("Name", NbtCompound.STRING_TYPE)) {
            name = nbt.getString("Name");
        }

        if (nbt.containsUuid("Id")) {
            uuid = nbt.getUuid("Id");
        }

        try {
            if (uuid == null || name == null) {
                return null;
            }
            GameProfile gameProfile = new GameProfile(uuid, name);
            if (nbt.contains("Properties", NbtCompound.COMPOUND_TYPE)) {
                NbtCompound propertiesTag = nbt.getCompound("Properties");

                for (String key : propertiesTag.getKeys()) {
                    NbtList list = propertiesTag.getList(key, NbtCompound.COMPOUND_TYPE);

                    for (int i = 0; i < list.size(); ++i) {
                        NbtCompound propertyTag = list.getCompound(i);
                        String value = propertyTag.getString("Value");
                        if (propertyTag.contains("Signature", NbtCompound.STRING_TYPE)) {
                            gameProfile.getProperties().put(key, new Property(key, value, propertyTag.getString("Signature")));
                        } else {
                            gameProfile.getProperties().put(key, new Property(key, value));
                        }
                    }
                }
            }

            return gameProfile;
        } catch (Throwable throwable) {
            return null;
        }
    }

    public static NbtCompound writeGameProfile(NbtCompound nbt, GameProfile profile) {
        if (!StringHelper.isEmpty(profile.getName())) {
            nbt.putString("Name", profile.getName());
        }

        if (profile.getId() != null) {
            nbt.putUuid("Id", profile.getId());
        }

        if (!profile.getProperties().isEmpty()) {
            NbtCompound propertiesTag = new NbtCompound();

            for (String key : profile.getProperties().keySet()) {
                NbtList list = new NbtList();

                for (Property property : profile.getProperties().get(key)) {
                    NbtCompound propertyTag = new NbtCompound();
                    propertyTag.putString("Value", property.value());
                    if (property.hasSignature()) {
                        propertyTag.putString("Signature", property.signature());
                    }
                    list.add(propertyTag);
                }

                propertiesTag.put(key, list);
            }

            nbt.put("Properties", propertiesTag);
        }

        return nbt;
    }
}
