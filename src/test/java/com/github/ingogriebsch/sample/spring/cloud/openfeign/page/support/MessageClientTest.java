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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.data.domain.PageRequest.of;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles(value = "feignclient")
@SpringBootTest(classes = Application.class, webEnvironment = DEFINED_PORT)
public class MessageClientTest {

    @Autowired
    private MessageClient messageClient;

    @Test
    public void findAll_should_return_page_if_using_given_pageable() {
        Page<Message> page = messageClient.findAll(of(0, 2));

        assertThat(page).isNotNull();
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getNumberOfElements()).isEqualTo(2);
        assertThat(page.getSize()).isEqualTo(2);
        assertThat(page.getTotalElements()).isEqualTo(3);
        assertThat(page.getTotalPages()).isEqualTo(2);

        List<Message> content = page.getContent();
        assertThat(content).isNotNull();
        assertThat(content).hasSize(2);
        assertThat(content).doesNotContainNull();
    }
}
