package net.waog.text2cb;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MyForgeEventHandler {

	private String lastMessage = null;

	@SubscribeEvent
	public void pickupItem(EntityItemPickupEvent event) {

		String message = event.getItem().getItem().getDisplayName() + " collected!";
		if (!message.equals(this.lastMessage)) {
			this.lastMessage = message;
			// writeToChat(message);
		}
		copyToClipBoard(message);
	}

	private void copyToClipBoard(String text) {
		Runnable myRunnable = new Runnable() {
			public void run() {
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				Clipboard clipboard = toolkit.getSystemClipboard();
				StringSelection strSel = new StringSelection("c2tts{" + text + "} ");
				clipboard.setContents(strSel, null);
			}
		};

		Thread thread = new Thread(myRunnable);
		thread.start();
	}

	private void writeToChat(String text) {
		Runnable myRunnable = new Runnable() {
			public void run() {
				Minecraft.getMinecraft().player.sendChatMessage(text);
			}
		};

		Thread thread = new Thread(myRunnable);
		thread.start();
	}

	@SubscribeEvent
	public void OnFinish(PlayerInteractEvent.RightClickBlock e) {
		logAndPlay(e.getItemStack().getDisplayName() + " used!");
	}

	@SubscribeEvent
	public void OnFinish(PlayerInteractEvent.RightClickItem e) {
		logAndPlay(e.getItemStack().getDisplayName() + " used!");
	}

	private void logAndPlay(String message) {
		// writeToChat(message);
		copyToClipBoard(message);
	}

}