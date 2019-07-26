// UserAcountSerivce.aidl
package com.z.hmi;
// Declare any non-default types here with import statements

interface IUserAcountSerivce {
    String getCurrentUserInfo();
    String uniteLogin(String clientIdString, String clientSecretString);
    String refreshUniteToken(String clientIdString, String clientSecretString);
    String refreshInnerUserAccessToken();
    String getInnerUserAccessToken();
    String refreshInnerAdminAccessToken();
    String getInnerAdminAccessToken();
    String getCurrentClientId();
    String getCurrentIdpUserId();
    void refreshUserAgreement();
    boolean getCurrentLoginStatus();
    boolean refreshUserprofileToken();
    String getUserprofileToken();
}
