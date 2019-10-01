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
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "message-client", url = "${message-client.url}")
public interface MessageClient {

    // @RequestMapping(path = PATH_FIND_ALL, method = GET, consumes = APPLICATION_JSON_UTF8_VALUE)
    // Page<Message> findAll(Pageable pageable);

    @RequestMapping(path = PATH_FIND_ALL, method = GET, consumes = APPLICATION_JSON_UTF8_VALUE)
    Page<Message> findAll(@RequestParam("page") int page, @RequestParam("size") int size);
}
