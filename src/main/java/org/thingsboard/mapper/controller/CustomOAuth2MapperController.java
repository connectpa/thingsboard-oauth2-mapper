/**
 * Copyright © 2016-2018 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingsboard.mapper.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thingsboard.server.dao.oauth2.OAuth2User;

import java.io.IOException;

@RestController
@RequestMapping(path = "/oauth2")
public class CustomOAuth2MapperController {

    private static final ObjectMapper json = new ObjectMapper();

    @RequestMapping(value = "/mapper", method = RequestMethod.POST)
    @ResponseBody
    public OAuth2User map(@RequestBody String request) {
        JsonNode externalUserInJson;
        try {
            externalUserInJson = json.readTree(request);
        } catch (IOException e) {
            throw new RuntimeException("Can't parse external user object", e);
        }
        OAuth2User result = new OAuth2User();

        String email = externalUserInJson.get("email").asText();
        result.setEmail(email);

        String firstName = externalUserInJson.get("givenName").asText();
        result.setFirstName(firstName);

        String lastName = externalUserInJson.get("familyName").asText();
        result.setLastName(lastName);

        String role = "";
        JsonNode jsonRole = externalUserInJson.get("attributes").get("role");
        if (jsonRole != null) role = jsonRole.asText();

        if (role.toLowerCase().equals("tenant")) {
            // Any attribute from the external user info object can be used as tenant name
            result.setTenantName(firstName + " " + lastName);
        } else {
            result.setCustomerName(firstName + " " + lastName);
        }

        return result;
    }
}
