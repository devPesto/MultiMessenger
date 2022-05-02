package com.github.pestonotpasta.multicache;

import com.hazelcast.config.Config;
import com.hazelcast.config.YamlConfigBuilder;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

public final class MultiCache extends JavaPlugin {
    private final File configFile = new File(getDataFolder(), "config.yml");
    private HazelcastInstance hazelcast = null;
    private static MultiCache instance;

    @Override
    public void onEnable() {
        instance = this;
        try {
            initHazelcast();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        hazelcast.shutdown();
    }

    private void initHazelcast() throws IOException {
        if (!configFile.exists())
            saveDefaultConfig();

        URL url = configFile.toPath().toUri().toURL();
        Config hazelConf = new YamlConfigBuilder(url).build();
        this.hazelcast = Hazelcast.newHazelcastInstance(hazelConf);
    }

    public MultiCache getInstance(){
        return instance;
    }

    public HazelcastInstance getHazelcast(){
        return hazelcast;
    }
}
