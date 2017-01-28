package com.dovydasvenckus.todo.auth;

import com.google.common.collect.ImmutableMap;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.creator.ProfileCreator;

import java.util.Map;

public class DumbProfileCreator implements ProfileCreator<UsernamePasswordCredentials, CommonProfile> {

    @Override
    public CommonProfile create(UsernamePasswordCredentials credentials, WebContext context) throws HttpAction {
        CommonProfile profile = new CommonProfile();
        Map<String, Object> values = ImmutableMap.<String, Object>builder()
                .put("username", credentials.getUsername())
                .build();

        profile.build(1, values);
        profile.addRole("user");
        return profile;
    }
}
