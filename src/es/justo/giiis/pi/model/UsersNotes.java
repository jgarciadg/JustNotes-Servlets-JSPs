package es.justo.giiis.pi.model;

import java.util.List;

import es.justo.giiis.pi.util.QueryParameters;

public class UsersNotes implements Comparable<UsersNotes> {
	private int idu;
	private int idn;
	private int owner; // 0=false,1=true
	private int archived;// 0=false,1=true
	private int pinned;// 0=false,1=true
	private int intrash;// 0=false,1=true
	private int color;

	public UsersNotes() {
	}

	public UsersNotes(int idu, int idn, int color) {
		super();
		this.idu = idu;
		this.idn = idn;
		this.color = color;

		this.owner = 1;
		this.archived = 0;
		this.pinned = 0;
		this.intrash = 0;
	}

	public UsersNotes(int idu, int idn, int owner, int archived, int pinned, int intrash, int color) {
		super();
		this.idu = idu;
		this.idn = idn;
		this.owner = owner;
		this.archived = archived;
		this.pinned = pinned;
		this.intrash = intrash;
		this.color = color;
	}

	public boolean validate(List<String> validationMessages) {
		if (owner != 0 && owner != 1) {
			validationMessages.add("Fill in the Owner field with a correct value (0=false,1=true).");
		}

		if (archived != 0 && archived != 1) {
			validationMessages.add("Fill in the Archived field with a correct value (0=false,1=true).");
		}

		if (pinned != 0 && pinned != 1) {
			validationMessages.add("Fill in the Pinned field with a correct value (0=false,1=true).");
		}

		if (intrash != 0 && intrash != 1) {
			validationMessages.add("Fill in the InTrash field with a correct value (0=false,1=true).");
		}

		if (!QueryParameters.COLORS.contains(color)) {
			validationMessages.add("Fill in the InTrash field with a correct value for colors.");
		}

		if (validationMessages.isEmpty())
			return true;
		else
			return false;
	}

	public int getIdu() {
		return idu;
	}

	public void setIdu(int idu) {
		this.idu = idu;
	}

	public int getIdn() {
		return idn;
	}

	public void setIdn(int idn) {
		this.idn = idn;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int propietary) {
		this.owner = propietary;
	}

	public int getArchived() {
		return archived;
	}

	public void setArchived(int stored) {
		this.archived = stored;
	}

	public int getPinned() {
		return pinned;
	}

	public void setPinned(int marked) {
		this.pinned = marked;
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
	public int compareTo(UsersNotes u) {
		if (this.getPinned() != u.getPinned()) {
			if (this.getPinned() != 0)
				return -1;
			else
				return 1;
		}
		if (this.idn > u.getIdn())
			return -1;
		else if (this.idn < u.getIdn())
			return 1;
		else
			return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + archived;
		result = prime * result + color;
		result = prime * result + idn;
		result = prime * result + idu;
		result = prime * result + intrash;
		result = prime * result + owner;
		result = prime * result + pinned;
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
		UsersNotes other = (UsersNotes) obj;
		if (idn != other.idn)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UsersNotes [idu=" + idu + ", idn=" + idn + ", owner=" + owner + ", archived=" + archived + ", pinned="
				+ pinned + ", intrash=" + intrash + ", color=" + color + "]";
	}

}
