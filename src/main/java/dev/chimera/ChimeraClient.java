package dev.chimera;

import dev.chimera.amalthea.EventListenerIDs;
import dev.chimera.amalthea.eventbus.EventBus;
import dev.chimera.amalthea.events.EventSystemTest;
import dev.chimera.amalthea.events.misc.TickEvent;
import dev.chimera.modules.ExampleModule;
import dev.chimera.modules.ModuleInitializer;
import dev.chimera.modules.combat.KillAuraModule;
import dev.chimera.modules.common.ClickGUIModule;
import dev.chimera.modules.common.FarmAuraModule;
import dev.chimera.modules.player.FlightModule;
import dev.chimera.modules.player.NoFallModule;
import dev.chimera.nemean.GuiLayer;
import dev.chimera.nemean.elements.Gui;
import dev.chimera.sisyphus.AddonInitializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.event.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChimeraClient implements ClientModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final String MOD_ID = "chimera-client";
    public static final Logger LOGGER = LoggerFactory.getLogger("chimera-client");
    public static final EventBus EVENT_BUS = new EventBus();

	public static int test = 0;
    @Override
    public void onInitializeClient() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
//        OVERLAY.SCREEN.size = new Size(1920,1080);


        //TODO clean up this class. we probably shouldn't be doing everything right here
        LOGGER.info("Hello Chimera sussers!");

        EventSystemTest test = new EventSystemTest();

        EVENT_BUS.postEvent("Systems operational?");

        EVENT_BUS.postEventToListener("idPush", EventListenerIDs.a);

        //should probably move this somewhere else at some point but for now it can stay here
        ModuleInitializer.addModule(new FlightModule());
        ModuleInitializer.addModule(new ExampleModule());
        ModuleInitializer.addModule(new FlightModule());
        ModuleInitializer.addModule(new NoFallModule());
        ModuleInitializer.addModule(new KillAuraModule());
        ModuleInitializer.addModule(new FarmAuraModule());
        ModuleInitializer.addModule(new ClickGUIModule());

        TickEvent.Start tickEventStart = new TickEvent.Start();
        ClientTickEvents.START_CLIENT_TICK.register((startTick) -> {
            EVENT_BUS.postEvent(tickEventStart);
        });
//        GuiRenderEvent guiRenderEvent = new GuiRenderEvent();
//        HudRenderCallback.EVENT.register((matrix, floatthing) -> {
//            guiRenderEvent.cancelled = false;
//            EVENT_BUS.postEvent(guiRenderEvent);
//        });

        new GuiLayer();
        new Gui();

        TickEvent.End tickEventEnd = new TickEvent.End();
        ClientTickEvents.END_CLIENT_TICK.register((endTick) -> {
            EVENT_BUS.postEvent(tickEventEnd);
        });

        AddonInitializer.initAddons();

        new ModuleInitializer().initializeModules();

    }

    public Event<?> event = ScreenEvents.AFTER_INIT;
}