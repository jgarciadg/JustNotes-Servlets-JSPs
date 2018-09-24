package es.justo.giiis.pi.model;

public class Notification {
	private int idu;
	private int idrequest;

	public Notification(int idu, int idrequest) {
		super();
		this.idu = idu;
		this.idrequest = idrequest;
	}

	public Notification() {
		super();
	}

	public int getIdu() {
		return idu;
	}

	public void setIdu(int idu) {
		this.idu = idu;
	}

	public int getIdrequest() {
		return idrequest;
	}

	public void setIdrequest(int idrequest) {
		this.idrequest = idrequest;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idrequest;
		result = prime * result + idu;
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
		Notification other = (Notification) obj;
		if (idrequest != other.idrequest)
			return false;
		if (idu != other.idu)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Notification [idu=" + idu + ", idrequest=" + idrequest + "]";
	}

}
