package model;

public class Booking {
    private String customerName;
    private String date;   // Format: "YYYY-MM-DD"
    private String time;
    private int people; //tableSize

    // Constructors, Getters, Setters
    public Booking() { }

    public Booking(String customerName, String date, String time, int people) {
        this.customerName = customerName;
        this.date = date;
        this.time = time;
        this.people = people;
    }

    public String getCustomerName()
    {
        return customerName;
    }
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public String getDate()
    {
        return date;
    }
    public void setDate(String date)
    {
        this.date = date;
    }

    public String getTime()
    {
        return time;
    }
    public void setTime(String time)
    {
        this.time = time;
    }

    public int getPeople()
    {
        return people;
    }
    public void setPeople(int people)
    {
        this.people = people;
    }

}

