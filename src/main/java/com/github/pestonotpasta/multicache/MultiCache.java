package com.github.pestonotpasta.multicache;

import com.hazelcast.config.Config;
import com.hazelcast.config.YamlConfigBuilder;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Logger;

public final class MultiCache extends JavaPlugin {
    private final File configFile = new File(getDataFolder(), "config.yml");
    private static final Logger log = Bukkit.getLogger();
    private static HazelcastInstance hazelcast;
    private static MultiCache instance;
    private boolean isShuttingDown = false;

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
        this.isShuttingDown = true;
        shutdown();
    }

    private void initHazelcast() throws IOException {
        if (!configFile.exists())
            saveDefaultConfig();

        URL url = configFile.toPath().toUri().toURL();
        Config hazelConf = new YamlConfigBuilder(url).build();
        this.hazelcast = Hazelcast.newHazelcastInstance(hazelConf);
    }

    public static MultiCache getInstance() {
        return instance;
    }

    public HazelcastInstance getHazelcast() {
        return hazelcast;
    }

    public void shutdown() {
        if (hazelcast != null && isShuttingDown)
            hazelcast.shutdown();
    }

    private static void logMembers() {
        Set<Member> members = hazelcast.getCluster().getMembers();
        log.info("--------------------------------------------------------------");
        log.info("Members:");
        int i = 1;
        for (Member member : members) {
            String host = member.getAddress().getHost();
            int port = member.getAddress().getPort();
            UUID id = member.getUuid();
            log.info(String.format("%d. [%s:%d] - %s", i, host, port, id.toString()));
            i++;
        }
        log.info("--------------------------------------------------------------");
    }

    private static class HazelcastListener implements MembershipListener {

        @Override
        public void memberAdded(MembershipEvent membershipEvent) {
            log.info("Cluster member added...");
            logMembers();
        }

        @Override
        public void memberRemoved(MembershipEvent membershipEvent) {
            log.info("Cluster member removed...");
            logMembers();
        }
    }
}
