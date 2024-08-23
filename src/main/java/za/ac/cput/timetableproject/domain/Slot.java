/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.timetableproject.domain;

/**
 *
 * @author hloni
 */
public class Slot {
    private int slotId;
    private int periodNumber;
    private String slotTime;
    private String dayOfWeek;

    public Slot(int slotId, String slotTime, String dayOfWeek) {
        this.slotId = slotId;
        this.slotTime = slotTime;
        this.dayOfWeek = dayOfWeek;
    }

    
    // Getters and Setters
    public int getSlotId() {
        return slotId;
    }

    public String getSlotTime() {
        return slotTime;
    }

    public void setSlotTime(String slotTime) {
        this.slotTime = slotTime;
    }

    
    public void setSlotId(int slotId) {
        this.slotId = slotId;
    }

    public int getPeriodNumber() {
        return periodNumber;
    }

    public void setPeriodNumber(int periodNumber) {
        this.periodNumber = periodNumber;
    }

  

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    
}
