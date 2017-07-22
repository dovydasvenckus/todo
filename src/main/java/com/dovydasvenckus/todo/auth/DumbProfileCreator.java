package com.dovydasvenckus.todo.auth;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.credentials.UsernamePasswordCredentials;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.creator.ProfileCreator;

import java.util.HashMap;
import java.util.Map;

public class DumbProfileCreator implements ProfileCreator<UsernamePasswordCredentials, CommonProfile> {

    @Override
    public CommonProfile create(UsernamePasswordCredentials credentials, WebContext context) throws HttpAction {
        CommonProfile profile = new CommonProfile();
        Map<String, Object> values = new HashMap<>();
        values.put("username", credentials.getUsername());

        profile.build(1, values);
        profile.addRole("user");
        return profile;
    }
}
