package com.polarbookshop.quoteservice;

import com.polarbookshop.quoteservice.domain.Genre;
import com.polarbookshop.quoteservice.domain.Quote;
import com.polarbookshop.quoteservice.domain.QuoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureMockMvc
public class QuoteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuoteService quoteService;

    @Test
    public void getAllQuotesTest() throws Exception {
        when(quoteService.getAllQuotes()).thenReturn(List.of(new Quote("Content A", "Abigail", Genre.ADVENTURE)));

        mockMvc.perform(get("/quotes")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"content\":\"Content A\",\"author\":\"Abigail\",\"genre\":\"ADVENTURE\"}]"));
    }

    @Test
    public void getRandomQuoteTest() throws Exception {
        when(quoteService.getRandomQuote()).thenReturn(new Quote("Content B", "Beatrix", Genre.FANTASY));

        mockMvc.perform(get("/quotes/random")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"content\":\"Content B\",\"author\":\"Beatrix\",\"genre\":\"FANTASY\"}"));
    }

    @Test
    public void getRandomQuoteByGenreTest() throws Exception {
        when(quoteService.getRandomQuoteByGenre(Genre.SCIENCE_FICTION)).thenReturn(new Quote("Content E", "Eileen", Genre.SCIENCE_FICTION));

        mockMvc.perform(get("/quotes/random/SCIENCE_FICTION")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"content\":\"Content E\",\"author\":\"Eileen\",\"genre\":\"SCIENCE_FICTION\"}"));
    }
}