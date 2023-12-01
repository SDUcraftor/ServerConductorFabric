package io.github.sunshinewzy.serverconductor.fabric;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.gui.SimpleGui;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ConductorMenu extends SimpleGui {

	private static final GuiElementBuilder EDGE = new GuiElementBuilder(Items.GRAY_STAINED_GLASS_PANE).setName(Text.of(" "));
	private static final GuiElementBuilder itemSurvival = new GuiElementBuilder(Items.STONE_PICKAXE)
			.setName(Text.literal("生存服 - Survival").formatted(Formatting.GREEN))
			.setLore(List.of(Text.empty(), Text.of("1.20.1 普通生存服"), Text.of("（首次进服默认进入生存服）")))
			.setCallback((index, clickType, action, gui) -> switchServer(gui.getPlayer(), "s"));
	private static final GuiElementBuilder itemMirror = new GuiElementBuilder(Items.GLASS)
			.setName(Text.literal("镜像服 - Mirror").formatted(Formatting.AQUA))
			.setLore(List.of(Text.empty(), Text.of("1.20.1 生存服的镜像"), Text.of("创造模式！想干什么就干什么")))
			.setCallback((index, clickType, action, gui) -> switchServer(gui.getPlayer(), "m"));
	private static final GuiElementBuilder itemCreative = new GuiElementBuilder(Items.CRAFTING_TABLE)
			.setName(Text.literal("创造服 - Creative").formatted(Formatting.YELLOW))
			.setLore(List.of(Text.empty(), Text.of("1.20.1 超平坦"), Text.of("创造模式！建筑基地与红石实验室！")))
			.setCallback((index, clickType, action, gui) -> switchServer(gui.getPlayer(), "c"));
	private static final GuiElementBuilder itemIsland = new GuiElementBuilder(Items.OAK_SAPLING)
			.setName(Text.literal("空岛服 - Island").formatted(Formatting.GREEN))
			.setLore(List.of(Text.empty(), Text.of("1.17.1 空岛"), Text.of("有丰富的成就系统，推荐给萌新～")))
			.setCallback((index, clickType, action, gui) -> switchServer(gui.getPlayer(), "i"));
	private static final GuiElementBuilder itemArchitecture = new GuiElementBuilder(Items.STONE_BRICKS)
			.setName(Text.literal("建筑服 - Architecture").formatted(Formatting.GOLD))
			.setLore(List.of(Text.empty(), Text.of("1.20.1 地皮世界"), Text.of("创造！可以领取一块自己的领地！")))
			.setCallback((index, clickType, action, gui) -> switchServer(gui.getPlayer(), "ar"));
	private static final GuiElementBuilder itemForbiddenCity = new GuiElementBuilder(Items.BRICKS)
			.setName(Text.literal("故宫服 - Forbidden City").formatted(Formatting.LIGHT_PURPLE))
			.setLore(List.of(Text.empty(), Text.of("1.20.1 故宫"), Text.of("重檐翠瓦缀天绫，砌玉楼栏嵌彩龙！")))
			.setCallback((index, clickType, action, gui) -> switchServer(gui.getPlayer(), "fc"));
	private static final GuiElementBuilder itemTest = new GuiElementBuilder(Items.STICK)
			.setName(Text.literal("测试服 - Test").formatted(Formatting.RED))
			.setLore(List.of(Text.empty(), Text.of("1.20.1 进行功能测试"), Text.of("用来整活的")))
			.setCallback((index, clickType, action, gui) -> switchServer(gui.getPlayer(), "t"));
	private static final GuiElementBuilder itemGame = new GuiElementBuilder(Items.SLIME_BALL)
			.setName(Text.literal("小游戏服 - Game").formatted(Formatting.GREEN))
			.setLore(List.of(Text.empty(), Text.of("1.20.1 小游戏"), Text.of("含有bingo和skywar等小游戏")))
			.setCallback((index, clickType, action, gui) -> new ConductorGameMenu(gui.getPlayer()).open());
	
	
	/**
	 * Constructs a conductor menu for the supplied player.
	 *
	 * @param player                the player to server this gui to
	 */
	public ConductorMenu(ServerPlayerEntity player) {
		super(ScreenHandlerType.GENERIC_9X5, player, false);
		setTitle(Text.of("选择子服"));
		
		for (int i = 0; i <= 8; i++) 
			setSlot(i, EDGE);
		for (int i = 36; i <= 44; i++)
			setSlot(i, EDGE);
		int[] otherEdges = new int[]{9, 17, 18, 26, 27, 35};
		for (int i : otherEdges)
			setSlot(i, EDGE);
		
		setSlot(11, itemSurvival);
		setSlot(13, itemMirror);
		setSlot(15, itemCreative);
		setSlot(20, itemIsland);
		setSlot(22, itemArchitecture);
		setSlot(24, itemForbiddenCity);
		setSlot(30, itemTest);
		setSlot(32, itemGame);
	}
	
	@SuppressWarnings("UnstableApiUsage")
	public static void switchServer(@NotNull ServerPlayerEntity player, @NotNull String server) {
		PacketByteBuf packetBuf = PacketByteBufs.create();
		ByteArrayDataOutput output = ByteStreams.newDataOutput();
		output.writeUTF(server);
		packetBuf.writeBytes(output.toByteArray());
		ServerPlayNetworking.send(player, new Identifier("server_conductor:switch"), packetBuf);
	}
	
}
