package br.com.edumatt3.keycloacklistener;

import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.events.admin.OperationType;
import org.keycloak.events.admin.ResourceType;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RealmProvider;
import org.keycloak.models.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static br.com.edumatt3.keycloacklistener.Util.getIdFromPath;
import static br.com.edumatt3.keycloacklistener.Util.getRoleFromRepresentation;

public class KeycloakCustomEventListener implements EventListenerProvider {

    private static final Logger log = LoggerFactory.getLogger(KeycloakCustomEventListener.class);
    private final KeycloakSession session;
    private final RealmProvider model;

    public KeycloakCustomEventListener(KeycloakSession keycloakSession) {
        session = keycloakSession;
        model = keycloakSession.realms();
    }

    @Override
    public void onEvent(Event event) {

    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {
        if(ResourceType.GROUP_MEMBERSHIP.equals((adminEvent.getResourceType()))){
            log.info("## GROUP MEMBERSHIP ACTION ##");

            String userId = getIdFromPath(adminEvent.getResourcePath());
            String role = getRoleFromRepresentation(adminEvent.getRepresentation());
            String userEmail = getUserEmailById(userId, adminEvent.getRealmId());
            Boolean isRemoving = adminEvent.getOperationType().equals(OperationType.DELETE);

            log.info("# RESOURCETYPE: {}", adminEvent.getResourceTypeAsString());
            log.info("# User email: {}", userEmail);
            log.info("# ROLE: {}", role);
            log.info("# IsRemoving: {}", isRemoving);
        }
    }

    private String getUserEmailById(String userId, String realmId){
        try {
            RealmModel realm = this.model.getRealm(realmId);
            UserModel user = this.session.users().getUserById(userId, realm);
            return user.getEmail();
        }catch (Error e){
            return null;
        }
    }

    @Override
    public void close() {

    }
}
