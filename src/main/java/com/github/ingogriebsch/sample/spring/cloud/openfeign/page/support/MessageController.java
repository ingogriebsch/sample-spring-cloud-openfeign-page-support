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

import static com.github.ingogriebsch.sample.spring.cloud.openfeign.page.support.PageUtils.toPage;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class MessageController {

    static final String PATH_FIND_ALL = "/messages";
    static final String REQUEST_PARAM_SINK = "sink";

    @NonNull
    private final MessageClient messageClient;

    @GetMapping(path = PATH_FIND_ALL, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<Message> findAll(@RequestParam(name = REQUEST_PARAM_SINK, required = false, defaultValue = "true") boolean sink,
        Pageable pageable) {

        if (sink) {
            log.info("Returning messages...");
            return toPage(messages(), pageable);
        } else {
            log.info("Getting messages from remote service...");
            return messageClient.findAll(pageable);
        }
    }

    private static List<Message> messages() {
        return newArrayList(new Message("Some message"), new Message("Another content"), new Message("My hoovercraft is..."));
    }
}
