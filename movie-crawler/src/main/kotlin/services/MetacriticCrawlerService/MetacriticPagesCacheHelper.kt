package services.MetacriticCrawlerService

import repositories.MetacriticHtmlString
import java.io.File
import java.io.FileNotFoundException

private const val CACHE_FOLDER = "metacritic_cache"
private const val CACHE_PAGE_PATTERN = "page_%d.html"

class MetacriticPagesCacheHelper{

    init {
        //make necessary folder structure
        File(CACHE_FOLDER).mkdir()
    }

    fun cachePage(content: MetacriticHtmlString, pageNum: Int){

        val cache_file = File(CACHE_FOLDER, CACHE_PAGE_PATTERN.format(pageNum))
        println(">>>>>>>>>>>" + cache_file.absolutePath)
        cache_file.writeText(content)
    }

    fun getCachedPage(pageNum: Int): MetacriticHtmlString? =
            try{
                File(CACHE_FOLDER, CACHE_PAGE_PATTERN.format(pageNum)).readText()
            } catch (e: Exception){
                if(e is FileNotFoundException)
                    null
                else
                    throw e
            }

}
