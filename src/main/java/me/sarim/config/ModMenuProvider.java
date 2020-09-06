package me.sarim.config;

import java.util.function.Function;

import io.github.prospector.modmenu.api.ModMenuApi;
import net.minecraft.client.gui.screen.Screen;

public class ModMenuProvider implements ModMenuApi {
    @Override
    public Function<Screen, ? extends Screen> getConfigScreenFactory()
    {
        return (screen) -> {
            ConfigGui gui = new ConfigGui();
            gui.setParent(screen);
            return gui;
        };
    }
}
