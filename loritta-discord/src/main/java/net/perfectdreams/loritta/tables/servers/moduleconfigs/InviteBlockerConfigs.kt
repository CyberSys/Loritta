package net.perfectdreams.loritta.tables.servers.moduleconfigs

import com.mrpowergamerbr.loritta.utils.exposed.array
import org.jetbrains.exposed.dao.LongIdTable
import org.jetbrains.exposed.sql.LongColumnType

object InviteBlockerConfigs : LongIdTable() {
    val enabled = bool("enabled").default(false)
    val whitelistedChannels = array<Long>("whitelisted_channels", LongColumnType())
    val whitelistServerInvites = bool("whitelist_server_invites").default(true)
    val deleteMessage = bool("delete_message").default(true)
    val tellUser = bool("tell_user").default(true)
    val warnMessage = text("warn_message").nullable()
}