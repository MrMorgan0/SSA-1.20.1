package net.morgan.ssamod;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, SSAMod.MOD_ID);

    public static final RegistryObject<SoundEvent> ROOSTER_MORNING = registerSoundEvent("rooster_morning");
    public static final RegistryObject<SoundEvent> WOLF_EVENING = registerSoundEvent("wolf_evening");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name)  {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(SSAMod.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }

}
