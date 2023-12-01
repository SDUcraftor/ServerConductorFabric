package io.github.sunshinewzy.serverconductor.fabric;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ItemUtils {

	@NotNull
	public static NbtCompound getOrCreateDisplay(@NotNull ItemStack item) {
		return item.getOrCreateSubNbt(ItemStack.DISPLAY_KEY);
	}

	public static void setItemName(@NotNull ItemStack item, @NotNull Text name) {
		if (name.getString().isEmpty()) getOrCreateDisplay(item).remove(ItemStack.NAME_KEY);
		else getOrCreateDisplay(item).putString(ItemStack.NAME_KEY, Text.Serializer.toJson(name));
	}

	public static void setItemLore(@NotNull ItemStack item, @Nullable Text[] lore) {
		if (lore == null || lore.length == 0) {
			getOrCreateDisplay(item).remove(ItemStack.LORE_KEY);
			return;
		}

		NbtList list = new NbtList();
		for (Text text : lore) {
			list.add(NbtString.of(Text.Serializer.toJson(text)));
		}
		getOrCreateDisplay(item).put(ItemStack.LORE_KEY, list);
	}
	
}
