package com.lesa.topfilms

import com.lesa.topfilms.models.Film
import com.lesa.topfilms.network.models.FilmApiDto
import com.lesa.topfilms.network.models.toDomain
import org.junit.Assert.assertEquals
import org.junit.Test

class FilmApiDtoTest {
    @Test
    fun toDomain_whenIsFav() {
        val dto = FilmApiDto(
            id = 9257,
            name = "Gary Holman",
            year = "aptent",
            genres = listOf(),
            posterUrlPreview = "http://www.bing.com/search?q=nominavi",
            posterUrl = "https://duckduckgo.com/?q=fabulas"
        )
        val domain = dto.toDomain(setOf(9257, 3, 4))
        val expectedDomain = Film(
            id = 9257,
            name = "Gary Holman",
            year = "aptent",
            genres = listOf(),
            posterUrlPreview = "http://www.bing.com/search?q=nominavi",
            posterUrl = "https://duckduckgo.com/?q=fabulas",
            isFav = true,
            countries = listOf(),
            description = null
        )
        assertEquals(expectedDomain, domain)
    }

    @Test
    fun toDomain_whenIsNotFav() {
        val dto = FilmApiDto(
            id = 9257,
            name = "Gary Holman",
            year = "aptent",
            genres = listOf(),
            posterUrlPreview = "http://www.bing.com/search?q=nominavi",
            posterUrl = "https://duckduckgo.com/?q=fabulas"
        )
        val domain = dto.toDomain(setOf(9, 3, 4))
        val expectedDomain = Film(
            id = 9257,
            name = "Gary Holman",
            year = "aptent",
            genres = listOf(),
            posterUrlPreview = "http://www.bing.com/search?q=nominavi",
            posterUrl = "https://duckduckgo.com/?q=fabulas",
            isFav = false,
            countries = listOf(),
            description = null
        )
        assertEquals(expectedDomain, domain)
    }
}