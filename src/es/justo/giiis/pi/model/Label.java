package es.justo.giiis.pi.model;

import java.util.List;

public class Label {
	private int idn;
	private String content;

	public Label() {
		super();
	}

	public Label(int idn, String content) {
		super();
		this.idn = idn;
		this.content = content;
	}

	public boolean validate(List<String> validationMessages) {
		if (content == null || content.trim().isEmpty() || content.length() < 3) {
			validationMessages.add("The label must be higher than 3 characters or lower than .");
		} else if (content.length() > 14) {
			validationMessages.add("The title cannot be higher than 1 characters.");
		} else if (!content.replace(" ", "").matches("[a-zA-Z][a-zA-Z0-9_-]*")) {
			validationMessages.add("Invalid Label (Pattern allowed:[a-zA-Z][a-zA-Z0-9_-]*).");
		}

		return validationMessages.isEmpty();
	}

	public int getIdn() {
		return idn;
	}

	public void setIdn(int idn) {
		this.idn = idn;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String label) {
		this.content = label;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + idn;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Label other = (Label) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (idn != other.idn)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Label [idn=" + idn + ", content=" + content + "]";
	}

}
