package com.example.androidsocialnetwork.Model;

import java.util.List;

public class Profile {

    private String aboutMe;
    private List<Chatroom> adminChatrooms = null;
    private Boolean banned;
    private String birthDate;
    private String displayName;
    private Ethnicity ethnicity;
    private String filterPreferences;
    private Gender gender;
    private Integer height;
    private Integer id;
    private Location location;
    private String picture;
    private String pictureContentType;
    private List<Block> receivedBlocks = null;
    private List<Invitation> receivedInvitations = null;
    private Relationship relationship;
    private List<Block> sentBlocks = null;
    private List<Invitation> sentInvitations = null;
    private List<SentMessage> sentMessages = null;
    private Boolean showAge;
    private String unitSystem;
    private UserDTO user;
    private Integer weight;

    public Profile(String aboutMe, String birthDate, int height, String genderType ) {
        this.aboutMe = aboutMe;
        this.birthDate = birthDate;
        this.height = height;
        this.gender.setType(genderType);
    }

    public Profile(String aboutMe, List<Chatroom> adminChatrooms, Boolean banned, String birthDate, String displayName, Ethnicity ethnicity, String filterPreferences, Gender gender, Integer height, Integer id, Location location, String picture, String pictureContentType, List<Block> receivedBlocks, List<Invitation> receivedInvitations, Relationship relationship, List<Block> sentBlocks, List<Invitation> sentInvitations, List<SentMessage> sentMessages, Boolean showAge, String unitSystem, UserDTO user, Integer weight) {
        this.aboutMe = aboutMe;
        this.adminChatrooms = adminChatrooms;
        this.banned = banned;
        this.birthDate = birthDate;
        this.displayName = displayName;
        this.ethnicity = ethnicity;
        this.filterPreferences = filterPreferences;
        this.gender = gender;
        this.height = height;
        this.id = id;
        this.location = location;
        this.picture = picture;
        this.pictureContentType = pictureContentType;
        this.receivedBlocks = receivedBlocks;
        this.receivedInvitations = receivedInvitations;
        this.relationship = relationship;
        this.sentBlocks = sentBlocks;
        this.sentInvitations = sentInvitations;
        this.sentMessages = sentMessages;
        this.showAge = showAge;
        this.unitSystem = unitSystem;
        this.user = user;
        this.weight = weight;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public List<Chatroom> getAdminChatrooms() {
        return adminChatrooms;
    }

    public void setAdminChatrooms(List<Chatroom> adminChatrooms) {
        this.adminChatrooms = adminChatrooms;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Ethnicity getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(Ethnicity ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getFilterPreferences() {
        return filterPreferences;
    }

    public void setFilterPreferences(String filterPreferences) {
        this.filterPreferences = filterPreferences;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPictureContentType() {
        return pictureContentType;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
    }

    public List<Block> getReceivedBlocks() {
        return receivedBlocks;
    }

    public void setReceivedBlocks(List<Block> receivedBlocks) {
        this.receivedBlocks = receivedBlocks;
    }

    public List<Invitation> getReceivedInvitations() {
        return receivedInvitations;
    }

    public void setReceivedInvitations(List<Invitation> receivedInvitations) {
        this.receivedInvitations = receivedInvitations;
    }

    public Relationship getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }

    public List<Block> getSentBlocks() {
        return sentBlocks;
    }

    public void setSentBlocks(List<Block> sentBlocks) {
        this.sentBlocks = sentBlocks;
    }

    public List<Invitation> getSentInvitations() {
        return sentInvitations;
    }

    public void setSentInvitations(List<Invitation> sentInvitations) {
        this.sentInvitations = sentInvitations;
    }

    public List<SentMessage> getSentMessages() {
        return sentMessages;
    }

    public void setSentMessages(List<SentMessage> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public Boolean getShowAge() {
        return showAge;
    }

    public void setShowAge(Boolean showAge) {
        this.showAge = showAge;
    }

    public String getUnitSystem() {
        return unitSystem;
    }

    public void setUnitSystem(String unitSystem) {
        this.unitSystem = unitSystem;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
