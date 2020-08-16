package mussatto.springlabstuff.channel

import kotlinx.coroutines.channels.Channel
import org.springframework.stereotype.Component
import javax.annotation.PreDestroy

@Component
class ChannelComponent {
    val channelMessage = Channel<String>(5)

    @PreDestroy
    fun preDestroy(){
        channelMessage.close()
        println("channel closed")
    }
}