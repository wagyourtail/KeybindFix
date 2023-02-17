package xyz.wagyourtail.keybindfix;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import cpw.mods.fml.common.Mod;
import net.minecraft.client.settings.KeyBinding;

@Mod(modid = KeybindFix.MODID, name = "Keybind Fix", version = "1.0.0", acceptedMinecraftVersions = "[1.7.10]")
public class KeybindFix {
    public static final String MODID = "keybindfix";

    private static final Multimap<Integer, KeyBinding> keyFixMap = ArrayListMultimap.create();

    public static void putKey(Integer key, KeyBinding keyBind) {
        keyFixMap.put(key, keyBind);
    }

    public static void clearMap() {
        keyFixMap.clear();
    }

    public static void onKeyPressed(Integer key) {
        keyFixMap.get(key).forEach(it -> it.pressTime++);
    }

    public static void setPressed(Integer key, boolean pressed) {
        keyFixMap.get(key).forEach(it -> it.pressed = pressed);
    }

}
