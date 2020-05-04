package net.perfectdreams.loritta.tables.servers

import com.mrpowergamerbr.loritta.tables.ServerConfigs
import net.perfectdreams.loritta.utils.CustomCommandCodeType
import org.jetbrains.exposed.dao.LongIdTable

object CustomGuildCommands : LongIdTable() {
	val guild = reference("guild", ServerConfigs).index()
	val label = text("label")
	val enabled = bool("enabled")
	val codeType = enumeration("code_type", CustomCommandCodeType::class)
	val code = text("code")
}