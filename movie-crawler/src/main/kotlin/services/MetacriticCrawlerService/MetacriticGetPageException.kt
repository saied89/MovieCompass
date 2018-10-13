package services.MetacriticCrawlerService

class MetacriticGetPageException(pageNum: Int, error: Throwable?): Exception("There was an error getting page \"$pageNum\": ${error?.message ?: "Error with empty message!"}")