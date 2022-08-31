package com.github.pestonotpasta.multicache

import com.hazelcast.cluster.MembershipEvent
import com.hazelcast.cluster.MembershipListener
import com.hazelcast.config.YamlConfigBuilder
import com.hazelcast.core.Hazelcast
import com.hazelcast.core.HazelcastInstance
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException

class MultiCache : JavaPlugin() {
    private val configFile = File(dataFolder, "config.yml")

    override fun onEnable() {
        instance = this
        try {
            initHazelcast()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onDisable() {
        if (Companion.hazelcast != null) Companion.hazelcast!!.shutdown()
    }

    @Throws(IOException::class)
    private fun initHazelcast() {
        if (!configFile.exists()) saveDefaultConfig()
        val url = configFile.toPath().toUri().toURL()
        val hazelConf = YamlConfigBuilder(url).build()
        hazelConf.jetConfig.isEnabled = true // Enable Jet config

        // Override logging type
        hazelConf.setProperty("hazelcast.logging.type", "none")
        Companion.hazelcast = Hazelcast.newHazelcastInstance(hazelConf)
        logMembers()
    }

    val hazelcast: HazelcastInstance?
        get() = Companion.hazelcast

    private class HazelcastListener : MembershipListener {
        override fun memberAdded(membershipEvent: MembershipEvent) {
            log.info("Cluster member added...")
            logMembers()
        }

        override fun memberRemoved(membershipEvent: MembershipEvent) {
            log.info("Cluster member removed...")
            logMembers()
        }
    }

    companion object {
        private val log = Bukkit.getLogger()
        private var hazelcast: HazelcastInstance? = null
        var instance: MultiCache? = null
            private set

        private fun logMembers() {
            val members = hazelcast!!.cluster.members
            log.info("--------------------------------------------------------------")
            log.info("Members:")
            var i = 1
            for (member in members) {
                val host = member.address.host
                val port = member.address.port
                val id = member.uuid
                log.info(String.format("%d. [%s:%d] - %s", i, host, port, id.toString()))
                i++
            }
            log.info("--------------------------------------------------------------")
        }
    }
}