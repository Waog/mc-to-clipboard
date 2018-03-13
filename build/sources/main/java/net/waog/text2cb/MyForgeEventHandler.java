package net.waog.text2cb;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MyForgeEventHandler {
	
//	private String lastEquippedItem;
//	
//	@SubscribeEvent
//	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
//		
//		if(event.phase == TickEvent.Phase.START 
//				&& event.side == Side.SERVER
//				) {
//			
////			System.out.println("+++ event.player.getHeldEquipment() " + event.player.getHeldEquipment());
//			ItemStack equipedItem = event.player.getHeldEquipment().iterator().next();
//			System.out.println("+++ ItemStack: " + equipedItem);
//			System.out.println("+++ equipedItem.getDisplayName(): " + equipedItem.getDisplayName());
//			System.out.println("+++ equipedItem.getUnlocalizedName(): " + equipedItem.getUnlocalizedName());
//			System.out.println("+++ equipedItem.getItem(): " + equipedItem.getItem());
//			System.out.println("+++ equipedItem.getItem(): " + equipedItem.);
//			
////			while(event.player.getHeldEquipment().iterator().hasNext()) {
////				String equipped = event.player.getHeldEquipment().iterator().next().getDisplayName();
////				if (!lastEquippedItem.equals(equipped)) {
////					lastEquippedItem = equipped;
////					System.out.println("---- equipped: " + lastEquippedItem + " ----");
////				}
////			}
//		}
//	}
	
	private String lastMessage = null;
	
    @SubscribeEvent
    public void pickupItem(EntityItemPickupEvent event) {
    	
    	String message = event.getItem().getItem().getDisplayName() + " collected!";
    	if (!message.equals(this.lastMessage)) {
    		this.lastMessage = message;
    		Minecraft.getMinecraft().player.sendChatMessage(message);
    		copyToClipBoard("c2tts{" + message + "}");
    	}
    }

	private void copyToClipBoard(String text) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		StringSelection strSel = new StringSelection(text);
		clipboard.setContents(strSel, null);
	}
}