/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package za.ac.cput.timetableproject.domain;

/**
 *
 * @author nkulu
 */
public class Venue {
    private int Venue_id;
    private String Description;
    private int Capacity;

    public Venue() {
    }

    public Venue(int Venue_id, String Description, int Capacity) {
        this.Venue_id = Venue_id;
        this.Description = Description;
        this.Capacity = Capacity;
    }

    public int getVenue_id() {
        return Venue_id;
    }

    public String getDescription() {
        return Description;
    }

    public int getCapacity() {
        return Capacity;
    }

    public void setVenue_id(int Venue_id) {
        this.Venue_id = Venue_id;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public void setCapacity(int Capacity) {
        this.Capacity = Capacity;
    }

    @Override
    public String toString() {
        return "WorkerClass{" + "Venue_id=" + Venue_id + ", Description=" + Description + ", Capacity=" + Capacity + '}';
    }
    
    
}