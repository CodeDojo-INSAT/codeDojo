package com.zs.codeDojo.models.DAO;

import org.json.JSONObject;

public class AnnouncementObject {
    private String announcementID;
    private final String announcementTitle;

    private final String announcementContent;

    private final String createdDate;

    private final String updatedDate;

    private final boolean isActive;

    public AnnouncementObject(String announcementID, String announcementTitle, String announcementContent,
            String createdDate, String updatedDate) {
        this.announcementID = announcementID;
        this.announcementTitle = announcementTitle;
        this.announcementContent = announcementContent;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.isActive = true;
    }

    public AnnouncementObject(JSONObject obj) {
        this.announcementTitle = obj.getString("announcementTitle");
        this.announcementContent = obj.getString("announcementContent");
        this.createdDate = null;
        this.updatedDate = null;
        this.isActive = true;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getAnnouncementContent() {
        return announcementContent;
    }

    public String getAnnouncementID() {
        return announcementID;
    }

    public String getAnnouncementTitle() {
        return announcementTitle;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public boolean isActive() {
        return isActive;
    }
}
