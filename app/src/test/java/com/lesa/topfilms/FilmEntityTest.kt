package com.lesa.topfilms

import com.lesa.topfilms.models.Film
import com.lesa.topfilms.models.toEntity
import com.lesa.topfilms.storage.FilmEntity
import com.lesa.topfilms.storage.toDomain
import org.junit.Assert
import org.junit.Test

class FilmEntityTest {
    @Test
    fun toDomain_isSuccess() {
        val entity = FilmEntity(
            id = 9257,
            name = "Gary Holman",
            year = "aptent",
            genres = listOf("adad", "a"),
            posterUrlPreview = "http://www.bing.com/search?q=nominavi",
            posterUrl = "https://duckduckgo.com/?q=fabulas",
            isFav = true,
            countries = listOf("adad", "a"),
            description = null
        )
        val domain = entity.toDomain()
        val expectedDomain = Film(
            id = 9257,
            name = "Gary Holman",
            year = "aptent",
            genres = listOf("adad", "a"),
            posterUrlPreview = "http://www.bing.com/search?q=nominavi",
            posterUrl = "https://duckduckgo.com/?q=fabulas",
            isFav = true,
            countries = listOf("adad", "a"),
            description = null
        )
        Assert.assertEquals(expectedDomain, domain)
    }

      @Test
    fun toEntity_isSuccess() {
        val domain = Film(
            id = 9257,
            name = "Gary Holman",
            year = "aptent",
            genres = listOf("adad", "a"),
            posterUrlPreview = "http://www.bing.com/search?q=nominavi",
            posterUrl = "https://duckduckgo.com/?q=fabulas",
            isFav = true,
            countries = listOf("adad", "a"),
            description = null
        )
        val entity = domain.toEntity()
        val expectedEntity = FilmEntity(
            id = 9257,
            name = "Gary Holman",
            year = "aptent",
            genres = listOf("adad", "a"),
            posterUrlPreview = "http://www.bing.com/search?q=nominavi",
            posterUrl = "https://duckduckgo.com/?q=fabulas",
            isFav = true,
            countries = listOf("adad", "a"),
            description = null
        )
        Assert.assertEquals(expectedEntity, entity)
    }
}