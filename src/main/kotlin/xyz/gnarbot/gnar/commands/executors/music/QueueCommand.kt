package xyz.gnarbot.gnar.commands.executors.music

import net.dv8tion.jda.core.EmbedBuilder
import xyz.gnarbot.gnar.commands.executors.music.parent.MusicExecutor
import xyz.gnarbot.gnar.commands.handlers.Command
import xyz.gnarbot.gnar.members.Clearance
import xyz.gnarbot.gnar.servers.Host
import xyz.gnarbot.gnar.servers.music.MusicManager
import xyz.gnarbot.gnar.utils.Note
import java.util.*

@Command(aliases = arrayOf("queue", "list"), clearance = Clearance.DJ)
class QueueCommand : MusicExecutor() {
    override fun execute(note: Note, args: Array<String>, host: Host, manager: MusicManager) {
        val queue = manager.scheduler.queue

        if (queue.isEmpty()) {
            note.replyMusic("The queue is currently empty.")
            return
        }

        var trackCount = 0
        var queueLength = 0L

        val eb = EmbedBuilder()
        val sj = StringJoiner("\n")

        eb.setTitle("Current Music Queue")

        for (track in queue) {
            queueLength += track.duration

            trackCount++
            sj.add("**$trackCount** `[${getTimestamp(track.duration)}]` __${track.info.title}__")
        }

        eb.addField("", sj.toString(), false)

        eb.addField("", "**Entries:** `${trackCount}`\n**Total queue length:** `[${getTimestamp(queueLength)}]`", false)
        eb.setColor(color)

        note.channel.sendMessage(eb.build()).queue()
    }
}