/*
 * Copyright 2019 Ingo Griebsch
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package com.github.ingogriebsch.sample.spring.cloud.openfeign.page.support;

import static com.github.ingogriebsch.sample.spring.cloud.openfeign.page.support.MessageController.PATH_FIND_ALL;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@WebMvcTest(MessageController.class)
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageClient messageClient;

    @Test
    public void findAll_should_return_page_if_using_default_pageable() throws Exception {
        ResultActions actions = mockMvc.perform(get(PATH_FIND_ALL).accept(APPLICATION_JSON_UTF8));

        actions.andExpect(status().isOk());
        actions.andExpect(content().contentType(APPLICATION_JSON_UTF8));

        actions.andExpect(jsonPath("$.content", not(empty())));
        actions.andExpect(jsonPath("$.first", is(true)));
        actions.andExpect(jsonPath("$.last", is(true)));
        actions.andExpect(jsonPath("$.number", is(0)));
        actions.andExpect(jsonPath("$.numberOfElements", is(3)));
        actions.andExpect(jsonPath("$.size", is(20)));
        actions.andExpect(jsonPath("$.totalElements", is(3)));
        actions.andExpect(jsonPath("$.totalPages", is(1)));
    }

    @Test
    public void findAll_should_return_page_if_using_given_pageable() throws Exception {
        ResultActions actions =
            mockMvc.perform(get(PATH_FIND_ALL).params(pageableParams(of(0, 2))).accept(APPLICATION_JSON_UTF8));

        actions.andExpect(status().isOk());
        actions.andExpect(content().contentType(APPLICATION_JSON_UTF8));

        actions.andExpect(jsonPath("$.content", not(empty())));
        actions.andExpect(jsonPath("$.first", is(true)));
        actions.andExpect(jsonPath("$.last", is(false)));
        actions.andExpect(jsonPath("$.number", is(0)));
        actions.andExpect(jsonPath("$.numberOfElements", is(2)));
        actions.andExpect(jsonPath("$.size", is(2)));
        actions.andExpect(jsonPath("$.totalElements", is(3)));
        actions.andExpect(jsonPath("$.totalPages", is(2)));
    }

    private static MultiValueMap<String, String> pageableParams(Pageable pageable) {
        MultiValueMap<String, String> result = new LinkedMultiValueMap<>();
        result.add("page", "" + pageable.getPageNumber());
        result.add("size", "" + pageable.getPageSize());
        return result;
    }

}
