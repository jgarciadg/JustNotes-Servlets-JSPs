package es.justo.giiis.pi.model;

public class VersionsNotes {
	private int idn;
	private int idu;
	private int owner; // 0=false,1=true
	private int archived;// 0=false,1=true
	private int pinned;// 0=false,1=true
	private int intrash;// 0=false,1=true
	private int color;
	private String timestamp;
	private String title;
	private String content;
	private String urlimage;

	public VersionsNotes() {
		super();
	}

	public VersionsNotes(int idn, int idu, int owner, int archived, int pinned, int intrash, int color,
			String timestamp, String title, String content, String urlimage) {
		super();
		this.idn = idn;
		this.idu = idu;
		this.owner = owner;
		this.archived = archived;
		this.pinned = pinned;
		this.intrash = intrash;
		this.color = color;
		this.timestamp = timestamp;
		this.title = title;
		this.content = content;
		this.urlimage = urlimage;
	}

	public int getIdn() {
		return idn;
	}

	public void setIdn(int idn) {
		this.idn = idn;
	}

	public int getIdu() {
		return idu;
	}

	public void setIdu(int idu) {
		this.idu = idu;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrlimage() {
		return urlimage;
	}

	public void setUrlimage(String urlimage) {
		this.urlimage = urlimage;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public int getArchived() {
		return archived;
	}

	public void setArchived(int archived) {
		this.archived = archived;
	}

	public int getPinned() {
		return pinned;
	}

	public void setPinned(int pinned) {
		this.pinned = pinned;
	}

	public int getIntrash() {
		return intrash;
	}

	public void setIntrash(int intrash) {
		this.intrash = intrash;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "VersionsNotes [idn=" + idn + ", idu=" + idu + ", owner=" + owner + ", archived=" + archived
				+ ", pinned=" + pinned + ", intrash=" + intrash + ", color=" + color + ", timestamp=" + timestamp
				+ ", title=" + title + ", content=" + content + ", urlimage=" + urlimage + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + archived;
		result = prime * result + color;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + idn;
		result = prime * result + idu;
		result = prime * result + intrash;
		result = prime * result + owner;
		result = prime * result + pinned;
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
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
		VersionsNotes other = (VersionsNotes) obj;
		if (archived != other.archived)
			return false;
		if (color != other.color)
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (idn != other.idn)
			return false;
		if (idu != other.idu)
			return false;
		if (intrash != other.intrash)
			return false;
		if (owner != other.owner)
			return false;
		if (pinned != other.pinned)
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
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


}
