package com.morgan.ssamod.registery;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import com.morgan.ssamod.SunriseSunsetAudio;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRegistry {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS_REGISTRY =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, SunriseSunsetAudio.MODID);

    public static final Supplier<SoundEvent> ROOSTER_MORNING = registerSoundEvent("rooster_morning");
    public static final Supplier<SoundEvent> WOLF_EVENING = registerSoundEvent("wolf_evening");


    private static Supplier<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS_REGISTRY.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(SunriseSunsetAudio.MODID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS_REGISTRY.register(eventBus);
    }


}
