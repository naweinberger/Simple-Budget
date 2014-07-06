package us.weinberger.natan.simplebudget;

import java.util.Calendar;
import java.util.Date;

public class Transaction {

    String amount;
    String location;
    String day, month, year;
    String outgoing;
    String id;
    String icon;
    String tag;
    int numDay, numMonth, numYear;
    Calendar date;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }



    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public int getNumDay() {
        return numDay;
    }

    public void setNumDay(int numDay) {
        this.numDay = numDay;
    }

    public int getNumMonth() {
        return numMonth;
    }

    public void setNumMonth(int numMonth) {
        this.numMonth = numMonth;
    }

    public int getNumYear() {
        return numYear;
    }

    public void setNumYear(int numYear) {
        this.numYear = numYear;
    }


		
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String isOutgoing() {
			return outgoing;
		}
		public void setOutgoing(String outgoing) {
			this.outgoing = outgoing;
		}

		public String getId() {
			return id;
		}
		
		public void setId(String id) {
			this.id = id;
		}
		
		public void setIcon(String icon) {
			this.icon = icon;
		}
		
		public String getIcon() {
			return icon;
		}
		
		
		
}
