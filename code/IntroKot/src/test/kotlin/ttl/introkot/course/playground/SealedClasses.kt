package ttl.introkot.course.playground

/**
 * @author whynot
 */

sealed class Media {
}

class Video : Media() {
    //code to read data and initialize

}

class Audio : Media() {
    //code to read data and initialize
}

class PodCast : Media() {
    //PodCast data
}

class MediaService {
    fun handleMedia(media: Media) =
            when (media) {
                is Video -> handleVideo(media)
                is Audio -> handleAudio(media)
                is PodCast -> throw NotImplementedError("PodCasts Not supported Yet")
            }

    private fun handleVideo(media: Video) {
        println("handling video")
    }

    private fun handleAudio(media: Audio) {
        println("handling audio")
    }
}

fun main() {
    val service = MediaService()
    //Imagine this is Controller code.
    //We get some input from the server, look at the
    //header and see it is video content, e.g. contentType=video/mp4
    //We create a Video object from the data and send it
    //to our 'handleMedia' method which is in the service
    val data = Video(/*input Data*/)

    service.handleMedia(data)
}

