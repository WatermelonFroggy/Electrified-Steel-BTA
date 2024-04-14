package wf.electrifiedsteel.mixin;

import  net.minecraft.client.Minecraft;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wf.electrifiedsteel.ElectrifiedSteel;

@Mixin(value = WorldRenderer.class, remap = false)
public class GetWeather {
	@Inject(method = "updateCameraAndRender", at = @At("TAIL"))
	public void onRender(float partialTick, CallbackInfo ci) {
		Minecraft mc = Minecraft.getMinecraft(Minecraft.class);
		if (mc.theWorld != null) {
			ElectrifiedSteel.OnTickInGame(mc);
		}
	}
}
