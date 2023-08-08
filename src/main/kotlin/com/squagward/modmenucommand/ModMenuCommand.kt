package com.squagward.modmenucommand

import com.mojang.brigadier.CommandDispatcher
import com.terraformersmc.modmenu.ModMenu
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.minecraft.client.MinecraftClient

object ModMenuCommand : ClientModInitializer {
    override fun onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register { dispatcher: CommandDispatcher<FabricClientCommandSource>, cra ->
            val mc: MinecraftClient = MinecraftClient.getInstance()

            val command = literal("modmenu")

            ModMenu.MODS.values.filter {
                ModMenu.getConfigScreen(it.id, null) != null
            }.forEach { mod ->
                command.then(literal(mod.id).executes {
                    mc.send {
                        mc.setScreen(ModMenu.getConfigScreen(mod.id, null))
                    }
                    1
                })
            }

            dispatcher.register(command)
        }
    }
}
