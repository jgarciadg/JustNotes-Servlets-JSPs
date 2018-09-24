package es.justo.giiis.pi.model;

import java.util.List;

public class Note {
	private int idn;
	private String title;
	private String content;
	private String urlimage;

	public Note(int idn, String title, String content, String urlimage) {
		super();
		this.idn = idn;
		this.title = title;
		this.content = content;
		this.urlimage = urlimage;
	}

	public Note() {
		super();
	}

	public boolean validate(List<String> validationMessages) {
		if (title == null || title.trim().isEmpty() || title.length() < 4) {
			validationMessages.add("The title must be higher than 3 characters or lower than .");
		} else if (title.length() > 25) {
			validationMessages.add("The title cannot be higher than 25 characters.");
		} else if (!title.replace(" ", "").matches("[a-zA-Z][a-zA-Z0-9_-]*")) {
			validationMessages.add("Invalid Title (Pattern allowed:[a-zA-Z][a-zA-Z0-9_-]*).");
		}

		System.out.println(title.trim());
		String words[] = title.split(" ");
		for (String word : words) {
			if (word.length() > 12) {
				validationMessages.add("The words cannot be higher than 12 characters.");
				break;
			}
		}

		if (content == null || content.replace(" ", "").isEmpty() || content.length() < 11) {
			validationMessages.add("The content must be higher than 10 characters.");
		} else if (content.length() > 1000) {
			validationMessages.add("The content cannot be higher than 1000 characters.");
		} else if (!content.replace(" ", "").matches("[a-zA-Z][a-zA-Z0-9_-]*")) {
			validationMessages.add("Invalid Content (Pattern allowed:[a-zA-Z][a-zA-Z0-9_-]*).");
		}

		if (validationMessages.isEmpty())
			return true;
		else
			return false;
	}

	public int getIdn() {
		return idn;
	}

	public void setIdn(int idn) {
		this.idn = idn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String newTitle) {
		this.title = newTitle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String newContent) {
		this.content = newContent;
	}

	public String getUrlimage() {
		return urlimage;
	}

	public void setUrlimage(String urlimage) {
		this.urlimage = urlimage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + idn;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((urlimage == null) ? 0 : urlimage.hashCode());
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
		Note other = (Note) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (idn != other.idn)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (urlimage == null) {
			if (other.urlimage != null)
				return false;
		} else if (!urlimage.equals(other.urlimage))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Note [idn=" + idn + ", title=" + title + ", content=" + content + ", urlimage=" + urlimage + "]";
	}
}
