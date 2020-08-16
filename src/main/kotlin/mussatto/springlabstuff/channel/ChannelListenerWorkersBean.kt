package mussatto.springlabstuff.channel

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.concurrent.Executors

@Component
class ChannelListenerWorkersBean(val channelComponent: ChannelComponent,
                                 @Value("\${channel.workers:3}") val numberOfWorkers:Int): InitializingBean {
    override fun afterPropertiesSet() {

        val workerPool = Executors.newFixedThreadPool(numberOfWorkers).asCoroutineDispatcher()
        for(i in 1..numberOfWorkers) {
            val workerName = "worker $i"
            println("Starting worker $workerName")
            val worker = ChannelWorkerReader(channelComponent.channelMessage, workerName)
            GlobalScope.launch(workerPool) {
                worker.doIt()
            }
        }
    }
}

class ChannelWorkerReader(val channel: Channel<String>, val workerName:String){
    suspend fun doIt(){
        for (message in channel) {
            // THIS IS SLOOOOOW, that is why we need an async process!
            Thread.sleep(5000)
            println("${workerName}: The message is $message")
        }
    }
}