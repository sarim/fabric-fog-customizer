package me.sarim;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.interfaces.IInitializationHandler;
import me.sarim.config.Config;

public class InitHandler implements IInitializationHandler
{
    @Override
    public void registerModHandlers()
    {
        ConfigManager.getInstance().registerConfigHandler(FogCustomizer.MOD_ID, new Config());

        // InputHandler handler = new InputHandler();
        // InputEventHandler.getKeybindManager().registerKeybindProvider(handler);
        // InputEventHandler.getInputManager().registerKeyboardInputHandler(handler);
        // InputEventHandler.getInputManager().registerMouseInputHandler(handler);

        // WorldLoadListener listener = new WorldLoadListener();
        // WorldLoadHandler.getInstance().registerWorldLoadPreHandler(listener);
        // WorldLoadHandler.getInstance().registerWorldLoadPostHandler(listener);

        // TickHandler.getInstance().registerClientTickHandler(KeybindCallbacks.getInstance());

        // KeybindCallbacks.getInstance().setCallbacks();
    }
}