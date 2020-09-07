package me.sarim.config;

import java.util.Collections;
import java.util.List;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.render.RenderUtils;
import me.sarim.FogCustomizer;

public class ConfigGui extends GuiConfigsBase {
    private static ConfigGuiTab tab = ConfigGuiTab.GENERIC;

    public ConfigGui() {
        super(10, 50, FogCustomizer.MOD_CONF, null, "Fog Customizer");
    }

    @Override
    public void initGui() {
        super.initGui();
        this.clearOptions();

        int x = 10;
        int y = 26;

        for (ConfigGuiTab tab : ConfigGuiTab.values()) {
            x += this.createButton(x, y, -1, tab);
        }
    }

    private int createButton(int x, int y, int width, ConfigGuiTab tab) {
        ButtonGeneric button = new ButtonGeneric(x, y, width, 20, tab.getDisplayName());
        button.setEnabled(ConfigGui.tab != tab);
        this.addButton(button, new ButtonListener(tab, this));

        return button.getWidth() + 2;
    }

    @Override
    protected int getConfigWidth() {
        return super.getConfigWidth();
    }

    @Override
    public List<ConfigOptionWrapper> getConfigs() {
        List<? extends IConfigBase> configs;
        ConfigGuiTab tab = ConfigGui.tab;

        if (tab == ConfigGuiTab.GENERIC) {
            configs = Config.Generic.OPTIONS;
        } else if (tab == ConfigGuiTab.THICK) {
            configs = Config.Thick.OPTIONS;
        }
        // else if (tab == CustomFogConfig.HOTKEYS)
        // {
        // configs = CustomFogConfig .HOTKEY_LIST;
        // }
        else {
            return Collections.emptyList();
        }

        return ConfigOptionWrapper.createFor(configs);
    }

    private static class ButtonListener implements IButtonActionListener {
        private final ConfigGui parent;
        private final ConfigGuiTab tab;

        public ButtonListener(ConfigGuiTab tab, ConfigGui parent) {
            this.tab = tab;
            this.parent = parent;
        }

        @Override
        public void actionPerformedWithButton(ButtonBase button, int mouseButton) {
            ConfigGui.tab = this.tab;

            this.parent.reCreateListWidget(); // apply the new config width
            this.parent.getListWidget().resetScrollbarPosition();
            this.parent.initGui();
        }
    }

    public enum ConfigGuiTab
    {
        GENERIC ("generic"),
        THICK ("thick");
        // HOTKEYS ("hotkeys");

        private final String displayName;

        private ConfigGuiTab(String displayName)
        {
            this.displayName = displayName;
        }

        public String getDisplayName()
        {
            return displayName;
        }
    }

    @Override
    protected void drawScreenBackground(int mouseX, int mouseY)
    {
        // Draw the dark background
        RenderUtils.drawRect(0, 0, this.width, this.height, 0x00000000);
    }
}
