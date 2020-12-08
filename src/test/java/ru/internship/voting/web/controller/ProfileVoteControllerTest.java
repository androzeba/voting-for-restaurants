package ru.internship.voting.web.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.internship.voting.TestUtil.userHttpBasic;
import static ru.internship.voting.UserTestData.USER2;
import static ru.internship.voting.VoteTestData.VOTE2;
import static ru.internship.voting.VoteTestData.VOTE5;
import static ru.internship.voting.VoteTestData.VOTE7;
import static ru.internship.voting.VoteTestData.VOTE_MATCHER;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.internship.voting.web.AbstractControllerTest;

class ProfileVoteControllerTest extends AbstractControllerTest {

  private static final String VOTE_URL = "/votes/";

  @Test
  void getAllVotes() throws Exception {
    perform(MockMvcRequestBuilders.get(VOTE_URL)
        .with(userHttpBasic(USER2)))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(VOTE_MATCHER.contentJson(VOTE7, VOTE5, VOTE2));
  }

  @Test
  void getUnAuth() throws Exception {
    perform(MockMvcRequestBuilders.get(VOTE_URL))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void getFilteredVotes() throws Exception {
    perform(MockMvcRequestBuilders.get(VOTE_URL + "filter")
        .param("startDate", "2020-08-04")
        .param("endDate", "2020-08-07")
        .with(userHttpBasic(USER2)))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(VOTE_MATCHER.contentJson(VOTE7, VOTE5));
  }
}