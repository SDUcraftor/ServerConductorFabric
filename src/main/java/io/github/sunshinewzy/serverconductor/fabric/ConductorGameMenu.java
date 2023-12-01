package io.github.sunshinewzy.serverconductor.fabric;

import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.gui.SimpleGui;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class ConductorGameMenu extends SimpleGui {

	private static final GuiElementBuilder EDGE = new GuiElementBuilder(Items.GRAY_STAINED_GLASS_PANE).setName(Text.of(" "));
	private static final GuiElementBuilder itemGameBingo = new GuiElementBuilder(Items.MAP)
			.setName(Text.literal("宾果小游戏 - Bingo").formatted(Formatting.GREEN))
			.setLore(List.of(Text.empty(), Text.of("一种原版生存竞赛小游戏"), Text.of("玩家需要通过探索,采掘,搏斗"), Text.of("与合成来收集Bingo卡片上的物品")))
			.setCallback((index, clickType, action, gui) -> ConductorMenu.switchServer(gui.getPlayer(), "bingo"));
	private static final GuiElementBuilder itemGameSkywar = new GuiElementBuilder(Items.ENDER_EYE)
			.setName(Text.literal("空岛战争 - Skywar").formatted(Formatting.YELLOW))
			.setLore(List.of(Text.empty(), Text.of("一种多人PvP小游戏"), Text.of("玩家在各自的空岛上开始游戏"), Text.of("收集资源，并彼此交战"), Text.of("努力活到最后吧！")))
			.setCallback((index, clickType, action, gui) -> ConductorMenu.switchServer(gui.getPlayer(), "skywar"));


	/**
	 * Constructs a conductor game menu for the supplied player.
	 *
	 * @param player                the player to server this gui to
	 */
	public ConductorGameMenu(ServerPlayerEntity player) {
		super(ScreenHandlerType.GENERIC_9X3, player, false);
		setTitle(Text.of("选择小游戏服"));

		for (int i = 0; i <= 9; i++)
			setSlot(i, EDGE);
		for (int i = 17; i <= 26; i++)
			setSlot(i, EDGE);
		
		setSlot(12, itemGameBingo);
		setSlot(14, itemGameSkywar);
	}
	
}
