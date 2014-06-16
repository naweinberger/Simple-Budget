package com.example.simplebudget;

public class Transaction {

		String amount;
		String location;
		String date;
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
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
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
