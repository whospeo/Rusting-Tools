package whospeo.github.io;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import whospeo.github.io.component.ModComponents;
import whospeo.github.io.rust.RustTickHandler;

public class RustingTools implements ModInitializer {
	public static final String MOD_ID = "rusting-tools";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Loading Rusting Tool Mod");
		ServerTickEvents.END_SERVER_TICK.register(RustTickHandler::tickServer);
		ModComponents.init();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
