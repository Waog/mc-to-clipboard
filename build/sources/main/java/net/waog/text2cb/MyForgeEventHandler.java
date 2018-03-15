package net.waog.text2cb;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

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
		callHttp(event.getItem().getItem().getDisplayName(), "collect");
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

	@SubscribeEvent
	public void OnFinish(PlayerInteractEvent.RightClickBlock e) {
		logAndPlay(e.getItemStack().getDisplayName() + " used!");
		callHttp(e.getItemStack().getDisplayName(), "useOnBlock");
	}

	@SubscribeEvent
	public void OnFinish(PlayerInteractEvent.RightClickItem e) {
		logAndPlay(e.getItemStack().getDisplayName() + " used!");
		callHttp(e.getItemStack().getDisplayName(), "useItem");
	}

	private void logAndPlay(String message) {
		// writeToChat(message);
		copyToClipBoard(message);
	}

	private void callHttp(String item, String activity) {
		try {
			String url = "http://localhost:3000/play"
					+ "/" + URLEncoder.encode(item, "UTF-8")
					+ "/" + Minecraft.getMinecraft().gameSettings.language
					+ "/" + URLEncoder.encode(activity, "UTF-8");
			
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			//add request header
			con.setRequestProperty("User-Agent", "Mozilla/5.0");

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}