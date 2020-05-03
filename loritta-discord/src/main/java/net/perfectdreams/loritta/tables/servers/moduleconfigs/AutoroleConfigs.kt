package net.perfectdreams.loritta.tables.servers.moduleconfigs

import com.mrpowergamerbr.loritta.utils.exposed.array
import org.jetbrains.exposed.dao.LongIdTable
import org.jetbrains.exposed.sql.LongColumnType

object AutoroleConfigs : LongIdTable() {
    val enabled = bool("enabled").default(false)
    val giveOnlyAfterMessageWasSent = bool("give_only_after_message_was_sent").default(true)
    val roles = array<Long>("roles", LongColumnType())
    val giveRolesAfter = long("give_roles_after").nullable()
}