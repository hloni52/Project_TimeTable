/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.timetableproject.domain;

/**
 *
 * @author hloni
 */
public class TimeTable {
       private int timeTableId;
    private int venueId;
    private int subjectId;
    private int lectureId;
    private int groupId;
    private int slotId;
    
    public TimeTable(int  groupId,int subjectId ,int lecture,int venue ,int slotId){
        
        this.groupId = groupId;
        this.venueId = venueId;
        this.subjectId = subjectId;
        this.lectureId = lectureId;
        this.slotId = slotId;
    }

    public TimeTable() {
    }
    
    

    public int getSlotId() {
        return slotId;
    }

    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
    
    

    // Getters and Setters
    public int getTimeTableId() {
        return timeTableId;
    }

    public void setTimeTableId(int timeTableId) {
        this.timeTableId = timeTableId;
    }

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getLectureId() {
        return lectureId;
    }

    public void setLectureId(int lectureId) {
        this.lectureId = lectureId;
    }
}
