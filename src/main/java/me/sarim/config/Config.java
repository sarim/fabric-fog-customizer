package me.sarim.config;

import java.io.File;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.config.IConfigOptionListEntry;
import fi.dy.masa.malilib.config.options.ConfigOptionList;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.JsonUtils;
import me.sarim.FogCustomizer;
import me.sarim.config.malilib.ConfigFloat;

public class Config implements IConfigHandler {

	public static class Generic
    {
        public static final ConfigFloat linearFogMultiplier = new ConfigFloat("linearFogMultiplier", 0.75F, 0, 1, true, "");
        public static final ConfigFloat expFogMultiplier    = new ConfigFloat("expFogMultiplier", 3.00F, 0, 10, true, "");
		public static final ConfigFloat exp2FogMultiplier   = new ConfigFloat("exp2FogMultiplier", 1.75F, 0, 10, true, "");
		public static final ConfigOptionList fogType         = new ConfigOptionList("fogType", FogType.LINEAR, "");

        public static final ImmutableList<IConfigBase> OPTIONS = ImmutableList.of(
			linearFogMultiplier,
			expFogMultiplier,
			exp2FogMultiplier,
			fogType
        );
    }

	public enum FogType implements IConfigOptionListEntry {
		LINEAR           ("LINEAR"),
		EXPONENTIAL      ("EXPONENTIAL"),
		EXPONENTIAL_TWO  ("EXPONENTIAL_TWO");

		private final String configString;
	
		private FogType(String configString)
		{
			this.configString = configString;
		}
	
		@Override
		public String getStringValue()
		{
			return this.configString;
		}
	
		@Override
		public String getDisplayName()
		{
			return this.configString;
		}
	
		@Override
		public IConfigOptionListEntry cycle(boolean forward)
		{
			int id = this.ordinal();
	
			if (forward)
			{
				if (++id >= values().length)
				{
					id = 0;
				}
			}
			else
			{
				if (--id < 0)
				{
					id = values().length - 1;
				}
			}
	
			return values()[id % values().length];
		}
	
		@Override
		public FogType fromString(String name)
		{
			return fromStringStatic(name);
		}
	
		public static FogType fromStringStatic(String name)
		{
			for (FogType mode : FogType.values())
			{
				if (mode.configString.equalsIgnoreCase(name))
				{
					return mode;
				}
			}
	
			return FogType.LINEAR;
		}
	}

	public static void loadFromFile()
    {
        File configFile = new File(FileUtils.getConfigDirectory(), FogCustomizer.MOD_CONF);

        if (configFile.exists() && configFile.isFile() && configFile.canRead())
        {
            JsonElement element = JsonUtils.parseJsonFile(configFile);

            if (element != null && element.isJsonObject())
            {
                JsonObject root = element.getAsJsonObject();

                ConfigUtils.readConfigBase(root, "Generic", Generic.OPTIONS);
                // ConfigUtils.readConfigBase(root, "Hotkeys", Hotkeys.HOTKEY_LIST);
            }
        }
    }

    public static void saveToFile()
    {
        File dir = FileUtils.getConfigDirectory();

        if ((dir.exists() && dir.isDirectory()) || dir.mkdirs())
        {
            JsonObject root = new JsonObject();

            ConfigUtils.writeConfigBase(root, "Generic", Generic.OPTIONS);
            // ConfigUtils.writeConfigBase(root, "Hotkeys", Hotkeys.HOTKEY_LIST);

            JsonUtils.writeJsonToFile(root, new File(dir, FogCustomizer.MOD_CONF));
        }
    }

    @Override
    public void load()
    {
        loadFromFile();
    }

    @Override
    public void save()
    {
        saveToFile();
    }
}
