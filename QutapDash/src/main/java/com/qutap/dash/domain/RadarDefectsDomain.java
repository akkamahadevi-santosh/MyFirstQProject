package com.qutap.dash.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="radar")
public class RadarDefectsDomain {
		private String priority;
		private int value;
		private String errorMessage;
		public String getPriority() {
			return priority;
		}
		public void setPriority(String priority) {
			this.priority = priority;
		}

		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
		public String getErrorMessage() {
			return errorMessage;
		}
		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}
		@Override
		public String toString() {
			return "RadarDefects [priority=" + priority + ", value=" + value + ", errorMessage=" + errorMessage + "]";
		}

}
