package mussatto.springlabstuff.channel

import kotlinx.coroutines.runBlocking
import mussatto.springlabstuff.channel.ChannelComponent
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class TestController(val channelComponent: ChannelComponent) {

    @GetMapping("/async")
    fun ShouldProcessAsync(@RequestParam message: String): String {
        val myId = UUID.randomUUID().toString();
        runBlocking {
            channelComponent.channelMessage.send("Sending Message! id=$myId")
            println("Message sent!")
        }

        return myId;
    }
}