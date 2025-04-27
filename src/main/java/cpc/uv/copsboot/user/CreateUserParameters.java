package cpc.uv.copsboot.user;

public record CreateUserParameters(AuthServerId authServerId, String email, String mobileToken) {
}
