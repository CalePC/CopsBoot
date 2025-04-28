package cpc.uv.copsboot.user.web;

import cpc.uv.copsboot.user.User;

public record UserDto(String userId, String email, String authServerId, String mobileToken) {

    public static UserDto fromUser(User user) {
        return new UserDto(
                user.getId().asString(),
                user.getEmail(),
                user.getAuthServerId().value().toString(),
                user.getMobileToken()
        );
    }
}