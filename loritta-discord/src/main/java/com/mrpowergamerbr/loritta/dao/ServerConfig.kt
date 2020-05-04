package com.mrpowergamerbr.loritta.dao

import com.mrpowergamerbr.loritta.network.Databases
import com.mrpowergamerbr.loritta.tables.DonationKeys
import com.mrpowergamerbr.loritta.tables.GuildProfiles
import com.mrpowergamerbr.loritta.tables.ServerConfigs
import net.perfectdreams.loritta.dao.servers.moduleconfigs.*
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import java.math.BigDecimal

class ServerConfig(id: EntityID<Long>) : Entity<Long>(id) {
	companion object : EntityClass<Long, ServerConfig>(ServerConfigs)

	val guildId = this.id.value
	var commandPrefix by ServerConfigs.commandPrefix
	var localeId by ServerConfigs.localeId
	var deleteMessageAfterCommand by ServerConfigs.deleteMessageAfterCommand
	var warnOnMissingPermission by ServerConfigs.warnOnMissingPermission
	var warnOnUnknownCommand by ServerConfigs.warnOnUnknownCommand
	var blacklistedChannels by ServerConfigs.blacklistedChannels
	var warnIfBlacklisted by ServerConfigs.warnIfBlacklisted
	var blacklistedWarning by ServerConfigs.blacklistedWarning
	var disabledCommands by ServerConfigs.disabledCommands
	// var donationKey by DonationKey optionalReferencedOn ServerConfigs.donationKey
	var donationConfig by DonationConfig optionalReferencedOn ServerConfigs.donationConfig
	var birthdayConfig by BirthdayConfig optionalReferencedOn ServerConfigs.birthdayConfig
	var economyConfig by EconomyConfig optionalReferencedOn ServerConfigs.economyConfig
	var levelConfig by LevelConfig optionalReferencedOn ServerConfigs.levelConfig
	var starboardConfig by StarboardConfig optionalReferencedOn ServerConfigs.starboardConfig
	var miscellaneousConfig by MiscellaneousConfig optionalReferencedOn ServerConfigs.miscellaneousConfig
	var eventLogConfig by EventLogConfig optionalReferencedOn ServerConfigs.eventLogConfig
	var autoroleConfig by AutoroleConfig optionalReferencedOn ServerConfigs.autoroleConfig
	var inviteBlockerConfig by InviteBlockerConfig optionalReferencedOn ServerConfigs.inviteBlockerConfig
	var welcomerConfig by WelcomerConfig optionalReferencedOn ServerConfigs.welcomerConfig
	var moderationConfig by ModerationConfig optionalReferencedOn ServerConfigs.moderationConfig
	var migrationVersion by ServerConfigs.migrationVersion

	fun getActiveDonationKeys() = transaction(Databases.loritta) {
		DonationKey.find { DonationKeys.activeIn eq id and (DonationKeys.expiresAt greaterEq System.currentTimeMillis()) }
				.toList()
	}

	fun getActiveDonationKeysValue() = getActiveDonationKeys().sumByDouble { it.value }

	fun getUserData(id: Long): GuildProfile {
		val t = this
		return transaction(Databases.loritta) {
			getUserDataIfExists(id) ?: GuildProfile.new {
				this.guildId = t.guildId
				this.userId = id
				this.money = BigDecimal(0)
				this.quickPunishment = false
				this.xp = 0
				this.isInGuild = true
			}
		}
	}

	fun getUserDataIfExists(id: Long): GuildProfile? {
		return transaction(Databases.loritta) {
			GuildProfile.find { (GuildProfiles.guildId eq guildId) and (GuildProfiles.userId eq id) }.firstOrNull()
		}
	}
}