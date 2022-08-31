package com.github.pestonotpasta.multicache

import com.esotericsoftware.kryo.kryo5.io.Output
import org.bukkit.Location
import org.bukkit.inventory.ItemStack
import java.util.*

fun Output.writeItemStack(item: ItemStack) {
    val data: ByteArray = item.serializeAsBytes()
    this.writeVarInt(data.size, false)
    this.write(data)
}

fun Output.writeLocation(loc: Location) {
    this.writeUUID(loc.world.uid)
    this.writeDouble(loc.x)
    this.writeDouble(loc.y)
    this.writeDouble(loc.z)
    this.writeFloat(loc.yaw)
    this.writeFloat(loc.pitch)
}

fun Output.writeUUID(uuid: UUID) {
    this.writeString(uuid.toString())
}

@Suppress("unused")
data class PayloadBuilder(
    val out: Output = Output()
) {
    fun writeBoolean(value: Boolean) = apply { out.writeBoolean(value) }
    fun writeByte(value: Byte) = apply { out.writeByte(value) }
    fun writeBytes(value: ByteArray) = apply { out.writeBytes(value) }
    fun writeChar(value: Char) = apply { out.writeChar(value) }
    fun writeDouble(value: Double) = apply { out.writeDouble(value) }
    fun writeFloat(value: Float) = apply { out.writeFloat(value) }
    fun writeInt(value: Int) = apply { out.writeInt(value) }
    fun writeItemStack(value: ItemStack) = apply { out.writeItemStack(value) }
    fun writeLocation(value: Location) = apply { out.writeLocation(value) }
    fun writeLong(value: Long) = apply { out.writeLong(value) }
    fun writeShort(value: Int) = apply { out.writeShort(value) }
    fun writeString(value: String) = apply { out.writeString(value) }
    fun writeUUID(value: UUID) = apply { out.writeUUID(value) }
    fun build(): ByteArray { return out.toBytes() }
}