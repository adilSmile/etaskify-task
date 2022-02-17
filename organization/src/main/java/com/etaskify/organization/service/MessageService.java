package com.etaskify.organization.service;

import java.util.UUID;

public interface MessageService {
    UUID getOrganizationIdFromReceivedToken(String token);
}
