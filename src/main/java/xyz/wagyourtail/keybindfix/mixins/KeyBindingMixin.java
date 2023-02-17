package xyz.wagyourtail.keybindfix.mixins;

import net.minecraft.client.settings.KeyBinding;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.wagyourtail.keybindfix.KeybindFix;

import java.util.List;

@Mixin(KeyBinding.class)
public class KeyBindingMixin {

    @Shadow @Final private static List keybindArray;

    @Inject(method = "onTick", at = @At("HEAD"), cancellable = true)
    private static void keybindfix_onKeyPressedFixed(int keycode, CallbackInfo ci) {
        KeybindFix.onKeyPressed(keycode);
        ci.cancel();
    }

    @Inject(method = "setKeyBindState", at = @At("HEAD"), cancellable = true)
    private static void keybindfix_setPressedFixed(int keycode, boolean pressed, CallbackInfo ci) {
        KeybindFix.setPressed(keycode, pressed);
        ci.cancel();
    }

    @Inject(method = "resetKeyBindingArrayAndHash", at = @At("TAIL"))
    private static void keybindfix_clearMap(CallbackInfo ci) {
        KeybindFix.clearMap();
        for (KeyBinding keyBinding : (List<KeyBinding>) keybindArray) {
            KeybindFix.putKey(keyBinding.getKeyCode(), keyBinding);
        }
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void keybindfix_putKey(String name, int keyCode, String category, CallbackInfo ci) {
        KeybindFix.putKey(keyCode, (KeyBinding) (Object) this);
    }

}
