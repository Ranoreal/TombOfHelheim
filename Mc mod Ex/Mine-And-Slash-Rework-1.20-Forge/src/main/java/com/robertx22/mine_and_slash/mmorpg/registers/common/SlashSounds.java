package com.robertx22.mine_and_slash.mmorpg.registers.common;

import com.robertx22.library_of_exile.deferred.RegObj;
import com.robertx22.mine_and_slash.mmorpg.registers.deferred_wrapper.Def;
import net.minecraft.sounds.SoundEvent;

public class SlashSounds {

    public static void init() {

    }

    public static RegObj<SoundEvent> FREEZE = Def.sound("freeze");
    public static RegObj<SoundEvent> DASH = Def.sound("dash");
    public static RegObj<SoundEvent> SPLASH = Def.sound("splash");
    public static RegObj<SoundEvent> STONE_CRACK = Def.sound("stone_crack");
    public static RegObj<SoundEvent> FIREBALL = Def.sound("fireball");
    public static RegObj<SoundEvent> BUFF = Def.sound("buff");
    public static RegObj<SoundEvent> FIRE = Def.sound("fire");
    public static RegObj<SoundEvent> BURN = Def.sound("burn");

}
