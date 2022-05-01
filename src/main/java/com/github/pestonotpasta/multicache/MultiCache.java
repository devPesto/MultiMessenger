package com.github.pestonotpasta.multicache;

import com.hazelcast.config.Config;
import com.hazelcast.config.YamlConfigBuilder;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.internal.nio.IOUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public final class MultiCache extends JavaPlugin {
    private final File configFile = new File(getDataFolder(), "hazelcast.yml");
    private HazelcastInstance hazelcast = null;
    private static final MultiCache INSTANCE = new MultiCache();

    @Override
    public void onEnable() {
        try {
            init();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        hazelcast.shutdown();
    }

    private void init() throws IOException {
        if (!configFile.exists())
            try (InputStream is = getResource("hazelcast.yml")) {
                IOUtil.copy(is, configFile);
            }
        Config hazelConf = new YamlConfigBuilder(configFile.getName()).build();
        this.hazelcast = Hazelcast.newHazelcastInstance(hazelConf);
    }

    public MultiCache getInstance(){
        return INSTANCE;
    }

    public HazelcastInstance getHazelcast(){
        if (hazelcast == null)
            onEnable();

        return hazelcast;
    }
}
