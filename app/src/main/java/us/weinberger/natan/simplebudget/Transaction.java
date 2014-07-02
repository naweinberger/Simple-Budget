package us.weinberger.natan.simplebudget;

public class Transaction {

		String amount;
		String location;
		String day, month, year;
		String outgoing;
		String id;
		int icon;
		
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
		
		public void setIcon(int icon) {
			this.icon = icon;
		}
		
		public int getIcon() {
			return icon;
		}
		
		
		
}
